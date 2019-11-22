package br.com.maddytec.dto;

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
	
	public Categoria converterToCategoria(CategoriaDTO categoriaDTO) {
		Categoria categoriaPai = null;
		
		if(categoriaDTO.getCategoriaPai() != null ) {
			categoriaPai = new Categoria();
			categoriaPai.setId(categoriaDTO.getCategoriaPai().getId());
		}
		
		return Categoria.builder()
				.id(categoriaDTO.getId())
				.descricao(categoriaDTO.getDescricao())
				.categoriaPai(categoriaPai)
				.build();
	}
}
