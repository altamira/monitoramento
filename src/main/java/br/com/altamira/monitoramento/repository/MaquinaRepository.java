package br.com.altamira.monitoramento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.altamira.monitoramento.model.Maquina;

@Repository
@Transactional
public interface MaquinaRepository extends JpaRepository<Maquina, String> {
	
}