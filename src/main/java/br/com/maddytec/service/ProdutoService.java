package br.com.maddytec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.maddytec.domain.Produto;
import br.com.maddytec.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	ProdutoRepository produtoRepository;
	
	public Produto save(Produto produto) {
		return produtoRepository.save(produto);
	}
	
	public List<Produto> findAll(){
		return produtoRepository.findAll();
	}
}
