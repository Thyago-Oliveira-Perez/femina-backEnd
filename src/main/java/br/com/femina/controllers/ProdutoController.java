package br.com.femina.controllers;

import br.com.femina.dto.produto.Filters;
import br.com.femina.dto.produto.ProdutoResponse;
import br.com.femina.services.ProdutoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:3000")
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping("/estoque/insert")
    public ResponseEntity<?> insert(String produto, @RequestParam("image") MultipartFile[] files) throws JsonProcessingException {
        return this.produtoService.insert(produto, files);
    }

    @GetMapping("/{idProduto}")
    public ResponseEntity<ProdutoResponse> findById(@PathVariable("idProduto") Long idProduto) {
        return this.produtoService.findById(idProduto);
    }

    @PostMapping("/list")
    public ResponseEntity<Page<ProdutoResponse>> findAllByFilters(@RequestBody Filters filters, Pageable pageable) {
        return ResponseEntity.ok().body(this.produtoService.findAllByFilters(filters, pageable));
    }

    @PutMapping("/estoque/{idProduto}")
    public ResponseEntity<?> update(
            String produto,
            @PathVariable Long idProduto,
            @RequestParam("image") Optional<MultipartFile[]> files
    ) {
        return this.produtoService.update(idProduto, produto, files);
    }

    @PutMapping("/estoque/remove/{idProduto}")
    public ResponseEntity<?> removeImage(
            @PathVariable("idProduto") Long id,
            @RequestBody String nameImage
    ) {
        return produtoService.removeImage(id, nameImage);
    }

    @PutMapping("/estoque/remove-all/{idProduto}")
    public ResponseEntity<?> removeAllImages(@PathVariable("idProduto") Long id) {
        return produtoService.removeAllImages(id);
    }

    @PutMapping("/estoque/disable/{idProduto}")
    public ResponseEntity<?> updateStatus(@PathVariable Long idProduto){
        return this.produtoService.updateStatusById(idProduto);
    }

}
