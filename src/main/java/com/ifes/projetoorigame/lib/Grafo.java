
package com.ifes.projetoorigame.lib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * @author maressakaren
 */
@Component
public class Grafo <T>{

    public ArrayList<Aresta<T>> arestas;
    public ArrayList<Vertice<T>> vertices;
    protected Comparator<T> comparador;

    public Grafo(){
        this.vertices = new ArrayList<>();
        this.arestas = new ArrayList<>();
    }
    public List<Vertice<T>> getVertices(){ return this.vertices;}


    public Vertice<T> adicionaVertice(T valor){
        Vertice<T> novo = new Vertice<T>(valor);
        this.vertices.add(novo);
        return novo;
    }

    public Vertice<T> obterVertice (T valor){
        for(Vertice<T> vertice: vertices){
            if(vertice.getValor()!= null && vertice.getValor().equals(valor)){ return vertice;}
        }
        return null;
    }

    public void adicionarAresta( Vertice<T> origem, Vertice<T> destino, float peso){
        Vertice<T> verticeOrigem, verticeDestino;
        Aresta<T> novAresta;
        List<Vertice<T>> visitados  = new ArrayList<>();

        verticeOrigem = obterVertice(origem.getValor());

        if( verticeOrigem == null){
            verticeOrigem = adicionaVertice(origem.getValor());
        
        }
        
        verticeDestino = obterVertice(destino.getValor());

        if ( verticeDestino == null){
            verticeDestino = adicionaVertice(destino.getValor());
        }

        novAresta = new Aresta<T>(verticeOrigem, verticeDestino, peso);
        this.arestas.add(novAresta);
        /*if( verificaCiclo()){
            System.out.println("\n Aresta não adicionada pois há um ciclo nesse caminho: \n");
            buscaEmProfundidadeRecursiva(verticeOrigem, visitados);
            this.arestas.remove(novAresta);
        }*/

    }

    public List<Aresta<T>> obterDestinos(Vertice<T> vertice){
        ArrayList<Aresta<T>> destinos = new ArrayList<Aresta<T>>();

        for( Aresta<T> aresta: this.arestas){
            
            if(aresta.getOrigem()!=null && aresta.getOrigem().equals(vertice)){ destinos.add(aresta);}

        }
        return destinos;
    }

    

    public void buscaEmProfundidade() {
        System.out.println("\n----------------- BUSCA EM PROFUNDIDADE ----------------- \n");
        List<Vertice<T>> visitados = new ArrayList<>();

        /* Loop que percorre todos os vertices para que sejam visitados, mesmo se não tiver uma aresta de origem */
        for (Vertice<T> vertice : this.vertices) { 
            if (!visitados.contains(vertice)) {
                buscaEmProfundidadeRecursiva(vertice, visitados);
            }
        }
    }
    private void buscaEmProfundidadeRecursiva( Vertice<T> atual, List<Vertice<T>> visitados){ 
        System.out.println(atual.getValor());
        visitados.add(atual);

        List<Aresta<T>> destinos = this.obterDestinos(atual);
        for(Aresta<T> aresta: destinos){
            Vertice<T> proximo = aresta.getDestino();
            if(!visitados.contains(proximo)){
                buscaEmProfundidadeRecursiva(proximo, visitados);
            }
        }

    }

    public boolean verificaCiclo(){
        List<Vertice<T>> visitados = new ArrayList<>();
        List<Vertice<T>> caminhoAtual = new ArrayList<>();

        for(Vertice<T> vertice : this.vertices){
            if(!visitados.contains(vertice)){
                if(verificaCicloRecursivo(vertice, visitados, caminhoAtual)){
                    return true;
                }

            }
        }

        return false;  

    }

    public boolean verificaCicloRecursivo(Vertice<T> atual, List<Vertice<T>> visitados, List<Vertice<T>> caminhoAtual){
        visitados.add(atual);
        caminhoAtual.add(atual);
        List<Aresta<T>> destinos = this.obterDestinos(atual);

        for(Aresta<T> aresta : destinos){
            Vertice<T> proximo = aresta.getDestino();

            if(!visitados.contains(proximo)){
                if( verificaCicloRecursivo(proximo, visitados, caminhoAtual)){
                    return true;
                }
            }else if( caminhoAtual.contains(proximo)){
                return true;
            }

        }
        caminhoAtual.remove(atual);

        return false;
 
    }

    /*public List<Vertice<T>> ordenacaoTopologica(){

        List<Vertice<T>> resultado = new ArrayList<>();
        int [] tempo  = new int [vertices.size()];  // Cria uma lista do tamanho da quantidade de vertices que há no grafo

        //Inicia a contagem do tempo para cada vertice

        for( Vertice<T> vertice : this. vertices){
            List<Aresta<T>> destinos = obterDestinos(vertice);

            for( Aresta<T> aresta : destinos){
                Vertice<T> proximo = aresta.getDestino();
                tempo[this.vertices.indexOf(proximo)]++;  // incrementa o valor do indice correspondente ao proximo, esse indice contem o numero de arestas quee apontam para ele
            }
        }

        //Adiciona os vertices sem tempo a lista

        for( Vertice<T> vertice : vertices){
            if( tempo[vertices.indexOf(vertice)]== 0){  // Verifica os vertices que não tem vizinhos 
                resultado.add(vertice);
            }
        }

        for( int i = 0; i < resultado.size(); i++){
            Vertice<T> atual = resultado.get(i);
            List<Aresta<T>> destinos = obterDestinos(atual);
            for(Aresta<T> aresta : destinos ){

                int indexProximo = vertices.indexOf(aresta.getDestino());
                tempo[indexProximo]--;

                //Quando o tempo for 0, eu posso adicionar a lista

                if(tempo[indexProximo] == 0){
                    resultado.add(aresta.getDestino());
                }
            }
        }
        return resultado;

    }*/

    public List<Vertice<T>> ordenacaoTopologicaDsf(){
        List<Vertice<T>> resultado = new ArrayList<>();
        List<Vertice<T>> visitados = new ArrayList<>();

        for( Vertice<T> vertice : vertices){
            if( !visitados.contains(vertice)){
                dsf(vertice, visitados,resultado);
            }
        }

        Collections.reverse(resultado);

        return resultado;

    }

    private void dsf(Vertice<T> vertice, List<Vertice<T>> visitados, List<Vertice<T>> resultado){
        visitados.add(vertice);
        List<Aresta<T>> destinos = obterDestinos(vertice);

        for( Aresta<T> aresta : destinos){
            Vertice<T> proximo = aresta.getDestino();
            if( !visitados.contains(proximo)){
                dsf(proximo,visitados,resultado);
            }
        }
        resultado.add(vertice);
    }

    public void imprimirTopologia(){
        List<Vertice<T>> resultado = ordenacaoTopologicaDsf();

        for( Vertice<T> vertice: resultado){
            System.out.println(vertice.getValor());
        }
    }
    
    public void removerAresta(Vertice<T> origem, Vertice<T> destino) {
        Aresta<T> arestaParaRemover = null;

        for (Aresta<T> aresta : this.arestas) {
            if (aresta.getOrigem().equals(origem) && aresta.getDestino().equals(destino)) {
                arestaParaRemover = aresta;
                break;
            }
        }

        if (arestaParaRemover != null) {
            this.arestas.remove(arestaParaRemover);
        } else {
            System.out.println("Aresta não encontrada para remoção.");
        }
    }

}
