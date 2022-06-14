package br.com.femina.entities;

import lombok.AllArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@AllArgsConstructor
@Entity
@Table(name = "clientes", schema = "public")
public class Cliente extends Usuario { }
