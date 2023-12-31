package com.ifes.projetoorigame.lib;

import java.util.Comparator;

import org.springframework.stereotype.Component;

import com.ifes.projetoorigame.model.Epico;

@Component
public class ComparadorEpicoPorId implements Comparator<Epico> {

    
    public int compare(Epico ep1, Epico ep2) {
            
        return Integer.compare(ep1.getId(), ep2.getId());
    }
}