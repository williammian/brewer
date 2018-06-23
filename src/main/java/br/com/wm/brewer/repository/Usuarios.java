package br.com.wm.brewer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.wm.brewer.model.Usuario;
import br.com.wm.brewer.repository.helper.usuario.UsuariosQueries;

public interface Usuarios extends JpaRepository<Usuario, Long>, UsuariosQueries {

	public Optional<Usuario> findByEmail(String email);
	
	public List<Usuario> findByCodigoIn(Long[] codigos);

}
