package br.com.valepresente.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.google.common.base.Preconditions;

import br.com.valepresente.domain.TipoConta;
import br.com.valepresente.repository.TipoContaRepository;

/**
 * Serviço com as funcionalidades relacionadas a consulta de Contas.
 * 
 */
@Service
@Validated
@Transactional(rollbackFor=Exception.class)
public class TipoContaService {
	
	private static final Logger log = LoggerFactory.getLogger(TipoContaService.class);

	@Autowired
	private TipoContaRepository repository;
	
	public TipoConta persiste(@NotNull @Valid TipoConta tipoConta) {
		if (log.isDebugEnabled()) {
			log.debug("Persistindo tipoConta: {}", tipoConta);
		}
		List<TipoConta> tipoContas = 
				repository.findByLogradouroAndNumeroAndCidadeAndEstado(tipoConta.getTipoConta(), 
						tipoConta.getCpf_cnpj(), tipoConta.getChave(), tipoConta.getNomeFantasia(), tipoConta.getDataNascimento());
		
		for (TipoConta o: tipoContas) {
			if (o.getId().equals(tipoConta.getId())) {
				continue;
			}
			Preconditions.checkArgument(!o.equals(tipoConta),
					String.format("Já existe uma conta (id: %s) cadastrado com essas informações.", o.getId()));
		}
		return repository.saveAndFlush(tipoConta);
	}
	
	public void delete(@NotNull Long tipoContaId) {
		if (log.isDebugEnabled()) {
			log.debug("Removendo endereço: {}", tipoContaId);
		}
		repository.delete(tipoContaId);
	}
	
	@Transactional(readOnly=true)
	public Optional<TipoConta> findTipoContaPorId(@NotNull Long tipoContaId) {
		TipoConta tipoConta = repository.findOne(tipoContaId);
		return Optional.ofNullable(tipoConta);
	}
	
}
