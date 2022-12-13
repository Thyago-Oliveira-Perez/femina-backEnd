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

    @Autowired
    private BannerRepository bannerRepository;

    public void populateDataBase(){

        if(this.categoriaRepository.count() <= 0){
            List<Categorias> categoriasDefault = new ArrayList<Categorias>(){{
                add(new Categorias("Calcinhas"));
                add(new Categorias("Cuecas"));
                add(new Categorias("Sutiã"));
                add(new Categorias("Pijamas"));
                add(new Categorias("Conjuntos"));
            }};
            categoriasDefault.forEach(categoria -> categoria.setIsActive(true));
            this.categoriaRepository.saveAll(categoriasDefault);

            if(this.modeloRepository.count() <= 0){
                List<Modelo> modelosDefault = new ArrayList<Modelo>(){{
                    add(new Modelo("Com Bojo"));
                    add(new Modelo("Sem Bojo"));
                    add(new Modelo("Renda"));
                    add(new Modelo("Box"));
                    add(new Modelo("Biquini"));
                }};
                modelosDefault.forEach(modelo -> modelo.setIsActive(true));
                this.modeloRepository.saveAll(modelosDefault);

                if(this.fornecedorRepository.count() <= 0){
                    Fornecedor fornecedor = new Fornecedor(
                            "Femina",
                            "11.774.839/0001-01",
                            "(11) 3643-4900",
                            "controladoria@controladoria.com"
                    );
                    fornecedor.setIsActive(true);
                    this.fornecedorRepository.save(fornecedor);

                    if(this.marcaRepository.count() <= 0){
                        List<Marca> marcasDefault = new ArrayList<Marca>(){{
                            add(new Marca("Polo Wear"));
                            add(new Marca("Hope"));
                            add(new Marca("Scala"));
                        }};
                        marcasDefault.forEach(marca -> marca.setIsActive(true));
                        this.marcaRepository.saveAll(marcasDefault);

                        if(this.produtoRepository.count() <= 0){
                            String codigoProduto = "7895582546878";
                            BigDecimal valorDefault = new BigDecimal((long)23.56);
                            Produto produto = new Produto(
                                    codigoProduto,
                                    "Conjunto Olinda",
                                    valorDefault,
                                    categoriasDefault.get(4),
                                    modelosDefault.get(4),
                                    fornecedor,
                                    marcasDefault.get(0),
                                    "#000000",
                                    Enums.Tamanhos.M,
                                    "\n" +
                                            "COMPOSIÇÃO\n" +
                                            "Poliamida-083 • " +
                                            "Elastano-017 • " +
                                            "Detalhe Poliamida-097 • " +
                                            "Detalhe Elastano-003 • " +
                                            "Detlahe Lateral Poli-065 • " +
                                            "Detalhe Lateral Poli-035 • " +
                                            "Forro Fundo Algodao-100.",
                                    "/images/produto/" + codigoProduto,
                                    true
                            );

                            String codigoProduto2 = "27502487532425";
                            BigDecimal valorDefault2 = new BigDecimal((long)20.00);
                            Produto produto2 = new Produto(
                                    codigoProduto2,
                                    "Calcinha Dona Olinda",
                                    valorDefault2,
                                    categoriasDefault.get(0),
                                    modelosDefault.get(3),
                                    fornecedor,
                                    marcasDefault.get(1),
                                    "#3f4f5f",
                                    Enums.Tamanhos.G,
                                    "CUIDADOS\n" +
                                            "Lavagem a mao, nao alvejar, " +
                                            "nao secar em tambor, secagem em varal por gotejamento a sombra, " +
                                            "nao passar ou utilizar vaporizacao, nao limpar a seco, " +
                                            "nao limpar a umido.",
                                    "/images/produto/" + codigoProduto2,
                                    false
                            );
                            produto2.setIsActive(true);

                            List<Produto> defaultProducts = new ArrayList<>(){{
                                add(produto);
                                add(produto2);
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

                                    if(this.bannerRepository.count() <= 0) {
                                        Banners bannerDestaque = new Banners("Banner default", "./images/DESTAQUE", usuario, Enums.TipoDeBanner.DESTAQUE);
                                        this.bannerRepository.save(bannerDestaque);
                                        Banners bannerColecao = new Banners("Banner default", "./images/COLECAO", usuario, Enums.TipoDeBanner.COLECAO);
                                        this.bannerRepository.save(bannerColecao);
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
