package com.meli.meteorologiagalactica;

import java.util.List;

/**
 * Agrupación de {@link AstronomicalObject}, debe contener 3 o más. <br>
 * Los mismos pueden ser configurados en el archivo
 * <b>configuration/meteorologyconfig.xml</b> en el bean <b>galaxy</b> en la
 * property <b>planets</b>.
 * 
 * @author lposca
 *
 */
public class Galaxy {

	private List<AstronomicalObject> planets;

	public Galaxy() {

	}

	/**
	 * Objetos astronómicos que pertenecen a una galaxia.
	 * 
	 * @return
	 */
	public List<AstronomicalObject> getPlanets() {
		return planets;
	}

	/**
	 * Setea los cuerpos astronóicos de una galaxia.
	 * 
	 * @param planets
	 */
	public void setPlanets(List<AstronomicalObject> planets) {
		this.planets = planets;
	}

}
