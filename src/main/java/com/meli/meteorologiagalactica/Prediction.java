package com.meli.meteorologiagalactica;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Predicciones de los períodos de lluvia, sequía y condiciones óptimas. <br>
 * Contiene los días en que se produce cada condición climática y los puntos de
 * posición de los planetas durante ese día.
 * 
 * @author lposca
 *
 */
public class Prediction {

	/**
	 * Posiciones de los {@link AstronomicalObject} para cada condición climática.
	 */
	@JsonIgnore
	private List<List<Position>> positions = new ArrayList<>();

	/**
	 * Días donde se produce el período de lluvia, sequia o condición óptima.
	 */
	private Set<Long> days = new HashSet<>();

	public Prediction() {

	}

	/**
	 * Posiciones de los planetas durante la condición climática.
	 * 
	 * @return posiciones
	 */
	public List<List<Position>> getPositions() {
		return positions;
	}

	/**
	 * Setea la lista con la posición de los planetas durante al condición climática
	 * 
	 * @param positions
	 *            posiciones de los planetas
	 */
	public void setPositions(List<List<Position>> positions) {
		this.positions = positions;
	}

	/**
	 * Días donde se produce el período de lluvia, sequia o condición óptima.
	 * 
	 * @return días
	 */
	public Set<Long> getDays() {
		return days;
	}

	public void setDays(Set<Long> days) {
		this.days = days;
	}

	/**
	 * Agrega un día a la condición climática en cuestión
	 * 
	 * @param day
	 *            día en que se produce la condición climática
	 */
	public void addDays(long day) {
		days.add(day);
	}

	/**
	 * Cantidad de períodos que posee la condición climática
	 * 
	 * @return cantidad de períodos
	 */
	public long getPeriods() {
		return days.size();
	}


}
