package com.meli.meteorologiagalactica;

import java.util.List;

/**
 * Predicciones para períodos de lluvia. <br>
 * Contiene el día de máximo
 * @author lposca
 *
 */
public class RainPrediction extends Prediction {

	private List<Position> maxPosition;
	private long maxday;
	private double maxPerimeter = 0;

	
	/**
	 * Lista de posiciones de los planetas cuando se forma un perímetro máximo.
	 * @return
	 */
	public List<Position> getMaxPosition() {
		return maxPosition;
	}

	public void setMaxPosition(List<Position> maxPosition) {
		this.maxPosition = maxPosition;
	}
	
	/**
	 * Día que corresponde al pico máximo de lluvia.
	 * @return
	 */
	public long getMaxday() {
		return maxday;
	}

	public void setMaxday(long maxday) {
		this.maxday = maxday;
	}
	/**
	 * Perímetro máximo del triángulo que forman los planetas.
	 * @return
	 */
	public double getMaxPerimeter() {
		return maxPerimeter;
	}

	public void setMaxPerimeter(double maxPerimeter) {
		this.maxPerimeter = maxPerimeter;
	}
	
	

}
