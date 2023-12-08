package com.ifes.projetoorigame.repository;

import com.ifes.projetoorigame.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer>
{
}
