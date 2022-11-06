package br.com.femina.controllers;

import br.com.femina.dto.BannerResponse;
import br.com.femina.entities.Banners;
import br.com.femina.enums.TipoDeBanner;
import br.com.femina.services.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/api/banners")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @GetMapping("/{typeBanner}")
    public ResponseEntity<BannerResponse> findByType(@PathVariable("typeBanner") TipoDeBanner tipoDeBanner){
        return bannerService.findByType(tipoDeBanner);
    }

    @PostMapping
    public ResponseEntity<?> insert(String bannerString, @RequestParam("images") MultipartFile[] files) {
        return bannerService.insert(bannerString, files);
    }

    @PutMapping("/{idBanner}")
    public ResponseEntity<?> updateBanner(
            @PathVariable("idBanner") Long id,
            String bannerString,
            @RequestParam("images") Optional<MultipartFile[]> files
    ) {
        return bannerService.updateBanner(id, bannerString, files);
    }

    @PutMapping("/remove/{idBanner}")
    public ResponseEntity<?> removeImage(
            @PathVariable("idBanner") Long id,
            @RequestBody String nameImage
    ) {
        return bannerService.removeImage(id, nameImage);
    }

    @PutMapping("/remove-all/{idBanner}")
    public ResponseEntity<?> removeAllImages(
        @PathVariable("idBanner") Long id
    ) {
       return bannerService.removeAllImages(id);
    }
}
