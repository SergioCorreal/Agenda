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
import mundo.Trabajo;
//DECLARACIÓN
/**
* En este panel se muestran los campos y botones necesarios para realizar las consultas
*/
public class PanelTrabajos extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//CONSTANTES
	public final static int FILAS = 5;
	//ATRIBUTOS
	private ArrayList trabajos;
	private InterfazAgenda interfaz;
	private JButton[] botones;
	private JLabel label;
	private JPanel panelUp;
	private JPanel panelCenter;
	//CONSTRUCTOR
    /**
     * Construye el panel de datos a consultar.
     */
	public PanelTrabajos(InterfazAgenda pInterfaz, ArrayList pTrabajos){
		interfaz = pInterfaz;
		trabajos = pTrabajos;
		botones = new JButton[FILAS];
		label = new JLabel("Trabajos");
		label.setFont(new Font("Calibri", Font.BOLD, 20));
		setLayout(new BorderLayout());
		panelUp = new JPanel();
		panelCenter = new JPanel();
		add(panelUp, BorderLayout.NORTH);
		add(panelCenter, BorderLayout.CENTER);
		
		panelUp.add(label);
		panelCenter.setLayout(new GridLayout(FILAS,1));
		
		for (int i = 0; i < botones.length; i++) {
			botones[i] = new JButton("    ");
			botones[i].setFont(new Font("Calabri", Font.PLAIN, 15));
			botones[i].setOpaque(true);
			botones[i].setBackground(Color.WHITE);
			panelCenter.add(botones[i]);
		}
		inicializarBotones();
	}
	/**
	 * Adiciona un trabajo al panel.
	 * @param trabajo El trabajo.
	 */
	public void adicionarTrabajo(Trabajo trabajo){
		int posicionBoton = trabajos.size()-1;
		activarBoton(posicionBoton, trabajo.darDescripcion());
	}
	/**
	 * Activa un botón
	 * @param b Posición del botón por activar.
	 * @param id Descripción del trabajo.
	 */
	public void activarBoton(int b, String id) {
		botones[b].setText(id);
		botones[b].setActionCommand(id);
		botones[b].addActionListener(this);
		repaint();
	}
	/**
	 * Desactiva un botón y le quita el action listener.
	 * @param b El número que identifica el botón.
	 */
	public void desactivarBoton(int b) {
		botones[b].setText("    ");
		botones[b].removeActionListener(this);
		repaint();
	}
	/**
	 * Inicializa los botones con la información de la matriz.
	 */
	public void inicializarBotones(){
		Color azulClaro = new Color(135,206,250);
		for (int i = 0; i < trabajos.size(); i++) {
			Trabajo trabajo = (Trabajo) trabajos.get(i);
			String id = trabajo.darDescripcion();
			botones[i].setText(id);
			if (trabajo.darCheck()) {
				botones[i].setBackground(azulClaro);
			}
			botones[i].setActionCommand(id);
			botones[i].addActionListener(this);
		}
		panelCenter.repaint();
	}
	/**
	 * Busca el botón con el comando de entrada.
	 * @param comando Comando del botón.
	 * @return Número que identifica el botón.
	 */
	public int buscarBoton(String comando) {
		int resp = -1;
		for (int i = 0; i < FILAS; i++) {
			String comandoBuscado = botones[i].getActionCommand();
			if (comandoBuscado.equals(comando)) {
				resp = i;
			}
		}
		return resp;
	}
	/**
	 * Actualiza el panel con una nueva lista de trabajos.
	 * @param pTrabajos Lista de trabajos
	 */
	public void actualizar(ArrayList pTrabajos){
		trabajos = pTrabajos;
		resetearBotones();
		inicializarBotones();
	}
	/**
	 * Remueve el action listener a los objetivos y los pone en blanco.
	 */
	public void resetearBotones(){
		for (int i = 0; i < FILAS; i++) {
			botones[i].setText("    ");
			botones[i].setBackground(Color.WHITE);
			botones[i].removeActionListener(this);
		}
	}
	/**
	 * Busca un trabajo y lo devuelve.
	 * @param idBuscado La descripción del trabajo buscado.
	 * @return El trabajo buscado.
	 */
	public Trabajo buscarTrabajo(String idBuscado) {
		
		Trabajo trabajoBuscado = null;
		for (int i = 0; i < trabajos.size(); i++) {
			Trabajo trabajo = (Trabajo) trabajos.get(i);
			String id = trabajo.darDescripcion();
			if (idBuscado.equals(id)) {
				trabajoBuscado = trabajo;
			}
		}
		return trabajoBuscado;
	}
	/**
	 * Realiza alguna acción.
	 */
	public void actionPerformed(ActionEvent evento) {
		String comando = evento.getActionCommand();
		Trabajo trabajo = buscarTrabajo(comando);
		interfaz.darTodoDeUnTrabajo(trabajo);
	}
}