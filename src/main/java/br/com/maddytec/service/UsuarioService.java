package br.com.maddytec.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.maddytec.domain.Grupo;
import br.com.maddytec.domain.Usuario;
import br.com.maddytec.exception.NotFoundException;
import br.com.maddytec.model.PageModel;
import br.com.maddytec.model.PageRequestModel;
import br.com.maddytec.repository.UsuarioRepository;
import br.com.maddytec.util.HashUtil;

@Service
public class UsuarioService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioReposotory;

	public Usuario save(Usuario Usuario) {
		String hash = HashUtil.getSecureHash(Usuario.getSenha());
		Usuario.setSenha(hash);
		return usuarioReposotory.save(Usuario);
	}

	public Usuario update(Usuario Usuario) {
		return usuarioReposotory.save(Usuario);
	}

	public Usuario findById(Long id) {
		Optional<Usuario> result = usuarioReposotory.findById(id);
		return result.orElseThrow(() -> new NotFoundException("Not exist Usuario with id: " + id));
	}

	public void deleteById(Long id) {
		usuarioReposotory.deleteById(id);
	}

	public List<Usuario> findAll() {
		List<Usuario> Usuarios = usuarioReposotory.findAll();
		return Usuarios;
	}

	public PageModel<Usuario> findAllOnLazyMode(PageRequestModel pageRequestModel) {
		PageRequest pageable = PageRequest.of(pageRequestModel.getPage(), pageRequestModel.getSize());
		Page<Usuario> page = usuarioReposotory.findAll(pageable);

		return new PageModel<>((int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
	}

	public Usuario login(String email, String password) {
		password = HashUtil.getSecureHash(password);
		Optional<Usuario> result = usuarioReposotory.login(email, password);
		return result.get();
	}

	public int updateRole(Usuario usuario) {
		return usuarioReposotory.updateRole(usuario.getId(), usuario.getGrupos());
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Usuario> result = usuarioReposotory.findByEmail(email);

		if (!result.isPresent()) {
			throw new UsernameNotFoundException("Doesn't exist Usuario with email: " + email);
		}

		Usuario usuario = result.get();

		List<String> roles = new ArrayList<>();

		if (!Collections.EMPTY_LIST.equals(usuario.getGrupos())) {
			for (Grupo grupo : usuario.getGrupos()) {
				roles.add("ROLE_" + grupo.getNome());
			}
		}
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		if(!Collections.EMPTY_LIST.equals(roles)) {
			for (String role : roles) {
				authorities.add(new SimpleGrantedAuthority(role));
			}
		}
		
		org.springframework.security.core.userdetails.User usuarioDetails = new org.springframework.security.core.userdetails.User(
				usuario.getEmail(), usuario.getSenha(), authorities);

		return usuarioDetails;
	}

}
