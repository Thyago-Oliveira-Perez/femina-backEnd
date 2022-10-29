package br.com.femina.repositories;

import br.com.femina.entities.Banners;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerRepository extends JpaRepository<Banners, Long> {
}
