package br.com.maddytec.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.maddytec.domain.Grupo;
import br.com.maddytec.domain.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	@Query("SELECT u FROM usuario u WHERE email = ?1 AND password = ?2")
	public Optional<Usuario> login(String email, String senha);

	@Transactional(readOnly = false)
	@Modifying
	@Query("UPDATE usuario SET role = ?2 WHERE id = ?1 ")
	public int updateRole(Long id, List<Grupo> grupos);

	public Optional<Usuario> findByEmail(String email);
}