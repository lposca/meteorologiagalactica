package com.meli.meteorologiagalactica;

import com.meli.meteorologiagalactica.time.RelativeTime;

/**
 * Interface para un cuerpo de la galaxia que pueda desplazarse mediante el movimiento circular uniforme alrededor del sol. <br>
 * 
 * @author lposca
 *
 */
public interface AstronomicalObject {

	/**
	 * Devuelve la posición del cuerpo para determinado instante de tiempo.
	 * @param time  tiempo de desplazamiento del cuerpo, en horas, minutos o segundos, determinado en {@link RelativeTime#getRadianTimeFactor}
	 * @return  Posición del cuerpo luego de aplicar el desplazamiento.s
	 */
	public Position getPositionByTime(long time);

	/**
	 * 
	 * Cantidad de dias que posee un año para dicho planeta.
	 *
	 * @return dias del año
	 */
	public int getDaysperyear();
	
	/**
	 * Nombre del cuerpo astronómico
	 */
	public String getName();
}
