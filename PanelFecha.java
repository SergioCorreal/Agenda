package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
//DECLARACIÓN
/**
* En este panel se muestran los campos y botones necesarios para realizar las consultas
*/
public class PanelFecha extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//CONSTANTES
	//ATRIBUTOS
	private JLabel labSemana;
	private JLabel labFecha;
	private JLabel labHoras;
	//CONSTRUCTOR
    /**
     * Construye el panel de datos a consultar.
     * @param s Número de la semana
     * @param d Cadena de texto con el nombre del día y la fecha.
     * @param tiempo Tiempo disponible del día.
     */
	public PanelFecha(int s, String d, double tiempo){
		
		Color azulOscuro = new Color(0,0,128);
		
		setLayout(new BorderLayout());
		setBorder(new TitledBorder(""));
		
		JPanel panelDerecha = new JPanel();
		JPanel panelCenter = new JPanel();
		add(panelDerecha, BorderLayout.EAST);
		add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new GridLayout(2,1));
		panelDerecha.setLayout(new BorderLayout());
		
		labSemana = new JLabel("Semana "+s);
		labSemana.setFont(new Font("Calabri", Font.PLAIN, 18));
		labSemana.setForeground(Color.WHITE);
		labSemana.setOpaque(true);
		labSemana.setBackground(azulOscuro);
		
		labFecha = new JLabel(d);
		labFecha.setFont(new Font("Calabri", Font.PLAIN, 30));
		labFecha.setForeground(Color.WHITE);
		labFecha.setBackground(Color.BLUE);
		labFecha.setOpaque(true);
		labFecha.setBackground(azulOscuro);
		
		labHoras = new JLabel(""+tiempo);
		labHoras.setFont(new Font("Calabri", Font.PLAIN, 50));
		labHoras.setForeground(Color.WHITE);
		labHoras.setOpaque(true);
		labHoras.setBackground(azulOscuro);
		
		panelCenter.add(labSemana);
		panelCenter.add(labFecha);
		
		panelDerecha.add(labHoras);
	}
	//METODOS
	/**
	 * Repinta el panel de fecha.
	 * @param s Número de la semana actual
	 * @param d Cadena de texto con el nombre del día y la fecha.
	 * @param tiempo Tiempo disponible del día.
	 */
	public void repintar(int s, String d, double tiempo) {
		labSemana.setText("Semana "+s);
		labFecha.setText(d);
		labHoras.setText(""+tiempo);
		repaint();
	}
}
