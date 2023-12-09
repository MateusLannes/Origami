package com.ifes.projetoorigame.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import java.util.List;

@Entity
@Data
@Table( name = "tipo_tarefa")
public class TipoTarefa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "descricao")
    private String descricao;

    @ManyToOne
    @JoinColumn
    private TipoHistoriaUsuario tipoHistoriaUsuario;

    @ManyToMany
    List<TipoTarefa> listaDependentes;
    
}
