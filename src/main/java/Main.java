import br.com.femina.FeminaApplication;
import br.com.femina.entities.*;
import br.com.femina.entities.enums.Tamanho;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        BCryptPasswordEncoder senha = new BCryptPasswordEncoder();
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(senha.encode("teste"));
        Categorias categorias = new Categorias("teste");
        Marca marca = new Marca("teste");
        Modelo modelo = new Modelo("teste");
        Fornecedor fornecedor = new Fornecedor("teste", "00.000.000/0000-00","999999999","teste@email.com");
        Collection<Modelo> modelos = List.of(modelo);
        BigDecimal valor = new BigDecimal(99);
        Produto produto = new Produto("codigo",
                "teste",
                valor, categorias,
                modelos, fornecedor,
                marca, "verde",
                Tamanho.M, "",
                "teste", false);

        String json = mapper.writeValueAsString(produto);
        System.out.println(json);
    /*
    * INSERT INTO public.usuario(
			id, atualizado, cadastrado, ativo, email, login, name, password, sexo, telefone)
	VALUES (1,
			now(),
			now(),
			true,
			'teste@gmail.com',
			'teste',
			'thyagao',
			'$2a$10$7kwAaOWwCUA./7A8CH7jWeLiPfgNkDb.MMRUafrhkkRF8.5mVCUoq',
			1,
			'96287917006140912184');
    * */

    /*
        {
            "id":null,
            "cadastrado":null,
            "atualizado":null,
            "isActive":null,
            "codigo":"codigo",
            "nome":"teste",
            "valor":99,
            "categoria": {
                "id":null,
                "cadastrado":null,
                "atualizado":null,
                "isActive":null,
                "nome":"teste"
            },
            "modelo":[
                {
                    "id":null,
                    "cadastrado":null,
                    "atualizado":null,
                    "isActive":null,
                    "nome":"teste"
                }
            ],
            "fornecedor": {
                "id":null,
                "cadastrado":null,
                "atualizado":null,
                "isActive":null,
                "name":"teste",
                "cnpj":"00.000.000/0000-00",
                "telefone":"999999999",
                "email":"teste@email.com"
            },
            "marca": {
                "id":null,
                "cadastrado":null,
                "atualizado":null,
                "isActive":null,
                "nome":"teste"
            },
            "cor": "verde",
            "tamanho": "M",
            "descricao": "",
            "imagem": "teste",
            "destaque": false
        }
     */
    }
}
