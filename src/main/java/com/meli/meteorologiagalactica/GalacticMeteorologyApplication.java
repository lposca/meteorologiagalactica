package com.meli.meteorologiagalactica;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ImportResource;

import com.meli.meteorologiagalactica.constants.Constants;
import com.meli.meteorologiagalactica.job.DayJob;
/**
 * Main class de la aplicación para meteorología galáctica.  Se levanta una web application con un path relativo <b>/meteorology/prediccion</b> donde se pueden consultar los períodos
 * de lluvia, sequia y condiciones optimas. <br> También brinda servicios rest para consultar predicciones por día. Para consultar la documentación swagger de los servicios rest disponibles, acceder al 
 * path relativo <b>/meteorology/swagger-ui.html</b>.
 * <br><b> file:configuration/meteorologyconfig.xml</b> se definene los planetas con sus características, la galaxia,  tiempo relativo de la galaxia,
 * y algoritmos para realizar cálculos geométricos.+
 * 
 * <br><b>file:configuration/databaseconfig.xml</b> configuración de la base de datos de predicciones.
 * <br><b>file:configuration/job.xml</b> job para poblar la base de predicciones.
 * <br>
 * <br>Para ejecutar el job para poblar la base de datos con predicciones, se debe pasar el parámetro <b>poblate</b> ejecuta el job de poblado de la base de predicciones y no levanta la aplicación web.
 * 
 * @author lposca
 *
 */
@EntityScan(basePackages = { "com.meli.meteorologiagalactica.model" })
@SpringBootApplication
@ImportResource({ "file:configuration/meteorologyconfig.xml", "file:configuration/databaseconfig.xml",
		"file:configuration/job.xml" })
public class GalacticMeteorologyApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(GalacticMeteorologyApplication.class);
	@Autowired
	private DayJob dayJob;

	public static void main(String[] args) {
		if (args.length >= 1 && Constants.POBLATE.equals(args[0])) {
			logger.warn("Poblate days to database");
			SpringApplication app = new SpringApplication(GalacticMeteorologyApplication.class);
			app.setWebApplicationType(WebApplicationType.NONE);
			app.run(args);
		} else {
			logger.warn("Starting meteorology application");
			SpringApplication.run(GalacticMeteorologyApplication.class, args);
		}

	}
	
	/**
	 * Ejecuta el job de poblado si se pasa como parámetro de la aplicación <b>poblate</b>.
	 */
	@Override
	public void run(String... args) throws Exception {
		if (args.length >=1 && Constants.POBLATE.equals(args[0])) {
			logger.warn("Starting poblate job");
			String planet = args.length>1? args[1]:Constants.VULCANO;
			dayJob.poblate(planet);
		}

	}

}
