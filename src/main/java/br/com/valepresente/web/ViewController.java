package br.com.valepresente.web;

import java.util.StringJoiner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.valepresente.stream.NonRepeatedCharStream;
import br.com.valepresente.stream.Stream;

/**
 * Controller faz o roteamento das páginas.
 * 
 * <p>URLs roteadas:</p>
 * 
 * <ul> *   
 *   <li><strong>"/?form"</strong> - <code>GET</code>: direciona p/ o formulário de cadastro / edição de Tipo Contas;</li>
 *   <li><strong>"/?view"</strong> - <code>GET</code>: direciona p/ a página de visualização de Tipo Contas;</li>
 *   <li><strong>"/search-conta"</strong> - <code>GET</code>: direciona p/ a página de consulta de Contas;</li>
 *   <li><strong>"/stream"</strong> - <code>GET</code>: direciona p/ a página de teste de caracteres não repetidos no Stream;</li>
 *   <li><strong>"/stream"</strong> - <code>POST</code>: recebe o conteúdo string p/ testar Stream, retorna uma string com os caracteres não repetidos;</li>
 * </ul>
 * 
 * 
 */
@Controller
@RequestMapping(value="/")
public class ViewController {
	
	private static final Logger log = LoggerFactory.getLogger(ViewController.class);

	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String createForm(@RequestParam(required=false) Long id, Model model) {
		if (id != null) {
			model.addAttribute("id", id);
		}
		return "tipoContas/form";
	}
	
	@RequestMapping(params = "view", method = RequestMethod.GET)
	public String view(@RequestParam Long id, Model model) {
		if (id != null) {
			model.addAttribute("id", id);
		}
		return "tipoContas/view";
	}
	
	@RequestMapping(value = "search-conta", method = RequestMethod.GET)
	public String searchConta() {
		return "contas/search";
	}
	
	@RequestMapping(value = "stream", method = RequestMethod.GET)
	public String stream() {
		return "stream/form";
	}
	
	@RequestMapping(value = "stream", method = RequestMethod.POST)
	public @ResponseBody String postStream(@RequestParam String conteudo) {
		Stream stream = new NonRepeatedCharStream(conteudo);
		StringJoiner nonRepeated = new StringJoiner(", ");
		while (stream.hasNext()) {
			nonRepeated = nonRepeated.add(String.format("%s", stream.getNext()));
		}
		String resultado = nonRepeated.toString();
		
		if (log.isDebugEnabled()) {
			log.debug("Acionando NonRepeatedCharStream. Entrou: {} - Saiu: {}", conteudo, resultado);
		}
		
		return resultado;
	}
	
}
