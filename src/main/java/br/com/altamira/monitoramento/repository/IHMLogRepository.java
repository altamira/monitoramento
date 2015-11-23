package br.com.altamira.monitoramento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.altamira.monitoramento.model.IHMLog;

@Repository
public interface IHMLogRepository extends JpaRepository<IHMLog, Integer> {

}
