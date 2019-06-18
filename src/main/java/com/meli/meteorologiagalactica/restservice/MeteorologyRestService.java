package com.meli.meteorologiagalactica.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.meli.meteorologiagalactica.PredictionResult;
import com.meli.meteorologiagalactica.constants.Constants;
import com.meli.meteorologiagalactica.model.Days;
import com.meli.meteorologiagalactica.restservice.model.ClimaticConditionResponse;
import com.meli.meteorologiagalactica.service.MeteorologyService;
/**
 * Servicios rest que dan la condición climática para cada día.
 * @author lposca
 *
 */
@RestController
public class MeteorologyRestService {

	@Autowired
	private MeteorologyService meteorologyService;
	
	/**
	 * Brinda la condición climática para un día
	 * @param day
	 * @return
	 */
	@GetMapping(value="/api/clima", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClimaticConditionResponse> weatherDay(@RequestParam(value="dia", required=true) long day)
	{
		
		Days climaticCondition = meteorologyService.getClimaticCondition(day);
		if ( climaticCondition == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		ClimaticConditionResponse condition = new ClimaticConditionResponse(day, climaticCondition.getCondition().getCondition());
		return new ResponseEntity<ClimaticConditionResponse>(condition, HttpStatus.OK);
	}
	
	/**
	 * Brinda la condición climática para un día de forma mas detallada, devolviendo los días de cada período.
	 * @param planet
	 * @return
	 */
	@GetMapping(value="/api/prediccioncompleta", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PredictionResult> fullPrediction(@RequestParam(value="planeta", required=true) String planet)
	{
		PredictionResult predictionResult = meteorologyService.getPredictions().get(planet);
		if ( predictionResult == null) {
			predictionResult = meteorologyService.getPredictions().get(Constants.FERENGI);
		}
	
		return new ResponseEntity<PredictionResult>(predictionResult, HttpStatus.OK);
	}


	
	
	
	
}
