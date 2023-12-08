package com.ifes.projetoorigame.application;

import com.ifes.projetoorigame.dto.ProjetoDTO;
import com.ifes.projetoorigame.exception.NotFoundException;
import com.ifes.projetoorigame.model.Epico;
import com.ifes.projetoorigame.model.Projeto;
import com.ifes.projetoorigame.repository.EpicoRepository;
import com.ifes.projetoorigame.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Service
public class ProjetoApplication
{
    @Autowired
    private ProjetoRepository projetoRepository;
    @Autowired 
    private EpicoApplication epicoApp;
    public Projeto create(ProjetoDTO projetoDTO)
    {
        Projeto projeto;

        projeto = new Projeto();
        projeto.setNome(projetoDTO.getNome());

        return projetoRepository.save(projeto);
    }

    public List<Projeto> getAll(){

        return projetoRepository.findAll();
    }
    

    public Projeto getById(int id) throws NotFoundException{
        Projeto projeto;
        Optional<Projeto> obOptional = projetoRepository.findById(id);

        if(obOptional.isPresent()){
            projeto = obOptional.get();
            return projeto;
        }
        throw new NotFoundException("Projeto não encontrado.");
    }

    public void update(int id, ProjetoDTO projetoDTO)
    {
        Projeto projeto;
        try{
            projeto = getById(id);
            projeto.setNome(projetoDTO.getNome());

            projetoRepository.save(projeto);
        }
        catch (NotFoundException e)
        {
            e.getMessage();
        }
    }

    public void delete(int id){
       
        List<Epico> epicos = epicoApp.retrieveAll();
        for(Epico epico: epicos){
            if((epico.getProjeto().getId())==(id)) epicoApp.delete(epico.getId());
            
        }
         projetoRepository.deleteById(id);
    }
}