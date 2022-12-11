package br.com.femina.services;

import br.com.femina.dto.modelo.ModeloResponse;
import br.com.femina.entities.Modelo;
import br.com.femina.repositories.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class ModeloService {

    @Autowired
    private ModeloRepository modeloRepository;

    public ResponseEntity<?> insert(Modelo modelo){
        try{
            saveModelo(modelo);
            return ResponseEntity.ok().body("Modelo cadastrado com sucesso!");
        }catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Modelo já cadastrado");
        }
    }

    public ResponseEntity<Modelo> findById(UUID id){
        Optional<Modelo> modelo = this.modeloRepository.findById(id);
        return  modelo.isPresent() ? ResponseEntity.ok().body(modelo.get()) : ResponseEntity.notFound().build();
    }

    public Page<ModeloResponse> findAll(Pageable pageable){
        return this.modeloRepository.findAllByIsActive(pageable, true);
    }

    public ResponseEntity<?> updateStatusById(UUID id) {
        if(this.modeloRepository.existsById(id)){
            String mensagem = "";
            Boolean status = this.modeloRepository.getById(id).getIsActive();
            updateStatus(id, !status);
            if(!status.equals(true)){
                mensagem = "ativado";
            }
            mensagem = "desativado";
            return ResponseEntity.ok().body("Modelo " + mensagem + " com sucesso!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> update(UUID idModelo, Modelo modelo) {
        try{
            if(this.modeloRepository.existsById(idModelo)){
                saveModelo(modelo);
                return ResponseEntity.ok().body("Modelo atualizado com sucesso!");
            }
            return ResponseEntity.notFound().build();
        }catch(Exception e){
            System.out.println(e.getMessage());
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Transactional
    protected void saveModelo(Modelo modelo){
        this.modeloRepository.save(modelo);
    }

    @Transactional
    protected void updateStatus(UUID id, Boolean status){
        this.modeloRepository.updateStatus(id, status);
    }
}
