package mundo;
//DECLARACI�N
/**
 * Esta es una clase que modela una semana del semestre.
 * @author Sergio Correal
 *
 */
public class Semana {
	//CONSTANTES-------------------------------------------------------------------------------------
	/**
	 * Es el n�mero de d�as a la semana.
	 */
	public static final int DIAS=7;
	//ATRIBUTOS--------------------------------------------------------------------------------------
	/**
	 * Es un contenedor quee tiene los d�as de la semana.
	 */
	private Dia[] dias;
	//CONSTRUCTOR------------------------------------------------------------------------------------
	/**
	 * Contruye una semana.
	 */
	public Semana(){
		dias = new Dia[DIAS];
	}
	/**
	 * Crea un d�a en la semana.
	 * @param d El n�mero del d�a 1<=d<=7
	 * @param f Fecha del d�a
	 * @param hDisp
	 */
	public void crearDia(int d, String f, double hDisp){
		dias[d-1] = new Dia(d,f,hDisp);
	}
	//M�TODOS----------------------------------------------------------------------------------------
	/**
	 * Da los d�as de la semana.
	 * @return Un contenedor con los d�as de la semana.
	 */
	public Dia[] darDias(){
		return dias;
	}
	/**
	 * Devuelve el d�a.
	 * @param d N�mero que identifica el d�a 1<=d<=7
	 * @return El d�a deseado
	 */
	public Dia darDia(int d) {
		return dias[d-1];
	}
	/**
	 * Actualiza cada d�a.
	 * @param path Camino donde se encuentra el archivo de la semana (e.g. datos\\s1).
	 * @throws Exception Si algo falla.
	 */
	public void actualizarDias(String path) throws Exception {
		for (int i = 1; i <= DIAS; i++) {
			Dia dia = dias[i-1];
			dia.actualizar(path+"/d"+""+i);
		}
	}
}
