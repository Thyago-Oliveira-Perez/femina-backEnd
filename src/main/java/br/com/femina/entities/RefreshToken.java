package br.com.femina.entities;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

import javax.persistence.*;

@Entity(name = "refreshtoken")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @Getter @Setter
    private Usuario user;

    @Column(nullable = false, unique = true)
    @Getter @Setter
    private String token;

    @Column(nullable = false)
    @Getter @Setter
    private Instant expiryDate;
}
