package br.com.femina.services;

import br.com.femina.configurations.security.services.TokenService;
import br.com.femina.dto.BannerResponse;
import br.com.femina.entities.Banners;
import br.com.femina.entities.Usuario;
import br.com.femina.enums.Enums;
import br.com.femina.repositories.BannerRepository;
import br.com.femina.repositories.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    private String path = "./images/banners/";

    public String[] getFilesName(Banners banners) {
        File directory = new File(path+banners.getTipo());
        return directory.list();
    }

    public int getLastFile(Banners banners) {
        File directory = new File(path+banners.getTipo());
        String[] files = directory.list();
        int[] numberFiles = new int[files.length];
        for(int i = 0;i < files.length;i++) {
            files[i] = files[i].substring(0, files[i].lastIndexOf('.'));
            numberFiles[i] = Integer.parseInt(files[i]);
        }
        if(files.length == 0) {
            return 0;
        }
        return Arrays.stream(numberFiles).max().getAsInt();
    }

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

    public void saveFile(Banners banners, MultipartFile[] files) {
        createDirIfNotExist(banners);
        int count = getLastFile(banners);
        if (count != 0){
            count += 1;
        }
        for(int i = count;i < count+files.length;i++) {
            try {
                byte[] bytes = files[i-count].getBytes();
                ByteArrayInputStream inStreambj = new ByteArrayInputStream(bytes);
                BufferedImage newImage = ImageIO.read(inStreambj);
                ImageIO.write(newImage, "png", new File(path + banners.getTipo() + "/" + i + ".png"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public ResponseEntity<?> insert(String bannerString, MultipartFile[] files, HttpHeaders headers) {
        try {
            Banners banners = new ObjectMapper().readValue(bannerString, Banners.class);
            String token = headers.getFirst(HttpHeaders.AUTHORIZATION);
            UUID idUser = this.tokenService.getUserId(token.substring(7, token.length()));
            Usuario usuario = usuarioRepository.getById(idUser);
            banners.setUsuario(usuario);
            banners.setImagens(path+banners.getTipo());
            saveBanners(banners);
            saveFile(banners, files);
            return ResponseEntity.ok().body("Banner cadastrado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Banner já cadastrado.");
        }
    }

    public ResponseEntity<Page<BannerResponse>> findAll(Pageable pageable) {
        try {
            return ResponseEntity.ok(pageDbBannersToPageBannerResponse(bannerRepository.findAll(pageable)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<BannerResponse> findByType(Enums.TipoDeBanner tipoDeBanner) {
        Optional<Banners> banners = bannerRepository.findByTipo(tipoDeBanner);
        return banners.isPresent()
                ? ResponseEntity.ok().body(this.dbBannerToBannerResponse(banners.get(), this.getFilesName(banners.get())))
                : ResponseEntity.notFound().build();
    }

    public ResponseEntity<BannerResponse> findById(UUID idBanner) {
        Optional<Banners> banners = bannerRepository.findById(idBanner);
        return banners.isPresent()
                ? ResponseEntity.ok().body(this.dbBannerToBannerResponse(banners.get(), this.getFilesName(banners.get())))
                : ResponseEntity.notFound().build();
    }

    public ResponseEntity<?> updateBanner(UUID id, String bannerString, Optional<MultipartFile[]> files) {
        try {
            Banners banners = new ObjectMapper().readValue(bannerString, Banners.class);
            if(bannerRepository.existsById(id) && id.equals(banners.getId())) {
                banners.setImagens(path+banners.getTipo());
                Banners updatedBanner = updateBanners(banners);
                files.ifPresent(multipartFiles -> saveFile(banners, multipartFiles));
                return ResponseEntity.ok().body(this.dbBannerToBannerResponse(updatedBanner, getFilesName(updatedBanner)));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro de Processamento Da Entidade");
        }
    }

    public ResponseEntity<?> removeImage(UUID id, String imageName) {
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

    public ResponseEntity<?> removeAllImages(UUID id) {
        if(bannerRepository.existsById(id)) {
            Banners banners = bannerRepository.getById(id);
            File dir = new File(path+banners.getTipo());
            for(File file: dir.listFiles()) {
                if(!file.isDirectory()){
                    file.delete();
                }
            }
            return ResponseEntity.ok().body("Imagens Deletadas com Sucesso.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private Page<BannerResponse> pageDbBannersToPageBannerResponse(Page<Banners> dbBanners) {
        List<BannerResponse> bannerResponseList = new ArrayList<>();
        dbBanners.map(dbBanner -> bannerResponseList.add(new BannerResponse(
                dbBanner.getId(),
                dbBanner.getNome(),
                dbBanner.getImagens(),
                dbBanner.getTipo(),
                dbBanner.getUsuario().getNome(),
                dbBanner.getUsuario().getId(),
                getFilesName(dbBanner)
        )));
        Pageable pageable = PageRequest.of(dbBanners.getPageable().getPageNumber(), dbBanners.getSize());
        Page<BannerResponse> pageBannerResponse = new PageImpl<BannerResponse>(bannerResponseList, pageable, dbBanners.getTotalElements());
        return pageBannerResponse;
    }

    private BannerResponse dbBannerToBannerResponse(Banners dbBanner, String[] imageNames){
        return new BannerResponse(
            dbBanner.getId(),
            dbBanner.getNome(),
            dbBanner.getImagens(),
            dbBanner.getTipo(),
            dbBanner.getUsuario().getNome(),
            dbBanner.getUsuario().getId(),
            imageNames
        );
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
