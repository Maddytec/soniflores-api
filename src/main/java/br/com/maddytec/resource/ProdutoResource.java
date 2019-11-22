package br.com.maddytec.resource;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public ResponseEntity<Produto> save(@RequestBody @Valid ProdutoDTO produtoDTO) {
		Produto produto = produtoDTO.converterToProduto(produtoDTO);
		produto = produtoService.save(produto);
		return ResponseEntity.status(HttpStatus.CREATED).body(produto);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Produto> update(@PathVariable(name = "id") Long id,
			@RequestBody @Valid ProdutoDTO produtoDTO) {
		Produto produto = produtoDTO.converterToProduto(produtoDTO);
		produto.setId(id);
		produto = produtoService.save(produto);
		return ResponseEntity.status(HttpStatus.CREATED).body(produto);
	}

	@GetMapping("/v2") // Lazy loading
	public ResponseEntity<PageModel<Produto>> findAllOnLazy(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "5") int size) {

		PageRequestModel pageRequestModel = new PageRequestModel(page, size);

		PageModel<Produto> pageModel = produtoService.findAllOnLazy(pageRequestModel);
		return ResponseEntity.ok(pageModel);
	}

	@GetMapping
	public ResponseEntity<List<Produto>> findAll() {
		List<Produto> produtos = produtoService.findAll();
		return ResponseEntity.ok(produtos);
	}
	
	@GetMapping("/{sku}")
	public ResponseEntity<Produto> findBySku(@PathVariable(name = "sku") String sku) {
		Produto produto = produtoService.findBySku(sku);
		return ResponseEntity.ok().body(produto);
	}

	@Secured({ "ROLE_ADMINISTRADORES" })
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable(name = "id") Long id) {
		produtoService.deleteById(id);
		return ResponseEntity.ok().body(HttpStatus.OK);
	}

}
