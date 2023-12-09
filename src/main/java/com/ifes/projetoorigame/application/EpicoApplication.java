package com.ifes.projetoorigame.application;

import com.ifes.projetoorigame.lib.Grafo;

import com.ifes.projetoorigame.dto.EpicoDTO;
import com.ifes.projetoorigame.exception.NotFoundException;
import com.ifes.projetoorigame.model.Epico;
import com.ifes.projetoorigame.model.HistoriaUsuario;
import com.ifes.projetoorigame.model.Projeto;
import com.ifes.projetoorigame.model.TipoEpico;
import com.ifes.projetoorigame.repository.EpicoRepository;
import com.ifes.projetoorigame.repository.HistoriaUsuarioRepository;
import com.ifes.projetoorigame.repository.ProjetoRepository;
import com.ifes.projetoorigame.repository.TipoEpicoRepository;

import org.springframework.beans.factory.annotation.Autowired;
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
    private Grafo<Epico> grafo;

    
    public Epico create(EpicoDTO epicoDTO)
    {
        Epico epico = new Epico();
        
        epico.setTitulo(epicoDTO.getTitulo());
        epico.setDescricao(epicoDTO.getDescricao());
        epico.setRelevancia(epicoDTO.getRelevancia());
        
        Optional<TipoEpico> op = repoTipoEpico.findById(epicoDTO.getTipoEpico_id());
        Optional<Projeto> op2 = repoProjeto.findById(epicoDTO.getProjeto_id());
        if(op.isPresent() && op2.isPresent()){
            epico.setCategoria(epicoDTO.getCategoria());
            epico.setProjeto(op2.get());
            epico.setTipoEpico(op.get());
            repository.save(epico);
            
            hu_app.gera(epico);
        }
        
        return epico;
    }

    public Epico retrieve(int id) throws NotFoundException
    {
        Optional<Epico> entity;

        entity = repository.findById(id);

        if (entity.isPresent()) return entity.get();
        else throw new NotFoundException("Épico não encontrado");
    }

    public List<Epico> retrieveAll(int idProjeto)
    {
        return this.repository.findAllByProjeto(idProjeto);
    }

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
            repository.deleteById(id);
        } catch (NotFoundException e) {
            e.getMessage();
        }
           
    }
    public Epico gerarDependentes(int idEpico,List<Integer> listIds){
        List<Epico> listaEpicos = new ArrayList<>();
        try {
            Epico epico = retrieve(idEpico);
            for(Integer id: listIds){
                listaEpicos.add(retrieve(id));
            }
            epico.setListaDependentes(listaEpicos);
            return repository.save(epico);
        } catch (NotFoundException e) {
            e.getMessage();
        }
        return null;
    }
}