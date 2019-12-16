package br.com.maddytec.resource;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.maddytec.domain.Usuario;
import br.com.maddytec.dto.UserUpdateRoleDTO;
import br.com.maddytec.dto.UsuarioDTO;
import br.com.maddytec.dto.UsuarioLoginDTO;
import br.com.maddytec.dto.UsuarioUpdateDTO;
import br.com.maddytec.model.PageModel;
import br.com.maddytec.model.PageRequestModel;
import br.com.maddytec.security.JwtManager;
import br.com.maddytec.service.UsuarioService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "usuario")
public class UsuarioResource {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtManager jwtManager;

	@Secured({ "ROLE_ADMINISTRADORES" })
	@PostMapping
	public ResponseEntity<Usuario> save(@RequestBody @Valid UsuarioDTO usuarioDTO) {
		Usuario usuario = new Usuario();
		BeanUtils.copyProperties(usuarioDTO, usuario);
		usuario = usuarioService.save(usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
	}

	//@PreAuthorize("@accessManager.isOwner(#id)")
	@PutMapping("/{id}")
	public ResponseEntity<Usuario> update(@PathVariable(name = "id") Long id,
			@RequestBody @Valid UsuarioUpdateDTO usuarioUpdateDTO) {

		Usuario usuario = new Usuario();
		BeanUtils.copyProperties(usuarioUpdateDTO, usuario);
		usuario.setId(id);

		Usuario usuarioAtualizado = usuarioService.update(usuario);
		return ResponseEntity.ok().body(usuarioAtualizado);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Usuario> findById(@PathVariable(name = "id") Long id) {
		Usuario user = usuarioService.findById(id);
		return ResponseEntity.ok().body(user);
	}

	@GetMapping
	public ResponseEntity<List<Usuario>> findAll() {
		List<Usuario> usuarios = usuarioService.findAll();
		return ResponseEntity.ok(usuarios);
	}

	@GetMapping("/v2") // Lazy loading	
	public ResponseEntity<PageModel<Usuario>> findAll(
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {

		PageRequestModel pageRequestModel = new PageRequestModel(page, size);

		PageModel<Usuario> pageModel = usuarioService.findAllOnLazyMode(pageRequestModel);
		return ResponseEntity.ok(pageModel);
	}

	@PostMapping("/login")
	public ResponseEntity<UsuarioLoginDTO> login(@RequestBody @Valid UsuarioLoginDTO usuarioLoginDTO) {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				usuarioLoginDTO.getEmail(), usuarioLoginDTO.getSenha());

		Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		org.springframework.security.core.userdetails.User userdetails = (org.springframework.security.core.userdetails.User) authentication
				.getPrincipal();

		String email = userdetails.getUsername();
		List<String> roles = userdetails.getAuthorities().stream().map(authority -> authority.getAuthority())
				.collect(Collectors.toList());
		
		String token = jwtManager.createToken(email, roles);
		usuarioLoginDTO.setSenha(token);
		return ResponseEntity.ok(usuarioLoginDTO);
	}

	@Secured({ "ROLE_ADMINISTRADORES" })
	@PatchMapping("/{id}")
	public ResponseEntity<?> updateRole(@RequestBody @Valid UserUpdateRoleDTO userUpdateRoleDTO,
			@PathVariable(name = "id") Long id) {

		usuarioService.updateRole(
				Usuario.builder()
				.id(id)
				.grupos(userUpdateRoleDTO.getGrupos())
				.build());

		return ResponseEntity.ok().build();
	}
	
	@Secured({ "ROLE_ADMINISTRADORES" })
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable(name = "id") Long id) {
		usuarioService.deleteById(id);
		return ResponseEntity.ok().body(HttpStatus.OK);
	}
	
}
