package br.com.maddytec.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.maddytec.domain.Grupo;
import br.com.maddytec.domain.Usuario;
import br.com.maddytec.domain.enums.Role;
import br.com.maddytec.repository.UsuarioRepository;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class UsuarioRepositoryTests {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Test
	public void asaveTest() {
		List<Grupo> grupos = new ArrayList<Grupo>();
		grupos.add(Grupo.builder()							
					.nome(Role.ADMINISTRADOR.name())
					.descricao(Role.ADMINISTRADOR.getDescricao())
					.build());
		
		Usuario usuario = Usuario.builder()
				.email("maddytec@gmail.com")
				.nome("Madson").senha("123")
				.grupos(grupos)
				.build();

		Usuario createdUsuario = usuarioRepository.save(usuario);

		assertThat(createdUsuario.getId()).isEqualTo(1L);
	}

	@Test
	public void updateTest() {
		List<Grupo> grupos = new ArrayList<Grupo>();
		grupos.add(Grupo.builder()							
					.nome(Role.ADMINISTRADOR.name())
					.descricao(Role.ADMINISTRADOR.getDescricao())
					.build());
		
		Usuario usuario = new Usuario();
		usuario.setId(1L);
		usuario.setEmail("maddytec@gmail.com");
		usuario.setNome("Madson Silva");
		usuario.setSenha("1234");
		usuario.setGrupos(grupos);

		Usuario updateUsuario = usuarioRepository.save(usuario);

		assertThat(updateUsuario.getNome()).isEqualTo("Madson Silva");
	}

	@Test
	public void findByIdTest() {
		Optional<Usuario> result = usuarioRepository.findById(1L);
		Usuario usuario = result.get();
		assertThat(usuario.getSenha()).isEqualTo("1234");
	}

	@Test
	public void listTest() {
		List<Usuario> usuarios = usuarioRepository.findAll();

		assertThat(usuarios.size()).isEqualTo(1);
	}

	@Test
	public void loginTest() {
		Optional<Usuario> result = usuarioRepository.login("maddytec@gmail.com", "1234");
		Usuario loggedUsuario = result.get();

		assertThat(loggedUsuario.getId()).isEqualTo(1L);
	}

	@Test
	public void updateRoleTest() {
		List<Grupo> grupos = new ArrayList<>();
		grupos.add(
				Grupo.builder()
				.nome(Role.ADMINISTRADOR.name())
				.descricao(Role.ADMINISTRADOR.getDescricao())
				.build()
				);
		
		int rowsEffected = usuarioRepository.updateRole(2L, grupos);
		assertThat(rowsEffected).isEqualTo(1);
	}
}
