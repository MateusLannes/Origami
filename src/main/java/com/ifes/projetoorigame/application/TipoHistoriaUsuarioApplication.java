package com.ifes.projetoorigame.application;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ifes.projetoorigame.dto.TipoHistoriaUsuarioDTO;
import com.ifes.projetoorigame.exception.NotFoundException;
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
    
    private TipoEpico getTipoEpico(int id){
        Optional<TipoEpico> op = repoTipoEpico.findById(id);
        return op.get();
    }
    public TipoHistoriaUsuario create(TipoHistoriaUsuarioDTO dto){

        TipoHistoriaUsuario tipoHU = new TipoHistoriaUsuario();
        tipoHU.setDescricao(dto.getDescricao());
        tipoHU.setTipoEpico(getTipoEpico(dto.getTipoEpico()));
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
    
    public List<TipoHistoriaUsuario> getAll(){
        return tipoHURepository.findAll();
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
        List<HistoriaUsuario> hu_list = hUsuarioApplication.getAll();
        List<TipoTarefa> tipoTarefas = tipoTarefaApp.getAll();
        for(HistoriaUsuario hu:hu_list){
            if(hu.getTipoHistoria()!=null && hu.getTipoHistoria().getId()==id){
                hUsuarioApplication.delete(hu.getId()); 
            }
        }
        for(TipoTarefa tipoT: tipoTarefas){
            if(tipoT.getHistoriaUsuario().getId() == id){
                tipoTarefaApp.delete(tipoT.getId());
            }
        }
        tipoHURepository.deleteById(id);
    }
    
}