package br.com.wm.brewer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.wm.brewer.model.Cliente;

public interface Clientes extends JpaRepository<Cliente, Long> {

}
