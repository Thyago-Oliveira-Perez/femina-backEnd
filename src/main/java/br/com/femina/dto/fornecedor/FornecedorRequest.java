package br.com.femina.dto.fornecedor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class FornecedorRequest {
    @Getter @Setter
    private Long id;
    @Getter @Setter
    private String nome;
    @Getter @Setter
    private String cnpj;
    @Getter @Setter
    private String telefone;
    @Getter @Setter
    private String email;
}
