package br.com.valepresente.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.google.common.base.Objects;

/**
 * Entidade representa os dados da base de Contas.
 * 
 * <p>Teste: <strong>Questão 1</strong></p>
 * 
 * 
 */
@Entity
public class Contas extends BaseEntity {
	
	@Size(max=99, message="Chave tem capacidade de 99 caracteres (numéricos).")	
	@Pattern(regexp="\\d{12}-?\\d{1}", message="Conta deve seguir o formato \\d{12}-?\\{d1}")
	@NotNull(message="Número Conta é obrigatória!")
	@Column(nullable = false, unique=true, length=2)
	private String chave;
	
	@Size(min=5, max=200, message="nome capacidade de 5 a 200 caracteres.")
	@NotNull(message="Nome Conta é obrigatório!")
	@Column(nullable = false, length=200)
	private String nome;
	
	@Size(max=10, message="Data tem capacidade de até 10 caracteres.")
	@Column(length=10)
	private Date dataCriacao;
	
	@Size(max=2, message="Tipo Conta tem capacidade de 2 caracteres.")
	@NotNull(message="Tipo Conta é obrigatório!")
	@Column(nullable = false, length=2)
	private String tipoConta;
	
	public Contas() {
	}
	
	public Contas(String chave) {
		this.chave = chave;
	}
	
	public Contas(String nome, 
			Date dataCriacao, String tipoConta) {
		this.nome = nome;
		this.dataCriacao = dataCriacao;
		this.tipoConta = tipoConta;
	}

	public String getChave() {
		return chave;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public String getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(String tipoConta) {
		this.tipoConta = tipoConta;
	}	

	@Override
	public String toString() {
		return "Contas [chave=" + chave + ", nome=" + nome + ", dataCriacao=" + dataCriacao + ", tipoConta=" + tipoConta + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.chave, this.nome, 
				this.dataCriacao, this.tipoConta);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		Contas outro = (Contas) obj;
		
		return Objects.equal(this.chave, outro.chave)
				&& Objects.equal(this.nome, outro.nome)
				&& Objects.equal(this.dataCriacao, outro.dataCriacao)
				&& Objects.equal(this.tipoConta, outro.tipoConta);
	}

}
