package com.ifes.projetoorigame.application;

import com.ifes.projetoorigame.lib.Grafo;

import com.ifes.projetoorigame.dto.EpicoDTO;
import com.ifes.projetoorigame.exception.NotFoundException;
import com.ifes.projetoorigame.model.Epico;
import com.ifes.projetoorigame.model.HistoriaUsuario;
import com.ifes.projetoorigame.model.Projeto;
import com.ifes.projetoorigame.model.TipoEpico;
import com.ifes.projetoorigame.model.TipoHistoriaUsuario;
import com.ifes.projetoorigame.repository.EpicoRepository;
import com.ifes.projetoorigame.repository.HistoriaUsuarioRepository;
import com.ifes.projetoorigame.repository.ProjetoRepository;
import com.ifes.projetoorigame.repository.TipoEpicoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
@Service

public class EpicoApplication
{
    @Autowired
    private EpicoRepository repository;
    @Autowired
    private ProjetoRepository repoProjeto;
    @Autowired
    private TipoEpicoRepository repoTipoEpico;
    @Autowired
    private HistoriaUsuarioApplication hu_app;

    @Autowired
    private Grafo<Integer> grafo;

    

    /**
     * Cria um novo épico com base nos dados fornecidos pelo DTO.
     * 
     * @param epicoDTO O DTO contendo os dados para criar o novo épico.
     * @return O novo épico criado ou null se o tipo de épico ou o projeto não forem encontrados.
     */
    public Epico create(EpicoDTO epicoDTO)
    {
        Epico epico = new Epico();
        
        epico.setTitulo(epicoDTO.getTitulo());
        epico.setDescricao(epicoDTO.getDescricao());
        epico.setRelevancia(epicoDTO.getRelevancia());
        
        Optional<TipoEpico> op = repoTipoEpico.findById(epicoDTO.getTipoEpico_id());
        Optional<Projeto> op2 = repoProjeto.findById(epicoDTO.getProjeto_id());

        // Verifica se o tipo de épico e o projeto foram encontrados
        if(op.isPresent() && op2.isPresent()){
            epico.setCategoria(epicoDTO.getCategoria());
            epico.setProjeto(op2.get());
            epico.setTipoEpico(op.get());
            repository.save(epico);
            
            hu_app.gera(epico);
        }
        
        return epico;
    }


    /**
     * Recupera um e épicos específico.
     *
     * @param id O identificador único do Épico.
     */    
    public Epico retrieve(int id) throws NotFoundException
    {
        Optional<Epico> entity;

        entity = repository.findById(id);

        if (entity.isPresent()) return entity.get();
        else throw new NotFoundException("Épico não encontrado");
    }


    /**
     * Recupera todos os épicos associados a um projeto específico.
     *
     * @param idProjeto O identificador único do projeto.
     */
    public List<Epico> retrieveAll(int idProjeto)
    {
        return repository.findAllByProjeto(idProjeto); 
    }


    /**
     * Atualiza as informações de um épico com base no ID fornecido e nos dados do DTO.
     *
     * @param id O identificador único do épico a ser atualizado.
     * @param epicoDTO O DTO contendo os novos dados para atualização.
     * @return O épico atualizado ou null se o épico não for encontrado.
     */
    public Epico update(int id, EpicoDTO epicoDTO)
    {
        Epico epico;

        try
        {
            epico = retrieve(id);
            epico.setTitulo(epicoDTO.getTitulo());
            epico.setDescricao(epicoDTO.getDescricao());
            epico.setRelevancia(epicoDTO.getRelevancia());
            epico.setCategoria(epicoDTO.getCategoria());
           /*if (epico.getTipoEpico().getHistoriasUser()!=null) {
                List<Epico> dependencias = (epicoDTO.getDependenciasEpico()).stream()
                .map(ids -> repository.findById(ids).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
                epico.setDependencias(dependencias);
            }*/
            return repository.save(epico);

        }
        catch (NotFoundException e)
        {
            e.getMessage();
        }

        return null;
    }



    /**
     * Exclui um épico e suas dependências associadas.
     * Este método remove todas as histórias de usuário vinculadas ao épico,
     * elimina as dependências do épico em outros épicos e, finalmente, exclui o épico.
     *
     * @param id O identificador único do épico a ser excluído.
     */
    public void delete(int id)
    {
        
        try {
            Epico epico = retrieve(id);
            List<HistoriaUsuario> hu_list = hu_app.getAll(id);
            for(HistoriaUsuario hu: hu_list){
                if(hu.getEpico()!=null && hu.getEpico().equals(epico)){
                    hu_app.delete(hu.getId());
                }
            }
            List<Epico> lista = repository.findAllByProjeto(epico.getProjeto().getId());
            for(Epico epic: lista){
                List<Epico> dependencias = epic.getListaDependentes();
                if(dependencias.contains(epico)){
                    dependencias.remove(epico);
                    epic.setListaDependentes(dependencias);

                }
            }
            epico.setListaDependentes(null);
            repository.deleteById(id); 
        } catch (NotFoundException e) {
            e.getMessage();
        }
           
    }

    public Epico gerarDependentes(int idEpico,List<Integer> listIds) {
        List<Epico> listaTH = new ArrayList<>();
        List<Epico> listaEpicos = repository.findAll(); 
        grafo = new Grafo<>();
        
        
        //ADICIONA TODOS OS VERTICES
        for (Epico each : listaEpicos) {
            grafo.adicionaVertice( each.getId());
            
        }

        //adiciona todas arestas ao grafo, mas sem a Dependencia que acabou de ser solocitada : (int idEpico,List<Integer> listIds)
        for (Epico epico : listaEpicos) {
            List<Epico> epicosDependentes = epico.getListaDependentes();
            for (Epico epic : epicosDependentes) { 
                grafo.adicionarAresta(grafo.obterVertice(epico.getId()), grafo.obterVertice(epic.getId()), 1);
                
            }
        } 
        

        try{
            Epico epico = retrieve(idEpico);

            // aqui é adicionado a dependencia que acabou de ser solicitada
            for (Integer ids : listIds) {
                grafo.adicionarAresta(grafo.obterVertice(idEpico), grafo.obterVertice(ids), 1);
                if(grafo.verificaCiclo()){
                    System.out.println("\n\n TEM CICLO \n\n");
                }else{
                    listaTH.add(retrieve(ids));
                    
                }
            }
            epico.setListaDependentes(listaTH);

            System.out.println("\n\n N TEM CICLO \n\n");
            grafo.imprimirTopologia();

            System.out.println("\n\n FIM DO GRAFO DE INTEIRO \n\n");


            return repository.save(epico);

        }catch (Exception e) {
        }

        return null;
    }


    public List<Epico> getAllEpicos(){
        return repository.findAll();
    }


}