package com.ifes.projetoorigame.repository;

import com.ifes.projetoorigame.model.TipoHistoriaUsuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoHistoriaUsuarioRepository extends JpaRepository<TipoHistoriaUsuario, Integer>
{
}
