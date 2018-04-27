package br.com.valepresente.web.tests;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.valepresente.boot.Application;
import br.com.valepresente.domain.Contas;

/**
 * Testes do serviço de Contas.
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("dev")
public class ContasWebServiceTest extends BaseWebServiceTest {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}
	
	@Test
	public void isServiceAlive() throws Exception {
		this.mvc.perform(get("/contas/health")
				.accept(MediaType.APPLICATION_JSON)
				)
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("ok")));
	}
	
	@Test
	public void findContasValido() throws Exception {
		String chave = "09726121";
		this.mvc.perform(get(String.format("/contas/%s", chave))
				.accept(MediaType.APPLICATION_JSON)
				)
			.andExpect(status().isOk())
			.andDo(setContentType("charset=utf-8"))	
			.andExpect(jsonPath("nome", equalTo("Patricia Bilas")))
			.andExpect(jsonPath("nomeRazao", equalTo("")))
			.andExpect(jsonPath("nomeFantasia", equalTo("")))
			.andExpect(jsonPath("dataNascimento", equalTo("1981-05-01")));
	}
	
	@Test
	public void findContasValidoComJson() throws Exception {
		Contas c = new Contas("09726121");
		this.mvc.perform(get("/contas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(convertObjectToJsonBytes(c))
				.accept(MediaType.APPLICATION_JSON)
				)
			.andDo(setContentType("charset=utf-8"))	
			.andExpect(status().isOk())
			.andExpect(jsonPath("nome", equalTo("Patricia Bilas")))			
			.andExpect(jsonPath("dataCriacao", equalTo("1981-05-01")))
			.andExpect(jsonPath("tipoConta", equalTo("PF")));
	}
	
	@Test
	public void findContasValidoComTraco() throws Exception {
		String chave = "09726-121";
		this.mvc.perform(get(String.format("/contas/%s", chave))
					.accept(MediaType.APPLICATION_JSON)
					)
				.andDo(setContentType("charset=utf-8"))	
				.andExpect(status().isOk())
				.andExpect(jsonPath("nome", equalTo("Patricia Bilas")))			
				.andExpect(jsonPath("dataCriacao", equalTo("1981-05-01")))
				.andExpect(jsonPath("tipoConta", equalTo("PF")));
	}
	
	@Test
	public void findContasValidoComJsonETraco() throws Exception {
		Contas c = new Contas("09726-121");
		this.mvc.perform(get("/contas")
					.contentType(MediaType.APPLICATION_JSON)
					.content(convertObjectToJsonBytes(c))
					.accept(MediaType.APPLICATION_JSON)
					)
				.andDo(setContentType("charset=utf-8"))	
				.andExpect(status().isOk())
				.andExpect(jsonPath("nome", equalTo("Patricia Bilas")))			
				.andExpect(jsonPath("dataCriacao", equalTo("1981-05-01")))
				.andExpect(jsonPath("tipoConta", equalTo("PF")));
	}
		
	@Test
	public void findContasInvalido() throws Exception {
		String chave = "qualquerUm";
		this.mvc.perform(get(String.format("/contas/%s", chave))
					.accept(MediaType.APPLICATION_JSON)
					)
				.andExpect(status().isBadRequest())
				.andDo(print());
	}
	
	@Test
	public void findContasSemChave() throws Exception {
		this.mvc.perform(get("/contas/")
				.accept(MediaType.APPLICATION_JSON)
				)
			.andExpect(status().isUnsupportedMediaType())
			.andDo(print());
	}
	
	@Test
	public void findContasInexistente() throws Exception {
		String chave = "000000000000-0";
		this.mvc.perform(get(String.format("/contas/%s", chave))
				.accept(MediaType.APPLICATION_JSON)
				)
			.andExpect(status().isNotFound())
			.andDo(print());
	}
	
}
