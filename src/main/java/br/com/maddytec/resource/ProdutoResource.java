package br.com.maddytec.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.maddytec.domain.Produto;
import br.com.maddytec.dto.ProdutoDTO;
import br.com.maddytec.service.ProdutoService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "produto")
public class ProdutoResource {

	@Autowired
	ProdutoService produtoService;

	@PostMapping
	public ResponseEntity<Produto> save(ProdutoDTO produtoDTO) {
		Produto produto = produtoDTO.converterToProduto(produtoDTO);
		produto = produtoService.save(produto);
		return ResponseEntity.status(HttpStatus.CREATED).body(produto);
	}
	
	@GetMapping
	public ResponseEntity<List<Produto>> findAll(){
		List<Produto> produtos = produtoService.findAll();
		return ResponseEntity.ok(produtos);
	}

}
