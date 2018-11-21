package interfaz;


import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;





import javax.swing.JButton;
import javax.swing.JPanel;

import mundo.Objetivo;


//DECLARACIÓN
/**
* En este panel se muestran los campos y botones necesarios para realizar las consultas
*/
public class PanelLista extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//CONSTANTES
	public final static int SIZE = 46; 
	//ATRIBUTOS
	private InterfazAgenda interfaz;
	private ArrayList matriz;
	private JButton[] botones;
	//CONSTRUCTOR
	/**
	 * Contruye el panel lista.
	 * @param pInterfaz La interfaz.
	 * @param pMatriz Matriz tipo: ((objetivo,id),...).
	 */
	public PanelLista(InterfazAgenda pInterfaz, ArrayList pMatriz){
		
		Color azulOscuro = new Color(0,0,128);
		interfaz = pInterfaz;
		matriz = pMatriz;
		botones = new JButton[SIZE];
		setLayout(new GridLayout(SIZE/2,2));
		for (int i = 0; i < botones.length; i++) {
			botones[i] = new JButton("    ");
			botones[i].setFont(new Font("Calabri", Font.PLAIN, 15));
			botones[i].setForeground(Color.WHITE);
			botones[i].setOpaque(true);
			botones[i].setBackground(azulOscuro);
			add(botones[i]);
		}
		inicializarBotones();
	}
	/**
	 * Inicializa los botones con la información de la matriz.
	 */
	public void inicializarBotones(){
		for (int i = 0; i < matriz.size(); i++) {
			ArrayList pack = (ArrayList) matriz.get(i);
			String id = (String) pack.get(1);
			botones[i].setText(id);
			botones[i].setActionCommand(id);
			botones[i].addActionListener(this);
		}
		repaint();
	}
	/**
	 * Busca el botón con el comando de entrada.
	 * @param comando Comando del botón.
	 * @return Número que identifica el botón.
	 */
	public int buscarBoton(String comando) {
		int resp = -1;
		for (int i = 0; i < SIZE; i++) {
			String comandoBuscado = botones[i].getActionCommand();
			if (comandoBuscado.equals(comando)) {
				resp = i;
			}
		}
		return resp;
	}
	/**
	 * Agraga a la lista un nuevo objetivo (modifica un botón).
	 * @param pack Arreglo con el objetivo y su identificación: (objetivo,id).
	 */
	public void crearObjetivo(ArrayList pack) {
		int posicionBoton = matriz.size();
		matriz.add(pack);
		activarBoton(posicionBoton, (String) pack.get(1));
	}
	/**
	 * Activa un botón y le adiciona actionListener.
	 * @param i Número que identifica el botón.
	 * @param id Comando del botón.
	 */
	public void activarBoton(int i, String id) {
		botones[i].setText(id);
		botones[i].setActionCommand(id);
		botones[i].addActionListener(this);
		repaint();
	}
	/**
	 * Desactiva un botón.
	 * @param index índica del botón a desactivar.
	 */
	public void desactivarBoton(int index) {
		botones[index].setText("    ");
		botones[index].removeActionListener(this);
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
		int index = buscarBoton(comando);
		if (index != -1) {
			desactivarBoton(index);
			Objetivo objetivo = buscarObjetivo(comando);
			interfaz.ubicarObjetivo(objetivo);
		}
	}
}
