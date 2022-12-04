package br.com.femina.dto.fornecedor;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class FornecedorResponse {
    @Getter @Setter
    private UUID id;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String cnpj;
    @Getter @Setter
    private String telefone;
    @Getter @Setter
    private String email;
}
