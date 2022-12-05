package br.com.femina.controllers;

import br.com.femina.dto.BannerResponse;
import br.com.femina.enums.Enums;
import br.com.femina.services.BannerService;
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
@RequestMapping("/api/banners")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @GetMapping("/list")
    public ResponseEntity<Page<BannerResponse>> findAll(Pageable pageable) {
        return bannerService.findAll(pageable);
    }

    @GetMapping("/{typeBanner}")
    public ResponseEntity<BannerResponse> findByType(@PathVariable("typeBanner") Enums.TipoDeBanner tipoDeBanner) {
        return bannerService.findByType(tipoDeBanner);
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestParam(name = "banner") String bannerString,
                                    @RequestParam(name = "images") MultipartFile[] files) {
        return bannerService.insert(bannerString, files);
    }

    @PutMapping("/{idBanner}")
    public ResponseEntity<?> updateBanner(
            @PathVariable("idBanner") UUID id,
            String bannerString,
            @RequestParam("images") Optional<MultipartFile[]> files
    ) {
        return bannerService.updateBanner(id, bannerString, files);
    }

    @PutMapping("/remove/{idBanner}")
    public ResponseEntity<?> removeImage(
            @PathVariable("idBanner") UUID id,
            @RequestBody String nameImage
    ) {
        return bannerService.removeImage(id, nameImage);
    }

    @PutMapping("/remove-all/{idBanner}")
    public ResponseEntity<?> removeAllImages(
        @PathVariable("idBanner") UUID id
    ) {
       return bannerService.removeAllImages(id);
    }
}
