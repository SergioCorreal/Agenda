package mundo;
//DECLARACIÓN
/**
 * Esta es una clase que modela una semana del semestre.
 * @author Sergio Correal
 *
 */
public class Semana {
	//CONSTANTES-------------------------------------------------------------------------------------
	/**
	 * Es el número de días a la semana.
	 */
	public static final int DIAS=7;
	//ATRIBUTOS--------------------------------------------------------------------------------------
	/**
	 * Es un contenedor quee tiene los días de la semana.
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
	 * Crea un día en la semana.
	 * @param d El número del día 1<=d<=7
	 * @param f Fecha del día
	 * @param hDisp
	 */
	public void crearDia(int d, String f, double hDisp){
		dias[d-1] = new Dia(d,f,hDisp);
	}
	//MÉTODOS----------------------------------------------------------------------------------------
	/**
	 * Da los días de la semana.
	 * @return Un contenedor con los días de la semana.
	 */
	public Dia[] darDias(){
		return dias;
	}
	/**
	 * Devuelve el día.
	 * @param d Número que identifica el día 1<=d<=7
	 * @return El día deseado
	 */
	public Dia darDia(int d) {
		return dias[d-1];
	}
	/**
	 * Actualiza cada día.
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
