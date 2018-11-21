package interfaz;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.*;

import mundo.Agenda;
import mundo.Curso;
import mundo.Dia;
import mundo.Objetivo;
import mundo.Semana;
import mundo.Tarea;
import mundo.Trabajo;

// DECLARACIONES
/**
 * Esta es la ventana principal de la aplicación.
 */
public class InterfazAgenda extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// CONSTANTES---------------------------------------------------
	// ATRIBUTOS----------------------------------------------------
	private int contadorSemana;
	private int contadorDia;
	/**
	 * Asociación al la clase principal del mundo.
	 */
	private static Agenda agenda;
	/**
	 * Asociación al Panel que muestra la fecha que se elije.
	 */
	private PanelFecha panelFecha;
	/**
	 * Asociación al Panel que muestra los trabajos del día.
	 */
	private PanelTrabajos panelTrabajos;
	/**
	 * Asociación al Panel que muestra los objetivos del día.
	 */
	private PanelObjetivos panelObjetivos;
	/**
	 * Asociación al Panel que muestra los objetivos pendientes.
	 */
	private PanelLista panelLista;
	/**
	 * Asociación al Panel que tiene los botones.
	 */
	private PanelBotones panelBotones;
	//private ArrayList vectoresSinAsignar;

	// CONSTRUCTOR--------------------------------------------------
	public InterfazAgenda() throws Exception{
		iniciarContadores();
		Semana semana = agenda.darSemana(contadorSemana);
		Dia dia = semana.darDia(contadorDia);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setResizable(true);
		setTitle("Agenda por Objetivos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		//construimos los páneles.
		JPanel panelCentral = new JPanel();
		JPanel panelSuperCentral = new JPanel();
		JPanel panelEast = new JPanel();
		ArrayList matrizPanelLista = darMatriz(agenda.darObjetivosSinAsignar());
		ArrayList matrizPanelObjetivos = darMatriz(agenda.darObjetivosDeDia(contadorSemana, contadorDia));
		ArrayList trabajosPanelTrabajos = agenda.darTrabajosDeDia(contadorSemana, contadorDia);
		panelFecha=new PanelFecha(contadorSemana, dia.darNombre()+" "+dia.darFecha(), dia.darTiempo());
		panelLista = new PanelLista(this, matrizPanelLista);
		panelTrabajos = new PanelTrabajos(this, trabajosPanelTrabajos);
		panelObjetivos=new PanelObjetivos(this, matrizPanelObjetivos);
		panelBotones=new PanelBotones(this);
		//organizamos los páneles más grandes.
		add(panelCentral, BorderLayout.CENTER);
		add(panelEast, BorderLayout.EAST); 
		//organizamos el panel central.
		panelCentral.setLayout(new BorderLayout());
		panelCentral.add(panelFecha, BorderLayout.NORTH);
		panelCentral.add(panelSuperCentral, BorderLayout.CENTER);
		panelCentral.add(panelBotones, BorderLayout.SOUTH);
		//organizamos el panelSuperCentral
		panelSuperCentral.setLayout(new BorderLayout());
		panelSuperCentral.add(panelTrabajos, BorderLayout.WEST);
		panelSuperCentral.add(panelObjetivos, BorderLayout.CENTER);

		//organizamos el panel de la izquierda
		panelEast.add(panelLista);

	}
	/**
	 * Lee un archivo donde están el número de semana y de día últimos (semana,día). Si no existe lo crea. 
	 * @throws Exception Si algo falla leyendo o creando el archivo
	 */
	public void iniciarContadores() throws Exception{
		File file = new File("./contadores.txt");
		if (!file.exists()) {
			FileWriter fw = new FileWriter("./contadores.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(1+","+1);
			bw.close();
		}
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream bis = new BufferedInputStream(fis);
		BufferedReader br = new BufferedReader(new InputStreamReader(bis));
		String l = br.readLine();
		String[] parts = l.split(",");
		contadorSemana = Integer.parseInt(parts[0]);
		contadorDia = Integer.parseInt(parts[1]);
		fis.close();
		bis.close();
	}
	// METODOS------------------------------------------------------
	/**
	 * Aumenta en i unidades el contador de las semanas.
	 * @param i Número entero.
	 */
	public void pasoContadorSemana(int i){
		int maxSemanas= Agenda.SEMANAS;
		contadorSemana += i;
		if (contadorSemana < 1) {
			contadorSemana = maxSemanas;
			Semana semana = agenda.darSemana(contadorSemana);
			Dia dia = semana.darDia(contadorDia);
			ArrayList pMatriz = darMatriz(agenda.darObjetivosDeDia(contadorSemana, contadorDia)); 
			panelFecha.repintar(contadorSemana, dia.darNombre()+" "+dia.darFecha(), dia.darTiempo());
			panelTrabajos.actualizar(dia.darTrabajos());
			panelObjetivos.actualizar(pMatriz);
			try {
				FileWriter fw = new FileWriter("./contadores.txt");
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(contadorSemana+","+contadorDia);
				bw.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog( null, e.getCause(), "Error", JOptionPane.ERROR_MESSAGE );
			}
		}if (contadorSemana > maxSemanas) {
			contadorSemana = 1;
			Semana semana = agenda.darSemana(contadorSemana);
			Dia dia = semana.darDia(contadorDia);
			ArrayList pMatriz = darMatriz(agenda.darObjetivosDeDia(contadorSemana, contadorDia)); 
			panelFecha.repintar(contadorSemana, dia.darNombre()+" "+dia.darFecha(), dia.darTiempo());
			panelTrabajos.actualizar(dia.darTrabajos());
			panelObjetivos.actualizar(pMatriz);
			try {
				FileWriter fw = new FileWriter("./contadores.txt");
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(contadorSemana+","+contadorDia);
				bw.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog( null, e.getCause(), "Error", JOptionPane.ERROR_MESSAGE );
			}
		}else {
			Semana semana = agenda.darSemana(contadorSemana);
			Dia dia = semana.darDia(contadorDia);
			ArrayList pMatriz = darMatriz(agenda.darObjetivosDeDia(contadorSemana, contadorDia)); 
			panelFecha.repintar(contadorSemana, dia.darNombre()+" "+dia.darFecha(), dia.darTiempo());
			panelTrabajos.actualizar(dia.darTrabajos());
			panelObjetivos.actualizar(pMatriz);
			try {
				FileWriter fw = new FileWriter("./contadores.txt");
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(contadorSemana+","+contadorDia);
				bw.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog( null, e.getCause(), "Error", JOptionPane.ERROR_MESSAGE );
			}
		}
	}
	/**
	 * Aumenta en i unidades el contador de los días.
	 * @param i Número entero.
	 */
	public void pasoContadorDia(int i){
		int maxSemanas= Agenda.SEMANAS;
		contadorDia += i;
		if (contadorDia < 1) {
			contadorDia = 7;
			if (contadorSemana == 1) {
				contadorSemana = maxSemanas;
				Semana semana = agenda.darSemana(contadorSemana);
				Dia dia = semana.darDia(contadorDia);
				ArrayList pMatriz = darMatriz(agenda.darObjetivosDeDia(contadorSemana, contadorDia)); 
				panelFecha.repintar(contadorSemana, dia.darNombre()+" "+dia.darFecha(), dia.darTiempo());
				panelTrabajos.actualizar(dia.darTrabajos());
				panelObjetivos.actualizar(pMatriz);
				try {
					FileWriter fw = new FileWriter("./contadores.txt");
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write(contadorSemana+","+contadorDia);
					bw.close();
				} catch (Exception e) {
					JOptionPane.showMessageDialog( null, e.getCause(), "Error", JOptionPane.ERROR_MESSAGE );
				}
			}else {
				contadorSemana = contadorSemana -1;
				Semana semana = agenda.darSemana(contadorSemana);
				Dia dia = semana.darDia(contadorDia);
				ArrayList pMatriz = darMatriz(agenda.darObjetivosDeDia(contadorSemana, contadorDia)); 
				panelFecha.repintar(contadorSemana, dia.darNombre()+" "+dia.darFecha(), dia.darTiempo());
				panelTrabajos.actualizar(dia.darTrabajos());
				panelObjetivos.actualizar(pMatriz);
				try {
					FileWriter fw = new FileWriter("./contadores.txt");
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write(contadorSemana+","+contadorDia);
					bw.close();
				} catch (Exception e) {
					JOptionPane.showMessageDialog( null, e.getCause(), "Error", JOptionPane.ERROR_MESSAGE );
				}
			}
		}else if (contadorDia > 7) {
			contadorDia = 1;
			if (contadorSemana == maxSemanas) {
				contadorSemana = 1;
				Semana semana = agenda.darSemana(contadorSemana);
				Dia dia = semana.darDia(contadorDia);
				ArrayList pMatriz = darMatriz(agenda.darObjetivosDeDia(contadorSemana, contadorDia)); 
				panelFecha.repintar(contadorSemana, dia.darNombre()+" "+dia.darFecha(), dia.darTiempo());
				panelTrabajos.actualizar(dia.darTrabajos());
				panelObjetivos.actualizar(pMatriz);
				try {
					FileWriter fw = new FileWriter("./contadores.txt");
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write(contadorSemana+","+contadorDia);
					bw.close();
				} catch (Exception e) {
					JOptionPane.showMessageDialog( null, e.getCause(), "Error", JOptionPane.ERROR_MESSAGE );
				}
			}else {
				contadorSemana = contadorSemana +1;
				Semana semana = agenda.darSemana(contadorSemana);
				Dia dia = semana.darDia(contadorDia);
				ArrayList pMatriz = darMatriz(agenda.darObjetivosDeDia(contadorSemana, contadorDia)); 
				panelFecha.repintar(contadorSemana, dia.darNombre()+" "+dia.darFecha(), dia.darTiempo());
				panelTrabajos.actualizar(dia.darTrabajos());
				panelObjetivos.actualizar(pMatriz);
				try {
					FileWriter fw = new FileWriter("./contadores.txt");
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write(contadorSemana+","+contadorDia);
					bw.close();
				} catch (Exception e) {
					JOptionPane.showMessageDialog( null, e.getCause(), "Error", JOptionPane.ERROR_MESSAGE );
				}
			}
		}else {
			Semana semana = agenda.darSemana(contadorSemana);
			Dia dia = semana.darDia(contadorDia);
			ArrayList pMatriz = darMatriz(agenda.darObjetivosDeDia(contadorSemana, contadorDia)); 
			panelFecha.repintar(contadorSemana, dia.darNombre()+" "+dia.darFecha(), dia.darTiempo());
			panelTrabajos.actualizar(dia.darTrabajos());
			panelObjetivos.actualizar(pMatriz);
			try {
				FileWriter fw = new FileWriter("./contadores.txt");
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(contadorSemana+","+contadorDia);
				bw.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog( null, e.getCause(), "Error", JOptionPane.ERROR_MESSAGE );
			}
		}
	}
	/**
	 * Asigna el valor s al contador de semanas y el d al contador de días.
	 * @param s Número de la semana que se desea.
	 * @param d Número del dia que se desea.
	 */
	public void cambiarContadores(int s, int d){
		int maxSemanas = Agenda.SEMANAS;
		if (s < 1 || d < 1 || s > maxSemanas || d > 7) {
			JOptionPane.showMessageDialog( this, "Introduzca valores válidos para la semana y el día.", "Error", JOptionPane.ERROR_MESSAGE );
		}else{
			contadorSemana = s;
			contadorDia = d;
			Semana semana = agenda.darSemana(contadorSemana);
			Dia dia = semana.darDia(contadorDia);
			ArrayList pMatriz = darMatriz(agenda.darObjetivosDeDia(contadorSemana, contadorDia)); 
			panelFecha.repintar(contadorSemana, dia.darNombre()+" "+dia.darFecha(), dia.darTiempo());
			panelTrabajos.actualizar(dia.darTrabajos());
			panelObjetivos.actualizar(pMatriz);
			try {
				FileWriter fw = new FileWriter("./contadores.txt");
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(contadorSemana+","+contadorDia);
				bw.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog( null, e.getCause(), "Error", JOptionPane.ERROR_MESSAGE );
			}
		}
	}
	/**
	 * Crea un nuevo trabajo al día de la semana actual.
	 */
	public void crearTrabajo(){

		ArrayList cursos = agenda.darCursos();
		String[] lista = new String[cursos.size()];
		for (int i = 0; i < cursos.size(); i++) {
			Curso curso = (Curso) cursos.get(i);
			String materia = curso.darNombre();
			lista[i] = materia;
		}
		String descTrabajo = (String) JOptionPane.showInputDialog(null,
				"Por favor, escoje un nombre para el trabajo", "Input",
				JOptionPane.INFORMATION_MESSAGE, null,
				lista, lista[0]);
		if (descTrabajo != null) {
			try {
				agenda.crearTrabajo(contadorSemana, contadorDia, descTrabajo);
				Semana semana = agenda.darSemana(contadorSemana);
				Dia dia = semana.darDia(contadorDia);
				int numTrabajo = (dia.darTrabajos()).size()-1;
				Trabajo trabajo = dia.darTrabajo(numTrabajo);
				panelTrabajos.adicionarTrabajo(trabajo);
			} catch (Exception e) {
				JOptionPane.showMessageDialog( this, "Error", e.getMessage(), JOptionPane.ERROR_MESSAGE );
			}
		}
	}
	/**
	 * Crea una nueva tarea al trabajo que se escoje.
	 */
	public void crearTarea(){
		Semana semana = agenda.darSemana(contadorSemana);
		Dia dia = semana.darDia(contadorDia);
		ArrayList trabajos = dia.darTrabajos();
		if (!trabajos.isEmpty()) {
			String[] lista = new String[trabajos.size()];
			int numTrabajo = -1;
			for (int i = 0; i < trabajos.size(); i++) {
				Trabajo trabajo = (Trabajo) trabajos.get(i);
				String nombre = trabajo.darDescripcion();
				lista[i] = nombre;
			}
			String descTrabajo = (String) JOptionPane.showInputDialog(null,
					"Escoje un trabajo", "Input",
					JOptionPane.INFORMATION_MESSAGE, null,
					lista, lista[0]);
			if (descTrabajo != null) {
				for (int i = 0; i < lista.length; i++) {
					if (descTrabajo.equals(lista[i])) {
						numTrabajo = i;
					}
				}
				String descTarea = JOptionPane.showInputDialog("Por favor, introduzca el nombre de la tarea");
				if (descTarea != null) {
					try {
						agenda.crearTarea(contadorSemana, contadorDia, numTrabajo, descTarea);
					} catch (Exception e) {
						JOptionPane.showMessageDialog( this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE );
					}
				}
			}
		}else{
			JOptionPane.showMessageDialog( this, "Introduzca primero un trabajo", "Error", JOptionPane.ERROR_MESSAGE );
		}
	}
	/**
	 * Crea un nuevo objetivo a la tarea que se escoje.
	 */
	public void crearObjetivo(){
		Semana semana = agenda.darSemana(contadorSemana);
		Dia dia = semana.darDia(contadorDia);
		ArrayList trabajos = dia.darTrabajos();
		if (!trabajos.isEmpty()) {
			String[] listaTrabajos = new String[trabajos.size()];
			int numTrabajo = -1;
			for (int i = 0; i < trabajos.size(); i++) {
				Trabajo trabajo = (Trabajo) trabajos.get(i);
				String nombre = trabajo.darDescripcion();
				listaTrabajos[i] = nombre;
			}
			String descTrabajo = (String) JOptionPane.showInputDialog(null,
					"Escoje un trabajo", "Input",
					JOptionPane.INFORMATION_MESSAGE, null,
					listaTrabajos, listaTrabajos[0]);
			if (descTrabajo != null) {
				for (int i = 0; i < listaTrabajos.length; i++) {
					if (descTrabajo.equals(listaTrabajos[i])) {
						numTrabajo = i;
					}
				}
				Trabajo trabajo = dia.darTrabajo(numTrabajo);
				ArrayList tareas = trabajo.darTareas();
				if (!tareas.isEmpty()) {
					String[] listaTareas = new String[tareas.size()];
					int numTarea = -1;
					for (int i = 0; i < tareas.size(); i++) {
						Tarea tarea = (Tarea) tareas.get(i);
						String nombre = tarea.darDescripcion();
						listaTareas[i] = nombre;
					}
					String descTarea = (String) JOptionPane.showInputDialog(null,
							"Escoje una tarea", "Input",
							JOptionPane.INFORMATION_MESSAGE, null,
							listaTareas, listaTareas[0]);
					if (descTarea != null) {
						for (int i = 0; i < listaTareas.length; i++) {
							if (descTarea.equals(listaTareas[i])) {
								numTarea = i;
							}
						}
						String descObjetivo = JOptionPane.showInputDialog("Por favor, "
								+ "introduzca la descripción del objetivo");
						if (descObjetivo != null) {
							String pTiempo = JOptionPane.showInputDialog("Por favor, "
									+ "introduzca la duración del objetivo");
							if (pTiempo != null) {
								try {
									double tiempo = Double.parseDouble(pTiempo);
									String pRepeticion = JOptionPane.showInputDialog("¿Cuántas repeticiones desea?");

									int repeticion = Integer.parseInt(pRepeticion);
									for (int i = 1; i <= repeticion; i++) {
										agenda.crearObjetivo(contadorSemana, contadorDia, numTrabajo, numTarea, descObjetivo+"("+i+")", tiempo);
										Tarea tarea = trabajo.darTarea(numTarea);
										int numObjetivo = (tarea.darObjetivos()).size()-1;
										Objetivo objetivo = tarea.darObjetivo(numObjetivo);
										ArrayList pack = new ArrayList();
										pack.add(objetivo);
										pack.add(darIdObjetivo(objetivo));
										panelLista.crearObjetivo(pack);
									}
								} catch (Exception e) {
									JOptionPane.showMessageDialog( this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE );
								}
							}
						}
					}
				}else {
					JOptionPane.showMessageDialog( this, "Introduzca primero una tarea", "Error", JOptionPane.ERROR_MESSAGE );
				}
			}
		}else{
			JOptionPane.showMessageDialog( this, "Introduzca primero un trabajo", "Error", JOptionPane.ERROR_MESSAGE );
		}
	}
	/**
	 * Muestra el trabajo independiente por materia.
	 */
	public void calcular(){
		String lista = agenda.darEstudioIndependiente();
		JOptionPane.showMessageDialog(null, lista, "Información", JOptionPane.INFORMATION_MESSAGE);
	}
	/**
	 * Devuelve una matriz.
	 * @param objetivos Lista de objetivos.
	 * @return Matriz de esta forma: ((objetivo,id)...).
	 */
	public ArrayList darMatriz(ArrayList objetivos) {
		ArrayList matriz = new ArrayList();
		for (int i = 0; i < objetivos.size(); i++) {
			ArrayList pack = new ArrayList();
			Objetivo objetivo = (Objetivo) objetivos.get(i);
			String id = darIdObjetivo(objetivo);
			pack.add(objetivo);
			pack.add(id);
			matriz.add(pack);
		}
		return matriz;
	}
	/**
	 * Da la identificación de un objetivo.
	 * @param objetivo El objetivo.
	 * @return Devuelve la identificación del objetivo.
	 */
	public String darIdObjetivo(Objetivo objetivo) {
		int[] vector = objetivo.darCoordenadas();
		int s = vector[0];
		int d = vector[1];
		int w = vector[2];
		int t = vector[3];
		Semana semana = agenda.darSemana(s);
		Dia dia = semana.darDia(d);
		Trabajo trabajo = dia.darTrabajo(w);
		Tarea tarea = trabajo.darTarea(t);
		String descTrabajo = trabajo.darDescripcion();
		String descTarea = tarea.darDescripcion();
		String descObjetivo = objetivo.darDescripcion();
		double tiempo = objetivo.darDuracion();
		String id = "s"+s+"-d"+d+"-"+descTrabajo+"-"+descTarea+"-"+descObjetivo+"-"+tiempo;
		return id;
	}
	/**
	 * Adiciona un objetivo a un día.
	 * @param i Número que identifica un vector de la lista de vectores sin asignar.
	 */
	public void ubicarObjetivo(Objetivo objetivo) {
		try {
			agenda.ubicarObjetivo(contadorSemana, contadorDia, objetivo);
			Semana semana = agenda.darSemana(contadorSemana);
			Dia dia = semana.darDia(contadorDia);
			ArrayList pack = new ArrayList();
			pack.add(objetivo);
			pack.add(darIdObjetivo(objetivo));
			panelFecha.repintar(contadorSemana, dia.darNombre()+" "+dia.darFecha(), dia.darTiempo());
			panelObjetivos.adicionarObjetivo(pack);
		} catch (Exception e) {
			JOptionPane.showMessageDialog( this, e.getCause(), "Error", JOptionPane.ERROR_MESSAGE );
		}
	}
	/**
	 * Devuelve un objetivo a la lista de objetivos sin realizar.
	 * @param objetivo El objetivo a devorlver.
	 */
	public void devolverObjetivo(Objetivo objetivo) {

		try {
			Semana semana = agenda.darSemana(contadorSemana);
			Dia dia = semana.darDia(contadorDia);
			int[] vector = objetivo.darCoordenadas();
			double sumarTiempo = - objetivo.darDuracion();
			ArrayList pack = new ArrayList();
			pack.add(objetivo);
			pack.add(darIdObjetivo(objetivo));
			panelLista.crearObjetivo(pack);
			agenda.devolverObjetivo(contadorSemana, contadorDia, vector, sumarTiempo);
			panelFecha.repintar(contadorSemana, dia.darNombre()+" "+dia.darFecha(), dia.darTiempo());	
		} catch (Exception e) {
			JOptionPane.showMessageDialog( this, e.getCause(), "Error", JOptionPane.ERROR_MESSAGE );
		}

	}
	/**
	 * Chequea un objetivo
	 * @param objetivo El objetivo a chequear.
	 */
	public void chequearObjetivo(Objetivo objetivo) {
		try {
			agenda.chequearObjetivo(objetivo);
			Semana semana = agenda.darSemana(contadorSemana);
			Dia dia = semana.darDia(contadorDia);
			panelTrabajos.actualizar(dia.darTrabajos());
		} catch (Exception e) {
			JOptionPane.showMessageDialog( this, e.getCause(), "Error", JOptionPane.ERROR_MESSAGE );
		}

	}
	/**
	 * Muestra toda la información relacionada a un trabajo.
	 * @param trabajo El trabajo que se quiere consultar.
	 */
	public void darTodoDeUnTrabajo(Trabajo trabajo) {

		ArrayList tareas = trabajo.darTareas();
		String respuesta = trabajo.darDescripcion()+"\t  "+trabajo.darCheck()+"\n";
		for (int i = 0; i < tareas.size(); i++) {
			Tarea tarea = (Tarea) tareas.get(i);
			ArrayList objetivos = tarea.darObjetivos();
			respuesta += "    "+tarea.darDescripcion()+"\t  "+tarea.darCheck()+"\n";
			for (int j = 0; j < objetivos.size(); j++) {
				Objetivo objetivo = (Objetivo) objetivos.get(j);
				respuesta += "        "+objetivo.darDescripcion()+"\t  "+objetivo.darDuracion()+"\t  "+ objetivo.darCheck()+"\n";
			}
		}
		JOptionPane.showMessageDialog(null, respuesta, "Información", JOptionPane.INFORMATION_MESSAGE);
	}
	/**
	 * Repinta la ventana con el día de la fecha ingresada.
	 * @param fecha
	 */
	public void buscarFecha(){
		String fecha = JOptionPane.showInputDialog("Ingrese la fecha que buscha (e.g. 3-febrero)");
		int[] buscada = agenda.buscarFecha(fecha);
		if (buscada[0] != -1 & buscada[1] != -1) {
			cambiarContadores(buscada[0], buscada[1]);
		}else {
			JOptionPane.showMessageDialog( null, "Introduzca una fecha válida", "Error", JOptionPane.ERROR_MESSAGE );
		}
	}
	/**
	 * Crea el archivo donde están guardados los cursos (nombre,creditos teoricos, creditos reales, horas a la semana).
	 * @return Un archivo donde está la informacion de los cursos.
	 */
	public static File iniciarCursos() throws Exception{
		File file = new File("./cursos.txt");
		if (!file.exists()) {
			String pNumCursos = JOptionPane.showInputDialog("¿Cuántos cursos desea inscribir?");
			int numCursos = Integer.parseInt(pNumCursos);

			FileWriter fw = new FileWriter("./cursos.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 1; i <= numCursos; i++) {
				JTextField txtNombre = new JTextField();
				JTextField txtCredTeor = new JTextField();
				JTextField txtCredReal = new JTextField();
				JTextField txtHoras = new JTextField();
				JPanel myPanel = new JPanel();
				myPanel.setLayout(new GridLayout(4, 2));
				myPanel.add(new JLabel("Nombre: "));
				myPanel.add(txtNombre);
				myPanel.add(new JLabel("Créditos teóricos: "));
				myPanel.add(txtCredTeor);
				myPanel.add(new JLabel("Créditos reales: "));
				myPanel.add(txtCredReal);
				myPanel.add(new JLabel("Horas a la semana: "));
				myPanel.add(txtHoras);

				int result = JOptionPane.showConfirmDialog(null, myPanel, 
						"Ingrese el curso "+i, JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					String n = txtNombre.getText(), 
							cT = txtCredTeor.getText(),
							cR = txtCredReal.getText(), 
							h = txtHoras.getText();
					if (n.equals("") || cT.equals("") || cR.equals("") || h.equals("")) {
						bw.close();
						fw.close();
						throw new Exception("Por favor, ingrese datos válidos. Borre el archivo cursos.txt e inténtelo de nuevo.");
					}else {
						bw.write(n+","+cT+","+cR+","+h);
						bw.newLine();
					}
				}else{
					bw.close();
					fw.close();
					throw new Exception("Vuelva a iniciar el programa");
				}
			}
			bw.close();
			fw.close();
			file = new File("./cursos.txt");
		}
		return file;
	}
	/**
	 * Crea y lee archivo donde se encuantra la fecha inicial para construir un calendario.
	 * @return Un ArrayList en el que el primer elemento es la fecha inicial y el segundo dice si el año es bisiesto o no.
	 * @throws Exception
	 */
	public static ArrayList iniciarFechas() throws Exception{
		ArrayList resp = new ArrayList();
		String fechaInicio = "";
		String bisiesto = "false";
		File fecha = new File("./fecha.txt");
		if (!fecha.exists()){
			// Crea el archivo
			int intBisiesto = JOptionPane.showConfirmDialog(null,
					"¿El año de su periodo académico es bisiesto?","",JOptionPane.YES_NO_OPTION);
			if (intBisiesto == JOptionPane.YES_OPTION) {
				bisiesto = "true";
			}
			fechaInicio = JOptionPane.showInputDialog("Introduzca la fecha del"+
					" lunes de la primera semana de su periodo académico (e.g. 2-enero)");
			FileWriter fw = new FileWriter("./fecha.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(fechaInicio+","+bisiesto);
			bw.close();
			fw.close();
		}
		// Lee el archivo

		FileInputStream fis = new FileInputStream(fecha);
		BufferedInputStream bis = new BufferedInputStream(fis);
		BufferedReader br = new BufferedReader(new InputStreamReader(bis));
		String l = br.readLine();
		String[] parts = l.split(",");
		fechaInicio = parts[0];
		bisiesto = parts[1];
		fis.close();
		bis.close();
		br.close();
		resp.add(fechaInicio);
		resp.add(bisiesto);
		return resp;
	}
	/**
	 * Crea y lee un archivo donde están las horas disponibles de cada día de la semana.
	 * @return Devuevle un arreglo con las horas disponibles de cada día.
	 * @throws Exception
	 */
	public static double[] iniciarCalendario() throws Exception {
		File calendario = new File("./calendario.txt");
		double[] hDisp = new double[7];
		if (!calendario.exists()) {
			//Crea el archivo
			FileWriter fw = new FileWriter("./calendario.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			JTextField txtLunes = new JTextField();
			JTextField txtMartes = new JTextField();
			JTextField txtMiercoles = new JTextField();
			JTextField txtJueves = new JTextField();
			JTextField txtViernes = new JTextField();
			JTextField txtSabado = new JTextField();
			JTextField txtDomingo = new JTextField();
			JPanel myPanel = new JPanel();
			myPanel.setLayout(new GridLayout(7, 2));
			myPanel.add(new JLabel("Lunes: "));
			myPanel.add(txtLunes);
			myPanel.add(new JLabel("Martes: "));
			myPanel.add(txtMartes);
			myPanel.add(new JLabel("Miércoles: "));
			myPanel.add(txtMiercoles);
			myPanel.add(new JLabel("Jueves: "));
			myPanel.add(txtJueves);
			myPanel.add(new JLabel("Viernes: "));
			myPanel.add(txtViernes);
			myPanel.add(new JLabel("Sábado: "));
			myPanel.add(txtSabado);
			myPanel.add(new JLabel("Domingo: "));
			myPanel.add(txtDomingo);

			int result = JOptionPane.showConfirmDialog(null, myPanel, 
					"Ingrese las horas disponiblies que tiene cada día", JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {
				String lu = txtLunes.getText(), 
						ma = txtMartes.getText(),
						mi = txtMiercoles.getText(),
						ju = txtJueves.getText(),
						vi = txtViernes.getText(),
						sa = txtSabado.getText(),
						dom = txtDomingo.getText();
				if (lu.equals("") || ma.equals("") || mi.equals("") 
						|| ju.equals("") || vi.equals("") 
						|| sa.equals("") || dom.equals("")) {
					bw.close();
					fw.close();
					throw new Exception("Ingrese números válidos. Borre el archivo calendario.txt e inténtelo de nuevo.");
				}else {
					bw.write(lu+","+ma+","+mi+","+ju+","+vi+","+sa+","+dom);
				}
				bw.close();
				fw.close();
				calendario = new File("./calendario.txt");
			}else {
				bw.close();
				fw.close();
				throw new Exception("Borre el archivo calendario.txt e inténtelo de nuevo.");
			}
		}
		//Lee el ar archivo
		FileInputStream fis = new FileInputStream(calendario);
		BufferedInputStream bis = new BufferedInputStream(fis);
		BufferedReader br = new BufferedReader(new InputStreamReader(bis));
		String l = br.readLine();
		String[] parts = l.split(",");
		hDisp[0] = Double.parseDouble(parts[0]);
		hDisp[1] = Double.parseDouble(parts[1]);
		hDisp[2] = Double.parseDouble(parts[2]);
		hDisp[3] = Double.parseDouble(parts[3]);
		hDisp[4] = Double.parseDouble(parts[4]);
		hDisp[5] = Double.parseDouble(parts[5]);
		hDisp[6] = Double.parseDouble(parts[6]);
		fis.close();
		bis.close();
		br.close();
		return hDisp;
	}
	// MAIN---------------------------------------------------------
	public static void main(String[] args){
		try {
			File cursos = iniciarCursos();
			String fechaInicio =  (String) iniciarFechas().get(0);
			String bisiestoString = (String) iniciarFechas().get(1);
			boolean bisiesto = Boolean.parseBoolean(bisiestoString);
			double[] hDisp = iniciarCalendario();
			agenda = new Agenda(cursos, fechaInicio, bisiesto, hDisp);
			InterfazAgenda ventana = new InterfazAgenda();
			ventana.setVisible(true);
		} catch (Exception e) {
			JOptionPane.showMessageDialog( null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE );
		}
	}
}
