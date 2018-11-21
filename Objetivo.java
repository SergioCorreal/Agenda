package mundo;

//DECLARACIÓN
public class Objetivo {
	//CONSTANTES-------------------------------------------------------------------------------------
	//ATRIBUTOS--------------------------------------------------------------------------------------
	public final static int NUMCOORDENADAS=5;
	/**
	 * Son cinco números que identifican el objetivo: semana, día, trabajo, tarea y objetivo.
	 */
	private int[] coordenadas;
	/**
	 * Es la descripción de un objetivo y siempre comienza con un verbo en infinitivo (e.g. leer).
	 */
	private String descripcion;
	/**
	 * Es el tiempo que el usuario pronostica que toma realizar el objetivo.
	 */
	private double duracion;
	/**
	 * Es una variable que indica si el objetivo se cumplió o no.
	 */
	private boolean check;
	//CONSTRUCTOR------------------------------------------------------------------------------------
	/**
	 * Crea un objetivo.
	 * @param desc Descripción corta del objetivo.
	 * @param tiempo Duración.
	 * @param s Número que identifica la semana.
	 * @param d Número que identifica el día.
	 * @param w Número que identifica el trabajo.
	 * @param t Número que identifica la tarea.
	 * @param o Número que identifica el objetivo.
	 */
	public Objetivo(String desc, double tiempo, int s, int d, int w, int t, int o) {
		descripcion = desc;
		duracion=tiempo;
		check=false;
		coordenadas =  new int[NUMCOORDENADAS];
		coordenadas[0]=s;
		coordenadas[1]=d;
		coordenadas[2]=w;
		coordenadas[3]=t;
		coordenadas[4]=o;
	}
	//MÉTODOS----------------------------------------------------------------------------------------
	public String darDescripcion() {
		return descripcion;
	}
	public double darDuracion(){
		return duracion;
	}
	public boolean darCheck() {
		return check; 
	}
	public int[] darCoordenadas(){
		return coordenadas;
	}
	public void cambiarCheck(){
		check = true;
	}
}
