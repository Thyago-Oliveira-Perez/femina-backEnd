package br.com.femina.service;
import br.com.femina.entity.Cliente;
import br.com.femina.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public void insert(Cliente cliente){
        this.validarCliente(cliente);
        this.save(cliente);
    }

    public void validarCliente(Cliente cliente){

    }

    @Transactional
    public void save(Cliente cliente){
        this.clienteRepository.save(cliente);
    }

    public Optional<Cliente> findById(Long id){
        return this.clienteRepository.findById(id);
    }

    public Page<Cliente> findAll(Pageable pageable){
        return this.clienteRepository.findAllByHabilitado(true, pageable);
    }

    public void update(Long id, Cliente cliente){
        if(id == cliente.getId()){
            this.validarCliente(cliente);
            this.save(cliente);
        } else {
            throw new RuntimeException();
        }
    }

    @Transactional
    public void disable(Long id, Cliente cliente){
        if(id == cliente.getId()){
            this.clienteRepository.disable(cliente.getId(), false);
        } else {
            throw new RuntimeException();
        }
    }
}
