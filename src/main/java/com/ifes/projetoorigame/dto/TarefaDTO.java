package com.ifes.projetoorigame.dto;

import lombok.Data;

@Data
public class TarefaDTO {

    private String titulo;

    private String descricao;

    private int historia_usuario;

    private int tipoTarefa;
    
}
