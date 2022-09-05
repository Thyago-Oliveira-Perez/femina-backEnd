import br.com.femina.FeminaApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Main {
    public static void main(String[] args) {
        BCryptPasswordEncoder senha = new BCryptPasswordEncoder();
        System.out.println(senha.encode("teste"));

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
    }
}
