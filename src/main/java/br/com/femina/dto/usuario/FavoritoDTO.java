package br.com.femina.dto.usuario;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

public class FavoritoDTO {

    @Getter @Setter
    private UUID idUser;
    @Getter @Setter
    private UUID idProduto;
}
