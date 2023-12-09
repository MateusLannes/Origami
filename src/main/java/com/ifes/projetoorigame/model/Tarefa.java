package com.ifes.projetoorigame.model;

import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table(name = "tarefa")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column
    private String titulo;

    @Column
    private String descricao;

    @ManyToOne
    @JoinColumn
    private HistoriaUsuario historia_usuario;

    @ManyToOne
    @JoinColumn
    private TipoTarefa tipoTarefa;
    
    @ManyToMany
    private List<Tarefa> dependentes;
    
}
