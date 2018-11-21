package mundo;
//DECLARACIÓN----------------------------------------------------------------------------------------
/**
 * Es la clase que modela un curso del semestre.
 * @author Sergio Correal
 *
 */
public class Curso {
	//CONSTANTES-------------------------------------------------------------------------------------
	//ATRIBUTOS--------------------------------------------------------------------------------------
	/**
	 * Es el título del curso.
	 */
	private String nombre;
	/**
	 * Corresponde a los créditos que la mataria tiene según la universidad.
	 */
	private double creditosTeoricos;
	/**
	 * Corresponde a los créditos que el estudiante percibe.
	 */
	private double creditosReales;
	/**
	 * Es el número de horas de clase totales ala semana.
	 */
	private double horasClase;
	//CONSTRUCTOR------------------------------------------------------------------------------------
	/**
	 * Crea el curso.
	 * @param n es el nombre.
	 * @param cT es el número de créditos teóricos.
	 * @param cR es el número de créditos que el estudiante percibe.
	 * @param hC es el número de horas de clase a la semana.
	 */
	public Curso(String n, double cT, double cR, double hC){
		nombre = n;
		creditosTeoricos=cT;
		creditosReales=cR;
		horasClase=hC;
	}
	//MÉTODOS----------------------------------------------------------------------------------------
	public String darNombre(){
		return nombre;
	}
	public double darCreditosTeoricos(){
		return creditosTeoricos;
	}
	public double darCreditosReales(){
		return creditosReales;
	}
	public double darHorasClase(){
		return horasClase;
	}
}
