package br.com.wm.brewer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.wm.brewer.model.Venda;
import br.com.wm.brewer.repository.helper.venda.VendasQueries;

public interface Vendas extends JpaRepository<Venda, Long>, VendasQueries {

}
