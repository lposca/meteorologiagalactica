package com.meli.meteorologiagalactica.job;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.meli.meteorologiagalactica.AstronomicalObject;
import com.meli.meteorologiagalactica.Galaxy;
import com.meli.meteorologiagalactica.PredictionResult;
import com.meli.meteorologiagalactica.constants.Constants;
import com.meli.meteorologiagalactica.dao.ClimaticConditionRepository;
import com.meli.meteorologiagalactica.dao.DaysRepository;
import com.meli.meteorologiagalactica.model.ClimaticCondition;
import com.meli.meteorologiagalactica.model.Days;
import com.meli.meteorologiagalactica.service.MeteorologyService;

/**
 * Job para insertar los dias y sus predicciones dentro de la tabla Days.<br>
 * 
 * @author lposca
 *
 */
public class DayJob {
	
	private final Logger logger = LoggerFactory.getLogger(DayJob.class);
	
	
	@Autowired
	private MeteorologyService meteorologyService;
	@Autowired
	private ClimaticConditionRepository climaticRepository;
	
	@Autowired
	private DaysRepository daysRepository;
	
	@Autowired
	private Galaxy galaxy;
	
	
	public DayJob() {
	
	}
	
	
	/**
	 * Inserta en la tabla days las predicciones del planeta pasado por parámetro; por defecto vulcano.<br>
	 * Se borra los datos de las tablas ClimaticCondition y Days.
	 * Luego se insertan los datos de ambas tablas, Days toma los valores de las predicciones realizadas durante el startup de la aplicación. <br>
	 * <br>Las condiciones climaticas son 4: lluvia, sequia, condiciones optimas y dia normal, siendo este último un dia que cae fuera de los cálculos de los 3 primeros.
	 * 
	 * @param planet
	 */
	public void poblate(String planet)
	{
		
		logger.warn("Day job");
		logger.warn("Deleting all days");
		daysRepository.deleteAll();
		logger.warn("Deleting all climatic conditions");
		climaticRepository.deleteAll();
		ClimaticCondition rainCondition = new ClimaticCondition(Constants.LLUVIA);
		ClimaticCondition droughtCondition = new ClimaticCondition(Constants.SEQUIA);
		ClimaticCondition optimalCondition = new ClimaticCondition(Constants.CONDICIONES_OPTIMAS);
		ClimaticCondition normalCondition = new ClimaticCondition(Constants.DIA_NORMAL);
		logger.warn("Saving climatic conditions");
		climaticRepository.save(rainCondition);
		climaticRepository.save(droughtCondition);
		climaticRepository.save(optimalCondition);
		climaticRepository.save(normalCondition);
		
		 /**
		  * Tomo la predicción del planeta, por defecto vulcano
		  */
		 AstronomicalObject pv = null;
		 Optional<AstronomicalObject> first = galaxy.getPlanets().stream().filter(p->p.getName().equals(planet)).findFirst();
		 if (!first.isPresent())
		 {
			 pv = galaxy.getPlanets().stream().filter(p->p.getName().equals(Constants.VULCANO)).findFirst().get();
		 }
		 else
		 {
			 pv = first.get();
		 }
		
		/**
		 *  inserto por cada dia una condición normal, para luego reemplazarla por la prediccion real de cada dia.  Si 
		 *  algun día no tiene predicción queda como dia normal. 
		 */
		 
		Map<Long, Days> days = new HashMap<>();
		for ( long i = 1; i<= pv.getDaysperyear()*10; i++)
		{
			days.put(i, new Days(i, normalCondition));
		}
		
		PredictionResult vulcanoPrediction = meteorologyService.getPredictions().get(Constants.VULCANO);
		
		Set<Long> droughtDays = vulcanoPrediction.getDrought().getDays();
		for ( Long dr: droughtDays)
		{
			days.put(dr,new Days(dr, droughtCondition));
		}
		
		Set<Long> rainDays = vulcanoPrediction.getRain().getDays();
		for ( Long rd: rainDays)
		{
			days.put(rd, new Days(rd, rainCondition));
		}
		
		Set<Long> optimalDays = vulcanoPrediction.getOptimalConditions().getDays();
		for ( Long od: optimalDays)
		{
			days.put(od, new Days(od, optimalCondition));
		}
		
		
		logger.warn("Saving days");
		daysRepository.save(days.values());
		logger.warn("Day job executed");
	}
	
}
