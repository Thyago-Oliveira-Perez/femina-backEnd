package br.com.femina.services;

import br.com.femina.dto.Filters;
import br.com.femina.entities.Produto;
import br.com.femina.repositories.FavoritosRepository;
import br.com.femina.repositories.ProdutoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    public String saveFile(Produto produto, MultipartFile[] files) {
        createDirIfNotExist(produto);
        for(int i = 0;i < files.length;i++) {
            try {
                byte[] bytes = files[i].getBytes();
                ByteArrayInputStream inStreambj = new ByteArrayInputStream(bytes);
                BufferedImage newImage = ImageIO.read(inStreambj);
                ImageIO.write(newImage, "png", new File(path + produto.getCodigo() + "/" + i + ".png"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return path+produto.getCodigo();
    }

    public ResponseEntity<?> insert(String produtoString, MultipartFile[] files) {
        try {
            Produto produto = new ObjectMapper().readValue(produtoString, Produto.class);
            String imagePath = saveFile(produto, files);
            produto.setImagem(imagePath);
            saveProduto(produto);
            return ResponseEntity.ok().body("Produto cadastrado com sucesso!");
        } catch(Exception e) {
            return ResponseEntity.badRequest().body("Produto já cadastrado.");
        }
    }

    public ResponseEntity<Produto> findById(Long id){
        Optional<Produto> produto = this.produtoRepository.findById(id);
        return produto.isPresent() ? ResponseEntity.ok().body(produto.get()) : ResponseEntity.notFound().build();
    }

    public ResponseEntity<?> update(Long id, String produtoString, Optional<MultipartFile[]> files) {
        try {
            Produto produto = new ObjectMapper().readValue(produtoString, Produto.class);
            if(this.produtoRepository.existsById(id) && id.equals(produto.getId())) {
                if(files.isPresent()) {
                    String imagePath = saveFile(produto, files.get());
                    produto.setImagem(imagePath);
                }
                saveProduto(produto);
                return ResponseEntity.ok().body("Produto atualizado com sucesso!");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro de Processamento Da Entidade");
        }
    }

    public Page<Produto> findAllByFilters(Filters filters, Pageable pageable) {
        return this.produtoRepository.findAllByFilters(
                filters.getCategoriaIds(),
                filters.getMarcaIds(),
                filters.getCor(),
                "GG",
                pageable,
                true
        );
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

}
