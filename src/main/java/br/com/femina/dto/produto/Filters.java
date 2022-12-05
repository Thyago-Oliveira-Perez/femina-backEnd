package br.com.femina.dto.produto;

import br.com.femina.enums.Enums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class Filters {

    @Getter @Setter
    private List<UUID> categoriaIds;
    @Getter @Setter
    private List<UUID> marcaIds;
    @Getter @Setter
    private String cor;
    @Getter @Setter
    private Enums.Tamanhos tamanho;
}
