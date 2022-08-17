package br.com.femina.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@NoArgsConstructor
@MappedSuperclass
public abstract class Usuario extends AbstractEntity {

    @Getter @Setter
    @Column(name = "nome", nullable = false, length = 50)
    @NotNull(message = "Nome é obrigatório")
    private String nome;

    @Getter @Setter
    @Column(name = "login", nullable = false, length = 30, unique = true)
    @NotNull(message = "Nome de usuário é obrigatório")
    private String login;

    @Getter @Setter
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "senha", nullable = false, length = 32, unique = true)
    @NotNull(message = "Senha é obrigatória")
    private String senha;

    @Getter @Setter
    @Column(name = "cpf", nullable = false, length = 15, unique = true)
    @Pattern(regexp = "([0-9]{3}.[0-9]{3}.[0-9]{3}-[0-9]{2}$)")
    @NotNull(message = "Cpf é obrigatório")
    private String cpf;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "sexo", nullable = false, length = 10)
    private Sexo sexo;

    @Getter @Setter
    @Column(name = "dataNascimento", nullable = false)
    private Date dataNascimento;

    @Getter @Setter
    @Column(name = "email", nullable = false, length = 50, unique=true)
    @Email(message = "E-mail é inválido")
    @NotNull(message = "E-mail é obrigatório")
    private String email;

    @Getter @Setter
    @Pattern(regexp = "([0-9]{11})")
    @Column(name = "telefone", nullable = false, length = 18)
    private String telefone;

    @Getter @Setter
    @Column(name = "pais", nullable = false, length = 20)
    private String pais;

    @Getter @Setter
    @Column(name = "estado", nullable = false, length = 20)
    private String estado;

    @Getter @Setter
    @Column(name = "cidade", nullable = false, length = 20)
    private String cidade;

    @Getter @Setter
    @Column(name = "logradouro", nullable = false, length = 50)
    private String logradouro;

    @Getter @Setter
    @Column(name = "numero", nullable = false)
    private String numero;

    @Getter @Setter
    @Pattern(regexp = "([0-9]{5}-[0-9]{3}$)")
    @Column(name = "cep", nullable = false, length = 10)
    private String cep;

}
