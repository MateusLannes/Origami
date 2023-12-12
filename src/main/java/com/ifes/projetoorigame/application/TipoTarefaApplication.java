package com.ifes.projetoorigame.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ifes.projetoorigame.dto.TipoTarefaDTO;
import com.ifes.projetoorigame.exception.NotFoundException;
import com.ifes.projetoorigame.lib.Grafo;
import com.ifes.projetoorigame.model.Tarefa;
import com.ifes.projetoorigame.model.TipoHistoriaUsuario;
import com.ifes.projetoorigame.model.TipoTarefa;
import com.ifes.projetoorigame.repository.TarefaRepository;
import com.ifes.projetoorigame.repository.TipoHistoriaUsuarioRepository;
import com.ifes.projetoorigame.repository.TipoTarefaRepository;

@Component
@Service

public class TipoTarefaApplication {
    @Autowired
    private TipoTarefaRepository repository;

    @Autowired
    private TipoHistoriaUsuarioRepository repoTipoH;
    @Autowired
    private TarefaRepository repoTarefa;
    @Autowired
    private Grafo<Integer> grafoTipoTarefa;

    public TipoTarefa create(TipoTarefaDTO dto){
        
        TipoTarefa tipotarefa = new TipoTarefa(); 
        TipoHistoriaUsuario thu = (repoTipoH.findById(dto.getTipoHistoria())).get();

        tipotarefa.setDescricao(dto.getDescricao());
        tipotarefa.setTipoHistoriaUsuario(thu);
        return this.repository.save(tipotarefa);
        
    
    }

    public TipoTarefa getById(int id) throws NotFoundException{
        TipoTarefa tipotarefa;
        Optional<TipoTarefa> opitional;

        opitional = repository.findById(id);
        if(opitional.isPresent()){
            tipotarefa = opitional.get();
            return tipotarefa;
        }
        else throw new NotFoundException("Tipo tarefa não achado");
    }
    
    public List<TipoTarefa> getAll(int idTipoHU){
        return repository.findByTipoHistoriaId(idTipoHU);
    }

    public void update(int id,TipoTarefaDTO dto){
        TipoTarefa tipoTarefa;
        try {
            TipoHistoriaUsuario thu = (repoTipoH.findById(dto.getTipoHistoria())).get();

            tipoTarefa = this.getById(id);
            tipoTarefa.setDescricao(dto.getDescricao());
            tipoTarefa.setTipoHistoriaUsuario(thu);
            repository.save(tipoTarefa);
            System.out.println(tipoTarefa);
           
        } catch (NotFoundException e) {
            e.getMessage();
        }
    }
    public void delete(int id){
        List<Tarefa> tarefas = repoTarefa.findByTipoHistoriaUsuarioId(id);
        for(Tarefa tarefa: tarefas){
            repoTarefa.deleteById(tarefa.getId());
        }
        repository.deleteById(id);
    }

    public TipoTarefa gerarDependentes(int idTipoTarefa, List<Integer> listaIds){
        List<TipoTarefa> listaDependentes = new ArrayList<>();
        List<TipoTarefa> listTodasTipoTarefa =  repository.findAll();
        grafoTipoTarefa = new Grafo<>();

        for(TipoTarefa tipoTarefa: listTodasTipoTarefa){
            grafoTipoTarefa.adicionaVertice(tipoTarefa.getId());
        }

        for(TipoTarefa tipoTarefa: listTodasTipoTarefa ){
            List<TipoTarefa> listaDep = tipoTarefa.getListaDependentes();

            for(TipoTarefa tipoT : listaDep){
                grafoTipoTarefa.adicionarAresta(grafoTipoTarefa.obterVertice(tipoTarefa.getId()), grafoTipoTarefa.obterVertice(tipoT.getId()),1);
            }
           
        }
        try {
             TipoTarefa tipoTaref = getById(idTipoTarefa);
             for(Integer ids: listaIds){
                grafoTipoTarefa.adicionarAresta(grafoTipoTarefa.obterVertice(idTipoTarefa), grafoTipoTarefa.obterVertice(ids), 1);

                if(grafoTipoTarefa.verificaCiclo()){
                    System.out.println("\n\n TEM CICLO \n\n");
                }else{
                    listaDependentes.add(getById(ids));

                }
             }

             tipoTaref.setListaDependentes(listaDependentes);
             grafoTipoTarefa.imprimirTopologia();
 
             System.out.println("\n\n FIM DO GRAFO DE INTEIRO \n\n");;


            return repository.save(tipoTaref);

           } catch (Exception e) {
            
           }
           return null;


        /*try {
            TipoTarefa tipoTarefa = getById(idTipoTarefa);
            for(Integer id: listaIds){
                listaDependentes.add(getById(id));
                
            }
            tipoTarefa.setListaDependentes(listaDependentes);
            //tipoTarefa.setListaDependentes(listaDependentes);
            return repository.save(tipoTarefa);
        } catch (NotFoundException e) {
            e.getMessage();
        }
        return null;*/

    }

}
