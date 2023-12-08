package com.ifes.projetoorigame.lib;

import java.util.ArrayList;
import java.util.List;

public class Vertice<T> {


    private T valor = null;

    public Vertice (T valor){
        this.valor = valor;
    }

    public T getValor(){
        return this.valor;
    }

    public void setValor(T valor){
        this.valor = valor;

    }
    
}