/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editorr.
 */
package com.ifes.projetoorigame.lib;

public class No<T>
{
    private T valor;
    private No<T> filhoDireita;
    private No<T> filhoEsquerda;
    //private No<T> pai;

    public No(T valor)
    {
        this.valor = valor;
        this.filhoDireita = null;
        this.filhoEsquerda = null;
        //this.pai = null;
    }

    public T getValor()
    {
        return valor;
    }

    public void setValor(T valor)
    {
        this.valor = valor;
    }

    public No<T> getFilhoDireita()
    {
        return filhoDireita;
    }

    public void setFilhoDireita(No<T> filhoDireita)
    {
        this.filhoDireita = filhoDireita;
    }

    public No<T> getFilhoEsquerda()
    {
        return filhoEsquerda;
    }

    public void setFilhoEsquerda(No<T> filhoEsquerda)
    {
        this.filhoEsquerda = filhoEsquerda;
    }

    /*public No<T> getPai()
    {
        return pai;
    }

    public void setPai(No<T> pai)
    {
        this.pai = pai;
    }*/
}