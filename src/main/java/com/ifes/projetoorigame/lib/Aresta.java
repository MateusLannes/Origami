package com.ifes.projetoorigame.lib;

public class Aresta<T> {

    private Vertice<T> origem, destino = null;
    private float peso ;

    public  Aresta( Vertice<T> origem, Vertice<T> destino,float peso){
        this.destino = destino;
        this.origem = origem;
        this.peso = peso;
    }
    public Vertice<T> getOrigem() {
        return origem;
    }

    public void setOrigem(Vertice<T> origem) {
        this.origem = origem;
    }

    public Vertice<T> getDestino() {
        return destino;
    }

    public void setDestino(Vertice<T> destino) {
        this.destino = destino;
    }

    public float getPeso() {
        return peso;
    }
    /*private Vertice origem;
    private Vertice destino;
    private int peso;

    public Aresta(Vertice origem, Vertice destino, int peso) {
        this.origem = origem;
        this.destino = destino;
        this.peso = peso;
    }

    public Aresta(Vertice origem, Vertice destino) {
        this.origem = origem;
        this.destino = destino;
        this.peso = 1;
    }

    public Vertice getOrigem() {
        return origem;
    }

    public void setOrigem(Vertice origem) {
        this.origem = origem;
    }

    public Vertice getDestino() {
        return destino;
    }

    public void setDestino(Vertice destino) {
        this.destino = destino;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    @Override
    public String toString() {
        return origem.getNome() + " -> " + destino.getNome() + "(" + getPeso() + ")";
    }*/
}

