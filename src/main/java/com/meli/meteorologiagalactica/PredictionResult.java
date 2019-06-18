package com.meli.meteorologiagalactica;

/**
 * Agrupamiento de predicciones climáticas para:
 * <br>- lluvia
 * <br>- sequía
 * <br>- condiciones óptimas
 * @author lposca
 *
 */
public class PredictionResult {

	private Prediction drought = new Prediction();
	private RainPrediction rain = new RainPrediction();
	private Prediction optimalConditions = new Prediction();
	 
	public PredictionResult() {
		
	}
	/**
	 * Predicciones climáticas para sequías
	 * @return predicciones climáticas para sequías
	 */
	public Prediction getDrought() {
		return drought;
	}
	
	/**
	 * Setea las predicciones climáticas para sequías
	 * @param drought sequías
	 */
	public void setDrought(Prediction drought) {
		this.drought = drought;
	}
	
	/**
	 * Predicciones climáticas para lluvia
	 * @return predicciones climáticas para lluvia
	 */
	public RainPrediction getRain() {
		return rain;
	}
	
	/**
	 * Setea las predicciones climáticas para lluvia
	 * @param rain lluvias
	 */
	public void setRain(RainPrediction rain) {
		this.rain = rain;
	}

	/**
	 * Predicciones climáticas para condiciones óptimas
	 * @return predicciones climáticas para condiciones óptimas
	 */
	public Prediction getOptimalConditions() {
		return optimalConditions;
	}

	/**
	 * Setea las predicciones climáticas para condiciones óptimas
	 * @param optimalConditions condiciones óptimas
	 */
	public void setOptimalConditions(Prediction optimalConditions) {
		this.optimalConditions = optimalConditions;
	}
	
	
	
}
