package com.ifes.projetoorigame.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ifes.projetoorigame.dto.TipoTarefaDTO;
import com.ifes.projetoorigame.exception.NotFoundException;
import com.ifes.projetoorigame.model.Tarefa;
import com.ifes.projetoorigame.model.TipoHistoriaUsuario;
import com.ifes.projetoorigame.model.TipoTarefa;
import com.ifes.projetoorigame.repository.TipoHistoriaUsuarioRepository;
import com.ifes.projetoorigame.repository.TipoTarefaRepository;

@Component
public class TipoTarefaApplication {
    @Autowired
    private TipoTarefaRepository repository;

    @Autowired
    private TipoHistoriaUsuarioRepository repoTipoH;
    @Autowired
    private TarefaApplication tarefaApplication;

    public TipoTarefa create(TipoTarefaDTO dto){
        
        TipoTarefa tipotarefa = new TipoTarefa(); 
        TipoHistoriaUsuario thu = (repoTipoH.findById(dto.getTipoHistoria())).get();

        tipotarefa.setDescricao(dto.getDescricao());
        tipotarefa.setHistoriaUsuario(thu);
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
    
    public List<TipoTarefa> getAll(){
        return repository.findAll();
    }

    public void update(int id,TipoTarefaDTO dto){
        TipoTarefa tipoTarefa;
        try {
            TipoHistoriaUsuario thu = (repoTipoH.findById(dto.getTipoHistoria())).get();

            tipoTarefa = this.getById(id);
            tipoTarefa.setDescricao(dto.getDescricao());
            tipoTarefa.setHistoriaUsuario(thu);
            this.repository.save(tipoTarefa);
           
        } catch (NotFoundException e) {
            e.getMessage();
        }
    }
    public void delete(int id){
        List<Tarefa> tarefas = tarefaApplication.getAll();
        for(Tarefa tarefa: tarefas){
            if(tarefa.getTipoTarefa().getId()==id){
                tarefaApplication.delete(tarefa.getId());
            }
        }
        repository.deleteById(id);
    }
}
