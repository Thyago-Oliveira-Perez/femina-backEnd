package br.com.femina.services;

import br.com.femina.entities.Funcionario;
import br.com.femina.repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class FuncionarioService {

    @Autowired
    public FuncionarioRepository funcionarioRepository;

    @Transactional
    public void insert(Funcionario funcionario) {
        this.funcionarioRepository.save(funcionario);
    }

    public Optional<Funcionario> findById(Long id) {
        return this.funcionarioRepository.findById(id);
    }

    public Page<Funcionario> findAll(Pageable pageable) {
        return this.funcionarioRepository.findAll(pageable);
    }

    @Transactional
    public void update(Long id, Funcionario funcionario) {
        if (id == funcionario.getId()){
            this.funcionarioRepository.save(funcionario);
        } else {
            throw new RuntimeException();
        }
    }

    @Transactional
    public void delete(Long id, Funcionario funcionario) {
        if (id == funcionario.getId()) {
            this.funcionarioRepository.delete(funcionario);
        } else {
            throw new RuntimeException();
        }
    }

}
