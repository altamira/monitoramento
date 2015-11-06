package br.com.altamira.monitoramento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.altamira.monitoramento.model.Medida;

@Repository
@Transactional
public interface MedidaRepository extends JpaRepository<Medida, String> {

	Medida findByDescricao(String descricao);
	Medida findByNome(String nome);
	Medida findByVariavel(String variavel);
}
