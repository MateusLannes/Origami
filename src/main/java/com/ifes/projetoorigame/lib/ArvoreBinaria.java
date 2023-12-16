/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifes.projetoorigame.lib;

import java.util.ArrayList;
import java.util.Comparator;

import org.springframework.stereotype.Component;


/**
 *
 * @author victoriocarvalho
 * @author jramso
 * @author fernandajaimara
 * @author maressakaren
 * @author MateusLannes
 * @author Rafael-byte-tech
 * 
 */
@Component
public class ArvoreBinaria<T> implements IArvoreBinaria<T>
{
    protected No<T> raiz;
    protected Comparator<T> comparador;
    protected No<T> atual;
    protected No<T> proximoNo;
    protected No<T> pai;

    public ArvoreBinaria(Comparator<T> comp)
    {
        this.raiz = null;
        this.comparador = comp;
        this.atual = null;
        this.proximoNo = null;
        //this.pai = null;
    }
    public No<T> getRaiz(){
        return this.raiz;
    }

    @Override
    public void  adicionar (T valor){
        No<T> novoNo  = new No<>(valor);
        if(this.raiz == null){
            this.raiz = novoNo;
        }
        else{
            this.raiz = adicionarRecursivo(this.raiz, novoNo);
        }
        
    }

    protected No<T> adicionarRecursivo(No<T> no, No<T> novoNo)
    {
        if (no == null){return novoNo;}

        if (this.comparador.compare(novoNo.getValor(), no.getValor()) < 0){
            no.setFilhoEsquerda(adicionarRecursivo(no.getFilhoEsquerda(), novoNo));
        }
        else
        {
            no.setFilhoDireita(adicionarRecursivo(no.getFilhoDireita(), novoNo));
        }

        return no;
    }

    @Override
    public T pesquisar(T valor)
    {
        return pesquisar(this.raiz,valor).getValor();
    }

    /**
     * Método que pesquisa e retorna um elemento da árvore se existir
     * @param no - sera utilizado para passar a partir de qual Nó sera pesquisado o elemento (normalmente a partir da raiz)
     * @param valor - valor a ser pesquisado nos nós
     * @return No<T> - anteriormente de tipo T (valor dentro do nó) mas para questoes de reuso foi atualizado para o tipo No<T> se precisar do valor utilize um getValor()
     */
    private No<T> pesquisar(No<T> no,T valor)
    {
        if (no == null){
            return null;
        }
        //compara com a "raiz"
        int comparar = comparador.compare(valor, no.getValor());

        
        if (comparar==0){
            
            return no; // Retorna o nó se foi encontrado
        
        }else if (comparar < 0){ // Se o nó buscado for menor que o no atual, faz a busca no lado esquerdo
    
           return pesquisar(no.getFilhoEsquerda(),valor); //MARESSA MUDOU AQUI, SE TIVER ERRADO COLOQUE getFilhoEsquerda
        }else{
           
            return pesquisar(no.getFilhoDireita(),valor); //MARESSA MUDOU AQUI, SE TIVER ERRADO COLOQUE getFilhoDireita
        }
    }

   
    /**
     * Método que escolhe a forma apropriada para remover o nó da árvore
     * @param no - era utilizado para passar a partir de qual Nó sera pesquisado o elemento para remover (normalmente a partir da raiz)
     * @param valor - o valor que será removido se existir
     * @return T - tipo de valor dos Nós
 

    /**
     * Método que encontra o maior filho de uma árvore ou subarvore
     * @param no - a partir de qual elemento é buscado o maior
     * @return No<T> - tipo nó
     */
    private No<T> encontrarMaiorFilhoEsquerda(No<T> no)
    {
        if (no.getFilhoDireita() == null)
        {
            return no; // Não há filho à direita, então este é o maior valor à esquerda
        }
        
        return encontrarMaiorFilhoEsquerda(no.getFilhoDireita());
    }
    /**
     * Método que retorna a altura da árvore de maneira Recursiva
     * @param no - a partir de qual elemento é buscado a altura
     * @return int - inteiro contador de níveis
     */
    
    @Override
    public int altura()
    {
        return altura(this.raiz);
    }

    protected int altura(No<T> no)
    {
       
        if (no == null) 
            return -1;
        else {
            int alturaDireita = altura(no.getFilhoDireita());
            int alturaEsquerda = altura((no.getFilhoEsquerda()));
            
            if (alturaDireita > alturaEsquerda) 
                return alturaDireita + 1;    
            else
                return alturaEsquerda + 1;
        }
    }   

    

    @Override
    public T remover(T valor) // CORRIGIR ESSE MÉTODO
    {


        this.raiz =  removerRecursivo(this.raiz, valor);
        return valor;
    }

    protected No<T> removerRecursivo(No<T> no, T valor) {
        if (no == null) {
            System.out.println("A ÁRVORE ESTÁ VAZIA");
            return null;
        } else {
            if (comparador.compare(valor, no.getValor()) < 0) {
                no.setFilhoEsquerda(removerRecursivo(no.getFilhoEsquerda(), valor));
            } else if (comparador.compare(valor, no.getValor()) > 0) {
                no.setFilhoDireita(removerRecursivo(no.getFilhoDireita(), valor));
            } else {
                // Nó a ser removido encontrado
                if (no.getFilhoDireita() != null && no.getFilhoEsquerda() != null) {
                    // Nó tem dois filhos
                    No<T> substituto = encontrarMenorNo(no.getFilhoDireita());
                    no.setValor(substituto.getValor());
                    no.setFilhoDireita(removerMenorNo(no.getFilhoDireita()));
                } else {
                    // Nó tem um ou nenhum filho
                    no = (no.getFilhoEsquerda() != null) ? no.getFilhoEsquerda() : no.getFilhoDireita();
                }
            }
            return no;
        }
    }
    

