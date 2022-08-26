package br.com.femina.repositories;

import br.com.femina.entities.*;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@SpringBootTest
@AutoConfigureMockMvc
public class FavoritosRepositoryTests {

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
    @Autowired
    private FavoritosRepository favoritosRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    public void insertFavoritos() {
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

        Cliente cliente = new Cliente();

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

        Favoritos favoritos = new Favoritos(cliente, produto);

        favoritosRepository.save(favoritos);
        int totalProducts = favoritosRepository.findAll().size();
        assertEquals(1, totalProducts);
    }


}
