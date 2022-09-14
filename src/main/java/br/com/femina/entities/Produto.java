package br.com.femina.entities;

import br.com.femina.entities.enums.Tamanho;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Collection;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "produtos", schema = "public")
public class Produto extends AbstractEntity {

    @Getter @Setter
    @Column(name = "codigo", nullable = false, length = 20, unique = true)
    @NotNull(message = "Código é obrigatório")
    private String codigo;

    @Getter @Setter
    @Column(name = "nome", nullable = false, length = 50)
    @NotNull(message = "Nome é obrigatório")
    private String nome;

    @Getter @Setter
    @Digits(integer = 5, fraction = 3)
    @Column(name = "valor", nullable = false)
    @NotNull(message = "Valor é obrigatório")
    private BigDecimal valor;

    @Getter @Setter
    @JoinColumn(name = "id_categoria")
    @ManyToOne(fetch = FetchType.EAGER)
    private Categorias categoria;

    @Getter @Setter
    @ManyToMany
    @JoinTable(name = "produtos_modelos",
            joinColumns = @JoinColumn(name = "id_produto", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_modelo", referencedColumnName = "id"))
    private Collection<Modelo> modelo;

    @Getter @Setter
    @JoinColumn(name = "id_fornecedor")
    @ManyToOne(fetch = FetchType.EAGER)
    private Fornecedor fornecedor;

    @Getter @Setter
    @JoinColumn(name = "id_marca")
    @ManyToOne(fetch = FetchType.EAGER)
    private Marca marca;

    @Getter @Setter
    @JoinColumn(name = "cor")
    private String cor;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "tamanho", nullable = false, length = 10)
    private Tamanho tamanho;

    @Getter @Setter
    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Getter @Setter
    @Column(name = "imagem")
    private String imagem;

    @Getter @Setter
    @Column(name = "destaque", columnDefinition = "boolean default false", nullable = false)
    private Boolean destaque;

}
