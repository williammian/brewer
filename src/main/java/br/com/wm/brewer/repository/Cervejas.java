package br.com.wm.brewer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.wm.brewer.model.Cerveja;

@Repository
public interface Cervejas extends JpaRepository<Cerveja, Long>{

}
