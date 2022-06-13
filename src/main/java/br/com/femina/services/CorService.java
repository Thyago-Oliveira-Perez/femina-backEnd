package br.com.femina.services;

import br.com.femina.entities.Cor;
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

    public Optional<Cor> findById(Long id){
        return this.corRepository.findById(id);
    }

    public Page<Cor> listAlL(Pageable pageable){
        return this.corRepository.findAll(pageable);
    }

    @Transactional
    public void update (Long id, Cor cor){
        if(id == cor.getId()){
            this.corRepository.save(cor);
        }
        else{
            throw new RuntimeException();
        }
    }
    @Transactional
    public void insert (Cor cor){
        this.corRepository.save(cor);
    }

}
