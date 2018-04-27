package br.com.valepresente.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.valepresente.domain.TipoConta;

/**
 * Contrato com as operações de persistência sob a entidade <code>TipoConta</code>.
 * 
 * 
 */
public interface TipoContaRepository extends JpaRepository<TipoConta, Long>{
	
	/**
	 * Realiza a consulta de contas, usando como filtros os campos obrigatórios,
	 * com objetivo de evitar duplicidade.
	 *  
	 * @param tipoConta
	 * @param cpf_cnpj
	 * @param chave
	 * @param nomeRazao
	 * @param nomeFantasia
	 *  @param dataNascimento
	 */
	List<TipoConta> findByLogradouroAndNumeroAndCidadeAndEstado(
			String tipoConta, String cpf_cnpj, String nomeRazao, String nomeFantasia, Date dataNascimento);

}
