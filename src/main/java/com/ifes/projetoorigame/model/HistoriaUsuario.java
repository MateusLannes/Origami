package com.ifes.projetoorigame.model;

import java.util.List;


import org.hibernate.mapping.OneToMany;

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
@Table(name = "historia_usuario")
public class HistoriaUsuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column (name = "titulo")
    private String titulo;

    @Column(name ="descricao")
    private String descricao;

    @Column
    @Enumerated(EnumType.STRING)
    private Relevancia relevancia;

    /*@ManyToOne
    @JoinColumn*/
    private String categoria;
   
    @ManyToOne
    @JoinColumn
    private TipoHistoriaUsuario tipoHistoria;

    @ManyToOne
    @JoinColumn
    private Epico epico;

    }
