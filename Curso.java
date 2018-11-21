package mundo;
//DECLARACI�N----------------------------------------------------------------------------------------
/**
 * Es la clase que modela un curso del semestre.
 * @author Sergio Correal
 *
 */
public class Curso {
	//CONSTANTES-------------------------------------------------------------------------------------
	//ATRIBUTOS--------------------------------------------------------------------------------------
	/**
	 * Es el t�tulo del curso.
	 */
	private String nombre;
	/**
	 * Corresponde a los cr�ditos que la mataria tiene seg�n la universidad.
	 */
	private double creditosTeoricos;
	/**
	 * Corresponde a los cr�ditos que el estudiante percibe.
	 */
	private double creditosReales;
	/**
	 * Es el n�mero de horas de clase totales ala semana.
	 */
	private double horasClase;
	//CONSTRUCTOR------------------------------------------------------------------------------------
	/**
	 * Crea el curso.
	 * @param n es el nombre.
	 * @param cT es el n�mero de cr�ditos te�ricos.
	 * @param cR es el n�mero de cr�ditos que el estudiante percibe.
	 * @param hC es el n�mero de horas de clase a la semana.
	 */
	public Curso(String n, double cT, double cR, double hC){
		nombre = n;
		creditosTeoricos=cT;
		creditosReales=cR;
		horasClase=hC;
	}
	//M�TODOS----------------------------------------------------------------------------------------
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
