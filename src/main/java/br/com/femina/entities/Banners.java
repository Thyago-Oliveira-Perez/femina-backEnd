package br.com.femina.entities;

import br.com.femina.enums.TipoDeBanner;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "banners", schema = "public")
public class Banners extends AbstractEntity {

    @Getter @Setter
    @Column(name = "nome", length = 50)
    private String name;

    @Getter @Setter
    @Column(name = "imagens", columnDefinition = "boolean default false", nullable = false)
    private String imagens;

    @Getter @Setter
    @JoinColumn(name = "id_usuario")
    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario usuario;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", length = 30, nullable = false)
    private TipoDeBanner tipo;

    @Getter @Setter
    @Column(name = "countImagens")
    private Integer countImagens;
}
