package br.com.femina.services;

import br.com.femina.dto.BannerResponse;
import br.com.femina.entities.Banners;
import br.com.femina.enums.TipoDeBanner;
import br.com.femina.repositories.BannerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    private String path = "./images/banners/";

    private void createDirIfNotExist(Banners banners) {
        Path directory = Paths.get(path+banners.getTipo());
        if (!Files.exists(directory)) {
            try {
                Files.createDirectories(directory);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public String saveFile(Banners banners, MultipartFile[] files) {
        createDirIfNotExist(banners);
        for(int i = 0;i < files.length;i++) {
            try {
                byte[] bytes = files[i].getBytes();
                ByteArrayInputStream inStreambj = new ByteArrayInputStream(bytes);
                BufferedImage newImage = ImageIO.read(inStreambj);
                ImageIO.write(newImage, "png", new File(path + banners.getTipo() + "/" + i + ".png"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return path+banners.getTipo();
    }

    public ResponseEntity<?> insert(String bannerString, MultipartFile[] files) {
        try {
            Banners banners = new ObjectMapper().readValue(bannerString, Banners.class);
            String imagePath = saveFile(banners, files);
            banners.setImagens(imagePath);
            saveBanners(banners);
            return ResponseEntity.ok().body("Banner cadastrado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Banner já cadastrado.");
        }
    }

    public ResponseEntity<Banners> findByType(TipoDeBanner tipoDeBanner) {
        Optional<Banners> banners = bannerRepository.findByTipo(tipoDeBanner);
        return banners.isPresent() ? ResponseEntity.ok().body(banners.get()) : ResponseEntity.notFound().build();
    }

    public ResponseEntity<?> updateBanner(Long id, String bannerString, Optional<MultipartFile[]> files) {
        try {
            Banners banners = new ObjectMapper().readValue(bannerString, Banners.class);
            if(bannerRepository.existsById(id) && id.equals(banners.getId())) {
                if(files.isPresent()) {
                    String imagePath = saveFile(banners, files.get());
                    banners.setImagens(imagePath);
                }
                Banners updatedBanner = updateBanners(banners);
                BannerResponse bannerResponse = new BannerResponse(
                        updatedBanner.getName(),
                        updatedBanner.getImagens(),
                        updatedBanner.getCountImagens(),
                        updatedBanner.getTipo(),
                        updatedBanner.getUsuario().getNome(),
                        updatedBanner.getUsuario().getId());
                return ResponseEntity.ok().body(bannerResponse);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro de Processamento Da Entidade");
        }
    }

    public ResponseEntity<?> removeImage(Long id, String imageName) {
        if(bannerRepository.existsById(id)) {
            Banners banners = bannerRepository.getById(id);
            File fileToDelete = new File(path + banners.getTipo() + "/" + imageName);
            if(fileToDelete.delete()) {
                return ResponseEntity.ok().body(banners);
            } else {
                return ResponseEntity.internalServerError().body("Imagem não encontrada");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> removeAllImages(Long id) {
        if(bannerRepository.existsById(id)) {
            Banners banners = bannerRepository.getById(id);
            for(int i = 0;i < banners.getCountImagens();i++) {
                File fileToDelete = new File(path + banners.getTipo() + "/" + i + ".png");
                fileToDelete.delete();
            }
            return ResponseEntity.ok().body("Imagens Deletadas com Sucesso.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional
    protected void saveBanners(Banners banners) {
        this.bannerRepository.save(banners);
    }

    @Transactional
    protected Banners updateBanners(Banners banners) {
        return this.bannerRepository.save(banners);
    }

}
