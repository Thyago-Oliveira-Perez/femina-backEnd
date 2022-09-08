package br.com.femina.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "fornecedores", schema = "public")
public class Fornecedor extends AbstractEntity {

    @Getter
    @Setter
    @Column(name = "nome", length = 50, nullable = false)
    @NotNull(message = "Nome é obrigatório")
    private String name;

    @Getter
    @Setter
    @Column(name = "cnpj", length = 20, nullable = false, unique = true)
    @Pattern(regexp = "([0-9]{2}.[0-9]{3}.[0-9]{3}/[0-9]{4}-[0-9]{2}$)")
    @NotNull(message = "Cnpj é obrigatório")
    private String cnpj;

    @Getter
    @Setter
    @Column(name = "telefone", length = 12, nullable = false)
    @NotNull(message = "Telefone é obrigatório")
    private String telefone;

    @Getter
    @Setter
    @Column(name = "email", length = 50, nullable = false, unique = true)
    @Email(message = "Email Inválido")
    @NotNull(message = "E-mail é obrigatório")
    private String email;

}
