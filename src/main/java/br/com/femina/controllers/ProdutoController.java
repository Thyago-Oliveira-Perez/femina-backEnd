package br.com.femina.controllers;
import br.com.femina.entities.Cliente;
import br.com.femina.entities.Produto;
import br.com.femina.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<Page<Produto>> listAll(Pageable pageable) {
        return ResponseEntity.ok().body(this.produtoService.findAll(pageable));
    }

    @GetMapping("/{idProduto}")
    public ResponseEntity<Produto> findById(@PathVariable("idProduto") Long idProduto){
        return ResponseEntity.ok().body(this.produtoService.findById(idProduto).get());
    }

    @PostMapping
    public ResponseEntity<?> insertProduto(@RequestBody Produto produto) {
        try{
            this.produtoService.insert(produto);
            return ResponseEntity.ok().body("Produto cadastrado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{idProduto}")
    public ResponseEntity<?> update(@RequestBody Produto produto, @PathVariable Long idProduto ){
        try{
            this.produtoService.update(idProduto,produto);
            return ResponseEntity.ok().body("Produto atualizada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/status/{idProduto}")
    public ResponseEntity<?> delete(@RequestBody Produto produto, @PathVariable Long idProduto ){
        try{
            this.produtoService.delete(idProduto, produto);
            return ResponseEntity.ok().body("Produto atualizada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
