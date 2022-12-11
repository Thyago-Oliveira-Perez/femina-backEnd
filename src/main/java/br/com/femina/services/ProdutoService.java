package br.com.femina.services;

import br.com.femina.dto.ImageRequest;
import br.com.femina.dto.produto.Filters;
import br.com.femina.dto.produto.ProdutoResponse;
import br.com.femina.entities.Produto;
import br.com.femina.enums.Enums;
import br.com.femina.repositories.FavoritosRepository;
import br.com.femina.repositories.ProdutoRepository;
import br.com.femina.services.Specification.ProdutoSpecification;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

    private ObjectMapper objectMapper = new ObjectMapper();

    public ProdutoService() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private String catalogPath = "../femina-webapp/public";
    private String stockPath = "../femina-stockManager/public";
    private String path = "/images/produto/";

    public String[] getFilesName(Produto produto) {
        File directory = new File(stockPath+path+produto.getCodigo());
        return directory.list();
    }

    public int getLastFile(Produto produto) {
        File directory = new File(stockPath+path+produto.getCodigo());
        String[] files = directory.list();
        int[] numberFiles = new int[files.length];
        if(files.length == 0) {
            return 0;
        }
        for(int i = 0;i < files.length;i++) {
            files[i] = files[i].substring(0, files[i].lastIndexOf('.'));
            numberFiles[i] = Integer.parseInt(files[i]);
        }
        if (numberFiles.length == 1) {
            return 1;
        }
        return Arrays.stream(numberFiles).max().getAsInt();
    }

    public void createDirIfNotExist(Produto produto) {
        Path directory = Paths.get(stockPath + path + produto.getCodigo());
        Path directoryCatalog = Paths.get(catalogPath + path + produto.getCodigo());
        if (!Files.exists(directory) || !Files.exists(directoryCatalog)) {
            try {
                Files.createDirectories(directory);
                Files.createDirectories(directoryCatalog);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void renameDir(Produto produto, String oldPath) {
        Path directory = Paths.get(stockPath + oldPath);
        Path directoryCatalog = Paths.get(catalogPath + oldPath);
        if (Files.exists(directory) && Files.exists(directoryCatalog)) {
            File oldDir = new File(stockPath + oldPath);
            File oldDirCatalog = new File(stockPath + oldPath);
            try {
                oldDir.renameTo(new File(stockPath + path + produto.getCodigo()));
                oldDirCatalog.renameTo(new File(catalogPath + path + produto.getCodigo()));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            try {
                Files.createDirectories(directory);
                Files.createDirectories(directoryCatalog);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void saveFile(Produto produto, MultipartFile[] files) {
        createDirIfNotExist(produto);
        int count = getLastFile(produto);
        for(int i = count;i < count+files.length;i++) {
            try {
                byte[] bytes = files[i-count].getBytes();
                ByteArrayInputStream inStreambj = new ByteArrayInputStream(bytes);
                BufferedImage newImage = ImageIO.read(inStreambj);
                ImageIO.write(newImage, "png", new File(stockPath + path + produto.getCodigo() + "/" + i + ".png"));
                ImageIO.write(newImage, "png", new File(catalogPath + path + produto.getCodigo() + "/" + i + ".png"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public ResponseEntity<?> insert(String produtoRequest, MultipartFile[] images) {
        try {
            Produto produto = objectMapper.readValue(produtoRequest, Produto.class);
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
            Produto produto = objectMapper.readValue(produtoString, Produto.class);
            if(this.produtoRepository.existsById(id) && id.equals(produto.getId())) {
                renameDir(produto, produto.getImagem());
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
            try {
                ImageRequest image = new ObjectMapper().readValue(imageName, ImageRequest.class);
                File fileToDelete = new File(stockPath+ path + produto.getCodigo() + "/" + image.getName());
                File fileToDeleteCatalog = new File(catalogPath + path + produto.getCodigo() + "/" + image.getName());
                fileToDelete.delete();
                fileToDeleteCatalog.delete();
                return ResponseEntity.ok().body("Imagem deletada com Sucesso");
            } catch(Exception e) {
                return ResponseEntity.internalServerError().body("Imagem não encontrada");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> removeAllImages(UUID id) {
        if(produtoRepository.existsById(id)) {
            Produto produto = produtoRepository.getById(id);
            File dir = new File(stockPath+path+produto.getCodigo());
            File dirCatalog = new File(catalogPath+path+produto.getCodigo());
            for(File file: dir.listFiles()) {
                if(!file.isDirectory()){
                    file.delete();
                }
            }
            for(File file: dirCatalog.listFiles()) {
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
            return pageDbProdutosToPageProdutoResponse(this.produtoRepository.findAllByIsActiveOrderByCadastradoAsc(true, pageable));
        }
        return pageDbProdutosToPageProdutoResponse(this.produtoRepository.findAll(produtoSpecification.getProductsFilters(filters), pageable));
    }

    public Page<ProdutoResponse> findAllProducts(Pageable pageable) {
         return pageDbProdutosToPageProdutoResponse(produtoRepository.findAllByIsActiveOrderByCadastradoAsc(true, pageable));
    }

    @Transactional
    public ResponseEntity<?> updateStatusById(UUID id) {
        if(this.produtoRepository.existsById(id)){
            String mensagem = "";
            Produto dbProduto = this.produtoRepository.getById(id);
            Boolean status = dbProduto.getIsActive();
            produtoRepository.updateStatus(id, !status);
            if(!status.equals(true)){
                mensagem = "ativado";
            }
            if(status.equals(true)){
                favoritosRepository.deleteFavoritosByProductId(id);
                mensagem = "desativado";
            }
            return ResponseEntity.ok().body("Produto " + mensagem + " com sucesso!");
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional
    protected void saveProduto(Produto produto){
        this.produtoRepository.save(produto);
    }

    //<editor-fold desc="Helpers">
    private Page<ProdutoResponse> pageDbProdutosToPageProdutoResponse(Page<Produto> dbProdutos) {
        List<ProdutoResponse> produtoResponseList = new ArrayList<>();
        dbProdutos.map(dbProduto -> produtoResponseList.add(new ProdutoResponse(
                dbProduto.getId(),
                dbProduto.getIsActive(),
                dbProduto.getCadastrado(),
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
                dbProduto.getIsActive(),
                dbProduto.getCadastrado(),
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
