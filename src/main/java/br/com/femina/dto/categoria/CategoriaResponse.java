package br.com.femina.dto.categoria;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class CategoriaResponse {
    @Getter @Setter
    private Long id;
    @Getter @Setter
    private String nome;
}
