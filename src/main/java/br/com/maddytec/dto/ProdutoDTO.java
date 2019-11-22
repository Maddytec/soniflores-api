package br.com.maddytec.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.maddytec.domain.Categoria;
import br.com.maddytec.domain.Produto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoDTO {

	@NotBlank(message = "{NotBlank}")
	@Size(max = 80, message = "{nome.size}")
	private String nome;

	@NotBlank(message = "{NotBlank}")
	@Size(max = 20)
	private String sku;

	
	@NotNull(message = "{NotNull}")
	@Min(value = 0, message = "não pode ser menor que 0")
	@Max(value = 9999, message = "não pode ser maior ou igual a 9999")
	private Long quantidadeEstoque;
	
	@NotNull(message = "{NotNull}")
	private BigDecimal valorUnitario;

	@NotNull(message = "{categoria.notNull}")
	private CategoriaDTO categoria;

	public Produto converterToProduto(ProdutoDTO produtoDTO) {
		Categoria categoria = null;
		if(produtoDTO.getCategoria() != null) {
			categoria = new Categoria();
			categoria.setId(produtoDTO.getCategoria().getId());
		}
		
		return Produto.builder()
				.nome(produtoDTO.getNome())
				.sku(produtoDTO.getSku())
				.quantidadeEstoque(produtoDTO.getQuantidadeEstoque())
				.valorUnitario(produtoDTO.getValorUnitario())
				.categoria(categoria)
				.build();
	}
}
