package br.com.femina.entities;

import br.com.femina.enums.Enums;
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
    private String nome;

    @Getter @Setter
    @Column(name = "imagens", nullable = false)
    private String imagens;

    @Getter @Setter
    @JoinColumn(name = "id_usuario")
    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario usuario;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", length = 30, nullable = false)
    private Enums.TipoDeBanner tipo;

}
