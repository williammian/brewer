package br.com.wm.brewer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.wm.brewer.model.Cerveja;

@Repository
public interface Cervejas extends JpaRepository<Cerveja, Long>{
	
	public Optional<Cerveja> findBySku(String sku);
	
	public Optional<Cerveja> findBySkuIgnoreCase(String sku);

}
