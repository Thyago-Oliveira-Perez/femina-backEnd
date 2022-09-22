package br.com.femina.services;

import br.com.femina.entities.Marca;
import br.com.femina.repositories.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    public ResponseEntity<?> insert(Marca marca) {
        try{
            saveMarca(marca);
            return ResponseEntity.ok().body("Marca cadastrada com sucesso!");
        }catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Marca já cadastrada.");
        }
    }

    public ResponseEntity<Marca> findById(Long id) {
        Optional<Marca> marca = this.marcaRepository.findById(id);
        return marca.isPresent() ? ResponseEntity.ok().body(marca.get()) : ResponseEntity.notFound().build();
    }

    public Page<Marca> findAll(Pageable pageable) {
        return this.marcaRepository.findAllByIsActive(pageable, true);
    }

    public ResponseEntity<?> updateStatusById(Long id) {
        String mensagem = "";
        if(this.marcaRepository.existsById(id)) {
            Boolean status = this.marcaRepository.getById(id).getIsActive();
            updateStatus(id, !status);
            if(!status.equals(true)){
                mensagem = "ativado";
            }
            mensagem = "desativado";
            return ResponseEntity.ok().body("" + mensagem +" com sucesso!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional
    protected void saveMarca(Marca marca){
        this.marcaRepository.save(marca);
    }

    @Transactional
    protected void updateStatus(Long id, Boolean status){
        this.marcaRepository.updateStatus(id, status);
    }

}
