package br.com.femina.dto.produto;

import br.com.femina.enums.Enums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class Filters {

    @Getter @Setter
    private List<Long> categoriaIds;
    @Getter @Setter
    private List<Long> marcaIds;
    @Getter @Setter
    private String cor;
    @Getter @Setter
    private Enums.Tamanhos tamanho;
}
