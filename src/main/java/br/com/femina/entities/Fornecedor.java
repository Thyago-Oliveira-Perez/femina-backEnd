package br.com.femina.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(schema="public", name="fornecedores")
public class Fornecedor extends AbstractEntity {

    @Getter
    @Setter
    @Column(name = "nome", nullable = false)
    @NotNull(message = "Nome é obrigatório")
    private String name;

    @Getter
    @Setter
    @Column(name = "cnpj", nullable = false, unique = true)
    @NotNull(message = "Cnpj é obrigatório")
    private String cnpj;

    @Getter
    @Setter
    @Column(name = "telefone", nullable = false)
    @NotNull(message = "Telefone é obrigatório")
    private String telefone;

    @Getter
    @Setter
    @Column(name = "numero", nullable = false)
    private int numero;

    @Getter
    @Setter
    @Column(name = "email", nullable = false, unique = true)
    @NotNull(message = "E-mail é obrigatório")
    private String email;

    @Getter
    @Setter
    @Column(name = "cep", nullable = false)
    private String cep;

    @Getter
    @Setter
    @Column(name = "estado", nullable = false)
    private String estado;

    @Getter
    @Setter
    @Column(name = "logradouro", nullable = false)
    private String logradouro;

    @Getter
    @Setter
    @Column(name = "cidade", nullable = false)
    private String cidade;

    @Getter
    @Setter
    @Column(name = "pais", nullable = false)
    private String pais;


}
