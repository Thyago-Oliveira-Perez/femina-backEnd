package br.com.femina.configurations.seed;

import br.com.femina.entities.*;
import br.com.femina.enums.Enums;
import br.com.femina.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class Seeder implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private FornecedorRepository fornecedorRepository;
    @Autowired
    private MarcaRepository marcaRepository;
    @Autowired
    private ModeloRepository modeloRepository;
    @Autowired
    private PerfilRepository perfilRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private FavoritosRepository favoritosRepository;

    public void populateDataBase(){

        if(this.categoriaRepository.count() <= 0){
            List<Categorias> categoriasDefault = new ArrayList<Categorias>(){{
                add(new Categorias("Camiseta"));
                add(new Categorias("Shorts e Bermudas"));
                add(new Categorias("Polo"));
                add(new Categorias("Camisa"));
                add(new Categorias("Calça"));
                add(new Categorias("Moletom"));
            }};
            categoriasDefault.forEach(categoria -> categoria.setIsActive(true));
            this.categoriaRepository.saveAll(categoriasDefault);

            if(this.modeloRepository.count() <= 0){
                List<Modelo> modelosDefault = new ArrayList<Modelo>(){{
                    add(new Modelo("Tradicional"));
                    add(new Modelo("Longline"));
                    add(new Modelo("Oversized"));
                    add(new Modelo("Henley"));
                    add(new Modelo("Raglan"));
                    add(new Modelo("Polo"));
                }};
                modelosDefault.forEach(modelo -> modelo.setIsActive(true));
                this.modeloRepository.saveAll(modelosDefault);

                if(this.fornecedorRepository.count() <= 0){
                    Fornecedor fornecedor = new Fornecedor(
                            "Polo Wear",
                            "11.774.839/0001-01",
                            "(11) 3643-4900",
                            "controladoria@controladoria.com"
                    );
                    fornecedor.setIsActive(true);
                    this.fornecedorRepository.save(fornecedor);

                    if(this.marcaRepository.count() <= 0){
                        Marca marca = new Marca("Polo Wear");
                        marca.setIsActive(true);
                        this.marcaRepository.save(marca);

                        if(this.produtoRepository.count() <= 0){
                            String codigoProduto = "7895582546878";
                            BigDecimal valorDefault = new BigDecimal((long)230.56);
                            Produto produto = new Produto(
                                    codigoProduto,
                                    "Camiseta Masculina Blessed com Bolso Polo Wear Cinza Escuro",
                                    valorDefault,
                                    categoriasDefault.get(0),
                                    modelosDefault.get(0),
                                    fornecedor,
                                    marca,
                                    "Cinza Escuro",
                                    Enums.Tamanhos.M,
                                    "As Camisetas São Itens Clássicos em Um Guarda-Roupa Moderno, Pois Oferecem " +
                                            "Versatilidade Na Hora de Se Vestir. Combinam Perfeitamente em Looks Esportivos, " +
                                            "Passeio com a Família, Festinha com Amigos e Curtir Um Dia em Casa. Nossas Peças " +
                                            "Acompanham As Linhas do Corpo com Fluidez, Leveza e Conforto. Aposte Nas Camisetas " +
                                            "Polo Wear, São Estilosas com Milhares de Opções de Looks Versateis, Irreverentes e " +
                                            "Despojados, Abraçando Todos Os Estilos Valorizando a Pluralidade Urbana.",
                                    "./images/produto/" + codigoProduto,
                                    true
                            );

                            String codigoProduto2 = "27502487532425";
                            BigDecimal valorDefault2 = new BigDecimal((long)200.00);
                            Produto produto2 = new Produto(
                                    codigoProduto2,
                                    "Camiseta Masculina Blessed com Bolso Polo Wear Preto Escuro",
                                    valorDefault2,
                                    categoriasDefault.get(0),
                                    modelosDefault.get(0),
                                    fornecedor,
                                    marca,
                                    "Pretp Escuro",
                                    Enums.Tamanhos.G,
                                    "As Camisetas São Itens Clássicos em Um Guarda-Roupa Moderno, Pois Oferecem " +
                                            "Versatilidade Na Hora de Se Vestir. Combinam Perfeitamente em Looks Esportivos, " +
                                            "Passeio com a Família, Festinha com Amigos e Curtir Um Dia em Casa. Nossas Peças " +
                                            "Acompanham As Linhas do Corpo com Fluidez, Leveza e Conforto. Aposte Nas Camisetas " +
                                            "Polo Wear, São Estilosas com Milhares de Opções de Looks Versateis, Irreverentes e " +
                                            "Despojados, Abraçando Todos Os Estilos Valorizando a Pluralidade Urbana.",
                                    "./images/produto/" + codigoProduto2,
                                    true
                            );
                            produto2.setIsActive(true);

                            String codigoProduto3 = "957234958720";
                            BigDecimal valorDefault3 = new BigDecimal((long)120.00);
                            Produto produto3 = new Produto(
                                    codigoProduto3,
                                    "Camiseta Masculina Roxo Escuro",
                                    valorDefault3,
                                    categoriasDefault.get(0),
                                    modelosDefault.get(0),
                                    fornecedor,
                                    marca,
                                    "Roxo",
                                    Enums.Tamanhos.G,
                                    "As Camisetas São Itens Clássicos em Um Guarda-Roupa Moderno, Pois Oferecem " +
                                            "Versatilidade Na Hora de Se Vestir. Combinam Perfeitamente em Looks Esportivos, " +
                                            "Passeio com a Família, Festinha com Amigos e Curtir Um Dia em Casa. Nossas Peças " +
                                            "Acompanham As Linhas do Corpo com Fluidez, Leveza e Conforto. Aposte Nas Camisetas " +
                                            "Polo Wear, São Estilosas com Milhares de Opções de Looks Versateis, Irreverentes e " +
                                            "Despojados, Abraçando Todos Os Estilos Valorizando a Pluralidade Urbana.",
                                    "./images/produto/" + codigoProduto3,
                                    true
                            );
                            produto3.setIsActive(true);

                            String codigoProduto4 = "943558720";
                            BigDecimal valorDefault4 = new BigDecimal((long)122.00);
                            Produto produto4 = new Produto(
                                    codigoProduto4,
                                    "Camiseta Masculina Roxo Escuro",
                                    valorDefault4,
                                    categoriasDefault.get(0),
                                    modelosDefault.get(0),
                                    fornecedor,
                                    marca,
                                    "Roxo",
                                    Enums.Tamanhos.G,
                                    "As Camisetas São Itens Clássicos em Um Guarda-Roupa Moderno, Pois Oferecem " +
                                            "Versatilidade Na Hora de Se Vestir. Combinam Perfeitamente em Looks Esportivos, " +
                                            "Passeio com a Família, Festinha com Amigos e Curtir Um Dia em Casa. Nossas Peças " +
                                            "Acompanham As Linhas do Corpo com Fluidez, Leveza e Conforto. Aposte Nas Camisetas " +
                                            "Polo Wear, São Estilosas com Milhares de Opções de Looks Versateis, Irreverentes e " +
                                            "Despojados, Abraçando Todos Os Estilos Valorizando a Pluralidade Urbana.",
                                    "./images/produto/" + codigoProduto4,
                                    true
                            );
                            produto3.setIsActive(true);

                            List<Produto> defaultProducts = new ArrayList<>(){{
                                    add(produto);
                                    add(produto2);
                                    add(produto3);
                                    add(produto4);
                                }};

                            this.produtoRepository.saveAll(defaultProducts);

                            if(this.perfilRepository.count() <= 0){
                                List<Cargos> perfis = new ArrayList<Cargos>(){{
                                    add(new Cargos(Enums.Cargos.USUARIO.toString()));
                                }};
                                perfis.forEach(perfil -> perfil.setIsActive(true));
                                this.perfilRepository.saveAll(perfis);

                                if(this.usuarioRepository.count() <= 0){
                                    BCryptPasswordEncoder senha = new BCryptPasswordEncoder();

                                    Usuario usuario = new Usuario(
                                            "Dayanne",
                                            "day123",
                                            senha.encode("123456"),
                                            Enums.Sexos.FEMININO,
                                            "dayday@gmail.com",
                                            "45 0 0000-0000",
                                            perfis,
                                            Enums.Provider.LOCAL
                                    );
                                    usuario.setIsActive(true);
                                    this.usuarioRepository.save(usuario);

                                    if(this.favoritosRepository.count() <= 0){
                                        Favoritos favoritos = new Favoritos(UUID.randomUUID(), usuario, produto);
                                        this.favoritosRepository.save(favoritos);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        populateDataBase();
    }

}
