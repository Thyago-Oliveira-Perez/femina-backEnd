package br.com.femina.repositories;

import br.com.femina.entities.*;
import br.com.femina.entities.enums.Sexo;
import br.com.femina.entities.enums.Tamanho;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FavoritoRepositoryTests {

    @Autowired
    private FavoritosRepository favoritosRepository;

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

    @Autowired
    private  UsuarioRepository usuarioRepository;

    Categorias categorias;
    Marca marca;
    Modelo modelo, modelo2;
    Fornecedor fornecedor;
    Collection<Modelo> modelos;
    BigDecimal valor;
    Produto produto;
    Usuario usuario;

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

        produto = new Produto("codigo", "teste", this.valor, categorias, modelos, fornecedor, marca, "verde", Tamanho.M, "", "teste", false);
        produtoRepository.save(produto);

        Usuario usuario = new Usuario("teste","teste","123", Sexo.MASCULINO, "teste@email.com", "45999999999", new ArrayList<>());
        usuarioRepository.save(usuario);
    }

    @Test
    @Order(1)
    @DisplayName("Inserir Favorito")
    public void insertFavorito() {
        Favoritos favoritos = new Favoritos(1L, usuario, produto);
        favoritosRepository.save(favoritos);
        int countFavoritos = favoritosRepository.findAll().size();
        assertThat(countFavoritos).isEqualTo(1);
    }

    @Test
    @Order(2)
    @DisplayName("Inserir Favorito com os mesmos valores(unique)")
    public void insertExistingModelo() {
        Favoritos favoritos = new Favoritos(1L, usuario, produto);
        favoritosRepository.save(favoritos);
        favoritosRepository.save(favoritos);
        int countFavoritos = favoritosRepository.findAll().size();
        assertThat(countFavoritos).isEqualTo(1);
    }


//    @Test
//    @Order(3)
//    @DisplayName("Listar Favoritos")
//    public void listFavoritos() {
//        Pageable pageable = PageRequest.of(0,10);
//        Favoritos favoritos = new Favoritos(1L, usuario, produto);
//        favoritosRepository.save(favoritos);
//        List<Usuario> usuarios = usuarioRepository.findAll();
//        Page<Favoritos> favoritosList = favoritosRepository.findFavoritosByUsuarioId(usuarios.get(0).getId(), pageable);
//        System.out.println(favoritosList);
//        assertThat(favoritosList.getContent().size()).isGreaterThanOrEqualTo(1);
//    }

}
