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
/**
 * Esta es la clase principal del mundo.
 * @author Sergio Correal
 *
 */
//DECLARACIÓN----------------------------------------------------------------------------------------
public class Agenda {
	//CONSTANTES-------------------------------------------------------------------------------------
	public static final int SEMANAS=20;
	public static final int DIAS_DE_ENERO = 31;
	public static final int DIAS_DE_FEBRERO_NO_BISIESTO = 28;
	public static final int DIAS_DE_FEBRERO_BISIESTO = 29;
	public static final int DIAS_DE_MARZO = 31;
	public static final int DIAS_DE_ABRIL = 30;
	public static final int DIAS_DE_MAYO = 31;
	public static final int DIAS_DE_JUNIO = 30;
	public static final int DIAS_DE_JULIO = 31;
	public static final int DIAS_DE_AGOSTO = 31;
	public static final int DIAS_DE_SEPTIEMBRE = 30;
	public static final int DIAS_DE_OCTUBRE = 31;
	public static final int DIAS_DE_NOVIEMBRE = 30; 
	public static final int DIAS_DE_DICIEMBRE = 31;

	//ATRIBUTOS--------------------------------------------------------------------------------------

	/**
	 * Este es el arreglo que contiene los cursos del semestre.
	 */
	private ArrayList semestre;
	/**
	 * Es un contenedor con las semanas del semestre. 
	 */
	private Semana[] semanas;
	/**
	 * Es un contenedor con los nombres de las fechas del año.
	 */
	private String[] fechasDelAnio;
	//CONSTRUCTOR------------------------------------------------------------------------------------
	/**
	 * Construye la agenda a partir de información en la carpeta de trabajo.
	 * file File archivo donde están las tareas, trabajos y objetivos de cada día.
	 * fecha String Es aquella donde inicia el periodo
	 * bisiesto boolean
	 * @throws Exception Si algo falla al leer archivos.
	 */
	public Agenda(File file, String fecha, boolean bisiesto, double[] hDisp) throws Exception {
		int contadorFecha = 0;
		semestre = new ArrayList();
		ponerFechasDelAnio(bisiesto);
		int inicio = buscarIndexFecha(fecha);

		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream bis = new BufferedInputStream(fis);
		BufferedReader br = new BufferedReader(new InputStreamReader(bis));
		String l;
		while ((l=br.readLine()) != null) {
			String[] parts = l.split(",");
			String n = parts[0]; //nombre
			double cT = Double.parseDouble(parts[1]); //créditos teóricos
			double cR = Double.parseDouble(parts[2]); // créditos reales
			double hC = Double.parseDouble(parts[3]); // horas
			Curso c= new Curso(n, cT, cR, hC);
			semestre.add(c);
		}
		fis.close();
		bis.close();
		br.close();
		File f = new File("datos");
		if (!f.exists()) {
			f.mkdir();
		}

		// creamos los directorios
		for (int i = 1; i <= SEMANAS; i++) {
			File s = new File("./datos/s"+""+i);
			if (!s.exists()) {
				s.mkdir();
				for (int j = 1; j <= Semana.DIAS; j++) {
					File d = new File("./datos/s"+""+i+"/d"+j);
					if (!d.exists()) {
						d.mkdir();
					}
				}
			}
		}
		// creamos los demás elementos del mundo
		semanas = new Semana[SEMANAS];
		for (int i = 1; i <= SEMANAS; i++) {
			semanas[i-1] = new Semana();
			for (int j = 1; j <= Semana.DIAS; j++) {
				if (inicio == -1) {
					throw new Exception("Ingrese una fecha inicial válida. Borre el archivo calendario.txt e intételo de nuevo.");
				}
				semanas[i-1].crearDia(j, darFecha(inicio+contadorFecha), hDisp[j-1]);
				contadorFecha +=1;
			}
		}
		actualizar();
	}
	//MÉTODOS----------------------------------------------------------------------------------------
	/**
	 * Inicia el contenedor de fechasDelAnio.
	 * @param bisiesto boolean
	 */
	public void ponerFechasDelAnio(boolean bisiesto){
		int contador = 0;
		if (bisiesto) {
			fechasDelAnio = new String[366];
		}else {
			fechasDelAnio = new String[365];
		}
		for (int a = 1; a <= DIAS_DE_ENERO; a++) {
			fechasDelAnio[contador] = a+"-enero";
			contador +=1;
		}
		if (bisiesto) {
			for (int b = 1; b <= DIAS_DE_FEBRERO_BISIESTO; b++) {
				fechasDelAnio[contador] = b+"-febrero";
				contador +=1;
			}
		}else {
			for (int b = 1; b <= DIAS_DE_FEBRERO_NO_BISIESTO; b++) {
				fechasDelAnio[contador] = b+"-febrero";
				contador +=1;
			}
		}
		for (int c = 1; c <= DIAS_DE_MARZO; c++) {
			fechasDelAnio[contador] = c+"-marzo";
			contador +=1;
		}
		for (int d = 1; d <= DIAS_DE_ABRIL; d++) {
			fechasDelAnio[contador] = d+"-abril";
			contador +=1;
		}
		for (int e = 1; e <= DIAS_DE_MAYO; e++) {
			fechasDelAnio[contador] = e+"-mayo";
			contador +=1;
		}
		for (int f = 1; f <= DIAS_DE_JUNIO; f++) {
			fechasDelAnio[contador] = f+"-junio";
			contador +=1;
		}
		for (int g = 1; g <= DIAS_DE_JULIO; g++) {
			fechasDelAnio[contador] = g+"-julio";
			contador +=1;
		}
		for (int a = 1; a <= DIAS_DE_AGOSTO; a++) {
			fechasDelAnio[contador] = a+"-agosto";
			contador +=1;
		}
		for (int a = 1; a <= DIAS_DE_SEPTIEMBRE; a++) {
			fechasDelAnio[contador] = a+"-septiembre";
			contador +=1;
		}
		for (int a = 1; a <= DIAS_DE_OCTUBRE; a++) {
			fechasDelAnio[contador] = a+"-octubre";
			contador +=1;
		}
		for (int a = 1; a <= DIAS_DE_NOVIEMBRE; a++) {
			fechasDelAnio[contador] = a+"-noviembre";
			contador +=1;
		}
		for (int a = 1; a <= DIAS_DE_DICIEMBRE; a++) {
			fechasDelAnio[contador] = a+"-diciembre";
			contador +=1;
		}
	}
	public String darFecha(int i) {
		return fechasDelAnio[i];
	}
	/**
	 * Busca el index que ubica una fecha dentro del contenedor fechasDelAnio.
	 * @param f String etiqueta de la fecha.
	 * @return índice de la fecha buscada. Si no encuantra alguna, devuelve el valor -1.
	 */
	public int buscarIndexFecha(String f) {
		int resp = -1;
		for (int i = 0; i < fechasDelAnio.length; i++) {
			if (f.equals(fechasDelAnio[i])) {
				resp = i;
			}
		}
		return resp;
	}
	/**
	 * Da el total de créditos de un semestre.
	 * @param i Da el total de créditos teóricos o reales dependiendo si i=1 o i=2, respectivamente.
	 * @return Número de créditos reales o teóricos.
	 */
	public double darCreditos(int i){
		double suma=0.0;
		if (i==1) {
			for (int j = 0; j < semestre.size(); j++) {
				Curso c= (Curso) semestre.get(j);
				suma+=c.darCreditosTeoricos();
			}
		}else if (i==2) {
			for (int j = 0; j < semestre.size(); j++) {
				Curso c= (Curso) semestre.get(j);
				suma+=c.darCreditosReales();
			}
		}
		return suma;
	}
	/**
	 * Retorna la semana de acuerdo al número que se ingresa.
	 * @param i Número de la semana. 1<=i<=16.
	 * @return Semana especificada por en número i.
	 */
	public Semana darSemana(int i){
		Semana semana= semanas[i-1];
		return semana;
	}
	public ArrayList darCursos() {
		return semestre;
	}
	/**
	 * Evalua si es posible estudiar el semestre actual.
	 * @return Cadena de texto que informa si es viable o no el semestre.
	 */
	public String evaluar(){
		String r;
		double maxCreditos=(9.0*6.0)*(16.0/48.0); // (h/s)*(c s/h) 9.0 son las horas disponibles al día
		double totalCreditosReales=darCreditos(2);
		if (totalCreditosReales<=maxCreditos) {
			r="El semestre SÍ es viable: "+"máximo de créditos soportables= "+ Double.toString(maxCreditos)+" vs créditos reales del semestre= "+Double.toString(totalCreditosReales);
		}else {
			r="El semestre NO es viable: "+"máximo de créditos soportables= "+ Double.toString(maxCreditos)+" vs créditos reales del semestre= "+Double.toString(totalCreditosReales);
		}
		return r;
	}
	/**
	 * Da las horas de estudio independiante de una materia a la semana.
	 * @param c El curso.
	 * @return El número de horas de estudio independiente.
	 */
	public double darHorasEstudioMateria(Curso c){
		double resultado = (c.darCreditosReales()*48.0/16.0)-c.darHorasClase(); // (c)*(h/c s)
		return resultado;
	}
	/**
	 * Da cadena de texto con las horas de estudio independiente por materia.
	 * @return Cadena de texto con el número de horas independientes de cada materia.
	 */
	public String darEstudioIndependiente(){
		int l = semestre.size();
		String s = "";
		for (int i = 0; i < l; i++) {
			Curso c = (Curso) semestre.get(i);
			s += c.darNombre()+": "+darHorasEstudioMateria(c)+" horas.\n";
		}
		return s;
	}
	/**
	 * Da los trabajos de un día específico.
	 * @param s Número que identifica la semana. 1<=s<=16
	 * @param d Número que identifica el día. 1<=d<=7
	 * @return Lista de trabajos.
	 */
	public ArrayList darTrabajosDeDia(int s, int d) {
		Semana semana = semanas[s-1];
		Dia dia = semana.darDia(d);
		return dia.darTrabajos();
	}
	/**
	 * Da los objetivos de un día específico.
	 * @param s Número que identifica la semana. 1<=s<=16
	 * @param d Número que identifica el día. 1<=d<=7
	 * @return Lista de objetivos.
	 */
	public ArrayList darObjetivosDeDia(int s, int d) {
		Semana semana = semanas[s-1];
		Dia dia = semana.darDia(d);
		ArrayList vectores = dia.darListaVectores();
		return darObjetivosDeVectores(vectores);
	}
	/**
	 * Crea un trabajo.
	 * @param s Número que identifica la semana. 1<=s<=16
	 * @param d Número que identifica el día. 1<=d<=7
	 * @param n Nombre del curso que tine un trabajo (i.e. el nombre del trabajo).
	 * @throws Exception Si un error sucede al escribir el archivo.
	 */
	public void crearTrabajo(int s, int d, String n) throws Exception{ 

		String path ="./datos/s"+""+s+"/d"+""+d;
		Semana semana = semanas[s-1];
		Dia dia = semana.darDia(d);
		dia.crearTrabajo(path, n);
	}
	/**
	 * Crea una tarea. Debe existir un trabajo antes de crearse la tarea.
	 * @param s Número que identifica la semana.
	 * @param d Número que identifica el día.
	 * @param w Número que identifica el trabajo.
	 * @param desc Descripción de la tarea.
	 * @throws Exception Si un error sucede al escribir el archivo.
	 */
	public void crearTarea(int s, int d, int w, String desc) throws Exception  {
		Semana semana = semanas[s-1];
		Dia dia = semana.darDia(d);
		Trabajo trabajo = dia.darTrabajo(w);
		String path = "./datos/s"+""+s+"/d"+""+d+"/w"+""+w;
		trabajo.crearTarea(path, desc);
	}
	/**
	 * Crea un objetivo.
	 * @param s Número que identifica la semana.
	 * @param d Número que identifica el día.
	 * @param w Número que identifica el trabajo.
	 * @param t Número que identifica la tarea.
	 * @param desc Descripción del objetivo
	 * @param tiempo Tiempo que se presume que tarda el objetivo.
	 * @throws Exception Si hay un error al escribir archivos.
	 */
	public void crearObjetivo(int s, int d, int w, int t,  String desc, double tiempo) throws Exception {
		Semana semana = semanas[s-1];
		Dia dia = semana.darDia(d);
		Trabajo trabajo = dia.darTrabajo(w);
		Tarea tarea = trabajo.darTarea(t);
		String path="./datos/s"+""+s+"/d"+""+d+"/w"+""+w+"/t"+""+t;
		tarea.crearObjetivo(desc, tiempo, path, s, d, w, t);
	}
	/**
	 * Actualiza la información de la agenda.
	 * @throws Exception Si algo falla leyendo archivos.
	 */
	public void actualizar() throws Exception {
		for (int i = 1; i <= SEMANAS; i++) {
			String path = "datos\\s"+""+i;
			Semana semana = semanas[i-1];
			semana.actualizarDias(path);
		}
		actualizarTiempoDias();
	}
	/**
	 * Adiciona un objetivo a un día de una semana.
	 * @param s Número que identifica la semana.
	 * @param d Número que identifica el día.
	 * @param objetivo Es el nuevo objetivo.
	 * @throws Exception Si hay un error al escribir el archivo.
	 */
	public void ubicarObjetivo(int s, int d, Objetivo objetivo) throws Exception {
		String path = "./datos/s"+""+s+"/d"+""+d;
		Semana semana = semanas[s-1];
		Dia dia = semana.darDia(d);
		double tiempo = objetivo.darDuracion();
		dia.adicionarVector(objetivo , path);
		dia.actualizarTiempo(tiempo);
	}
	/**
	 * Chequea un objetivo y modifica el archivo de este.
	 * @param objetivo Objetivo chequeado.
	 * @throws IOException Si algo falla al modificar el archivo.
	 */
	public void chequearObjetivo(Objetivo objetivo) throws IOException{

		objetivo.cambiarCheck();
		int[] vector = objetivo.darCoordenadas();
		int s = vector[0];
		int d = vector[1];
		int w = vector[2];
		int t = vector[3];
		int o = vector[4];
		Semana semana = semanas[s-1];
		Dia dia = semana.darDia(d);
		Trabajo trabajo = dia.darTrabajo(w);
		Tarea tarea = trabajo.darTarea(t);
		
		// actualiza el archivo del objetivo
		String path ="./datos/s"+s+"/d"+d+"/w"+w+"/t"+t+"/o"+o+".txt";
		String desc = objetivo.darDescripcion();
		double tiempo = objetivo.darDuracion();
		boolean check = objetivo.darCheck();
		FileWriter fw = new FileWriter(path);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(desc+","+tiempo+","+check+","+s+","+d+","+w+","+t+","+o);
		bw.close();
		tarea.actualizarCheck("./datos/s"+s+"/d"+d+"/w"+w+"/t"+t);
		trabajo.actualizarCheck("./datos/s"+s+"/d"+d+"/w"+w);
	}
	/**
	 * Actualiza el tiempo disponible de cada día.
	 */
	public void actualizarTiempoDias() {
		for (int i = 1; i <= SEMANAS; i++) {
			Semana semana = semanas[i-1];

			for (int j = 1; j <= Semana.DIAS; j++) {
				Dia dia = semana.darDia(j);
				double tiempoObjetivos = 0.0;
				ArrayList objetivos = darObjetivosDeDia(i, j);
				for (int k = 0; k < objetivos.size(); k++) {

					Objetivo objetivo = (Objetivo) objetivos.get(k);
					tiempoObjetivos += objetivo.darDuracion();
				}
				dia.actualizarTiempo(tiempoObjetivos);
			}
		}
	}
	/**
	 * Devuelve los vectores sin asignar.
	 * @return Lista de vecotres sin asignar.
	 */
	public ArrayList darVectoresSinAsignar(){
		ArrayList respuesta = darTodosLosVectores();
		ArrayList asignados = darVectoresAsignados();
		for (int i = 0; i < asignados.size(); i++) {
			int[] vector = (int[]) asignados.get(i);
			int index = buscarIndexDeVector(vector, respuesta);
			if (index != -1) {
				respuesta.remove(index);
			}
		}
		return respuesta;
	}
	/**
	 * Devuelve los vectores asignados
	 * @return Lista de los vectores asignados.
	 */
	public ArrayList darVectoresAsignados() {
		ArrayList respuesta = new ArrayList();
		for (int i = 1; i <= SEMANAS; i++) {
			Semana semana = semanas[i-1];
			for (int j = 1; j <= Semana.DIAS; j++) {
				Dia dia = semana.darDia(j);
				ArrayList vectores = dia.darListaVectores();
				for (int k = 0; k < vectores.size(); k++) {
					int[] vector = dia.darVector(k);
					respuesta.add(vector);
				}
			}
		}
		return respuesta;
	}
	/**
	 * Devuelve todos los vectores de loc objetivos.
	 * @return Lista con los vectores de todos los objetivos.
	 */
	public ArrayList darTodosLosVectores() {
		ArrayList respuesta = new ArrayList();
		for (int i = 1; i <= SEMANAS; i++) {
			Semana semana = semanas[i-1];
			for (int j = 1; j <= Semana.DIAS; j++) {
				Dia dia = semana.darDia(j);
				int sizeTrabajos = (dia.darTrabajos()).size();
				for (int k = 0; k < sizeTrabajos; k++) {
					Trabajo trabajo = dia.darTrabajo(k);
					int sizeTareas = (trabajo.darTareas()).size();
					for (int l = 0; l < sizeTareas; l++) {
						Tarea tarea = trabajo.darTarea(l);
						int sizeObjetivos = (tarea.darObjetivos()).size();
						for (int m = 0; m < sizeObjetivos; m++) {
							Objetivo objetivo = tarea.darObjetivo(m);
							int[] vector = objetivo.darCoordenadas();
							respuesta.add(vector);
						}
					}
				}
			}
		}
		return respuesta;
	}
	/**
	 * Busca la posición de un vector en una lista de vectores.
	 * @param vector El vector buscado.
	 * @param lista Lista de vectores.
	 * @return Número de la posición del vector en la lista.
	 */
	public int buscarIndexDeVector(int[] vector, ArrayList lista){
		int resp = -1;
		for (int i = 0; i < lista.size(); i++) {
			int[] buscado = (int[]) lista.get(i);
			if (vector[0] == buscado[0] 
					&& vector[1] == buscado[1]
							&& vector[2] == buscado[2]
									&& vector[3] == buscado[3]
											&& vector[4] == buscado[4]) {
				resp = i;
			}
		}
		return resp;
	}
	/**
	 * Devuelve un vector a la lista de objetivos.
	 * @param s Número que identifica la semana.
	 * @param d Número que identifica el día.
	 * @param vector Es el vector a devolver.
	 * @param Es el negativo del tiempo a sumar.
	 * @throws Exception Si algo falla.
	 */
	public void devolverObjetivo(int s, int d, int[] vector, double sumarTiempo) throws Exception {
		Semana semana = semanas[s-1];
		Dia dia = semana.darDia(d);
		String path = "datos\\s"+s+"\\d"+d;
		dia.devolverObjetivo(path, vector);
		dia.actualizarTiempo(sumarTiempo);
	}
	/**
	 * Devuelve los objetivos que corresponden a los vectores
	 * @param vectores Lista de vectores.
	 * @return Lista de objetivos correspondiente.
	 */
	public ArrayList darObjetivosDeVectores(ArrayList vectores){
		ArrayList respuesta = new ArrayList();
		for (int i = 0; i < vectores.size(); i++) {
			int[] vector = (int[]) vectores.get(i);
			int s = vector[0];
			int d = vector[1];
			int w = vector[2];
			int t = vector[3];
			int o = vector[4];
			Semana semana = semanas[s-1];
			Dia dia = semana.darDia(d);
			Trabajo trabajo = dia.darTrabajo(w);
			Tarea tarea = trabajo.darTarea(t);
			Objetivo objetivo = tarea.darObjetivo(o);
			respuesta.add(objetivo);
		}
		return respuesta;
	}
	/**
	 * Devuelve los objetivos sin asignar.
	 * @return Lista de objtivos no asignados.
	 */
	public ArrayList darObjetivosSinAsignar(){
		ArrayList vectoresSinAsignar = darVectoresSinAsignar();
		return darObjetivosDeVectores(vectoresSinAsignar);
	}
	/**
	 * Busca los números de semana y dia de una fecha que el usuario ingresa
	 * @param fecha La fecha del día buscado
	 * @return Arreglo con numero de semana y numereo de dia [s,d]. Los elementos son -1 si no se encuentra el día.
	 */
	public int[] buscarFecha(String fecha) {
		int[] buscada = new int[2];
		buscada[0] = -1;
		buscada[1] = -1;
		for (int s = 1; s <= SEMANAS; s++) {
			Semana semana = semanas[s-1]; 
			for (int d = 1; d <= Semana.DIAS; d++) {
				Dia dia = semana.darDia(d);
				if (fecha.equals(dia.darFecha())) {
					buscada[0] = s;
					buscada[1] = d;
					d = Semana.DIAS;
					s = SEMANAS;
				}
			}
		}
		return buscada;
	}
}
