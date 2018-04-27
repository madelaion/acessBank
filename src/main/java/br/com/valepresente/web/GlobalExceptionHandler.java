package br.com.valepresente.web;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.valepresente.exception.ExceptionUtil;

/**
 * Gerencia as exceções lançadas (e geradas) pela camada controller.
 * 
 * 
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(IllegalArgumentException.class)
	public void handleIllegalArgumentException(HttpServletResponse response, 
			Exception ex) throws IOException {
		log.error("Request falhou, exceção lançada: {} ", ex.getMessage());
		response.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
	}
	
	@ExceptionHandler(DataAccessException.class)
	public void handleDataAccessException(HttpServletResponse response, 
			Exception ex) throws IOException {
		log.error("Ocorreu algum problema no acesso a dados: {} ", ex.getMessage());
		String msg = String.format("Ocorreu algo não esperado no acesso aos dados: %s", ex.getMessage());
		response.sendError(HttpStatus.BAD_GATEWAY.value(), msg);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public void handleConstraintViolationException(HttpServletResponse response, 
			Exception ex) throws IOException {
		String exMsg = ExceptionUtil.extractMessage(ex);
		log.error("A validação dos campos identificou problemas no preenchimento de campo. ");
		String msg = String.format("Fique atento ao preenchimento: %s", exMsg);
		response.sendError(HttpStatus.BAD_REQUEST.value(), msg);
	}
	
}
