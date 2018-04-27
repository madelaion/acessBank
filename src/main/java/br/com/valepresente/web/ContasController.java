package br.com.valepresente.web;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.valepresente.domain.Contas;
import br.com.valepresente.exception.ResourceNotFoundException;
import br.com.valepresente.service.ContasService;

/**
 * Controller Rest expõe o serviço para a consulta de tipo de conta a partir da numero conta.
 * 
 * 
 * <ul>
 *   <li><strong>"/contas/{chave}"</strong> - <code>GET</code>: consulta as informações do Cep de acordo com o ID e retorna em formato Json.
 *   Caso não encontre o registro, retorna código <code>HTTP 404</code> (NOT FOUND). Se a chave indicada não respeitar os formatos 
 *   99999-999 ou 99999999 retorna código <code>HTTP 400</code> (BAD REQUEST).</li>
 *   <li><strong>"/contas?{chave:99999999}"</strong> - <code>GET</code>: consulta as informações do Contas de acordo com a chave informada
 *   no Json de entrada e retorna o resultado também no formato Json. Caso não encontre o registro, retorna código <code>HTTP 404</code> 
 *   (NOT FOUND). Se a chave indicada não respeitar os formatos 999999999999-9 ou 9999999999999 retorna código <code>HTTP 400</code> 
 *   (BAD REQUEST).</li>
 *   <li><strong>"/contas/health"</strong> - <code>GET</code>: apenas um check para validar se o serviço esta no ar.</li>
 * </ul>
 * 
 * 
 */
@RestController
@RequestMapping(value="/contas")
public class ContasController {
	
	private static final Logger log = LoggerFactory.getLogger(ContasController.class);

	@Autowired
	private ContasService service;

	//outra alternativa seria usar regex no mapeamento do value, assim uma chamada invalida geraria 404
	@RequestMapping(value = "/{chave}", method = RequestMethod.GET,
			produces = { MediaType.APPLICATION_JSON_VALUE })
	public Contas findPorChave(@PathVariable String chave) {
		Optional<Contas> optContas = service.findContasPorChave(chave);
		if (!optContas.isPresent()) {
			log.error("CEP {} não encontrado", chave);
			throw new ResourceNotFoundException();
		}
		return optContas.get();
	}
	
	@RequestMapping(method = RequestMethod.GET,
			consumes = { MediaType.APPLICATION_JSON_VALUE },
			produces = { MediaType.APPLICATION_JSON_VALUE })
	public Contas findPorChave(@RequestBody Contas filtro) {
		//TODO seria possivel implementar uma consulta levando outros campos da Conta
		return findPorChave(filtro.getChave()); 
	}
	
//	@RequestMapping(value = "/health", method = RequestMethod.GET,
//			produces = { MediaType.APPLICATION_JSON_VALUE })
//	public Message health() {
//		return new Message("ok");
//	}
	
}
