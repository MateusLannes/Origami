package com.ifes.projetoorigame.repository;

import com.ifes.projetoorigame.model.HistoriaUsuario;
import com.ifes.projetoorigame.model.TipoTarefa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HistoriaUsuarioRepository extends JpaRepository<HistoriaUsuario, Integer>
{
    @Query("SELECT t FROM HistoriaUsuario t WHERE t.tipoHistoria.id = :tipoHU")
    List<HistoriaUsuario> findByTipoHistoriaUserId(int tipoHU);

    @Query("SELECT t FROM HistoriaUsuario t WHERE t.epico.id = :epic")
    List<HistoriaUsuario> findByEpicoId(int epic);
}
