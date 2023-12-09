package com.ifes.projetoorigame.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ifes.projetoorigame.model.Categoria;
import com.ifes.projetoorigame.model.Epico;
import com.ifes.projetoorigame.model.Relevancia;
import com.ifes.projetoorigame.model.TipoHistoriaUsuario;

import lombok.Data;

@Data

public class HistoriaUsuarioDTO {

    private String titulo;

    private String descricao;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
   private Relevancia relevancia;

   @JsonFormat(shape = JsonFormat.Shape.STRING)
   private Categoria categoria;

    private int tipoHistoria;

    private int epico;
    
    //private List<Integer> dependencias;
    
}
