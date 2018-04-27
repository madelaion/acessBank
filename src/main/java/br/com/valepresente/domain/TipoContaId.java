package br.com.valepresente.domain;

/**
 * Wrapper utilizado para trafegar o id do tipoConta em formato Json.
 * 
 */
public class TipoContaId {

	private final Long id;
	
	public TipoContaId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
}
