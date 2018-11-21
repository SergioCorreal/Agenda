package mundo;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
//DECLARACIÓN----------------------------------------------------------------------------------------
/**
 * Es la clase que modela un día.
 * @author Sergio Correal
 *
 */
public class Dia {
	//CONSTANTES-------------------------------------------------------------------------------------
	//ATRIBUTOS--------------------------------------------------------------------------------------
	/**
	 * Es el nombre del día.
	 */
	private String nombre;
	/**
	 * Es la fecha del día.
	 */
	private String fecha;
	/**
	 * Es el tiempo disponible en el día (i.e. tiempo descontando clases).
	 */
	private double tiempo;
	/**
	 * Es una lista de trabajos.
	 */
	private ArrayList trabajos;
	private ArrayList listaVectores;
	//CONSTRUCTOR------------------------------------------------------------------------------------
	/**
	 * Contruye un día.
	 * @param i El número que identifica al día. 1<=i<=7.
	 * @param f Texto que identifica la fecha.
	 * @param hDisp Las horas disponibles al día (sin clases).
	 */
	public Dia(int i, String f, double hDisp) {
		ponerNombre(i);
		tiempo = hDisp;
		fecha = f;
		trabajos = new ArrayList();
		listaVectores = new ArrayList();
		
	}
	//MÉTODOS----------------------------------------------------------------------------------------
	public String darNombre() {
		return nombre;
	}
	public double darTiempo() {
		return tiempo;
	}
	public ArrayList darTrabajos(){
		return trabajos;
	}
	public ArrayList darListaVectores() {
		return listaVectores;
	}
	public Trabajo darTrabajo(int w){
		return (Trabajo) trabajos.get(w);
	}
	public int[] darVector(int v){
		return (int[]) listaVectores.get(v);
	} 
	public String darFecha() {
		return fecha;
	}
	/**
	 * Nombra al día según el número que lo identifica.
	 * @param i Número que identifica el día. 1<=i<=7.
	 */
	public void ponerNombre(int i){
		if (i==1) {
			nombre="Lunes";
		}else if (i==2) {
			nombre="Martes";
		}else if (i==3) {
			nombre="Miércoles";
		}else if (i==4) {
			nombre="Jueves";
		}else if (i==5) {
			nombre="Viernes";
		}else if (i==6) {
			nombre="Sábado";
		}else{
			nombre="Domingo";
		}
	}
	/**
	 * Este método actualiza el tiempo del día.
	 * @param h Horas gastadas.
	 */
	public void actualizarTiempo(double h){
		tiempo = tiempo-h;
	}
	/**
	 * Crea un trabajo, un nuevo directorio y un txt con la descripción de éste (e.g. org,false [i.e. descripción,check]).
	 * @param path Camino hacia el archivo que incluye la semana y el día (e.g. datos\\s1\\d1).
	 * @param n Nombre del curso que tine un trabajo (i.e. el nombre del trabajo).
	 * @throws Exception Si algo falla al crear el archivo.
	 */
	public void crearTrabajo(String path, String n) throws Exception{
		
		int i = trabajos.size();
		String camino =path+"/w"+i;
		File f = new File(camino);
		if (!f.exists()) {
			f.mkdir();
			FileWriter fw = new FileWriter(camino+"/descripcion.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(n+",false");
			bw.close();
			Trabajo t = new Trabajo(n);
			trabajos.add(t);
		}
	}
	/**
	 * Adiciona las coordenadas de un objetivo al día y lo guarda en un archivo.
	 * @param objetivo El objetivo.
	 * @param path Camino donde se crea el archivo (e.g. datos\\s1\\d1).
	 * @throws Exception Si hay un error al escribir el archivo.
	 */
	public void adicionarVector(Objetivo objetivo, String path) throws Exception{
		int i = listaVectores.size();
		int[] vector = objetivo.darCoordenadas();
		int s = vector[0];
		int d = vector[1];
		int w = vector[2];
		int t = vector[3];
		int o = vector[4];
		FileWriter fw = new FileWriter(path+"/v"+i+".txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(s+","+d+","+w+","+t+","+o);
		bw.close();
		listaVectores.add(vector);
	}
	/**
	 * Actualiza las listas de trabajos y objetivos del día
	 * @param path Camino donde se encuentra la carpeta del día (e.g. datos\\s1\\d1)
	 * @throws Exception
	 */
	public void actualizar(String path) throws Exception {
		actualizarTrabajos(path);
		actualizarVectores(path);
	}
	/**
	 * Si existen archivos con trabajos, los adiciona a la lista de trabajos.
	 * @param path Camino donde se encuentra la carpeta del día (e.g. datos\\s1\\d1)
	 * @throws Exception Si algo falla.
	 */
	public void actualizarTrabajos(String path) throws Exception {
		
		boolean stop = false;
		int k = 0;
		while(!stop){
			String camino = path+"/w"+""+k;
			File file = new File(camino);
			if (file.exists()) {
				FileInputStream fis = new FileInputStream(camino+"/descripcion.txt");
				BufferedInputStream bis = new BufferedInputStream(fis);
				BufferedReader br = new BufferedReader(new InputStreamReader(bis));
				String linea = br.readLine();
				String[] parts = linea.split(",");
				String desc = parts[0];
				boolean check = Boolean.parseBoolean(parts[1]);
				Trabajo trabajo = new Trabajo(desc);
				if (check) {
					trabajo.cambiarCheck();
				}
				trabajo.actualizarTareas(camino);
				trabajos.add(trabajo);
				br.close();
			}else{
				stop = true;
			}
			k++;
		}
	}
	/**
	 * Si existen archivos con vectores de objetivos, los adiciona a la lista de vectores.
	 * @param path Camino donde se encuentra la carpeta del día (e.g. datos\\s1\\d1)
	 * @throws Exception Si algo falla.
	 */
	public void actualizarVectores(String path) throws Exception {
		boolean stop = false;
		int k = 0;
		while(!stop){
			String camino = path+"/v"+""+k+".txt";
			File file = new File(camino);
			if (file.exists()) {
				FileInputStream fis = new FileInputStream(camino);
				BufferedInputStream bis = new BufferedInputStream(fis);
				BufferedReader br = new BufferedReader(new InputStreamReader(bis));
				String linea = br.readLine();
				String[] parts = linea.split(",");
				int[] vector = new int[Objetivo.NUMCOORDENADAS];
				vector[0] = Integer.parseInt(parts[0]);
				vector[1] = Integer.parseInt(parts[1]);
				vector[2] = Integer.parseInt(parts[2]);
				vector[3] = Integer.parseInt(parts[3]);
				vector[4] = Integer.parseInt(parts[4]);
				listaVectores.add(vector);
				br.close();
			}else{
				stop = true;
			}
			k++;
		}
	}
	/**
	 * Elimina un vector del dia y actualiza los demás archivos.
	 * @param path Camino donde está el archivo del vector a eliminar (e.g. datos\\s1\\d1).
	 * @param vector Vector a eliminar.
	 * @throws Exception Si algo falla.
	 */
	public void devolverObjetivo(String path, int[] vector) throws Exception {
		
		int index = buscarIndexVector(vector);
		//borra todos los archivos comenzando desde el que se quiere eliminar.
		for (int i = index; i < listaVectores.size(); i++) {
			File file = new File(path+"\\v"+i+".txt");
			file.delete();
		}
		listaVectores.remove(index);
		// vuelve a escribir los archivos de los vectores que no se borraron.
		for (int i = index; i < listaVectores.size(); i++) {
			int[] v = (int[]) listaVectores.get(i);
			FileWriter fw = new FileWriter(path+"/v"+i+".txt");
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(v[0]+","+v[1]+","+v[2]+","+v[3]+","+v[4]);
			bw.close();
			}
	}
	/**
	 * Devuelve la posición de un vector en la lista. Si no está devuelve -1.
	 * @param buscado El vector buscado.
	 * @return El index del vector buscado.
	 */
	public int buscarIndexVector(int[] buscado){
		
		int index = -1;
		for (int i = 0; i < listaVectores.size(); i++) {
			int[] vector = (int[]) listaVectores.get(i);
			if (vector[0] == buscado[0] 
					&& vector[1] == buscado[1]
							&& vector[2] == buscado[2]
									&& vector[3] == buscado[3]
											&& vector[4] == buscado[4]) {
				index = i;
			}
		}
		return index;
	}
}
