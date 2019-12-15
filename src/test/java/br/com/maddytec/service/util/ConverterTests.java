package br.com.maddytec.service.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.maddytec.domain.Categoria;
import br.com.maddytec.dto.CategoriaDTO;
import br.com.maddytec.util.Converter;

@RunWith(SpringRunner.class)
public class ConverterTests {

	@Test
	public void converterGenericTest() {

		CategoriaDTO categoriaDTO = new CategoriaDTO();
		categoriaDTO.setDescricao("Descrição Teste 01");
		categoriaDTO.setId(10L);

		Categoria categoria = Converter.dePara(categoriaDTO, Categoria.class);

		assertThat(categoria.getId()).isEqualTo(categoriaDTO.getId());
		assertThat(categoria.getDescricao()).isEqualTo(categoriaDTO.getDescricao());
		
	}
}
