package br.com.femina.controllers;

import br.com.femina.dto.Filters;
import br.com.femina.entities.Produto;
import br.com.femina.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/{idProduto}")
    public ResponseEntity<Produto> findById(@PathVariable("idProduto") Long idProduto) {

        Optional<Produto> produto = this.produtoService.findById(idProduto);

        return produto.isPresent() ? ResponseEntity.ok().body(produto.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping("/list")
    public ResponseEntity<Page<Produto>> findAllByFilters(@RequestBody Filters filters, Pageable pageable) {
        return ResponseEntity.ok().body(this.produtoService.findAllByFilters(filters, pageable));
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Produto produto) {
        return this.produtoService.insert(produto) ? ResponseEntity.ok().body("Produto cadastrado com sucesso")
                : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{idProduto}")
    public ResponseEntity<?> update(@RequestBody Produto produto, @PathVariable Long idProduto) {
        return this.produtoService.update(idProduto,produto) ? ResponseEntity.ok().body("Produto atualizada com sucesso")
                : ResponseEntity.notFound().build();
    }

    @PutMapping("/disable/{idProduto}")
    public ResponseEntity<?> updateStatus(@PathVariable Long idProduto){
        return this.produtoService.updateStatus(idProduto) ? ResponseEntity.ok().body("Produto atualizada com sucesso")
                : ResponseEntity.notFound().build();
    }

}
