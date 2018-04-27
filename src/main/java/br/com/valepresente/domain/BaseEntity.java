package br.com.valepresente.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Estrutura base para definição de entidades.
 * 
 * 
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	public Long getId() {
		return id;
	}
	
	@JsonIgnore
	public boolean isNew() {
		return id == null;
	}

	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
}
