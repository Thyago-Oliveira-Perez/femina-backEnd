package br.com.femina.controllers;

import br.com.femina.dto.produto.Filters;
import br.com.femina.dto.produto.ProdutoResponse;
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

@RestController
@CrossOrigin(origins = "http://127.0.0.1:3000")
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<?> insert(String produto, @RequestParam("image") MultipartFile[] files) throws JsonProcessingException {
        Produto produtoJson = new ObjectMapper().readValue(produto, Produto.class);
        return this.produtoService.insert(produtoJson, files);
    }

    @GetMapping("/{idProduto}")
    public ResponseEntity<ProdutoResponse> findById(@PathVariable("idProduto") Long idProduto) {
        return this.produtoService.findById(idProduto);
    }

    @PostMapping("/list")
    public ResponseEntity<Page<ProdutoResponse>> findAllByFilters(@RequestBody Filters filters, Pageable pageable) {
        return ResponseEntity.ok().body(this.produtoService.findAllByFilters(filters, pageable));
    }

    @PutMapping("/{idProduto}")
    public ResponseEntity<ProdutoResponse> update(@RequestBody Produto produto, @PathVariable Long idProduto) {
        return this.produtoService.update(idProduto, produto);
    }

    @PutMapping("/disable/{idProduto}")
    public ResponseEntity<?> updateStatus(@PathVariable Long idProduto){
        return this.produtoService.updateStatusById(idProduto);
    }

}