    private No<T> removerMenorNo(No<T> no){
        if (no == null) {  
            System.out.println("  ERRO ");  
        } else if (no.getFilhoEsquerda() != null) {  
            no.setFilhoEsquerda(removerMenorNo(no.getFilhoEsquerda()));  
            return no;  
        } else {  
            return no.getFilhoDireita();  
        }  
        return null; 
    }
    
    
    @Override
    public int quantidadeNos()
    {
        return quantidadeNosRecursiva(this.raiz);
    }

    /**
     * Método que retorna a quantidade de nós da árvore ou subarvore de maneira recursiva
     * @param no - a partir de qual elemento é contado a quantidade de nós
     * @return int - quantidade de nos
     */
    private int quantidadeNosRecursiva(No<T> no)
    {
        
        if (no == null){return 0;}

        else
        {
            return (quantidadeNosRecursiva(no.getFilhoEsquerda()) + quantidadeNosRecursiva(no.getFilhoDireita()) + 1);
        }
    }

    @Override
    public String caminharEmNivel() {
        return "\n CAMINHA EM NIVEL [\n"+caminhaEmNivel()+"\n]\n FIM CAMINHA EM NIVEL\n";
    }
    /**
     * Método que preenche uma fila que ordena os elementos por níveis e coloca a saída em uma ‘String’
     * @return String
     */
    private String caminhaEmNivel() {
        if (this.raiz == null) {
            return "Caminhamento por Nível - Árvore Vazia";
        }
    
        StringBuilder resultado = new StringBuilder();
        ArrayList<No<T>> fila = new ArrayList<>();
        fila.add(this.raiz);
    
        while (!fila.isEmpty()) {
            No<T> atual = fila.remove(0);  // Removendo o primeiro elemento
            resultado.append(atual.getValor()).append("\n");
    
            // Adicionando primeiro o filho esquerdo, e depois o filho direito
            if (atual.getFilhoEsquerda() != null) {
                fila.add(atual.getFilhoEsquerda());
            }
    
            if (atual.getFilhoDireita() != null) {
                fila.add(atual.getFilhoDireita());
            }
        }
    
        return resultado.toString();
    }
    

    
    @Override
    public String caminharEmOrdem() {
        return "[" + caminharEmOrdem(this.raiz) + "]";
    }

    /**
     * Método que monta a ‘String’ de encaminhamento em ordem
     * @param raiz - a partir de qual elemento e feito o caminho
     * @return String
     */
    private String caminharEmOrdem(No<T> raiz)
    {
        if (raiz == null)
        {
            return "";
        }

        String resultado = "";
        resultado += caminharEmOrdem(raiz.getFilhoEsquerda());
        resultado += raiz.getValor() + "\n";
        resultado += caminharEmOrdem(raiz.getFilhoDireita());

        return resultado;
    }

    @Override
    public T obterProximo() {
       if (proximoNo == null) {
            return null; // Árvore vazia ou todos os elementos já foram retornados
        }
    
        T valor = proximoNo.getValor(); // Obtemos o valor do nó atual
        proximoNo = encontrarSucessor(proximoNo); // Encontramos o próximo nó na ordem crescente e atualizamos o próximo
        return valor;
    }
    
    @Override
    public void reiniciarNavegacao()
    {
        this.proximoNo = encontrarMenorNo(this.raiz);
    }
    

    //metodos adicionais

    /**
     *  Método que encontra o menor nó da arvore ou subarvore a partir de um no
     * @param no - no que serve de raiz
     * @return No<T> - tipo No
    */
    private No<T> encontrarMenorNo(No<T> no)
    {
        //o nó recebido deve ser a raiz ou sera o menor nó a partir daquele "galho"
        No<T> atual = no;
        while (atual != null && atual.getFilhoEsquerda() != null)
        {
            atual = atual.getFilhoEsquerda();
        }
        return atual;
    }

    /**
     * Método que encontra o próximo nó para o método de obter próximo - encontrando o valor seguinte ao chamado anteriormente
     * @param no - raiz
     * @return No <T> - tipo nó
     */
    private No<T> encontrarSucessor(No<T> no)
    {
        if (no.getFilhoDireita() != null)
        {
            // se tiver filho a direita o sucessor é o menor do lado desse filho
            return encontrarMenorNo(no.getFilhoDireita());
        }
    
        No<T> sucessor = null;
        // busca o sucessor a partir da raiz
        while (this.raiz != null)
        {
            int comp = comparador.compare(no.getValor(), raiz.getValor());
            if (comp < 0)
            {
                sucessor = this.raiz;
                this.raiz = this.raiz.getFilhoEsquerda();
            }
            else if (comp > 0)
            {
                this.raiz = this.raiz.getFilhoDireita();
            }
            else
            {
                break; // O nó foi encontrado
            }
        }
    
        return sucessor;
    }

    
}