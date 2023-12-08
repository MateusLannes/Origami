package com.ifes.projetoorigame.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "projeto")
public class Projeto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nome")
    private String nome;
}
