package com.ifes.projetoorigame.repository;

import com.ifes.projetoorigame.model.TipoHistoriaUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoHistoriaUsuarioRepository extends JpaRepository<TipoHistoriaUsuario, Integer> {
    
    @Query("SELECT t FROM TipoHistoriaUsuario t WHERE t.tipoEpico.id = :tipoEpic")
    List<TipoHistoriaUsuario> findByTipoEpicoId(Integer tipoEpic);
}
