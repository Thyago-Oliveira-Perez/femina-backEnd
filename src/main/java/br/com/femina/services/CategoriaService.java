package br.com.femina.services;

import br.com.femina.entities.Categorias;
import br.com.femina.repositories.CategoriaRepository;
import br.com.femina.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private ProdutoRepository produtoRepository;

    public ResponseEntity<?> insert(Categorias categoria) {
        try{
            saveCategoria(categoria);
            return ResponseEntity.ok().body("Categoria cadastrada com sucesso!");
        }catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Categoria já cadastrada.");
        }
    }

    public ResponseEntity<Categorias> findById(Long id){
        Optional<Categorias> categoria = this.categoriaRepository.findById(id);
        return categoria.isPresent() ? ResponseEntity.ok().body(categoria.get()) : ResponseEntity.notFound().build();
    }

    public Page<Categorias> findAll(Pageable pageable){
        return this.categoriaRepository.findAll(pageable);
    }

    public ResponseEntity<?> updateStatusById(Long id){
        String mensagem = "";
        if(this.categoriaRepository.existsById(id)){
            this.produtoRepository.updateCategoriaByIdCategoria(id);
            Boolean status = this.categoriaRepository.getById(id).getIsActive();
            updateStatus(id, !status);
            if(!status.equals(true)){
                mensagem = "ativada";
            }
            mensagem = "desativada";
            return ResponseEntity.ok().body("Categoria " + mensagem + " com sucesso!");
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional
    protected void saveCategoria(Categorias categoria){
        this.categoriaRepository.save(categoria);
    }

    @Transactional
    protected void updateStatus(Long id, Boolean status){
        this.categoriaRepository.updateStatus(id, status);
    }
}
