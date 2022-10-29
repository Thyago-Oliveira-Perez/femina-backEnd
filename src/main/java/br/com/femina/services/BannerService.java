package br.com.femina.services;

import br.com.femina.entities.Banners;
import br.com.femina.entities.Produto;
import br.com.femina.repositories.BannerRepository;
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
import java.util.Objects;

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

    public String saveFile(Banners banners, MultipartFile[] files){
        createDirIfNotExist(banners);
        for(int i = 0;i < files.length;i++) {
            try {
                byte[] bytes = files[i].getBytes();
                ByteArrayInputStream inStreambj = new ByteArrayInputStream(bytes);
                BufferedImage newImage = ImageIO.read(inStreambj);
                String extension = files[i].getOriginalFilename().split("\\.")[1];
                System.out.println(extension);
                if (Objects.equals(extension, "png")) {
                    ImageIO.write(newImage, "png", new File(path + banners.getTipo() + "/" + i + ".png"));
                } else {
                    ImageIO.write(newImage, "jpg", new File(path + banners.getTipo() + "/" + i + ".jpg"));
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return path+banners.getTipo();
    }

    public ResponseEntity<?> insert(Banners banners, MultipartFile[] files) {
        String imagePath = saveFile(banners, files);
        banners.setImagens(imagePath);
        try {
            saveBanners(banners);
            return ResponseEntity.ok().body("");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Categoria já cadastrada.");
        }
    }

    @Transactional
    protected void saveBanners(Banners banners){
        this.bannerRepository.save(banners);
    }
}
