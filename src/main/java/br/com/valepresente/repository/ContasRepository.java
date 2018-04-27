package br.com.valepresente.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.valepresente.domain.Contas;

/**
 * Contrato com as operações de persistência sob a entidade <code>Contas</code>.
 * 
 * 
 */
@Repository
public interface ContasRepository extends JpaRepository<Contas, Long>{

	Contas findOneByChave(String chave);

}
