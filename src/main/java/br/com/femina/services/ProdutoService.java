package br.com.femina.services;

import br.com.femina.dto.produto.Filters;
import br.com.femina.dto.produto.ProdutoRequest;
import br.com.femina.dto.produto.ProdutoResponse;
import br.com.femina.entities.Modelo;
import br.com.femina.entities.Produto;
import br.com.femina.enums.Enums;
import br.com.femina.repositories.FavoritosRepository;
import br.com.femina.repositories.ProdutoRepository;
import br.com.femina.services.Specification.ProdutoSpecification;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.oauth2.sdk.util.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FavoritosRepository favoritosRepository;

    @Autowired
    private ProdutoSpecification produtoSpecification;

    private String path = "./images/produto/";

    public String[] getFilesName(Produto produto) {
        File directory = new File(path+produto.getCodigo());
        return directory.list();
    }

    public int getLastFile(Produto produto) {
        File directory = new File(path+produto.getCodigo());
        String[] files = directory.list();
        int[] numberFiles = new int[files.length];
        if(files.length == 0) {
            return 0;
        }
        for(int i = 0;i < files.length;i++) {
            files[i] = files[i].substring(0, files[i].lastIndexOf('.'));
            numberFiles[i] = Integer.parseInt(files[i]);
        }
        return Arrays.stream(numberFiles).max().getAsInt();
    }

    public void createDirIfNotExist(Produto produto) {
        Path directory = Paths.get(path+produto.getCodigo());
        if (!Files.exists(directory)) {
            try {
                Files.createDirectories(directory);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void saveFile(Produto produto, MultipartFile[] files) {
        createDirIfNotExist(produto);
        int count = getLastFile(produto);
        if (count != 0){
            count += 1;
        }
        for(int i = count;i < count+files.length;i++) {
            try {
                byte[] bytes = files[i-count].getBytes();
                ByteArrayInputStream inStreambj = new ByteArrayInputStream(bytes);
                BufferedImage newImage = ImageIO.read(inStreambj);
                ImageIO.write(newImage, "png", new File(path + produto.getCodigo() + "/" + i + ".png"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public ResponseEntity<?> insert(String produtoRequest, MultipartFile[] images) {
        try {
            //TODO rever Mapper por que esta quebrando -> descobrir o motivo
            Produto produto = new ObjectMapper().readValue(produtoRequest, Produto.class);
            produto.setImagem(path+produto.getCodigo());
            saveProduto(produto);
            saveFile(produto, images);
            return ResponseEntity.ok().body("Produto cadastrado com sucesso!");
        } catch(Exception e) {
            return ResponseEntity.badRequest().body("Produto já cadastrado.");
        }
    }

    public ResponseEntity<ProdutoResponse> findById(UUID id){
        Optional<Produto> produto = this.produtoRepository.findById(id);
        return produto.isPresent() ?
                ResponseEntity.ok().body(this.dbProdutoToProdutoResponse(produto.get(), getFilesName(produto.get()))) :
                ResponseEntity.notFound().build();
    }

    public ResponseEntity<?> update(UUID id, String produtoString, Optional<MultipartFile[]> files) {
        try {
            Produto produto = new ObjectMapper().readValue(produtoString, Produto.class);
            if(this.produtoRepository.existsById(id) && id.equals(produto.getId())) {
                produto.setImagem(path+produto.getCodigo());
                saveProduto(produto);
                files.ifPresent(multipartFiles -> saveFile(produto, multipartFiles));
                return ResponseEntity.ok().body(this.dbProdutoToProdutoResponse(produto, getFilesName(produto)));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    public ResponseEntity<?> removeImage(UUID id, String imageName) {
        if(produtoRepository.existsById(id)) {
            Produto produto = produtoRepository.getById(id);
            File fileToDelete = new File(path + produto.getCodigo() + "/" + imageName);
            if(fileToDelete.delete()) {
                return ResponseEntity.ok().body("Imagem deletada com Sucesso");
            } else {
                return ResponseEntity.internalServerError().body("Imagem não encontrada");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> removeAllImages(UUID id) {
        if(produtoRepository.existsById(id)) {
            Produto produto = produtoRepository.getById(id);
            File dir = new File(path+produto.getCodigo());
            for(File file: dir.listFiles()) {
                if(!file.isDirectory()){
                    file.delete();
                }
            }
            return ResponseEntity.ok().body("Imagens Deletadas com Sucesso.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public Page<ProdutoResponse> findAllByFilters(Filters filters, Pageable pageable){

        if(filters.getCategoriaIds().size() == 0 &&
           filters.getMarcaIds().size() == 0 &&
           filters.getCor().equals("") &&
           filters.getTamanho().equals(Enums.Tamanhos.ALL)){
            return pageDbProdutosToPageProdutoResponse(this.produtoRepository.findAllByIsActive(true, pageable));
        }
        return pageDbProdutosToPageProdutoResponse(this.produtoRepository.findAll(produtoSpecification.getProductsFilters(filters), pageable));
    }

    public Page<ProdutoResponse> findAllProducts(Pageable pageable) {
         return pageDbProdutosToPageProdutoResponse(produtoRepository.findAll(pageable));
    }

    public ResponseEntity<?> updateStatusById(UUID id) {
        if(this.produtoRepository.existsById(id)){
            String mensagem = "";
            Produto dbProduto = this.produtoRepository.getById(id);
            Boolean status = dbProduto.getIsActive();
            changeStatus(id, !status);
            if(!status.equals(true)){
                mensagem = "ativado";
            }
            if(status.equals(true)){
                this.deleteFavoritosRelatedToProduct(dbProduto.getId());
                mensagem = "desativado";
            }
            return ResponseEntity.ok().body("Produto " + mensagem + " com sucesso!");
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional
    protected void changeStatus(UUID id, Boolean status){
        this.produtoRepository.updateStatus(id, status);
    }

    @Transactional
    protected void saveProduto(Produto produto){
        this.produtoRepository.save(produto);
    }

    @Transactional
    protected void deleteFavoritosRelatedToProduct(UUID id){
        this.favoritosRepository.deleteFavoritosByProductId(id);
    }

    //<editor-fold desc="Helpers">
    private Page<ProdutoResponse> pageDbProdutosToPageProdutoResponse(Page<Produto> dbProdutos) {
        List<ProdutoResponse> produtoResponseList = new ArrayList<>();
        dbProdutos.map(dbProduto -> produtoResponseList.add(new ProdutoResponse(
                dbProduto.getId(),
                dbProduto.getNome(),
                dbProduto.getCodigo(),
                dbProduto.getValor(),
                dbProduto.getMarca(),
                dbProduto.getCategoria(),
                dbProduto.getModelo(),
                dbProduto.getFornecedor(),
                dbProduto.getTamanho(),
                dbProduto.getCor(),
                dbProduto.getDescricao(),
                dbProduto.getImagem(),
                dbProduto.getDestaque(),
                getFilesName(dbProduto)
        )));
        Pageable pageable = PageRequest.of(dbProdutos.getPageable().getPageNumber(), dbProdutos.getSize());
        Page<ProdutoResponse> pageProdutoResponse = new PageImpl<ProdutoResponse>(produtoResponseList, pageable, dbProdutos.getTotalElements());
        return pageProdutoResponse;
    }
    private ProdutoResponse dbProdutoToProdutoResponse(Produto dbProduto, String[] imageNames){
        return new ProdutoResponse(
                dbProduto.getId(),
                dbProduto.getNome(),
                dbProduto.getCodigo(),
                dbProduto.getValor(),
                dbProduto.getMarca(),
                dbProduto.getCategoria(),
                dbProduto.getModelo(),
                dbProduto.getFornecedor(),
                dbProduto.getTamanho(),
                dbProduto.getCor(),
                dbProduto.getDescricao(),
                dbProduto.getImagem(),
                dbProduto.getDestaque(),
                imageNames
        );
    }
    //</editor-fold>
}
