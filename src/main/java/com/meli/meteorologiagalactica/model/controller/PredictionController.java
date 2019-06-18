package com.meli.meteorologiagalactica.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.meli.meteorologiagalactica.PredictionResult;
import com.meli.meteorologiagalactica.constants.Constants;
import com.meli.meteorologiagalactica.service.MeteorologyService;
/**
 * Controlador que despliega por pantalla, la predicción de cada planeta. <br>
 * El nombre del planeta se pasa en el parametro planeta, si es inválido se toma por defecto a {@link Constants#FERENGI}
 * @author lposca
 *
 */
@Controller
public class PredictionController {

	@Autowired
	private MeteorologyService meteorologyService;
	
	  @GetMapping("/prediccion")
	    public String homePage(Model model, @RequestParam(value="planeta", required=false, defaultValue=Constants.FERENGI) String name) {
	        
		  PredictionResult predictionResult = meteorologyService.getPredictions().get(name);
		  if ( predictionResult == null)
		  {
			  name = Constants.FERENGI;
		  }
		  model.addAttribute("prediction", predictionResult);
		  model.addAttribute("planet", name);
		  
	        return "prediction";
	    }
}
