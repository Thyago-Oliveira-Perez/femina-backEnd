package br.com.femina.controllers;

import br.com.femina.dto.produto.Filters;
import br.com.femina.dto.produto.ProdutoResponse;
import br.com.femina.dto.produto.ProdutoRequest;
import br.com.femina.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = {"http://127.0.0.1:3000", "http://localhost:3000", "http://127.0.0.1:3002" ,"http://localhost:3002"})
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping("/estoque/insert")
    public ResponseEntity<?> insert(@RequestParam(name = "produto") String produto,
                                    @RequestParam(name = "image") MultipartFile[] images) {
        return this.produtoService.insert(produto, images);
    }

    @GetMapping("/{idProduto}")
    public ResponseEntity<ProdutoResponse> findById(@PathVariable("idProduto") UUID idProduto) {
        return this.produtoService.findById(idProduto);
    }

    @PostMapping("/list")
    public ResponseEntity<Page<ProdutoResponse>> findAllByFilters(@RequestBody Filters filters, Pageable pageable) {
        return ResponseEntity.ok().body(this.produtoService.findAllByFilters(filters, pageable));
    }

    @GetMapping("/estoque/list")
    public ResponseEntity<Page<ProdutoResponse>> findAllProducts(Pageable pageable) {
        return ResponseEntity.ok().body(this.produtoService.findAllProducts(pageable));
    }

    @PutMapping("/estoque/{idProduto}")
    public ResponseEntity<?> update(
            String produto,
            @PathVariable UUID idProduto,
            @RequestParam("image") Optional<MultipartFile[]> files
    ) {
        return this.produtoService.update(idProduto, produto, files);
    }

    @PutMapping("/estoque/remove-image/{idProduto}")
    public ResponseEntity<?> removeImage(
            @PathVariable("idProduto") UUID id,
            @RequestBody String nameImage
    ) {
        return produtoService.removeImage(id, nameImage);
    }

    @PutMapping("/estoque/remove-all-images/{idProduto}")
    public ResponseEntity<?> removeAllImages(@PathVariable("idProduto") UUID id) {
        return produtoService.removeAllImages(id);
    }

    @PutMapping("/estoque/disable/{idProduto}")
    public ResponseEntity<?> updateStatus(@PathVariable UUID idProduto){
        return this.produtoService.updateStatusById(idProduto);
    }

}
