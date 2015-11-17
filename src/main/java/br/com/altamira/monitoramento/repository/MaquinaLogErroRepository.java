package br.com.altamira.monitoramento.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.altamira.monitoramento.model.MaquinaLogErro;

@Repository
@Transactional
public interface MaquinaLogErroRepository extends JpaRepository<MaquinaLogErro, Long> {
	
	Page<MaquinaLogErro> findAll(Pageable pageable);

}
