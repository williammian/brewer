package br.com.wm.brewer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.wm.brewer.model.Cliente;

public interface Clientes extends JpaRepository<Cliente, Long> {
	
	public Optional<Cliente> findByCpfOuCnpj(String cpfOuCnpj);

}
