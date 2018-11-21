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
public class Tarea {
	//CONSTANTES-------------------------------------------------------------------------------------
	//ATRIBUTOS--------------------------------------------------------------------------------------
	/**
	 * Describe la tarea y es un sustantivo (e.g. taller).
	 */
	private String descripcion;
	/**
	 * Verifica si la tarea está cumplida o no.
	 */
	private boolean check;
	/**
	 * Es un arreglo de objetivos que tiene la tarea.
	 */
	private ArrayList objetivos;
	//CONSTRUCTOR------------------------------------------------------------------------------------
	/**
	 * Contruye la tarea.
	 * @param d Descripción de la tarea.
	 */
	public Tarea(String d) {
		descripcion=d;
		check=false;
		objetivos=new ArrayList();
	}
	//MÉTODOS----------------------------------------------------------------------------------------
	public String darDescripcion() {
		return descripcion;
	}
	public boolean darCheck() {
		return check;
	}
	public Objetivo darObjetivo(int i) {
		return (Objetivo) objetivos.get(i);
	}
	public void cambiarCheck(){
		check = true;
	}
	/**
	 * Crea un objetivo y un txt con la información de éste.
	 * @param desc Descripción del objetivo.
	 * @param tiempo Duración del objetivo.
	 * @param path Camino con semana, día y tarea para la creación del archivo (e.g. datos\\s1\\d1\\w0\\t0).
	 * @param s Número que identifica la semana.
	 * @param d Número que identifica el día.
	 * @param w Número que identifica el trabajo.
	 * @param t Número que identifica la tarea.
	 * @throws Exception Si algo falla.
	 */
	public void crearObjetivo(String desc, double tiempo, String path, int s, int d, int w, int t) throws Exception{
		int o = objetivos.size();
		FileWriter fw = new FileWriter(path+"/o"+o+".txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(desc+","+""+tiempo+",false"+","+s+","+d+","+w+","+t+","+o);
		bw.close();
		Objetivo objetivo = new Objetivo(desc, tiempo, s, d, w, t, o);
		objetivos.add(objetivo);
	}
	/**
	 * Si existen archivos con objetivos, los adiciona.
	 * @param path camino donde se encuentra la carpeta de la tarea (e.g. datos\\s1\\d1\\w0\\t0)
	 * @throws Exception Si algo falla.
	 */
	public void actualizarObjetivos(String path) throws Exception {
		boolean stop = false;
		int k = 0;
		while(!stop){
			String camino = path+"/o"+""+k+".txt";
			File file = new File(camino);
			if (file.exists()) {
				FileInputStream fis = new FileInputStream(camino);
				BufferedInputStream bis = new BufferedInputStream(fis);
				BufferedReader br = new BufferedReader(new InputStreamReader(bis));
				String linea = br.readLine();
				String[] parts = linea.split(",");
				String desc = parts[0];
				double tiempo = Double.parseDouble(parts[1]);
				boolean check = Boolean.parseBoolean(parts[2]);
				int s = Integer.parseInt(parts[3]);
				int d = Integer.parseInt(parts[4]);
				int w = Integer.parseInt(parts[5]);
				int t = Integer.parseInt(parts[6]);
				int o = Integer.parseInt(parts[7]);
				Objetivo objetivo = new Objetivo(desc, tiempo, s, d, w, t, o);
				if (check) {
					objetivo.cambiarCheck();
				}
				objetivos.add(objetivo);
				br.close();
			}else{
				stop = true;
			}
			k++;
		}
	}
	public ArrayList darObjetivos() {
		return objetivos;
	}
	/**
	 * Chequea la tarea si todos los objetivos están cumplidos y actualiza el archivo.
	 * @param path camino donde se encuentra la carpeta de la tarea (e.g. datos\\s1\\d1\\w0\\t0)
	 * @throws IOException Si algún error sucede al escrbir el archivo.
	 */
	public void actualizarCheck(String path) throws IOException{
		int size = objetivos.size();
		int contador = 0;
		for (int i = 0; i < size; i++) {
			Objetivo objetivo = (Objetivo) objetivos.get(i);
			boolean check = objetivo.darCheck();
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
	/**
	 * Chequea un objetivo y actualiza el archivo de éste.
	 * @param o Número que identifica el objetivo. o != -1.
	 * @param path camino donde se encuentra la carpeta de la tarea (e.g. datos\\s1\\d1\\w0\\t0)
	 * @throws IOException
	 */
	public void chequearObjetivo(int o, String path) throws IOException{
		Objetivo objetivo = (Objetivo) objetivos.get(o);
		objetivo.cambiarCheck();
		String d = objetivo.darDescripcion();
		String t = Double.toString(objetivo.darDuracion());
		String c = Boolean.toString(objetivo.darCheck());
		String camino = path+"/o"+""+o+".txt";
		FileWriter fw = new FileWriter(camino);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(d + "," + t + "," + c);
		bw.close();
		actualizarCheck(path);
	}
}
