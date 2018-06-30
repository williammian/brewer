package br.com.wm.brewer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.wm.brewer.model.Venda;

public interface Vendas extends JpaRepository<Venda, Long> {

}
