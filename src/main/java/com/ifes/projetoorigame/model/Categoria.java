package com.ifes.projetoorigame.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "categoria")
public class Categoria
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nome")
    private String nome;
}
