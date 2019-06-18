package com.meli.meteorologiagalactica.restservice.model;

import com.meli.meteorologiagalactica.restservice.MeteorologyRestService;

/**
 * Wrapper para respuesta del servicio rest {@link MeteorologyRestService}
 * @author lposca
 *
 */
public class ClimaticConditionResponse {

	private long dia;
	private String clima;

	public ClimaticConditionResponse() {

	}

	public ClimaticConditionResponse(long dia, String clima) {
		super();
		this.dia = dia;
		this.clima = clima;
	}

	public long getDia() {
		return dia;
	}

	public void setDia(long dia) {
		this.dia = dia;
	}

	public String getClima() {
		return clima;
	}

	public void setClima(String clima) {
		this.clima = clima;
	}

}
