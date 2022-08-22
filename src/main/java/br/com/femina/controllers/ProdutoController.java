package br.com.femina.controllers;

import br.com.femina.entities.Produto;
import br.com.femina.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/{idProduto}")
    public ResponseEntity<Produto> findById(@PathVariable("idProduto") Long idProduto) {
        if(this.produtoService.findById(idProduto).isPresent()){
            return ResponseEntity.ok().body(this.produtoService.findById(idProduto).get());
        }else{
            return ResponseEntity.badRequest().body(new Produto());
        }
    }

    @GetMapping
    public ResponseEntity<Page<Produto>> findAll(Pageable pageable) {
        return ResponseEntity.ok().body(this.produtoService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Produto produto) {
        try{
            this.produtoService.insert(produto);
            return ResponseEntity.ok().body("Produto cadastrado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{idProduto}")
    public ResponseEntity<?> update(@RequestBody Produto produto,
                                    @PathVariable Long idProduto)
    {
        try{
            this.produtoService.update(idProduto,produto);
            return ResponseEntity.ok().body("Produto atualizada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/disable/{idProduto}")
    public ResponseEntity<?> updateStatus(@PathVariable Long idProduto)
    {
        try{
            this.produtoService.updateStatus(idProduto);
            return ResponseEntity.ok().body("Produto atualizada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Produto não existe no banco.");
        }
    }

}
