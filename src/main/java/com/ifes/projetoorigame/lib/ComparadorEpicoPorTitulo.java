package com.ifes.projetoorigame.lib;

import java.util.Comparator;

import org.springframework.stereotype.Component;

import com.ifes.projetoorigame.model.Epico;

@Component
public class ComparadorEpicoPorTitulo implements Comparator<Epico> {

    @Override
    public int compare(Epico ep1, Epico ep2) {
        // Use o método compareToIgnoreCase para comparar os títulos sem diferenciar maiúsculas e minúsculas
        return ep1.getTitulo().compareToIgnoreCase(ep2.getTitulo());
    }
}
