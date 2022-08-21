package br.com.femina.repositories;

import br.com.femina.entities.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ProdutoRepositoryTests {

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private CorRepository corRepository;
    @Autowired
    private FornecedorRepository fornecedorRepository;
    @Autowired
    private MarcaRepository marcaRepository;
    @Autowired
    private ModeloRepository modeloRepository;

    @Test
    public void insertProduto() {
        Produto produto = new Produto();
        BigDecimal valor = new BigDecimal(9999);

        Categorias categorias = new Categorias("calcinhas");
        categoriaRepository.save(categorias);

        Cor cor = new Cor("red", "#012365");
        corRepository.save(cor);

        Fornecedor fav = new Fornecedor("alla","89899","989898", "99905406", "mj@gmal.com", "pr","aduana", "foz","foz", "br");
        fornecedorRepository.save(fav);

        Marca marca = new Marca("hope");
        marcaRepository.save(marca);

        Modelo modelo = new Modelo("lalala");
        modeloRepository.save(modelo);

        produto.setNome("Calcinha dona olinda");
        produto.setCategoria(categorias);
        produto.setCor(cor);
        produto.setCodigo("1234566");
        produto.setDestaque(true);
        produto.setImagem("125fkfkfnff");
        produto.setDescricao("lalalala");
        produto.setFornecedor(fav);
        produto.setMarca(marca);
        produto.setModelo(modelo);
        produto.setValor(valor);
        produto.setTamanho(Tamanho.P);

        produtoRepository.save(produto);
        int totalProducts = produtoRepository.findAll().size();
        assertEquals(1, totalProducts);
    }

    @Test
    public void insertExistingProduct() {
        Produto produto = new Produto();
        BigDecimal valor = new BigDecimal(9999);

        Categorias categorias = new Categorias("calcinhas");
        categoriaRepository.save(categorias);

        Cor cor = new Cor("red", "#012365");
        corRepository.save(cor);

        Fornecedor fav = new Fornecedor("lojinha","89899","989898", "99905406", "mj@gmal.com", "pr","aduana", "foz","foz", "br");
        fornecedorRepository.save(fav);

        Marca marca = new Marca("hope");
        marcaRepository.save(marca);

        Modelo modelo = new Modelo("lalala");
        modeloRepository.save(modelo);

        produto.setNome("Calcinha dona olinda");
        produto.setCategoria(categorias);
        produto.setCor(cor);
        produto.setCodigo("1234566");
        produto.setDestaque(true);
        produto.setImagem("125fkfkfnff");
        produto.setDescricao("lalalala");
        produto.setFornecedor(fav);
        produto.setMarca(marca);
        produto.setModelo(modelo);
        produto.setValor(valor);
        produto.setTamanho(Tamanho.P);

        produtoRepository.save(produto);
        produtoRepository.save(produto);
        int totalProducts = produtoRepository.findAll().size();
        assertEquals(1, totalProducts);
    }

    @Test
    public void listProducts() {
        List<Produto> listProducts = produtoRepository.findAll();
        assertNotNull(listProducts);
    }

}
