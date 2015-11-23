package br.com.altamira.monitoramento.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.altamira.monitoramento.model.MaquinaLog;

@Repository
public interface MaquinaLogRepository extends JpaRepository<MaquinaLog, Integer> {
	
	Page<MaquinaLog> findAllByMaquina(@Param("maquina") String maquina, Pageable pageable);

}
