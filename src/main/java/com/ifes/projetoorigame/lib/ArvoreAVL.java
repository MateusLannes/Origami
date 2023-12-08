package com.ifes.projetoorigame.lib;

import java.util.Comparator;

/**
 *
 * @author jramso
 * @author fernandajaimara
 * @author maressakaren
 * @author MateusLannes
 * @author Rafael-byte-tech
 * 
 */
public class ArvoreAVL<T> extends ArvoreBinaria<T>
{
    public ArvoreAVL(Comparator<T> comparator)
    {
        super(comparator);
    }

    
    /*public void adicionar(T novoValor)
    {
        super.adicionar(novoValor);
        balancear();
    }*/

    @Override
    public T remover(T valor) {
        T removido;

        removido = super.remover(valor);

        //balancear();

        return removido;
    }

    /*private void balancear()
    {
        if (fatorDeBalanceamento(this.raiz) > 1)
        {
            if (fatorDeBalanceamento(this.raiz.getFilhoDireita()) > 0)
            {
                this.raiz = rotacaoEsquerda(this.raiz);
            }
            else
            {
                this.raiz = rotacaoDireitaEsquerda(this.raiz);
            }
        }
        else if (fatorDeBalanceamento(this.raiz) < -1)
        {
            if (fatorDeBalanceamento(this.raiz.getFilhoEsquerda()) < 0)
            {
                this.raiz = rotacaoDireita(this.raiz);
            }
            else
            {
                this.raiz = rotacaoEsquerdaDireita(this.raiz);
            }
        }
    }*/

    private No<T> rotacaoEsquerda(No<T> no) {
        No<T> noAuxiliar = no.getFilhoDireita();
        no.setFilhoDireita(noAuxiliar.getFilhoEsquerda());
        noAuxiliar.setFilhoEsquerda(no);

        return noAuxiliar;
    }

    private No<T> rotacaoEsquerdaDireita(No<T> no) {
        no.setFilhoEsquerda(rotacaoEsquerda(no.getFilhoEsquerda()));
        return rotacaoDireita(no);
    }

    private No<T> rotacaoDireita(No<T> no) {
        No<T> noAuxiliar = no.getFilhoEsquerda();
        no.setFilhoEsquerda(noAuxiliar.getFilhoDireita());
        noAuxiliar.setFilhoDireita(no);
        return noAuxiliar;
    }

    private No<T> rotacaoDireitaEsquerda(No<T> no) {
        no.setFilhoDireita(rotacaoDireita(no.getFilhoDireita()));
        return rotacaoEsquerda(no);
    }

    public int fatorDeBalanceamento(No<T> no)
    {
        int alturaDireita = altura(no.getFilhoDireita());
        int alturaEsquerda = altura(no.getFilhoEsquerda());
        return alturaDireita - alturaEsquerda;
    }

    private No<T> sucessorInorder(No<T> no)
    {
        No<T> current = no.getFilhoDireita();
        while (current != null && current.getFilhoEsquerda() != null)
        {
            current = current.getFilhoEsquerda();
        }
        return current;
    }
    @Override
    public void adicionar(T valor){
        super.adicionar(valor);
    }

    @Override
    protected No<T> adicionarRecursivo(No<T> no,No<T> novoValor){
        no = super.adicionarRecursivo(no, novoValor);

        if (fatorDeBalanceamento(no) > 1)
        {
            if (fatorDeBalanceamento(no.getFilhoDireita()) > 0)
            {
                no = rotacaoEsquerda(no);
            }
            else
            {
                no = rotacaoDireitaEsquerda(no);
            }
        }
        else if (fatorDeBalanceamento(no) < -1)
        {
            if (fatorDeBalanceamento(no.getFilhoEsquerda()) < 0)
            {
                no = rotacaoDireita(no);
            } else
            {
                no = rotacaoEsquerdaDireita(no);
            }
        }

        return no;
    }
}