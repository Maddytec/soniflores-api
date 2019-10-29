package br.com.maddytec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.maddytec.domain.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
		
}
