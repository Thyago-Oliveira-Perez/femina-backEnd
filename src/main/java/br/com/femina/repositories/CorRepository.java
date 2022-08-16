package br.com.femina.repositories;

import br.com.femina.entities.Cor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CorRepository extends JpaRepository<Cor, Long> {

    public Page<Cor> findAllByIsActive(Pageable pageable, Boolean active);

    @Modifying
    @Query("UPDATE Cor cor " +
            "SET cor.isActive = false " +
            "WHERE cor.id = :id")
    public void updateStatus(@Param("id") Long id);

}
