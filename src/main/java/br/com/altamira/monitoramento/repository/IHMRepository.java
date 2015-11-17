package br.com.altamira.monitoramento.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.altamira.monitoramento.model.IHM;

@Repository
@Transactional
public interface IHMRepository extends JpaRepository<IHM, String> {

	List<IHM> findAllByMaquina(@Param("maquina") String maquina);
}
