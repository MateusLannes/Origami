package com.ifes.projetoorigame.dto;

import java.util.List;

import com.ifes.projetoorigame.model.Epico;
import com.ifes.projetoorigame.model.Relevancia;
import com.ifes.projetoorigame.model.TipoHistoriaUsuario;

import lombok.Data;

@Data

public class HistoriaUsuarioDTO {

    private String titulo;

    private String descricao;

    private Relevancia relevancia;
    
    private String categoria;

    private int tipoHistoria;

    private int epico;
    
    //private List<Integer> dependencias;
    
}
