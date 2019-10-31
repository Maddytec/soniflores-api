package br.com.maddytec.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.maddytec.domain.Produto;
import br.com.maddytec.dto.ProdutoDTO;
import br.com.maddytec.model.PageModel;
import br.com.maddytec.model.PageRequestModel;
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
	public ResponseEntity<PageModel<Produto>> findAllOnLazy(
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "5") int size) {

		PageRequestModel pageRequestModel = new PageRequestModel(page, size);

		PageModel<Produto> pageModel = produtoService.findAllOnLazy(pageRequestModel);
		return ResponseEntity.ok(pageModel);
	}

}
