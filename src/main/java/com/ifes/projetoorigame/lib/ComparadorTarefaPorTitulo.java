package com.ifes.projetoorigame.lib;

import java.util.Comparator;

import org.springframework.stereotype.Component;

import com.ifes.projetoorigame.model.Tarefa;

@Component
public class ComparadorTarefaPorTitulo implements Comparator<Tarefa> {

    @Override
    public int compare(Tarefa tarefa1, Tarefa tarefa2) {
        // Use o método compareToIgnoreCase para comparar os títulos sem diferenciar maiúsculas e minúsculas
        return tarefa1.getTitulo().compareToIgnoreCase(tarefa2.getTitulo());
    }
}
