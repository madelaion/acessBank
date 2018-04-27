package br.com.valepresente.domain;

/**
 * Wrapper utilizado para trafegar uma mensagem de status no formato Json.
 * 
 * 
 */
public class Message {

	private final String status;
	
	public Message(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
	
}
