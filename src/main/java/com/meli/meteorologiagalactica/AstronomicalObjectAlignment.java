package com.meli.meteorologiagalactica;
/**
 * Objeto wrapper para indicar si hubo períodos de sequia o condiciones optimas
 * resultante de la alineación de los planetas/sol.
 * @author lposca
 *
 */
public class AstronomicalObjectAlignment {

	private boolean drought = false, optimalConditions = false;
	
	public static AstronomicalObjectAlignment NONE = new AstronomicalObjectAlignment();
	
	public AstronomicalObjectAlignment() {
		
	}
	
	/**
	 * Indica si hubo sequia
	 * @return
	 */
	public boolean isDrought() {
		return drought;
	}
	
	/**
	 * Setter para períodos de sequia
	 * @param drought
	 */
	public void setDrought(boolean drought) {
		this.drought = drought;
	}

	/**
	 * Indica si hubo condiciones optimas
	 * @return
	 */
	public boolean isOptimalConditions() {
		return optimalConditions;
	}

	/**
	 * Setter para condiciones optimas
	 * @param optimalConditions
	 */
	public void setOptimalConditions(boolean optimalConditions) {
		this.optimalConditions = optimalConditions;
	}
	
	
	
}
