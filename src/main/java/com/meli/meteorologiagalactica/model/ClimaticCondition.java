package com.meli.meteorologiagalactica.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "climaticcondition")
public class ClimaticCondition {
	@Id
	@SequenceGenerator(name = "seq", sequenceName = "climaticcondition_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
	@Column(name = "id")
	private Integer id;
	
	
	
	public ClimaticCondition(String condition) {
		super();
		this.condition = condition;
	}

	public ClimaticCondition() {
		
	}
	
	@Column(name="conditionname")
	private String condition;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	
	
	
}
