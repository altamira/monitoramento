package br.com.altamira.monitoramento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.altamira.monitoramento.model.MaquinaLogParametro;

@Repository
public interface MaquinaLogParametroRepository extends JpaRepository<MaquinaLogParametro, Long> {

}
