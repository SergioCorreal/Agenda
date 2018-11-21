package mundo;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
//DECLARACIÓN
public class Trabajo {
	//CONSTANTES-------------------------------------------------------------------------------------
	//ATRIBUTOS--------------------------------------------------------------------------------------
	private String descripcion;
	private boolean check;
	private ArrayList tareas;
	//CONSTRUCTOR------------------------------------------------------------------------------------
	/**
	 * Crea un trabajo.
	 * @param n Es el nombre del curso para el cual es el trabajo.
	 */
	public Trabajo(String n){
		descripcion=n;
		check=false;
		tareas = new ArrayList();
	}
	//MÉTODOS----------------------------------------------------------------------------------------
	public String darDescripcion() {
		return descripcion;
	}
	public Tarea darTarea(int t){
		return (Tarea) tareas.get(t);
	}
	public ArrayList darTareas() {
		return tareas;
	}
	public boolean darCheck() {
		return check;
	}
	public void cambiarCheck(){
		check = true;
	}
	/**
	 * Adiciona una tarea, crea un nuevo directorio y un txt con la descripción de ésta (e.g.taller,false [i.e. descripción,check]). 
	 * @param path Camino hacia el archivo e incluye semana, dia y trabajo (e.g. datos\\s1\\d1\\w0).
	 * @param d Descripción de la tarea.
	 * @throws IOException 
	 */
	public void crearTarea(String path, String d) throws IOException{
		int i = tareas.size();
		String camino = path+"/t"+i;
		File f = new File(camino);
		if (!f.exists()) {
			f.mkdir();
			FileWriter fw = new FileWriter(camino+"/descripcion.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(d+",false");;
			bw.close();
			Tarea t = new Tarea(d);
			tareas.add(t);
		}
	}
	/**
	 * Si existen archivos con tareas, los adiciona.
	 * @param path camino donde se encuentra la carpeta del trabajo (e.g. datos\\s1\\d1\\w0)
	 * @throws Exception Si algo falla.
	 */
	public void actualizarTareas(String path) throws Exception {
		boolean stop = false;
		int k = 0;
		while(!stop){
			String camino = path +"/t"+""+k;
			File file = new File(camino);
			if (file.exists()) {
				FileInputStream fis = new FileInputStream(camino+"/descripcion.txt");
				BufferedInputStream bis = new BufferedInputStream(fis);
				BufferedReader br = new BufferedReader(new InputStreamReader(bis));
				String linea = br.readLine();
				String[] parts = linea.split(",");
				String desc = parts[0];
				boolean check = Boolean.parseBoolean(parts[1]);
				Tarea tarea = new Tarea(desc);
				if (check) {
					tarea.cambiarCheck();
				}
				tarea.actualizarObjetivos(camino);
				tareas.add(tarea);
				br.close();
			}else{
				stop = true;
			}
			k++;
		}
	}
	/**
	 * Chequea el trabajo si todas las tareas están cumplidas y actualiza el archivo de éste.
	 * @param path camino donde se encuentra el trabajo (e.g. datos\\s1\\d1\\w0)
	 * @throws IOException Si algún error sucede al escrbir el archivo.
	 */
	public void actualizarCheck(String path) throws IOException{
		int size = tareas.size();
		int contador = 0;
		for (int i = 0; i < size; i++) {
			Tarea tarea = (Tarea) tareas.get(i);
			boolean check = tarea.darCheck();
			if (check) {
				contador++;
			}
		}
		if (contador == size) {
			cambiarCheck();
			FileWriter fw = new FileWriter(path+"/descripcion.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(descripcion+","+check);
			bw.close();
		}
	}
}
