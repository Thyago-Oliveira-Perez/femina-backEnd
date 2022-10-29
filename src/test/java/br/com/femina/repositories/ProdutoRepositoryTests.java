package br.com.femina.repositories;

import br.com.femina.entities.*;
import br.com.femina.enums.Tamanho;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProdutoRepositoryTests {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private ModeloRepository modeloRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    Categorias categorias;
    Marca marca;
    Modelo modelo, modelo2;
    Fornecedor fornecedor;
    Collection<Modelo> modelos;
    BigDecimal valor;

    @BeforeEach
    void initUseCase() {
        categorias = new Categorias("categoria");
        categoriaRepository.save(categorias);

        marca = new Marca("Vitoria Secret");
        marcaRepository.save(marca);

        modelo = new Modelo("jogger");
        modeloRepository.save(modelo);

        modelo2 = new Modelo("camisa");
        modeloRepository.save(modelo2);

        fornecedor = new Fornecedor("teste", "00.000.000/0000-00","45999999999","teste@email.com");
        fornecedorRepository.save(fornecedor);

        modelos = List.of(modelo, modelo2);

        valor = new BigDecimal(99);
    }

    @Test
    @Order(1)
    @DisplayName("Inserir Produto")
    public void insertProduto() {
        Produto produto = new Produto("codigo", "teste", this.valor, categorias, modelos, fornecedor, marca, "verde", Tamanho.M, "", "teste", false);
        produtoRepository.save(produto);
        int countProdutos = produtoRepository.findAll().size();
        assertThat(countProdutos).isEqualTo(1);
    }

    @Test
    @Order(2)
    @DisplayName("Inserir Produto com os valores nulos")
    public void insertProdutoNull() {
        Produto produto = new Produto(null,null,null,null,null,null,null,null,null,null,null,null);
        AtomicInteger countProdutos = new AtomicInteger();
        ConstraintViolationException exception = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            produtoRepository.save(produto);
            countProdutos.set(produtoRepository.findAll().size());
        });
        assertThat(exception.getMessage()).isNotNull();
        assertThat(countProdutos.get()).isEqualTo(0);
    }

    @Test
    @Order(3)
    @DisplayName("Inserir Produto com os mesmos valores(unique)")
    public void insertExistingProdutoUnsupportedOperation() {
        Produto produto = new Produto("codigo", "teste", valor, categorias, modelos, fornecedor, marca, "verde", Tamanho.M, "", "teste", false);
        AtomicInteger countProdutos = new AtomicInteger();
        UnsupportedOperationException exception = Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            produtoRepository.save(produto);
            produtoRepository.save(produto);
            countProdutos.set(produtoRepository.findAll().size());
        });
        assertThat(countProdutos.get()).isEqualTo(0);
    }

    @Test
    @Order(4)
    @DisplayName("Listar Produtos")
    public void listProdutos() {
        Produto produto = new Produto("codigo", "teste", valor, categorias, modelos, fornecedor, marca, "verde", Tamanho.M, "", "teste", false);
        produtoRepository.save(produto);
        List<Produto> produtosList = produtoRepository.findAll();
        assertThat(produtosList.size()).isGreaterThanOrEqualTo(1);
    }

}
