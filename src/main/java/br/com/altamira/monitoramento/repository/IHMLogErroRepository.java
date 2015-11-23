package br.com.altamira.monitoramento.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.altamira.monitoramento.model.IHMLogErro;

@Repository
public interface IHMLogErroRepository extends JpaRepository<IHMLogErro, Long> {
	
	Page<IHMLogErro> findAll(Pageable pageable);

}
