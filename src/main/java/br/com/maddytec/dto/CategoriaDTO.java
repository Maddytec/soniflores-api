package br.com.maddytec.dto;

import java.util.List;

import br.com.maddytec.domain.Categoria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoriaDTO {

	private Long id;
	
	private String descricao;
	
	private CategoriaDTO categoriaPai;
	
	private List<CategoriaDTO> subcategorias;
	
	
	public Categoria converterToProduto(CategoriaDTO categoriaDTO) {
		
		Categoria categoriaPai = new Categoria();
		
		if(categoriaDTO.getCategoriaPai() != null ) {
			categoriaPai.setId(categoriaDTO.getCategoriaPai().getId());
		}
		
		return Categoria.builder()
				.id(categoriaDTO.getId())
				.descricao(categoriaDTO.getDescricao())
				.categoriaPai(categoriaPai)
				.build();
	}
}
