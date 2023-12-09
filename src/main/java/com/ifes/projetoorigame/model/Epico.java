package com.ifes.projetoorigame.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "epico")
@NoArgsConstructor
@AllArgsConstructor
public class Epico
{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column
   private int id;

   @Column
   private String titulo;
   
   @Column
   private String descricao;

   @Column
   @Enumerated(EnumType.STRING)
   private Relevancia relevancia;

   @Column
   @Enumerated(EnumType.STRING)
   private Categoria categoria;

   @ManyToOne (cascade =  CascadeType.ALL)
   @JoinColumn(name = "id_projeto")
   private Projeto projeto;

   @ManyToOne
   @JoinColumn
   private TipoEpico tipoEpico;

   
   
}