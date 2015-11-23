package br.com.altamira.monitoramento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.altamira.monitoramento.model.Medida;

@Repository
public interface MedidaRepository extends JpaRepository<Medida, String> {

	Medida findByDescricao(String descricao);
	Medida findByNome(String nome);
	Medida findByVariavel(String variavel);
}
