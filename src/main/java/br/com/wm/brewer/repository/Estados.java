package br.com.wm.brewer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.wm.brewer.model.Estado;

public interface Estados extends JpaRepository<Estado, Long> {

}
