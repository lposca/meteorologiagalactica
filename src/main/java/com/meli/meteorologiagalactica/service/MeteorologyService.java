package com.meli.meteorologiagalactica.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meli.meteorologiagalactica.AstronomicalObject;
import com.meli.meteorologiagalactica.AstronomicalObjectAlignment;
import com.meli.meteorologiagalactica.Galaxy;
import com.meli.meteorologiagalactica.Planet;
import com.meli.meteorologiagalactica.Position;
import com.meli.meteorologiagalactica.Prediction;
import com.meli.meteorologiagalactica.PredictionResult;
import com.meli.meteorologiagalactica.Rain;
import com.meli.meteorologiagalactica.RainPrediction;
import com.meli.meteorologiagalactica.Utils;
import com.meli.meteorologiagalactica.constants.Constants;
import com.meli.meteorologiagalactica.dao.DaysRepository;
import com.meli.meteorologiagalactica.model.Days;
import com.meli.meteorologiagalactica.time.RelativeTime;
/**
 * Servicio que contiene la lógica de construcción de predicciones. <br>
 * Se genera una predicción para cada planeta, utilizando el tiempo relativo configurado en {@link RelativeTime} y
 * la configuración de los planetas en {@link Planet}.<br><br>
 * Por cada planeta se itera dependiendo el tiempo configurado y se calcula el desplazamiento de cada planeta para ese tiempo, 
 * luego, con las posiciones de cada planeta, se calculan los períodos de lluvia, sequia y condiciones óptimas.
 * @author lposca
 *
 */
@Service("MeteorologyService")
public class MeteorologyService {

	private final Logger logger = LoggerFactory.getLogger(MeteorologyService.class);
	
	@Autowired
	private Galaxy galaxy;
	
	@Autowired
	private Utils utils;
	
	private Map<String, PredictionResult> predictions;
	
	@Autowired
	private DaysRepository daysRepository;
	
	@Autowired
	private RelativeTime relativeTime;
	
	@PostConstruct
	public void init() {
		
		logger.warn("Building predictions");
		predictions = new HashMap<>();
		/**
		 * Se generan predicciones para cada planeta
		 */
		for (AstronomicalObject p:galaxy.getPlanets())
		{
			logger.warn("Build prediction for planet " + p.getName());
			PredictionResult prediction = new PredictionResult();
			predictions.put(p.getName(), prediction);	 //agrego predicciones para el planeta en cuestion
			
			/**
			 * Se itera por 10 años para calcular las predicciones.  La iteración está dada por:
			 *  relativeTime.getRadianTimeFactor() -> factor de tiempo configurado en minutos, horas o segundos, ej: si 
			 *  son horas, serían 24, si fuesen minutos, serian horas * minutos.
			 */
			for ( int t= 0; t< relativeTime.getRadianTimeFactor() * p.getDaysperyear() * Constants.TENYEARS; t++)
			{
				ArrayList<Position> positions = new ArrayList<>();
				
				/**
				 * Calculo posición de cada planeta para un instante de tiempo
				 */
				for (AstronomicalObject planet:galaxy.getPlanets() )
				{
					
					positions.add(planet.getPositionByTime(t));
				}
				/**
				 * Determino si en ese instante, se produce un período de lluvia, sequía o condiciones optimas.
				 */
				AstronomicalObjectAlignment alignment = Utils.calculateAstronomicalObjectAlignment(positions);
				if ( alignment.isDrought()) {
					Prediction drought = prediction.getDrought();
					drought.getDays().add(relativeTime.toDay(t));
					drought.getPositions().add(positions);
					
					
				}else if ( alignment.isOptimalConditions()) {
					Prediction optimalConditions = prediction.getOptimalConditions();
					optimalConditions.getDays().add(relativeTime.toDay(t));
					optimalConditions.getPositions().add(positions);
				}
				
				else 
				{
					Rain rains = utils.rains(Position.sunPosition, positions);
					if ( rains.isRain())
					{
						RainPrediction rainPrediction = prediction.getRain();
						rainPrediction.getPositions().add(positions);
						long day = relativeTime.toDay(t);
						rainPrediction.getDays().add(day);
						
						if ( rains.getPerimeter() > rainPrediction.getMaxPerimeter())
						{	
							// determino el pico máximo de lluvias
							rainPrediction.setMaxPerimeter(rains.getPerimeter());
							rainPrediction.setMaxday(day);
							rainPrediction.setMaxPosition(positions);
						}
						
					}
					
					
				}
			}
			
			
		}
		logger.warn("Predictions built");
		
	}
	
	public Days getClimaticCondition(long day)
	{
		return daysRepository.loadDay(day);
	}
	
	public Map<String, PredictionResult> getPredictions() {
		return predictions;
	}

}
