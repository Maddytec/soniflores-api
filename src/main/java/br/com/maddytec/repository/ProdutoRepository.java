package br.com.maddytec.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.maddytec.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	public Optional<Produto> findBySku(String sku);
}
