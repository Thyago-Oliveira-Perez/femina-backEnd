package br.com.femina.controllers;

import br.com.femina.dto.Filters;
import br.com.femina.entities.Produto;
import br.com.femina.services.ProdutoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/{idProduto}")
    public ResponseEntity<Produto> findById(@PathVariable("idProduto") Long idProduto) {
        return this.produtoService.findById(idProduto);
    }

    @PostMapping("/list")
    public ResponseEntity<Page<Produto>> findAllByFilters(@RequestBody Filters filters, Pageable pageable) {
        return ResponseEntity.ok().body(this.produtoService.findAllByFilters(filters, pageable));
    }

    @PostMapping
    public ResponseEntity<?> insert(String produto, @RequestParam("image") MultipartFile[] files) {
        return this.produtoService.insert(produto, files);
    }

    @PutMapping("/{idProduto}")
    public ResponseEntity<?> update(
            @PathVariable("idProduto") Long idProduto,
            String produto,
            @RequestParam("image") Optional<MultipartFile[]> files
    ) {
        return this.produtoService.update(idProduto,produto,files);
    }

    @PutMapping("/disable/{idProduto}")
    public ResponseEntity<?> updateStatus(@PathVariable Long idProduto){
        return this.produtoService.updateStatusById(idProduto);
    }


}
