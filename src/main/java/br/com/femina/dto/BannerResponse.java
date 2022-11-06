package br.com.femina.dto;

import br.com.femina.enums.TipoDeBanner;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BannerResponse {

    private String nome;
    private String imagens;
    private TipoDeBanner tipoDeBanner;
    private String nomeUsuario;
    private Long idUsuario;
    private String[] imageNames;

}
