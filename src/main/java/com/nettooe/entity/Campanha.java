package com.nettooe.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel(value = "Campanha", description = "Campanha publicitária.")
public class Campanha implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@NotEmpty
	@ApiModelProperty(notes = "O nome da Campanha", required = true)
	private String nomeDaCampanha;

	@NotNull
	@ApiModelProperty(notes = "O ID do time do Coração associado à Campanha", required = true)
	private Long idTimeDoCoracao;

	@NotNull
	@ApiModelProperty(notes = "Dia em que inicia a vigência da campanha.", required = true)
	@Column(name = "inicio_vigencia")
	private LocalDate inicioVigencia;

	@NotNull
	@ApiModelProperty(notes = "Dia em que termina a vigência da campanha.", required = true)
	@Column(name = "fim_vigencia")
	private LocalDate fimVigencia;

	public Campanha() {
	}

	public Campanha(String nomeDaCampanha, Long idTimeDoCoracaoo, LocalDate inicioVigencia, LocalDate fimVigencia) {
		super();
		this.nomeDaCampanha = nomeDaCampanha;
		this.idTimeDoCoracao = idTimeDoCoracaoo;
		this.inicioVigencia = inicioVigencia;
		this.fimVigencia = fimVigencia;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeDaCampanha() {
		return nomeDaCampanha;
	}

	public void setNomeDaCampanha(String nomeDaCampanha) {
		this.nomeDaCampanha = nomeDaCampanha;
	}

	public Long getIdTimeDoCoracao() {
		return idTimeDoCoracao;
	}

	public void setIdTimeDoCoracao(Long idTimeDoCoracao) {
		this.idTimeDoCoracao = idTimeDoCoracao;
	}

	public LocalDate getInicioVigencia() {
		return inicioVigencia;
	}

	public void setInicioVigencia(LocalDate inicioVigencia) {
		this.inicioVigencia = inicioVigencia;
	}

	public LocalDate getFimVigencia() {
		return fimVigencia;
	}

	public void setFimVigencia(LocalDate fimVigencia) {
		this.fimVigencia = fimVigencia;
	}

}
