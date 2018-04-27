package br.com.valepresente.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

/**
 * Entidade representa os dados do Endereço.
 * 
 * <p>Teste: <strong>Questão 2</strong></p>
 * 
 * 
 */
@Entity
public class TipoConta extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Size(min=2, max=2, message="Tipo Conta tem capacidade de 2caracteres.")
	@NotNull(message="Tipo Conta é obrigatório!")
	@Column(nullable = false, length=2)
	private String tipoConta;
	
	@NotNull(message="Número CPF/CNPJ é obrigatório!")
	@Size(min=11, max=16, message="Número CPF/CNPJ tem capacidade de até 16 caracteres.")
	@Column(nullable = false, length=16)
	private String cpf_cnpj;
	
	@Pattern(regexp="\\d{12}-?\\d{1}", message="Numero Conta deve seguir o formato \\d{12}-?\\{d1}")
	@Size(max=13, message="Numero Conta tem capacidade de 13 caracteres (numéricos).")
	@NotNull(message="Numero Conta é obrigatório!")
	@Column(length=13)
	private String chave;
	
	@Size(max=60, message="Nome tem capacidade de até 60 caracteres.")
	@Column(length=60)
	private String nomeRazao;
	
	@Size(min=3, max=60, message="Nome Fantasia tem capacidade de 3 a 60 caracteres.")
	@Column(nullable = false, length=60)
	private String nomeFantasia;
	
	@Size(max=10, message="Data Nascimento tem capacidade de até 10 caracteres.")
	@Column(length=10)
	private Date dataNascimento;

	public String getCpf_cnpj() {
		return cpf_cnpj;
	}

	public void setCpf_cnpj(String cpf_cnpj) {
		this.cpf_cnpj = cpf_cnpj;
	}
		
	public String getChave() {
		return chave;
	}
	
	public String getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(String tipoConta) {
		this.tipoConta = tipoConta;
	}

	public String getNomeRazao() {
		return nomeRazao;
	}

	public void setNomeRazao(String nomeRazao) {
		this.nomeRazao = nomeRazao;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public void setChave(String chave) {
		this.chave = chave;
		if (!Strings.isNullOrEmpty(this.chave)) {
			this.chave = this.chave.replace("-", "");
		}
	}

	@Override
	public String toString() {
		return "TipoConta [tipoConta=" + tipoConta + ", cpf_cnpj=" + cpf_cnpj + ", chave=" + chave + ", nomeRazao="
				+ nomeRazao + ", nomeFantasia=" + nomeFantasia + ", dataNascimento=" + dataNascimento + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		TipoConta outro = (TipoConta) obj;
		
		return Objects.equal(this.tipoConta, outro.tipoConta)
				&& Objects.equal(this.cpf_cnpj, outro.cpf_cnpj)
				&& Objects.equal(this.chave, outro.chave)
				&& Objects.equal(this.nomeRazao, outro.nomeRazao)
				&& Objects.equal(this.nomeFantasia, outro.nomeFantasia)				
				&& Objects.equal(this.dataNascimento, outro.dataNascimento);
	}
	
}
