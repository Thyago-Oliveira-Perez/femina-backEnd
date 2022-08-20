package br.com.femina.services;

import br.com.femina.entities.Cliente;
import br.com.femina.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public void insert(Cliente cliente){
        this.clienteRepository.save(cliente);
    }

    public Optional<Cliente> findById(Long id){
        return this.clienteRepository.findById(id);
    }

    public Page<Cliente> findAll(Pageable pageable) { return this.clienteRepository.findAll(pageable); }

    @Transactional
    public void update(Long id, Cliente cliente) {
        if(id == cliente.getId()) {
            this.clienteRepository.save(cliente);
        } else {
            throw new RuntimeException();
        }
    }

    @Transactional
    public void updateStatus(Long id) {
        if(this.clienteRepository.findById(id).isPresent()) {
            this.clienteRepository.updateStatus(id);
        } else {
            throw new RuntimeException();
        }
    }




}
