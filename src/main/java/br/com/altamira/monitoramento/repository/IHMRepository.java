package br.com.altamira.monitoramento.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.altamira.monitoramento.model.IHM;
import br.com.altamira.monitoramento.model.Maquina;

@Repository
public interface IHMRepository extends JpaRepository<IHM, String> {
	
	@Query("SELECT m FROM IHM m WHERE m.ativo = 1 AND m.situacao <> 7 AND DATEDIFF(SECOND, m.atualizacao, GETDATE()) > :segundos")
	List<Maquina> findByDesatualizadas(@Param("segundos") int segundos);
	
	List<Maquina> findAllByAtivo(@Param("ativo") Boolean ativo);
	
	List<IHM> findAllByMaquina(@Param("maquina") String maquina);
}
