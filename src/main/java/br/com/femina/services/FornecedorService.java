package br.com.femina.services;

import br.com.femina.dto.fornecedor.FornecedorRequest;
import br.com.femina.dto.fornecedor.FornecedorResponse;
import br.com.femina.entities.Fornecedor;
import br.com.femina.repositories.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FornecedorService {

    @Autowired
    public FornecedorRepository fornecedorRepository;

    public ResponseEntity<?> insert(FornecedorRequest fornecedor) {
        try{
            System.out.println(this.fornecedorRequestToDbFornecedor(fornecedor));
            saveFornecedor(this.fornecedorRequestToDbFornecedor(fornecedor));
            return ResponseEntity.ok().body("Fornecedor cadastrado com sucesso!");
        }catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Fornecedor já cadastrado!");
        }
    }

    public ResponseEntity<FornecedorResponse> findById(UUID id) {
        Optional<Fornecedor> fornecedor = this.fornecedorRepository.findById(id);
        return fornecedor.isPresent() ?
                ResponseEntity.ok().body(this.dbFornecedorToFornecedorResponse(fornecedor.get())) :
                ResponseEntity.notFound().build();
    }

    public Page<FornecedorResponse> findAll(Pageable pageable) {
        return this.fornecedorRepository.findAllByIsActive(pageable, true);
    }

    public ResponseEntity<FornecedorResponse> update(UUID id, Fornecedor fornecedor) {
        if (this.fornecedorRepository.existsById(id) && fornecedor.getId().equals(id)){
            saveFornecedor(fornecedor);
            return ResponseEntity.ok().body(this.dbFornecedorToFornecedorResponse(fornecedor));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional
    public ResponseEntity<?> updateStatusById(UUID id) {
        String mensagem = "";
        if (this.fornecedorRepository.existsById(id)) {
            Boolean status = this.fornecedorRepository.getById(id).getIsActive();
            fornecedorRepository.updateStatus(id, !status);
            if(!status.equals(true)){
                mensagem = "ativada";
            }
            mensagem = "desativada";
            return ResponseEntity.ok().body("Fornecedor " + mensagem + " com sucesso!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional
    protected void saveFornecedor(Fornecedor fornecedor){
        this.fornecedorRepository.save(fornecedor);
    }

    //<editor-fold desc="Helpers">
    private Page<FornecedorResponse> pageDbFornecedoresToPageFornecedorResponse(Page<Fornecedor> dbFornecedores){
        List<FornecedorResponse> fornecedorResponseList = new ArrayList<>();

        dbFornecedores.map(dbFornecedor -> fornecedorResponseList.add(new FornecedorResponse(
                dbFornecedor.getId(),
                dbFornecedor.getIsActive(),
                dbFornecedor.getNome(),
                dbFornecedor.getCnpj(),
                dbFornecedor.getTelefone(),
                dbFornecedor.getEmail()
        )));

        Page<FornecedorResponse> pageFornecedorResponse = new PageImpl<FornecedorResponse>(fornecedorResponseList);
        return pageFornecedorResponse;
    }

    private FornecedorResponse dbFornecedorToFornecedorResponse(Fornecedor dbFornecedor){
        return new FornecedorResponse(
            dbFornecedor.getId(),
            dbFornecedor.getIsActive(),
            dbFornecedor.getNome(),
            dbFornecedor.getCnpj(),
            dbFornecedor.getTelefone(),
            dbFornecedor.getEmail()
        );
    }

    private Fornecedor fornecedorRequestToDbFornecedor(FornecedorRequest fornecedorRequest){
        return new Fornecedor(
                fornecedorRequest.getNome(),
                fornecedorRequest.getCnpj(),
                fornecedorRequest.getTelefone(),
                fornecedorRequest.getEmail()
        );
    }
    //</editor-fold>
}
