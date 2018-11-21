package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import mundo.Objetivo;
//DECLARACIÓN
/**
* En este panel se muestran los campos y botones necesarios para realizar las consultas
*/
public class PanelObjetivos extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//CONSTANTES
		public final static int FILAS = 20;
		//ATRIBUTOS
		private ArrayList matriz;
		private InterfazAgenda interfaz;
		private JButton[] buts1stColumn;
		private JButton[] buts2ndColumn;
		private JLabel label;
		//CONSTRUCTOR
	    /**
	     * Construye el panel de datos a consultar.
	     */
		public PanelObjetivos(InterfazAgenda pInterfaz, ArrayList pMatriz){
			interfaz = pInterfaz;
			matriz = pMatriz;
			JPanel panelUp = new JPanel();
			JPanel panelCenter = new JPanel();
			JPanel panelDerecha = new JPanel();
			JPanel panelIzquierda = new JPanel();
			buts1stColumn = new JButton[FILAS];
			buts2ndColumn = new JButton[FILAS];
			label = new JLabel("Objetivos");
			
			setLayout(new BorderLayout());
			panelCenter.setLayout(new BorderLayout());
			panelDerecha.setLayout(new GridLayout(FILAS,1));
			panelIzquierda.setLayout(new GridLayout(FILAS,1));
			label.setFont(new Font("Calibri", Font.BOLD, 20));
			
			add(panelUp, BorderLayout.NORTH);
			add(panelCenter, BorderLayout.CENTER);
			
			panelUp.add(label);
			panelCenter.add(panelDerecha, BorderLayout.EAST);
			panelCenter.add(panelIzquierda, BorderLayout.CENTER);
			for (int i = 0; i < FILAS; i++) {
				buts1stColumn[i] = new JButton("    ");
				buts1stColumn[i].setFont(new Font("Calabri", Font.PLAIN, 15));
				buts1stColumn[i].setOpaque(true);
				buts1stColumn[i].setBackground(Color.WHITE);
				
				buts2ndColumn[i] = new JButton("");
				buts2ndColumn[i].setFont(new Font("Calabri", Font.PLAIN, 15));
				buts2ndColumn[i].setOpaque(true);
				buts2ndColumn[i].setBackground(Color.WHITE);
				panelIzquierda.add(buts1stColumn[i]);
				panelDerecha.add(buts2ndColumn[i]);
			}
			inicializarBotones();
		}
		/**
		 * Inicializa los botones con la información de la matriz.
		 */
		public void inicializarBotones(){
			Color azulClaro = new Color(135,206,250);
			for (int i = 0; i < matriz.size(); i++) {
				ArrayList pack = (ArrayList) matriz.get(i);
				Objetivo objetivo = (Objetivo) pack.get(0);
				String id = (String) pack.get(1);
				buts1stColumn[i].setText(id);
				if (objetivo.darCheck()) {
					buts1stColumn[i].setBackground(azulClaro);
					buts2ndColumn[i].setText("");
				}else{
					buts1stColumn[i].setActionCommand(id);
					buts1stColumn[i].addActionListener(this);
					buts2ndColumn[i].setText("X");
					buts2ndColumn[i].setForeground(Color.red);
					buts2ndColumn[i].setActionCommand("x"+id);
					buts2ndColumn[i].addActionListener(this);
				}
			}
			repaint();
		}
		/**
		 * Adiciona un objetivo a la lista.
		 * @param pack Un arreglo del tipo: ((objetivo,id),...).
		 */
		public void adicionarObjetivo(ArrayList pack) {
			int numBoton = matriz.size();
			matriz.add(pack);
			activarBotones(numBoton, (String) pack.get(1));
		}
		/**
		 * Activa un botones y les adiciona actionListener.
		 * @param i Número que identifica la fila de botones.
		 * @param id Comando del botón (a los de la segunda columna les agrega una "x" al comienzo).
		 */
		public void activarBotones(int i, String id) {
			buts1stColumn[i].setText(id);
			buts1stColumn[i].setActionCommand(id);
			buts1stColumn[i].addActionListener(this);
			buts2ndColumn[i].setText("X");
			buts2ndColumn[i].setForeground(Color.red);
			buts2ndColumn[i].setActionCommand("x"+id);
			buts2ndColumn[i].addActionListener(this);
			repaint();
		}
		/**
		 * Busca el botón con el comando de entrada.
		 * @param comando Comando del botón.
		 * @param columna El número de columna - puede ser 1 o 2.
		 * @return
		 */
		public int buscarBoton(String comando, int columna) {
			int resp = -1;
			if (columna == 1) {
				for (int i = 0; i < FILAS; i++) {
					String comandoBuscado = buts1stColumn[i].getActionCommand();
					if (comandoBuscado.equals(comando)) {
						resp = i;
					}
				}
			}else if (columna == 2) {
				for (int i = 0; i < FILAS; i++) {
					String comandoBuscado = buts2ndColumn[i].getActionCommand();
					if (comandoBuscado.equals(comando)) {
						resp = i;
					}
				}
			}
			return resp;
		}
		/**
		 * Actualiza el panel con una nueva matriz.
		 * @param pMatriz Nueva matriz.
		 */
		public void actualizar(ArrayList pMatriz) {
			matriz = pMatriz;
			resetearBotones();
			inicializarBotones();
		}
		/**
		 * Coloca en su estado inicial todos los botones.
		 */
		public void resetearBotones() {
			for (int i = 0; i < FILAS; i++) {
				buts1stColumn[i].setText("    ");
				buts1stColumn[i].setBackground(Color.WHITE);
				buts1stColumn[i].removeActionListener(this);
				buts2ndColumn[i].setText("");
				buts2ndColumn[i].setBackground(Color.WHITE);
				buts2ndColumn[i].removeActionListener(this);
			}
		}
		/**
		 * Desactiva el botón. Si se desactiva por chequeo lo colorea de verde
		 * @param posicionBoton Posición del botón.
		 * @param verde Tipo boolean, si es verde, entonces es verdadero.
		 */
		public void desactivarBotones(int posicionBoton, boolean verde) {
			Color azulClaro = new Color(135,206,250);
			if (verde) {
				buts1stColumn[posicionBoton].setBackground(azulClaro);
				buts1stColumn[posicionBoton].removeActionListener(this);
				buts2ndColumn[posicionBoton].setText("");
				buts2ndColumn[posicionBoton].removeActionListener(this);
			}else {
				buts1stColumn[posicionBoton].setText("    ");
				buts1stColumn[posicionBoton].setOpaque(true);
				buts1stColumn[posicionBoton].setBackground(Color.WHITE);
				buts1stColumn[posicionBoton].removeActionListener(this);
				buts2ndColumn[posicionBoton].setText("");
				buts2ndColumn[posicionBoton].removeActionListener(this);
				buts2ndColumn[posicionBoton].setOpaque(true);
				buts2ndColumn[posicionBoton].setBackground(Color.WHITE);
			}
			repaint();
		}
		/**
		 * Devuelve el objetivo que corresponde a la identificación.
		 * @param idBuscado Identificación del objetivo.
		 * @return Objetivo buscado.
		 */
		public Objetivo buscarObjetivo(String idBuscado){
			Objetivo objetivo = null;
			for (int i = 0; i < matriz.size(); i++) {
				ArrayList pack = (ArrayList) matriz.get(i);
				String id = (String) pack.get(1);
				if (idBuscado.equals(id)) {
					objetivo = (Objetivo) pack.get(0);
				}
			}
			return objetivo;
		}
		/**
		 * Realiza una acción.
		 */
		public void actionPerformed(ActionEvent evento) {
			String comando = evento.getActionCommand();
			if (comando.charAt(0) == 'x') {
				int posicionBoton = buscarBoton(comando, 2);
				desactivarBotones(posicionBoton, false);
				String[] lista = comando.split("");
				String nuevoComando = "";
				for (int i = 1; i < lista.length; i++) {
					nuevoComando += lista[i];
				}
				Objetivo objetivo = buscarObjetivo(nuevoComando);
				interfaz.devolverObjetivo(objetivo);
			}else {
				int posicionBoton = buscarBoton(comando, 1);
				desactivarBotones(posicionBoton, true);
				Objetivo objetivo = buscarObjetivo(comando);
				interfaz.chequearObjetivo(objetivo);
			}
		}
	}
