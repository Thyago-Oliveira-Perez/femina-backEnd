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

    public ResponseEntity<CategoriaResponse> findById(Long id){
        Optional<Categorias> categoria = this.categoriaRepository.findById(id);
        return categoria.isPresent() ?
                ResponseEntity.ok().body(this.dbCategoriaToCategoriaResponse(categoria.get())) :
                ResponseEntity.notFound().build();
    }

    public Page<CategoriaResponse> findAll(Pageable pageable){
        return this.categoriaRepository.findAllCategoriaResponse(pageable);
    }

    public ResponseEntity<?> updateStatusById(Long id){
        String mensagem = "";
        if(this.categoriaRepository.existsById(id)){
            Boolean status = this.categoriaRepository.getById(id).getIsActive();
            updateStatus(id, !status);
            if(!status.equals(true)){
                mensagem = "ativada";
            }
            if(status.equals(true)){
                this.removeCategoriasDosProdutosRelacionados(id);
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

    @Transactional
    protected void updateStatus(Long id, Boolean status){
        this.categoriaRepository.updateStatus(id, status);
    }

    @Transactional
    protected void removeCategoriasDosProdutosRelacionados(Long id){
        this.produtoRepository.updateCategoriaByIdCategoria(id);
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
