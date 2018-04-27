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

import com.google.common.base.Preconditions;

import br.com.valepresente.domain.TipoConta;
import br.com.valepresente.domain.TipoContaId;
import br.com.valepresente.exception.ResourceNotFoundException;
import br.com.valepresente.service.TipoContaService;

/**
 * Controller Rest expõe o serviço de consultade Tipo de Conta.
 * 
 * <ul>
 *   <li><strong>"/tipoContas?pagina=0"</strong> - <code>GET</code>: retorna um Json informando um contador de registros cadastrados e
 *   a lista de endereços, de acordo com a paginação. O parâmetro <code>pagina</code> é opcional. Pode retornar uma lista de endereços
 *   vazia, caso a base não tenha cadastro</li>
 *   <li><strong>"/tipoContas/{id}"</strong> - <code>GET</code>: consulta o endereço pelo ID (obrigatório) e retorna um Json com suas
 *   informações. Retorna código <code>HTTP 404</code> (NOT FOUND) caso não encontre o registro pelo ID.</li> *   
 * </ul>
 * 
 * 
 */
@RestController
@RequestMapping(value="/tipoContas")
public class TipoContaController {
	
	private static final Logger log = LoggerFactory.getLogger(TipoContaController.class);

	@Autowired
	private TipoContaService service;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET,
			produces = { MediaType.APPLICATION_JSON_VALUE })
	public TipoConta findPorId(@PathVariable Long id) {
		Optional<TipoConta> optTpConta = service.findTipoContaPorId(id);
		if (!optTpConta.isPresent()) {
			log.error("TipoConta com id {}, não encontrado", id);
			throw new ResourceNotFoundException();
		}
		return optTpConta.get();
	}
	
	@RequestMapping(method = RequestMethod.POST,
			consumes = { MediaType.APPLICATION_JSON_VALUE },
			produces = { MediaType.APPLICATION_JSON_VALUE })
	public TipoContaId save(@RequestBody TipoConta tipoConta) {
		Preconditions.checkArgument(tipoConta.isNew(), 
				"Utilize o método POST para inserir o Tipo de Conta");
		TipoConta persistido = service.persiste(tipoConta);
		return new TipoContaId(persistido.getId());
	}	
	
//	@RequestMapping(value = "/health", method = RequestMethod.GET)
//	public Message health() {
//		return new Message("ok");
//	}
	
}
