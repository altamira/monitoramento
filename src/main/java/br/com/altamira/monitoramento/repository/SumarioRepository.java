package br.com.altamira.monitoramento.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.altamira.monitoramento.model.Sumario;

@Repository
public interface SumarioRepository extends JpaRepository<Sumario, String> {
	
	List<Sumario> findByMaquina(@Param("maquina") String maquina);
	
	@Query(value = "SELECT * FROM SUMARIO_GERAL", nativeQuery = true)
	List<Sumario> findAll();
}
