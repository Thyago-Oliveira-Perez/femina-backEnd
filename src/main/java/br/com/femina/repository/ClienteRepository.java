package br.com.femina.repository;
import br.com.femina.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    public Page<Cliente> findAllByHabilitado(Boolean habilitado);

    @Modifying
    @Query("UPDATE Cliente cliente " +
            "SET cliente.habilitado = :habilitado " +
            "WHERE cliente.id = :cliente")
    public void disable(
            @Param("cliente") Long idCliente,
            @Param("habilitado") Boolean habilitado
    );
}
