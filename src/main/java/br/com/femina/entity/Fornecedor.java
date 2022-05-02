package br.com.femina.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(schema="public", name="fornecedores")
public class Fornecedor extends AbstractEntity {

    @Getter
    @Setter
    @Column(name = "nome", nullable = false)
    private String name;

    @Getter
    @Setter
    @Column(name = "cnpj", nullable = false)
    private String cnpj;

    @Getter
    @Setter
    @Column(name = "telefone", nullable = false)
    private String telefone;

    @Getter
    @Setter
    @Column(name = "numero", nullable = false)
    private int numero;

    @Getter
    @Setter
    @Column(name = "email", nullable = false)
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
