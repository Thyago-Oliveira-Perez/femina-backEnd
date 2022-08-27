package br.com.femina.services;

import br.com.femina.repositories.CorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CorService {

    @Autowired
    private CorRepository corRepository;

    @Transactional
    public void insert(Cor cor){
        this.corRepository.save(cor);
    }

    public Optional<Cor> findById(Long id){
        return this.corRepository.findById(id).isPresent() ? this.corRepository.findById(id) : null;
    }

    public Page<Cor> findAll(Pageable pageable){
        return this.corRepository.findAll(pageable);
    }

    @Transactional
    public void updateStatus(Long id) {
        if(this.corRepository.findById(id).isPresent()) {
            this.corRepository.updateStatus(id);
        } else {
            throw new RuntimeException();
        }
    }

}
