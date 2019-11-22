package br.com.maddytec.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.maddytec.domain.Produto;
import br.com.maddytec.model.PageModel;
import br.com.maddytec.model.PageRequestModel;
import br.com.maddytec.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	ProdutoRepository produtoRepository;

	public Produto save(Produto produto) {
		return produtoRepository.save(produto);
	}

	public PageModel<Produto> findAllOnLazy(PageRequestModel pageRequestModel) {
		PageRequest pageable = PageRequest.of(pageRequestModel.getPage(), pageRequestModel.getSize());
		Page<Produto> page = produtoRepository.findAll(pageable);

		return new PageModel<>((int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
	}

	public List<Produto> findAll() {
		List<Produto> produtos = produtoRepository.findAll();
		return produtos;
	}

	public void deleteById(Long id) {
		produtoRepository.deleteById(id);
	}
	
	public Produto findBySku(String sku) {
		Optional<Produto> optional = produtoRepository.findBySku(sku);
		return optional.orElse(null);
	}
}
