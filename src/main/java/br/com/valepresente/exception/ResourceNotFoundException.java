package br.com.valepresente.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception convertida em Response 404 pelo Spring MVC .
 * 
 * 
 */
@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Recurso não encontrado!")
public class ResourceNotFoundException extends RuntimeException {

}
