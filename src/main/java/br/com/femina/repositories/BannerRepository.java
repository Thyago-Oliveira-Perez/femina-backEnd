package br.com.femina.repositories;

import br.com.femina.entities.Banners;
import br.com.femina.enums.Enums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BannerRepository extends JpaRepository<Banners, Long> {
    public Optional<Banners> findByTipo(Enums.TipoDeBanner tipoDeBanner);
}
