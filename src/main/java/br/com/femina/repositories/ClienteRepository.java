package br.com.femina.repositories;

import br.com.femina.entities.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    public Page<Cliente> findAllByIsActive(Pageable pageable, Boolean active);

    @Modifying
    @Query("UPDATE Cliente cliente " +
            "SET cliente.isActive = false " +
            "WHERE cliente.id = :id")
    public void updateStatus(@Param("id") Long id);

}
