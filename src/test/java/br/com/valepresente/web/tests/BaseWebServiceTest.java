package br.com.valepresente.web.tests;

import java.io.IOException;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Estrutura básica para definição de testes de serviços Web.
 * 
 * 
 */
public abstract class BaseWebServiceTest {

	protected static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
	
	protected static <T> T convertJsonToObject(String json, Class<T> clazz) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(json, clazz);
	}
	
	//workaround p/ MockMvcResultMatcher considere o conteúdo da response como UTF-8: SPR-12676
	protected SetContentTypeResultHandler setContentType(String contentType) { 
		return new SetContentTypeResultHandler(contentType); 
	}
	
	protected static class SetContentTypeResultHandler implements ResultHandler {
		private String contentType;
		private SetContentTypeResultHandler(String contentType) { 
			this.contentType = contentType; 
		}
		@Override
		public void handle(MvcResult result) throws Exception { 
			result.getResponse().setContentType(contentType); 
		}
	}
}
