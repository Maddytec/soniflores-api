package br.com.maddytec.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.com.maddytec.constants.SecurityConstant;
import br.com.maddytec.domain.Usuario;
import br.com.maddytec.exception.NotFoundException;
import br.com.maddytec.repository.UsuarioRepository;

@Component("accessManager")
public class AccessManager {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public Boolean isOwner(Long idOwner) {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<Usuario> result = usuarioRepository.findByEmail(email);

		if (!result.isPresent()) {
			throw new NotFoundException(SecurityConstant.JWT_INVALID_MSG + ": " + email);
		}

		Usuario usuario = result.get();

		return usuario.getId() == idOwner;
	}
	
}
