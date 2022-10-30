package br.com.femina.dto;

import br.com.femina.enums.TipoDeBanner;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class BannerResponse {

    private String nome;
    private String imagens;
    private int countImagens;
    private TipoDeBanner tipoDeBanner;
    private String nomeUsuario;
    private Long idUsuario;

}
