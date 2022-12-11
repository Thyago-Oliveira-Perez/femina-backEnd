package br.com.femina.services;

import br.com.femina.configurations.security.services.TokenService;
import br.com.femina.dto.ImageRequest;
import br.com.femina.dto.banner.BannerResponse;
import br.com.femina.entities.Banners;
import br.com.femina.entities.Produto;
import br.com.femina.entities.Usuario;
import br.com.femina.enums.Enums;
import br.com.femina.repositories.BannerRepository;
import br.com.femina.repositories.UsuarioRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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
import java.util.*;

@Service
public class BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    private ObjectMapper objectMapper = new ObjectMapper();

    public BannerService() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private String catalogPath = "../femina-webapp/public";
    private String stockPath = "../femina-stockManager/public";
    private String path = "/images/banners/";

    public String[] getFilesName(Banners banners) {
        File directory = new File(stockPath+path+banners.getTipo());
        return directory.list();
    }

    public int getLastFile(Banners banners) {
        File directory = new File(stockPath+path+banners.getTipo());
        String[] files = directory.list();
        int[] numberFiles = new int[files.length];
        for(int i = 0;i < files.length;i++) {
            files[i] = files[i].substring(0, files[i].lastIndexOf('.'));
            numberFiles[i] = Integer.parseInt(files[i]);
        }
        if(files.length == 0) {
            return 0;
        }
        if (numberFiles.length == 1) {
            return 1;
        }
        return Arrays.stream(numberFiles).max().getAsInt();
    }

    private void createDirIfNotExist(Banners banners) {
        Path directory = Paths.get(stockPath+path+banners.getTipo());
        Path directoryCatalog = Paths.get(catalogPath+path+banners.getTipo());
        if (!Files.exists(directory) || !Files.exists(directoryCatalog)) {
            try {
                Files.createDirectories(directory);
                Files.createDirectories(directoryCatalog);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void renameDir(Banners banners, String oldPath) {
        Path directory = Paths.get(stockPath + oldPath);
        Path directoryCatalog = Paths.get(catalogPath + oldPath);
        if (Files.exists(directory) && Files.exists(directoryCatalog)) {
            File oldDir = new File(stockPath + oldPath);
            File oldDirCatalog = new File(stockPath + oldPath);
            try {
                oldDir.renameTo(new File(stockPath + path + banners.getTipo()));
                oldDirCatalog.renameTo(new File(catalogPath + path + banners.getTipo()));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            try {
                Files.createDirectories(directory);
                Files.createDirectories(directoryCatalog);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void saveFile(Banners banners, MultipartFile[] files) {
        createDirIfNotExist(banners);
        int count = getLastFile(banners);
        for(int i = count;i < count+files.length;i++) {
            try {
                byte[] bytes = files[i-count].getBytes();
                ByteArrayInputStream inStreambj = new ByteArrayInputStream(bytes);
                BufferedImage newImage = ImageIO.read(inStreambj);
                ImageIO.write(newImage, "png", new File(stockPath + path + banners.getTipo() + "/" + i + ".png"));
                ImageIO.write(newImage, "png", new File(catalogPath + path + banners.getTipo() + "/" + i + ".png"));
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
            Banners banners = objectMapper.readValue(bannerString, Banners.class);
            if(bannerRepository.existsById(id) && id.equals(banners.getId())) {
                renameDir(banners, banners.getImagens());
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
            try {
                ImageRequest image = new ObjectMapper().readValue(imageName, ImageRequest.class);
                File fileToDelete = new File(stockPath+ path + banners.getTipo() + "/" + image.getName());
                File fileToDeleteCatalog = new File(catalogPath + path + banners.getTipo() + "/" + image.getName());
                fileToDelete.delete();
                fileToDeleteCatalog.delete();
                return ResponseEntity.ok().body("Imagem deletada com Sucesso");
            } catch(Exception e) {
                return ResponseEntity.internalServerError().body("Imagem não encontrada");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> removeAllImages(UUID id) {
        if(bannerRepository.existsById(id)) {
            Banners banners = bannerRepository.getById(id);
            File dir = new File(stockPath+path+banners.getTipo());
            File dirCatalog = new File(catalogPath+path+banners.getTipo());
            for(File file: dir.listFiles()) {
                if(!file.isDirectory()){
                    file.delete();
                }
            }
            for(File file: dirCatalog.listFiles()) {
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
                dbBanner.getUsuario(),
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
            dbBanner.getUsuario(),
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
