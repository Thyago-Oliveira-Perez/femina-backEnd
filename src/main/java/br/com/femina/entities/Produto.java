package br.com.femina.entities;

import br.com.femina.enums.Enums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Collection;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "produtos", schema = "public")
public class Produto extends AbstractEntity {

    @Getter @Setter
    @Column(name = "codigo", nullable = false, length = 20, unique = true)
    @NotNull(message = "Código é obrigatório")
    private String codigo;

    @Getter @Setter
    @Column(name = "nome", nullable = false)
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
    @ManyToOne
    private Modelo modelo;

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
    private Enums.Tamanhos tamanho;

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
