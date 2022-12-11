package br.com.femina.dto.banner;

import java.util.UUID;

import br.com.femina.entities.Usuario;
import br.com.femina.enums.Enums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BannerResponse {
    private UUID id;
    private String nome;
    private String imagens;
    private Enums.TipoDeBanner tipo;
    private String nomeUsuario;
    private Usuario usuario;
    private String[] imageNames;
}
