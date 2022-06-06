package br.com.femina.service;

import br.com.femina.entity.Funcionario;
import br.com.femina.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

public class FuncionarioService {

    @Autowired
    public FuncionarioRepository funcionarioRepository;

    @Transactional
    public void insert(Funcionario funcionario) {
        this.funcionarioRepository.save(funcionario);
    }

    public Page<Funcionario> listAll (Pageable pageable) {
        return this.funcionarioRepository.findAll(pageable);
    }

    public Optional<Funcionario> findById(Long id) {
        return this.funcionarioRepository.findById(id);
    }

    public void update(Long id, Funcionario funcionario) {
        if (id == funcionario.getId()){
            this.funcionarioRepository.save(funcionario);
        } else {
            throw new RuntimeException();
        }
    }

    @Transactional
    public void updateStatus(Long id, Funcionario funcionario) {
        if (id == funcionario.getId()) {
            this.funcionarioRepository.updateStatus(LocalDateTime.now(), funcionario.getId());
        } else {
            throw new RuntimeException();
        }
    }
}
