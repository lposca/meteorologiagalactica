package com.meli.meteorologiagalactica;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.meli.meteorologiagalactica.time.RelativeTime;

/**
 * Representación de un planeta. Su desplazamiento se rige por el tiempo, el
 * cual se calcula mediante su velocidad angular. <br>
 * Su implementación debe definirse en el archivo
 * <b>configuration/meteorologyconfig.xml</b> para ser inyectado por el
 * framework de Spring como bean. <br>
 * Podemos configurar:
 * <br>- el nombre del planeta
 * <br>- los grados centigrados que se desplaza el planeta por día
 * <br>- su distancia al sol
 * <br>- el sentido de desplazamiento (horario/anti-horario)
 * <br>- el ángulo inicial del planeta <br>
 * Esta configuración se realiza seteando dichos fields:
 * <br>- {@link Planet#name} que indica el nombre del planeta
 * <br>- {@link Planet#degreePerDay} grados por dia que se desplaza el planeta, en
 * centigrados
 * <br>- {@link Planet#distanceToSun} indica la distancia al sol en km
 * <br>- {@link Planet#clockwise} true si el planeta se desplaza en sentido
 * horario, false en sentido anti-horario
 * <br>- {@link Planet#initialAngle} permite calcular el angulo inicial del
 * planeta en el momento 0, calculado por initialAngle/angularVelocity <br>
 * Luego de construído el bean, el método {@link Planet#init()} completa los
 * siguientes fields automáticamente: <br>
 * <br>- {@link Planet#clockwiseFactor} -1 sentido horario y 1 sentido
 * anti-horario, dicho valor se utiliza para el sentido del desplazamiento del
 * planeta
 * <br>- {@link Planet#angularVelocity} velocidad angular, calculada mediante
 * degreePerDay(en radianes)/time.getRadianTimeFactor()
 * <br>- {@link Planet#daysperyear} dias por año que tarda el planeta en dar una
 * vuelta de 360°, calculado por time.getDaysperyear()/degreePerDay
 * 
 * 
 * 
 * @author lposca
 *
 */
public class Planet implements AstronomicalObject {

	private static final Logger logger = LoggerFactory.getLogger(Planet.class);
	private double degreePerDay;
	private int distanceToSun;

	private int clockwiseFactor;
	private double angularVelocity;
	private boolean clockwise = false;
	private int daysperyear;
	private int initialAngle = 0, initialTime = 0;
	@Autowired
	private RelativeTime time;
	private String name;

	/**
	 * Constructor por defecto de un planeta
	 */
	public Planet() {

	}

	public Planet(double degreePerDay, int distanceToSun, boolean clockwise) {
		setDegreePerDay(degreePerDay);
		setDistanceToSun(distanceToSun);
		clockwiseFactor = clockwise ? -1 : 1;
		angularVelocity = Math.toRadians(degreePerDay) / 24;
	}

	/**
	 * Determina si el desplazamiento del planeta es en sentido horario.
	 * 
	 * @return true si es sentido horario, false caso contrario.
	 */
	public boolean isClockwise() {
		return clockwise;
	}

	/**
	 * Setea el sentido del desplazamiento del planeta.
	 * 
	 * @param clockwise
	 *            true si es sentido horario, false caso contrario.
	 */
	public void setClockwise(boolean clockwise) {
		this.clockwise = clockwise;
	}

	/**
	 * Grados por dia que se desplaza un planeta.
	 * 
	 * @return los grados de desplazamiento.
	 */
	public double getDegreePerDay() {
		return degreePerDay;
	}

	/**
	 * Setea los grados por dia que se desplaza un planeta
	 * 
	 * @param degreePerDay
	 *            grados de desplazazamiento
	 */
	public void setDegreePerDay(double degreePerDay) {
		this.degreePerDay = degreePerDay;
	}

	/**
	 * Distancia al sol
	 * 
	 * @return distancia al sol
	 */
	public int getDistanceToSun() {
		return distanceToSun;
	}

	/**
	 * Seta la distancia al sol
	 * 
	 * @param distanceToSun
	 *            distancia al sol
	 */
	public void setDistanceToSun(int distanceToSun) {
		this.distanceToSun = distanceToSun;
	}

	public int getDaysperyear() {
		return daysperyear;
	}

	/**
	 * Angulo inicial de posición del planeta con respecto al sol. Por defecto es 0.
	 * 
	 * @return ángulo inicial.
	 */
	public int getInitialAngle() {
		return initialAngle;
	}

	/**
	 * Setea el águlo inicial de posición del planeta con respecto al sol
	 * 
	 * @param initialAngle
	 */
	public void setInitialAngle(int initialAngle) {
		this.initialAngle = initialAngle;
	}

	@Override
	public Position getPositionByTime(long time) {

		/**
		 * El cálculo de la posición del planeta en determinado instante, se realiza
		 * aplicando la fórmula de desplazamiento del movimiento circular uniforme,
		 * aplicando la velocidad angular en función del tiempo. 
		 * clockwiseFactor
		 * determina el sentido del desplazamiento del planeta. 
		 * angularVelocity es la velocidad angular initialTime es el tiempo a 
		 * sumar en el caso que el planeta
		 * se encuentre en un ángulo inicial distinto de (0,0). Se simula que se
		 * encuentra en determinado instante para poder aplicar la misma fórumla.
		 */
		double x = distanceToSun * Math.cos((clockwiseFactor) * angularVelocity * (time + initialTime));
		double y = distanceToSun * Math.sin((clockwiseFactor) * angularVelocity * (time + initialTime));
		return new Position(x, y);
	}

	/**
	 * Definición del tiempo relativo en la galaxia.
	 * 
	 * @return tiempo relativo
	 */
	public RelativeTime getTime() {
		return time;
	}

	/**
	 * Inicializa propiedades del planeta que luego serán utilizadas para calcular
	 * las condiciones climáticas. <br>
	 * Se calcula:
	 * <br>- sentido de desplazamiento del planeta
	 * <br>- velocidad angular en base al tiempo utilizado para realizar los cálculos
	 * climáticos
	 * <br>- días que posee un año
	 * <br>- en base al ángulo inicial de posicionamiento del planeta, se calcula un
	 * período de tiempo que será sumado al tiepmo durante el desplazamiento
	 * circular
	 */
	@PostConstruct
	public void init() {
		// sentido horario u anti-horario
		clockwiseFactor = clockwise ? -1 : 1;
		// velocidad angular
		angularVelocity = Math.toRadians(degreePerDay) / time.getRadianTimeFactor();
		// dias que posee un año
		daysperyear = new Double(time.getDaysperyear() / degreePerDay).intValue();
		if (initialAngle != 0) {
			// tiempo en el que se encontraría un planeta en el caso que estuviese en
			// determinado ángulo inicial. Dicho tiempo se suma
			// a cada cálculo de desplazamiento
			initialTime = (int) (Math.toRadians(initialAngle) / angularVelocity);
		}
		
		logger.warn("Planet built " + toString());

	}

	public String getName() {
		return name;
	}

	/**
	 * Setea el nombre del planeta
	 * 
	 * @param name
	 *            nombre del planeta
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Planet [name=" + name + ", degreePerDay=" + degreePerDay + ", distanceToSun=" + distanceToSun
				+ ", clockwiseFactor=" + clockwiseFactor + ", angularVelocity=" + angularVelocity + ", clockwise="
				+ clockwise + ", daysperyear=" + daysperyear + ", initialAngle=" + initialAngle + ", initialTime="
				+ initialTime + ", time=" + time + "]";
	}

	

	
	
}
