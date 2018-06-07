package br.com.wm.brewer.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.wm.brewer.model.Cliente;
import br.com.wm.brewer.repository.filter.ClienteFilter;
import br.com.wm.brewer.repository.helper.cliente.ClientesQueries;

public interface Clientes extends JpaRepository<Cliente, Long>, ClientesQueries {
	
	public Optional<Cliente> findByCpfOuCnpj(String cpfOuCnpj);
	
	public Page<Cliente> filtrar(ClienteFilter filtro, Pageable pageable);

}
