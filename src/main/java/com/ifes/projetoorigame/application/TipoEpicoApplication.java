package com.ifes.projetoorigame.application;

import com.ifes.projetoorigame.dto.TipoEpicoDTO;
import com.ifes.projetoorigame.exception.NotFoundException;
import com.ifes.projetoorigame.model.Epico;
import com.ifes.projetoorigame.model.TipoEpico;
import com.ifes.projetoorigame.model.TipoHistoriaUsuario;
import com.ifes.projetoorigame.repository.EpicoRepository;
import com.ifes.projetoorigame.repository.TipoEpicoRepository;
import com.ifes.projetoorigame.repository.TipoHistoriaUsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Component
@Service
public class TipoEpicoApplication
{
    @Autowired
    private TipoEpicoRepository repository;
    @Autowired
    private EpicoRepository repoEpico;
    @Autowired
    private TipoHistoriaUsuarioApplication tipoHUApp;
    @Autowired
    private EpicoApplication epicoApplication;
    

    public TipoEpico create(TipoEpicoDTO tipoEpicoDTO)
    {
        TipoEpico tipoEpico;

        tipoEpico = new TipoEpico();
        tipoEpico.setDescricao(tipoEpicoDTO.getDescricao());

        return this.repository.save(tipoEpico);
    
        
    }

    public TipoEpico retrieve(int id) throws NotFoundException
    {
        TipoEpico tipoEpico;
        Optional<TipoEpico> entity;

        entity = repository.findById(id);

        if (entity.isPresent()) tipoEpico = entity.get();
        else throw new NotFoundException("Tipo Épico não encontrado.");

        return tipoEpico;
    }

    public List<TipoEpico> retrieveAll()
    {
        return repository.findAll();
    }

    public void update(int id, TipoEpicoDTO tipoEpicoDTO)
    {
        TipoEpico tipoEpico;

        try
        {
            tipoEpico = retrieve(id);
            //tipoEpico.setNome(tipoEpicoDTO.getNome());
            tipoEpico.setDescricao(tipoEpicoDTO.getDescricao());

            repository.save(tipoEpico);
        }
        catch (NotFoundException e)
        {
            e.getMessage();
        }
    }

    public void delete(int id){

        try {
            TipoEpico tipoEpico = retrieve(id);
            List<Epico> epicos = repoEpico.findAll();
            List<TipoHistoriaUsuario> tiposHU = tipoHUApp.getAll(id);
            for(Epico epico: epicos){
                epicoApplication.delete(epico.getId());
            }
            for(TipoHistoriaUsuario thu :tiposHU){
                if(thu.getTipoEpico()!= null && thu.getTipoEpico().equals(tipoEpico)){
                    tipoHUApp.delete(thu.getId());
                }
            }
            
            
        } catch (NotFoundException e) {
            e.getMessage();
        }
        repository.deleteById(id);
    
    }
}