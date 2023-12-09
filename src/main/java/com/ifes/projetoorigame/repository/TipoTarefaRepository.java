package com.ifes.projetoorigame.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ifes.projetoorigame.model.Tarefa;
import com.ifes.projetoorigame.model.TipoHistoriaUsuario;
import com.ifes.projetoorigame.model.TipoTarefa;

@Repository
public interface TipoTarefaRepository extends JpaRepository<TipoTarefa,Integer> {

    @Query("SELECT t FROM TipoTarefa t WHERE t.tipoHistoriaUsuario.id = :tipoHU")
    List<TipoTarefa> findByTipoHistoriaId(Integer tipoHU);
}
