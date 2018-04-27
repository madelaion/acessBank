package br.com.valepresente.web.tests;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.valepresente.boot.Application;
import br.com.valepresente.domain.TipoConta;

/**
 * Testa Conta Cadastrada
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("dev")
public class TipoContaWebServiceTest extends BaseWebServiceTest {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}
	
	@Test
	public void testServiceIsAlive() throws Exception {
		this.mvc.perform(get("/tipoContas/health")
				.accept(MediaType.APPLICATION_JSON)
				)
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("ok")));
	}
	
	@Test
	public void testFindAllTipoContasValidaTamanho() throws Exception {
		this.mvc.perform(get("/tipoContas/")
				.accept(MediaType.APPLICATION_JSON)
				)
			.andDo(setContentType("charset=utf-8"))	
			.andExpect(status().isOk())
			.andExpect(jsonPath("items", hasSize(greaterThanOrEqualTo(1))));
	}
	
	@Test
	public void findTipoContaCadastrado() throws Exception {
		Long id = 2l;
		this.mvc.perform(get(String.format("/tipoContas/%s", id))
				.accept(MediaType.APPLICATION_JSON)
				)
			.andDo(setContentType("charset=utf-8"))	
			.andExpect(status().isOk())
			.andExpect(jsonPath("tipoConta", equalTo("PF")))
			.andExpect(jsonPath("cpf_cnpj", equalTo("53307536435")))
			.andExpect(jsonPath("chave", equalTo("09726121")))
			.andExpect(jsonPath("nomeRazao", equalTo("Patricia Bilas")))
			.andExpect(jsonPath("nomeFantasia", equalTo("")))
			.andExpect(jsonPath("dataNascimento", equalTo("1972-01-10")));
	}
	
	@Test
	public void findTipoContaInexistente() throws Exception {
		Long id = 20l;
		this.mvc.perform(get(String.format("/tipoContas/%s", id))
				.accept(MediaType.APPLICATION_JSON)
				)
			.andDo(setContentType("charset=utf-8"))	
			.andExpect(status().isNotFound())
			.andDo(print());
	}
	
	@Test
	public void updateTipoContaCadastrado() throws Exception {
		Long id = 3l;
		MvcResult r = this.mvc.perform(get(String.format("/tipoContas/%s", id))
				.accept(MediaType.APPLICATION_JSON)
				)
			.andDo(setContentType("charset=utf-8"))	
			.andExpect(status().isOk())
			.andReturn();
		
		String json = r.getResponse().getContentAsString();
		TipoConta e = convertJsonToObject(json, TipoConta.class);
		e.setTipoConta(e.getTipoConta()+" - modificado");
		
		this.mvc.perform(put("/tipoContas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(convertObjectToJsonBytes(e))
				.accept(MediaType.APPLICATION_JSON)
				)
			.andDo(setContentType("charset=utf-8"))	
			.andExpect(status().isOk());
	}
	
	@Test
	public void updateTipoContaCadastradoInconsistente() throws Exception {
		TipoConta e = new TipoConta() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Long getId() {
				return 33l;
			}
		};
		e.setTipoConta("Teste TipoConta inconsistente - sem campos obrigatórios");
		
		MvcResult r = this.mvc.perform(put("/tipoContas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(convertObjectToJsonBytes(e))
				.accept(MediaType.APPLICATION_JSON)
				)
			.andDo(setContentType("charset=utf-8"))	
			.andExpect(status().isBadRequest())
			.andDo(print())
			.andReturn();
		
		assertTrue(r.getResponse()
				.getErrorMessage().contains("TipoConta é obrigatório!"));
	}
	
	@Test
	public void updateTipoContaInexistente() throws Exception {
		TipoConta e = new TipoConta() {
			@Override
			public Long getId() {
				return 33l;
			}
		};
		e.setTipoConta("Teste Inexistente (id invalido)");
		e.setCpf_cnpj("10000000000");
		e.setChave("111111111111-1");
		e.setNomeRazao("Josefa Silva");
		e.setNomeFantasia("");
		e.setDataNascimento(new Date());
		
		this.mvc.perform(put("/tipoContas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(convertObjectToJsonBytes(e))
				.accept(MediaType.APPLICATION_JSON)
				)
			.andDo(setContentType("charset=utf-8"))	
			.andExpect(status().isOk());
		
		this.mvc.perform(get(String.format("/tipoContas/%s", e.getId()))
				.accept(MediaType.APPLICATION_JSON)
				)
			.andDo(setContentType("charset=utf-8"))	
			.andExpect(status().isNotFound())  //nao existe
			.andDo(print());
	}
	
	@Test
	public void removeTipoContaCadastrado() throws Exception {
		Long id = 3l;
		this.mvc.perform(delete(String.format("/tipoContas/%s",id))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				)
			.andDo(setContentType("charset=utf-8"))	
			.andExpect(status().isOk());
	}
	
	@Test
	public void insereTipoConta() throws Exception {
		TipoConta e = new TipoConta();
		e.setTipoConta("PF)");
		e.setCpf_cnpj("43310065812");
		e.setChave("0972611-0");
		e.setNomeRazao("Marcos Silva");
		e.setNomeFantasia("");
		e.setDataNascimento(new Date());
		
		this.mvc.perform(post("/tipoContas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(convertObjectToJsonBytes(e))
				.accept(MediaType.APPLICATION_JSON)
				)
			.andDo(setContentType("charset=utf-8"))	
			.andExpect(status().isOk())
			.andExpect(jsonPath("id", greaterThanOrEqualTo(1)));
	}
	
	@Test
	public void insereTipoContaComDigInvalido() throws Exception {
		TipoConta e = new TipoConta();
		e.setTipoConta("PF)");
		e.setCpf_cnpj("433");
		e.setChave("097261100011112");
		e.setNomeRazao("Marcos Silva");
		e.setNomeFantasia("");
		e.setDataNascimento(null);
		
		MvcResult r = this.mvc.perform(post("/tipoContas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(convertObjectToJsonBytes(e))
				.accept(MediaType.APPLICATION_JSON)
				)
			.andDo(setContentType("charset=utf-8"))	
			.andExpect(status().isBadRequest())
			.andDo(print())
			.andReturn();
		
		assertTrue(r.getResponse()
				.getErrorMessage().contains("Conta deve seguir o formato \\d{12}-?\\{d1}"));
	}
	
	@Test
	public void insereTipoContaInconsistente() throws Exception {
		TipoConta e = new TipoConta();
		
		MvcResult r = this.mvc.perform(post("/tipoContas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(convertObjectToJsonBytes(e))
				.accept(MediaType.APPLICATION_JSON)
				)
			.andDo(setContentType("charset=utf-8"))	
			.andExpect(status().isBadRequest())
			.andDo(print())
			.andReturn();
		
		assertTrue(r.getResponse()
				.getErrorMessage().contains("Tipo de Conta é obrigatório!"));
	}
	
}
