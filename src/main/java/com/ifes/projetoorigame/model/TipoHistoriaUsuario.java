package com.ifes.projetoorigame.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tipo_historia_de_usuario")
public class TipoHistoriaUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "tipo_epico_id") // Coluna que faz referência à chave primária de TipoEpico
    private TipoEpico tipoEpico;

}
