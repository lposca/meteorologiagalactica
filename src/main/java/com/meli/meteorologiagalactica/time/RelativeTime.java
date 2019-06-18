package com.meli.meteorologiagalactica.time;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meli.meteorologiagalactica.Utils;
/**
 * Configuración del tiempo de la galaxia.<br>
 * Por defecto se utiliza los valores normales y conocidos de segundos, minutos y horas por día.
 * Podrían cambiarse dichos valores en el bean <b>relativetime</b> 
 * del archivo de configuración <b>configuration/meteorologyconfig.xml</b> 
 * seteando los fields: 
 * <br>- {@link RelativeTime#secondsperminute}
 * <br>-  {@link RelativeTime#minutesperhour}
 * <br>- {@link RelativeTime#hoursperday}
 * <br>
 * Una propiedad importante es {@link RelativeTime#angularvelocitytime}, que indica la magnitud de tiempo 
 * con la cual va a trabajar la velocidad angular, y por ende los cálculos de desplazamiento del planeta serán sobre dicha magnitud.
 * Por defecto es en <b>horas</b> <br>
 * Puede tomar dichos valores: <b> HOURS, MINUTES, SECONDS</b>
 * @author lposca
 *
 */
public class RelativeTime {

	private static final Logger logger = LoggerFactory.getLogger(RelativeTime.class);
	
	private int secondsperminute = 60;
	private int minutesperhour = 60;
	private int hoursperday = 24;
	private int daysperyear = 360;
	private String angularvelocitytime = "HOURS";
	
	private DayCalculator dayCalculator;
	
	public RelativeTime() {
		
	}
	
	/**
	 * Cantidad de horas por día
	 * @return
	 */
	public int getHoursperday() {
		return hoursperday;
	}
	
	/**
	 * Seteo de horas por día
	 * @param hoursperday horas
	 */
	public void setHoursperday(int hoursperday) {
		this.hoursperday = hoursperday;
	}

	/**
	 * Cantidad de días que posee un año, no se puede cambiar.
	 * @return días del año
	 */
	public int getDaysperyear() {
		return daysperyear;
	}


	/**
	 * Cantidad de segundos por minuto
	 * @return segundos por minuto
	 */
	public int getSecondsperminute() {
		return secondsperminute;
	}

	/**
	 * Seeta la cantidad de segundos por minuto
	 * @param secondsperminute segundos por minuto
	 */
	public void setSecondsperminute(int secondsperminute) {
		this.secondsperminute = secondsperminute;
	}

	/**
	 * Cantidad de 
	 * @return minutos por hora
	 */
	public int getMinutesperhour() {
		return minutesperhour;
	}

	/**
	 * Setea la cantidad de minutos por hora
	 * @param minuteperhour minutos
	 */
	public void setMinutesperhour(int minuteperhour) {
		this.minutesperhour = minuteperhour;
	}

	/**
	 * Magnitud de tiempo con al cual va a trabajar la velocidad angular
	 * <br>
	 * <b> HOURS, MINUTES, SECONDS</b>
	 * @return
	 */
	public String getAngularvelocitytime() {
		return angularvelocitytime;
	}

	/**
	 * Setea la magnitud de tiempo de la velocidad angular.
	 * @param angularvelocitytime <b> HOURS, MINUTES, SECONDS</b>
	 */
	public void setAngularvelocitytime(String angularvelocitytime) {
		this.angularvelocitytime = angularvelocitytime;
	}

	/**
	 * Facto para calcular la velocidad angular dependiendo la magnitud de tiempo. <br>
	 * Ej: si la velocidad angular es de 5° por dia, y angularvelocitytime = HOURS, entonces
	 * la transformación de la velocida angular es de  Math.toRadians(5) / {@link RelativeTime#hoursperday}.  <br>Si 
	 *  angularvelocitytime = MINUTES entonces la velocidad angular es Math.toRadians(5) / {@link RelativeTime#hoursperday}	 * {@link RelativeTime#minutesperhour}.
	 * @return
	 */
	public int getRadianTimeFactor()
	{
		switch (angularvelocitytime) {
		case "HOURS":
			return hoursperday;
		case "MINUTES":
			return hoursperday * minutesperhour;
		case "SECONDS":
			return hoursperday*minutesperhour*secondsperminute;

		default:
			return hoursperday;
			
		}
		
	}
	
	/**
	 * Determina la clase que va a transformar la magnitud con la que trabajar la velocidad angular a días.
	 */
	@PostConstruct
	public void init() {
		
		switch (angularvelocitytime) {
		case "HOURS":
			dayCalculator = new HourToDayCalculator();
			break;
		case "MINUTES":
			dayCalculator = new MinutesToDayCalculator();
			break;
		case "SECONDS":
			dayCalculator = new SecondsToDayCalculator();
			break;
		default:
			dayCalculator = new HourToDayCalculator();
			
		}
		
		logger.warn("Relative time configuration");
		logger.warn("Angular velocity magnitude " + angularvelocitytime);
		logger.warn(toString());
		
	}
	/**
	 * Calcula los días en base a la magnitud de tiempo y la clase utilizada para cálculo de días.
	 * @param value
	 * @return
	 */
	public long toDay(long value)
	{
		return dayCalculator.toDay(value)+1;
	}
	
	/**
	 *  Calcula los días en base a la magnitud de tiempo.
	 * @author lposca
	 *
	 */
	interface DayCalculator
	{
		long toDay(long value);
	}
	
	/**
	 * Calcula los días en base a las horas por día seteadas en el sistema
	 * @author lposca
	 *
	 */
	class HourToDayCalculator implements DayCalculator{
		@Override
		public long toDay(long value) {
			return Utils.bd(value, 2).divide(Utils.bd(hoursperday, 2),2).longValue();
		}
	}
	
	/**
	 * Calcula los días en base a los minutos y horas por día seteadas en el sistema
	 * @author lposca
	 *
	 */
	class MinutesToDayCalculator implements DayCalculator{
		
		@Override
		public long toDay(long value) {
			
			return Utils.bd(value, 2).divide(Utils.bd(hoursperday*minutesperhour, 2),2).longValue();
		}
	}
	
	/**
	 * Calcula los días en base a los segundos,  minutos y horas por día seteadas en el sistema
	 * @author lposca
	 *
	 */
	class SecondsToDayCalculator implements DayCalculator{
		@Override
		public long toDay(long value) {
			return Utils.bd(value, 2).divide(Utils.bd(hoursperday*minutesperhour*secondsperminute, 2),2).longValue();
		}
	}

	@Override
	public String toString() {
		return "RelativeTime [secondsperminute=" + secondsperminute + ", minutesperhour=" + minutesperhour
				+ ", hoursperday=" + hoursperday + ", daysperyear=" + daysperyear + "]";
	}
	
	
}
