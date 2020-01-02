package br.com.maddytec.converter;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.maddytec.domain.Cliente;
import br.com.maddytec.domain.Endereco;
import br.com.maddytec.dto.ClienteDTO;
import br.com.maddytec.dto.EnderecoDTO;

/**
 * EnderecoConverterTest
 */
@RunWith(SpringRunner.class)
public class EnderecoConverterTest {

    @TestConfiguration
    static class EnderecoConverterTestContextConfiguration {

        @Bean
        public EnderecoConverter enderecoConverter() {
            return new EnderecoConverter();
        }
    }

    @Autowired
    private EnderecoConverter enderecoConverter;

    @Mock
    private ClienteDTO clienteDTO;

    @Mock
    private Cliente cliente;

    @Before
    public void setUp() {
        Endereco endereco1 = Endereco.builder().id(1L).cidade("Taboao da Serra").cep("000666")
                .logradouro("Rua do Tesouro").bairro("Jd do Tesouro").build();

        Endereco endereco2 = Endereco.builder().id(2L).cidade("Salvador").cep("000999").logradouro("Rua do Mad")
                .bairro("Jd do Esforçado").build();

        EnderecoDTO dto1 = EnderecoDTO.builder().id(1L).cidade("Taboao da Serra").cep("000666")
                .logradouro("Rua do Tesouro").bairro("Jd do Tesouro").build();

        EnderecoDTO dto2 = EnderecoDTO.builder().id(2L).cidade("Salvador").cep("000999").logradouro("Rua do Mad")
                .bairro("Jd do Esforçado").build();

        Mockito.when(cliente.getEnderecos()).thenReturn(Arrays.asList(endereco1, endereco2));
        Mockito.when(clienteDTO.getEnderecos()).thenReturn(Arrays.asList(dto1, dto2));
    }

    @Test
    public void converterSimples() {
        List<Endereco> enderecosResultantes = enderecoConverter.converterToEnderecosPorClienteDTO(clienteDTO, cliente);
        assertEquals(2, enderecosResultantes.size());
    }

    @Test
    public void converterComEnderecoNovo() {
        List<EnderecoDTO> enderecosNovos = clienteDTO.getEnderecos();

        EnderecoDTO dto = EnderecoDTO.builder().id(3L).cidade("Santos").cep("0044999").logradouro("Rua da Praia")
                .bairro("Jd da Areia").build();

        Mockito.when(clienteDTO.getEnderecos())
                .thenReturn(Arrays.asList(enderecosNovos.get(0), enderecosNovos.get(1), dto));

        List<Endereco> enderecosResultantes = enderecoConverter.converterToEnderecosPorClienteDTO(clienteDTO, cliente);
        assertEquals(3, enderecosResultantes.size());
    }

    @Test
    public void converterAlterandoValoresDoEndereco() {
        EnderecoDTO enderecoModificado = clienteDTO.getEnderecos().get(0);
        enderecoModificado.setCidade("São Paulo");
        enderecoModificado.setLogradouro("Avenida Paulista");

        List<Endereco> enderecosResultantes = enderecoConverter.converterToEnderecosPorClienteDTO(clienteDTO, cliente);

        enderecosResultantes.stream()
                    .filter(endereco -> endereco.getId() == enderecoModificado.getId())
                    .forEach(enderecoFiltrado -> {
                        assertEquals("São Paulo",enderecoFiltrado.getCidade());
                        assertEquals("Avenida Paulista",enderecoFiltrado.getLogradouro());
                    });
    }

}