package br.com.femina.services;

import br.com.femina.dto.Filters;
import br.com.femina.dto.Produto.ProdutoResponse;
import br.com.femina.entities.Produto;
import br.com.femina.repositories.FavoritosRepository;
import br.com.femina.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FavoritosRepository favoritosRepository;

    private String path = "./images/produto/";

    private void createDirIfNotExist(Produto produto) {
        Path directory = Paths.get(path+produto.getCodigo());
        if (!Files.exists(directory)) {
            try {
                Files.createDirectories(directory);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public String saveFile(Produto produto, MultipartFile[] files){
        createDirIfNotExist(produto);
        for(int i = 0;i < files.length;i++) {
            try {
                byte[] bytes = files[i].getBytes();
                ByteArrayInputStream inStreambj = new ByteArrayInputStream(bytes);
                BufferedImage newImage = ImageIO.read(inStreambj);
                String extension = files[i].getOriginalFilename().split("\\.")[1];
                System.out.println(extension);
                if (Objects.equals(extension, "png")) {
                    ImageIO.write(newImage, "png", new File(path + produto.getCodigo() + "/" + i + ".png"));
                } else {
                    ImageIO.write(newImage, "jpg", new File(path + produto.getCodigo() + "/" + i + ".jpg"));
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return path+produto.getCodigo();
    }

    public ResponseEntity<?> insert(Produto produto, MultipartFile[] files){
        String imagePath = saveFile(produto, files);
        produto.setImagem(imagePath);
        try{
            saveProduto(produto);
            return ResponseEntity.ok().body("Produto cadastrado com sucesso!");
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Produto já cadastrado.");
        }
    }

    public ResponseEntity<ProdutoResponse>  findById(Long id){
        Optional<Produto> produto = this.produtoRepository.findById(id);
        return produto.isPresent() ?
                ResponseEntity.ok().body(this.dbProdutoToProdutoResponse(produto.get())) :
                ResponseEntity.notFound().build();
    }

    public ResponseEntity<ProdutoResponse> update(Long id, Produto produto) {
        if(this.produtoRepository.existsById(id) && id.equals(produto.getId())){
            saveProduto(produto);
            return ResponseEntity.ok().body(this.dbProdutoToProdutoResponse(produto));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    public Page<ProdutoResponse> findAllByFilters(Filters filters, Pageable pageable){
        Page<Produto> dbProdutosList = this.produtoRepository.findAllByFilters(
                filters.getCategoriaIds(),
                filters.getMarcaIds(),
                filters.getCor(),
                filters.getTamanho().toString(),
                pageable,
                true
        );
        return this.pageDbProdutosToPageProdutoResponse(dbProdutosList);
    }

    public ResponseEntity<?> updateStatusById(Long id) {
        if(this.produtoRepository.existsById(id)){
            String mensagem = "";
            Boolean status = this.produtoRepository.getById(id).getIsActive();
            changeStatus(id, !status);
            if(!status.equals(true)){
                mensagem = "ativado";
            }
            mensagem = "desativado";
            return ResponseEntity.ok().body("Produto " + mensagem + " com sucesso!");
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional
    protected void changeStatus(Long id, Boolean status){
        this.produtoRepository.updateStatus(id, status);
    }

    @Transactional
    protected void saveProduto(Produto produto){
        this.produtoRepository.save(produto);
    }

    @Transactional
    protected void deleteFavoritosRelatedToProduct(Long id){
        this.favoritosRepository.deleteFavoritosByProductId(id);
    }

    //<editor-fold desc="Helpers">
    private Page<ProdutoResponse> pageDbProdutosToPageProdutoResponse(Page<Produto> dbProdutos){
        List<ProdutoResponse> produtoResponseList = new ArrayList<>();
        dbProdutos.map(dbProduto -> produtoResponseList.add(new ProdutoResponse(
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
                dbProduto.getDestaque()
        )));

        Page<ProdutoResponse> pageProdutoResponse = new PageImpl<ProdutoResponse>(produtoResponseList);
        return pageProdutoResponse;
    }

    public ProdutoResponse dbProdutoToProdutoResponse(Produto dbProduto){
        return new ProdutoResponse(
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
                dbProduto.getDestaque()
        );
    }
    //</editor-fold>
}
