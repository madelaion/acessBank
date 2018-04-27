package br.com.valepresente.service;


import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.google.common.base.Preconditions;

import br.com.valepresente.domain.Contas;
import br.com.valepresente.repository.ContasRepository;

/**
 * Serviço com as funcionalidades relacionadas a consulta de CEP.
 * 
 * <p>Teste: <strong>Questão 1</strong></p>
 * 
 * 
 */
@Service
@Validated
@Transactional(readOnly=true, rollbackFor=Exception.class)
public class ContasService {
	
	private static final Logger log = LoggerFactory.getLogger(ContasService.class);
	
	@Autowired
	private ContasRepository repository;

	public Optional<Contas> findContasPorChave(@NotNull @NotEmpty String chave) {
		Preconditions.checkArgument(chave.matches("\\d{5}-?\\d{3}"), "Cep Inválido!");
		return findContasPorChave(geraChavesSufixo(chave.replace("-", "")));
	}
	
	private Optional<Contas> findContasPorChave(Collection<String> chaves) {
		int tentativa = 0;
		Contas conta = null;
		for (String chave: chaves) {
			if (!"".equals(chave == null ? "" : chave)) {
				if (log.isDebugEnabled())
					log.debug("Consultando Conta pela chave {}, tentiva {}.", chave, ++tentativa);
				
				conta = repository.findOneByChave(chave);
				if (conta != null) {
					return Optional.of(conta);
				}
			}
		}
		
		return Optional.empty();
	}
	
	private static Set<String> geraChavesSufixo(String chaveOriginal) {
		Set<String> poolChaves = new LinkedHashSet<>();
		poolChaves.add(chaveOriginal);
		for (int i = chaveOriginal.length()-1; i > 0; i--) {
			String novaChave = String.format("%-8s", 
					chaveOriginal.substring(0, i)).replace(' ', '0');
			poolChaves.add(novaChave);
		}
		return poolChaves;
	}
	
	
}
