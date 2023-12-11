package com.ifes.projetoorigame.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ifes.projetoorigame.dto.TipoHistoriaUsuarioDTO;
import com.ifes.projetoorigame.exception.NotFoundException;
import com.ifes.projetoorigame.lib.Grafo;
import com.ifes.projetoorigame.lib.Vertice;
import com.ifes.projetoorigame.model.Epico;
import com.ifes.projetoorigame.model.HistoriaUsuario;
import com.ifes.projetoorigame.model.Tarefa;
import com.ifes.projetoorigame.model.TipoEpico;
import com.ifes.projetoorigame.model.TipoHistoriaUsuario;
import com.ifes.projetoorigame.model.TipoTarefa;
import com.ifes.projetoorigame.repository.HistoriaUsuarioRepository;
import com.ifes.projetoorigame.repository.TarefaRepository;
import com.ifes.projetoorigame.repository.TipoEpicoRepository;
import com.ifes.projetoorigame.repository.TipoHistoriaUsuarioRepository;


@Component
@Service

public class TipoHistoriaUsuarioApplication {
    @Autowired
    private TipoHistoriaUsuarioRepository tipoHURepository;
    @Autowired
    private TipoEpicoRepository repoTipoEpico;
    @Autowired
    private TipoTarefaApplication tipoTarefaApp;
    @Autowired HistoriaUsuarioApplication hUsuarioApplication;

    @Autowired
    private Grafo<TipoHistoriaUsuario> grafoHU;

    
    @Autowired
    private Grafo<Integer> grafoHUINT;

    
    private TipoEpico getTipoEpico(int id){
        Optional<TipoEpico> op = repoTipoEpico.findById(id);
        return op.get();
    }
    public TipoHistoriaUsuario create(TipoHistoriaUsuarioDTO dto){

        TipoHistoriaUsuario tipoHU = new TipoHistoriaUsuario();
        tipoHU.setDescricao(dto.getDescricao());
        tipoHU.setTipoEpico(getTipoEpico(dto.getTipoEpico()));

        grafoHU.adicionaVertice(tipoHU); 

        return tipoHURepository.save(tipoHU);
    }

    public TipoHistoriaUsuario getById(int id) throws NotFoundException{
        TipoHistoriaUsuario tipoHU;
        Optional<TipoHistoriaUsuario> opTipoHU;
        
        opTipoHU = tipoHURepository.findById(id);
        if(opTipoHU.isPresent()){ 
            tipoHU = opTipoHU.get();
            return tipoHU;
        }

        throw new NotFoundException("Tipo de História de Usuário não encontrado.");
    }
    
    public List<TipoHistoriaUsuario> getAll(int idTipoEpico){
        return tipoHURepository.findByTipoEpicoId(idTipoEpico);
    }

    public void update(int id,TipoHistoriaUsuarioDTO dto){
        TipoHistoriaUsuario tipoHU;
        try {
            tipoHU = getById(id);
            tipoHU.setDescricao(dto.getDescricao());
            tipoHU.setTipoEpico(getTipoEpico(dto.getTipoEpico()));
            tipoHURepository.save(tipoHU); 

        }catch (NotFoundException e)
        {
            e.getMessage();
        }
    }
    public void delete(int id){
        List<HistoriaUsuario> hu_list = hUsuarioApplication.getAllTipo(id);
        List<TipoTarefa> tipoTarefas = tipoTarefaApp.getAll(id);
        for(HistoriaUsuario hu:hu_list){
            if(hu.getTipoHistoria()!=null && hu.getTipoHistoria().getId()==id){
                hUsuarioApplication.delete(hu.getId()); 
            }
        }
        for(TipoTarefa tipoT: tipoTarefas){
            if(tipoT.getTipoHistoriaUsuario().getId() == id){
                tipoTarefaApp.delete(tipoT.getId());
            }
        }
        tipoHURepository.deleteById(id);
    }




    public TipoHistoriaUsuario gerarDependentes(int idTHU,List<Integer> listIds) {
        List<TipoHistoriaUsuario> listaTH = new ArrayList<>();
        List<TipoHistoriaUsuario> ListTiposHU = tipoHURepository.findAll();
        grafoHUINT = new Grafo<>();
        
        
        //ADICIONA TODOS OS VERTICES
        for (TipoHistoriaUsuario each : ListTiposHU) {
            grafoHUINT.adicionaVertice( each.getId());
            
        }

        for (TipoHistoriaUsuario oneTHU : ListTiposHU) {
            List<TipoHistoriaUsuario> THUESeusDependentes = oneTHU.getListaDependentes();
            for (TipoHistoriaUsuario THUESeusDependentes1 : THUESeusDependentes) { 
                grafoHUINT.adicionarAresta(grafoHUINT.obterVertice(oneTHU.getId()), grafoHUINT.obterVertice(THUESeusDependentes1.getId()), 1);
                
            }
        } 
        
        
        try{
            TipoHistoriaUsuario hu = getById(idTHU);
            for (Integer ids : listIds) {
                grafoHUINT.adicionarAresta(grafoHUINT.obterVertice(idTHU), grafoHUINT.obterVertice(ids), 1);
                if(grafoHUINT.verificaCiclo()){
                    System.out.println("\n\n TEM CICLO \n\n");
                }else{
                    listaTH.add(getById(ids));
                    hu.setListaDependentes(listaTH);
                }
            }
            System.out.println("\n\n N TEM CICLO \n\n");
            grafoHUINT.imprimirTopologia();

            System.out.println("\n\n FIM DO GRAFO DE INTEIRO \n\n");


            return tipoHURepository.save(hu);

        }catch (Exception e) {
        }

        return null;
    }



    //adiciona todos os vertices no grafo
    private Grafo geraGrafoComVertice(List<TipoHistoriaUsuario> AllTHU){
        for (TipoHistoriaUsuario oneTHU : AllTHU) {
            grafoHU.adicionaVertice(oneTHU);
        }  
        return grafoHU;      
    }

    private Grafo geraGrafoComAresta(List<TipoHistoriaUsuario> ListTiposHU){

        for (TipoHistoriaUsuario oneTHU : ListTiposHU) {
            List<TipoHistoriaUsuario> THUESeusDependentes = oneTHU.getListaDependentes();
            for (TipoHistoriaUsuario THUESeusDependentes1 : THUESeusDependentes) { 
                grafoHU.adicionarAresta(grafoHU.obterVertice(oneTHU), grafoHU.obterVertice(THUESeusDependentes1), 1);
            }
        } 
        return grafoHU;
    }
    
}
