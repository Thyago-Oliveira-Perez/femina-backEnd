package br.com.femina.dto.produto;

import br.com.femina.entities.Categorias;
import br.com.femina.entities.Fornecedor;
import br.com.femina.entities.Marca;
import br.com.femina.entities.Modelo;
import br.com.femina.enums.Enums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class ProdutoResponse {
    @Getter @Setter
    private UUID id;
    @Getter @Setter
    private String nome;
    @Getter @Setter
    private String codigo;
    @Getter @Setter
    private BigDecimal valor;
    @Getter @Setter
    private Marca marca;
    @Getter @Setter
    private Categorias categoria;
    @Getter @Setter
    private Collection<Modelo> modelo;
    @Getter @Setter
    private Fornecedor fornecedor;
    @Getter @Setter
    private Enums.Tamanhos tamanho;
    @Getter @Setter
    private String cor;
    @Getter @Setter
    private String descricao;
    @Getter @Setter
    private String imagemUrl;
    @Getter @Setter
    private Boolean destaque;
    @Getter @Setter
    private String[] imageNames;
}
