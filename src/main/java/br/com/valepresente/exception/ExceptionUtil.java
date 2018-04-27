package br.com.valepresente.exception;

import java.util.Set;
import java.util.StringJoiner;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * Classe com funcionalidades para facilitar a extração de informações de exceptions.
 * 
 * 
 */
public class ExceptionUtil {

	private ExceptionUtil() {
	}
	
	/**
	 * @param ex - exceção
	 * @return a mensagem vinculada a exceção informada.
	 */
	public static String extractMessage(Exception ex) {
		if (ex == null) {
			return null;
		}
		
		if (ex instanceof ConstraintViolationException) {
			return extractMessagesFromConstraints((ConstraintViolationException) ex);
		}
		
		return ex.getMessage();
	}
	
	private static String extractMessagesFromConstraints(ConstraintViolationException ex) {
		Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
		StringJoiner sj = new StringJoiner(", ");
		
		if (violations != null && !violations.isEmpty()) {
			for (ConstraintViolation<?> v: violations) {
				sj = sj.add(String.format("%s", v.getMessage()));
			}
		}
		return sj.toString();
	}
	
}
