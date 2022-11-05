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

@Service
public class FornecedorService {

    @Autowired
    public FornecedorRepository fornecedorRepository;

    public ResponseEntity<?> insert(FornecedorRequest fornecedor) {
        try{
            saveFornecedor(this.fornecedorRequestToDbFornecedor(fornecedor));
            return ResponseEntity.ok().body("Fornecedor cadastrada com sucesso!");
        }catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Fornecedor já cadastrado!");
        }
    }

    public ResponseEntity<FornecedorResponse> findById(Long id) {
        Optional<Fornecedor> fornecedor = this.fornecedorRepository.findById(id);
        return fornecedor.isPresent() ?
                ResponseEntity.ok().body(this.dbFornecedorToFornecedorResponse(fornecedor.get())) :
                ResponseEntity.notFound().build();
    }

    public Page<FornecedorResponse> findAll(Pageable pageable) {
        return this.pageDbFornecedoresToPageFornecedorResponse(this.fornecedorRepository.findAllByIsActive(pageable, true));
    }

    public ResponseEntity<FornecedorResponse> update(Long id, Fornecedor fornecedor) {
        if (this.fornecedorRepository.existsById(id) && fornecedor.getId().equals(id)){
            saveFornecedor(fornecedor);
            return ResponseEntity.ok().body(this.dbFornecedorToFornecedorResponse(fornecedor));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> updateStatusById(Long id) {
        String mensagem = "";
        if (this.fornecedorRepository.existsById(id)) {
            Boolean status = this.fornecedorRepository.getById(id).getIsActive();
            updateStatus(id, !status);
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

    @Transactional
    protected void updateStatus(Long id, Boolean status){
        this.fornecedorRepository.updateStatus(id, status);
    }

    //<editor-fold desc="Helpers">
    private Page<FornecedorResponse> pageDbFornecedoresToPageFornecedorResponse(Page<Fornecedor> dbFornecedores){
        List<FornecedorResponse> fornecedorResponseList = new ArrayList<>();

        dbFornecedores.map(dbFornecedor -> fornecedorResponseList.add(new FornecedorResponse(
                dbFornecedor.getId(),
                dbFornecedor.getName(),
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
            dbFornecedor.getName(),
            dbFornecedor.getCnpj(),
            dbFornecedor.getTelefone(),
            dbFornecedor.getEmail()
        );
    }

    private Fornecedor fornecedorRequestToDbFornecedor(FornecedorRequest fornecedorRequest){
        return new Fornecedor(
                fornecedorRequest.getName(),
                fornecedorRequest.getCnpj(),
                fornecedorRequest.getTelefone(),
                fornecedorRequest.getEmail()
        );
    }
    //</editor-fold>
}
