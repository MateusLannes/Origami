package com.ifes.projetoorigame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ifes.projetoorigame.model.TipoTarefa;

@Repository
public interface TipoTarefaRepository extends JpaRepository<TipoTarefa,Integer> {
    
}
