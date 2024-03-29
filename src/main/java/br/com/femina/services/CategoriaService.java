package br.com.femina.services;

import br.com.femina.dto.categoria.CategoriaResponse;
import br.com.femina.dto.produto.ProdutoResponse;
import br.com.femina.entities.Categorias;
import br.com.femina.repositories.CategoriaRepository;
import br.com.femina.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public ResponseEntity<CategoriaResponse> findById(UUID id){
        Optional<Categorias> categoria = this.categoriaRepository.findById(id);
        return categoria.isPresent() ?
                ResponseEntity.ok().body(this.dbCategoriaToCategoriaResponse(categoria.get())) :
                ResponseEntity.notFound().build();
    }

    public Page<CategoriaResponse> findAll(Pageable pageable){
        return this.categoriaRepository.findAllCategoriaResponse(pageable, true);
    }

    @Transactional
    public ResponseEntity<?> updateStatusById(UUID id){
        String mensagem = "";
        if(this.categoriaRepository.existsById(id)){
            Boolean status = this.categoriaRepository.getById(id).getIsActive();
            categoriaRepository.updateStatus(id, !status);
            if(!status.equals(true)){
                mensagem = "ativada";
            }
            if(status.equals(true)){
                produtoRepository.updateCategoriaByIdCategoria(id);
                mensagem = "desativada";
            }
            return ResponseEntity.ok().body("Categoria " + mensagem + " com sucesso!");
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional
    protected void saveCategoria(Categorias categoria){
        this.categoriaRepository.save(categoria);
    }

    //<editor-fold desc="Helpers">
    private CategoriaResponse dbCategoriaToCategoriaResponse(Categorias dbCategoria){
        return new CategoriaResponse(
                dbCategoria.getId(),
                dbCategoria.getNome()
        );
    }

    private Page<CategoriaResponse> pageDbCategoriaToPageCategoriaResponse(Page<Categorias> pageDbCategoria){
        List<CategoriaResponse> categoriasResponseList = new ArrayList<>();
        pageDbCategoria.map(dbCategoria -> categoriasResponseList.add(new CategoriaResponse(
                dbCategoria.getId(),
                dbCategoria.getNome()
        )));

        Page<CategoriaResponse> pageCategoriasResponse = new PageImpl<CategoriaResponse>(categoriasResponseList);
        return pageCategoriasResponse;
    }
    //</editor-fold>
}
