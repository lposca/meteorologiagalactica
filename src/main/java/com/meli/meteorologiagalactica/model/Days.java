package com.meli.meteorologiagalactica.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Days {

	@Id
	@SequenceGenerator(name = "seqd", sequenceName = "days_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqd")
	@Column(name = "id")
	private Integer id;
	
	
	private Long daynumber;
	
	@ManyToOne
	@JoinColumn(name = "conditiontype")
	private ClimaticCondition condition;

	public Days() {
		
	}
	
	public Days(Long daynumber, ClimaticCondition condition) {
		super();
		this.daynumber = daynumber;
		this.condition = condition;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getDaynumber() {
		return daynumber;
	}

	public void setDaynumber(Long daynumber) {
		this.daynumber = daynumber;
	}

	public ClimaticCondition getCondition() {
		return condition;
	}

	public void setCondition(ClimaticCondition condition) {
		this.condition = condition;
	}

}
