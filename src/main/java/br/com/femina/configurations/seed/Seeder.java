package br.com.femina.configurations.seed;

import br.com.femina.entities.*;
import br.com.femina.enums.Cargos;
import br.com.femina.enums.Provider;
import br.com.femina.enums.Sexos;
import br.com.femina.enums.Tamanhos;
import br.com.femina.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
                this.modeloRepository.saveAll(modelosDefault);

                if(this.fornecedorRepository.count() <= 0){
                    Fornecedor fornecedor = new Fornecedor(
                            "Polo Wear",
                            "11.774.839/0001-01",
                            "(11) 3643-4900",
                            "controladoria@controladoria.com"
                    );
                    this.fornecedorRepository.save(fornecedor);

                    if(this.marcaRepository.count() <= 0){
                        Marca marca = new Marca("Polo Wear");
                        this.marcaRepository.save(marca);

                        if(this.produtoRepository.count() <= 0){
                            String codigoProduto = "7895582546878";
                            BigDecimal valorDefault = new BigDecimal((long)230.56);
                            Produto produto = new Produto(
                                    codigoProduto,
                                    "Camiseta Masculina Blessed com Bolso Polo Wear Cinza Escuro",
                                    valorDefault,
                                    categoriasDefault.get(0),
                                    modelosDefault,
                                    fornecedor,
                                    marca,
                                    "Cinza Escuro",
                                    Tamanhos.M,
                                    "As Camisetas São Itens Clássicos em Um Guarda-Roupa Moderno, Pois Oferecem " +
                                            "Versatilidade Na Hora de Se Vestir. Combinam Perfeitamente em Looks Esportivos, " +
                                            "Passeio com a Família, Festinha com Amigos e Curtir Um Dia em Casa. Nossas Peças " +
                                            "Acompanham As Linhas do Corpo com Fluidez, Leveza e Conforto. Aposte Nas Camisetas " +
                                            "Polo Wear, São Estilosas com Milhares de Opções de Looks Versateis, Irreverentes e " +
                                            "Despojados, Abraçando Todos Os Estilos Valorizando a Pluralidade Urbana.",
                                    "./images/produto/" + codigoProduto,
                                    true
                            );
                            this.produtoRepository.save(produto);

                            if(this.perfilRepository.count() <= 0){
                                List<Perfil> perfis = new ArrayList<Perfil>(){{
                                    add(new Perfil(Cargos.USUARIO.toString()));
                                }};
                                this.perfilRepository.saveAll(perfis);

                                if(this.usuarioRepository.count() <= 0){
                                    BCryptPasswordEncoder senha = new BCryptPasswordEncoder();

                                    Usuario usuario = new Usuario(
                                            "Dayanne",
                                            "day123",
                                            senha.encode("123456"),
                                            Sexos.FEMININO,
                                            "dayday@gmail.com",
                                            "45 0 0000-0000",
                                            perfis,
                                            Provider.LOCAL
                                    );
                                    this.usuarioRepository.save(usuario);

                                    if(this.favoritosRepository.count() <= 0){
                                        Favoritos favoritos = new Favoritos(0l, usuario, produto);
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
