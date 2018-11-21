package interfaz;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
//DECLARACIÓN
/**
* En este panel se muestran los campos y botones necesarios para realizar las consultas
*/
public class PanelBotones extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//CONSTANTES
	public final static String SUPER_ATRAS = "super atras";
	public final static String ATRAS = "atras";
	public final static String ADELANTE = "adelante";
	public final static String SUPER_ADELANTE = "super adelante";
	public final static String ADD_TRABAJO = "add trabajo";
	public final static String ADD_TAREA = "add tarea";
	public final static String ADD_OBJETIVO = "add objetivo";
	public final static String CALCULAR = "calcular";
	public final static String IR = "ir";
	public final static String BUSCAR_FECHA = "buscar fecha";
	
	//ATRIBUTOS
	private InterfazAgenda interfaz;
	//botones
	private JButton butSuperAtras;
	private JButton butAtras;
	private JButton butAdelante;
	private JButton butSuperAdelante;
	private JButton butAddTrabajo;
	private JButton butAddTarea;
	private JButton butAddObjetivo;
	private JButton butCalcular;
	private JButton butIr;
	private JButton butBuscarFecha;
	//etiquetas
	private JLabel labSemana;
	private JLabel labDia;
	//cajas de texto
	private JTextField txtSemana;
	private JTextField txtDia;
	//CONSTRUCTOR
    /**
     * Construye el panel de datos a consultar.
     */
	public PanelBotones(InterfazAgenda pInterfaz){
		
		interfaz = pInterfaz;
		Color azulClaro = new Color(135,206,250);
		setLayout(new GridLayout(3,5));
		//creamos los elementos gráficos.
		butSuperAtras = new JButton("<<");
		butSuperAtras.setFont(new Font("Arial", Font.BOLD, 20));
		butSuperAtras.setActionCommand(SUPER_ATRAS);
		butSuperAtras.addActionListener(this);
		butSuperAtras.setOpaque(true);
		butSuperAtras.setBackground(azulClaro);
		
		butAtras = new JButton("<");
		butAtras.setFont(new Font("Arial", Font.BOLD, 20));
		butAtras.setActionCommand(ATRAS);
		butAtras.addActionListener(this);
		butAtras.setOpaque(true);
		butAtras.setBackground(azulClaro);
		
		butAdelante = new JButton(">");
		butAdelante.setFont(new Font("Arial", Font.BOLD, 20));
		butAdelante.setActionCommand(ADELANTE);
		butAdelante.addActionListener(this);
		butAdelante.setOpaque(true);
		butAdelante.setBackground(azulClaro);
		
		butSuperAdelante = new JButton(">>");
		butSuperAdelante.setFont(new Font("Arial", Font.BOLD, 20));
		butSuperAdelante.setActionCommand(SUPER_ADELANTE);
		butSuperAdelante.addActionListener(this);
		butSuperAdelante.setOpaque(true);
		butSuperAdelante.setBackground(azulClaro);
		
		butAddTrabajo = new JButton("+ Trabajo");
		butAddTrabajo.setFont(new Font("Calabri", Font.PLAIN, 15));
		butAddTrabajo.setActionCommand(ADD_TRABAJO);
		butAddTrabajo.addActionListener(this);
		butAddTrabajo.setOpaque(true);
		butAddTrabajo.setBackground(azulClaro);
		
		butAddTarea = new JButton("+ Tarea");
		butAddTarea.setFont(new Font("Calabri", Font.PLAIN, 15));
		butAddTarea.setActionCommand(ADD_TAREA);
		butAddTarea.addActionListener(this);
		butAddTarea.setOpaque(true);
		butAddTarea.setBackground(azulClaro);
		
		butAddObjetivo = new JButton("+ Objetivo");
		butAddObjetivo.setFont(new Font("Calabri", Font.PLAIN, 15));
		butAddObjetivo.setActionCommand(ADD_OBJETIVO);
		butAddObjetivo.addActionListener(this);
		butAddObjetivo.setOpaque(true);
		butAddObjetivo.setBackground(azulClaro);
		
		butCalcular = new JButton("Calcular");
		butCalcular.setFont(new Font("Calabri", Font.PLAIN, 15));
		butCalcular.setActionCommand(CALCULAR);
		butCalcular.addActionListener(this);
		butCalcular.setOpaque(true);
		butCalcular.setBackground(azulClaro);
		
		butIr = new JButton("Ir");
		butIr.setFont(new Font("Calabri", Font.PLAIN, 15));
		butIr.setActionCommand(IR);
		butIr.addActionListener(this);
		butIr.setOpaque(true);
		butIr.setBackground(azulClaro);
		
		butBuscarFecha = new JButton("Buscar fecha");
		butBuscarFecha.setFont(new Font("Calabri", Font.PLAIN, 15));
		butBuscarFecha.setActionCommand(BUSCAR_FECHA);
		butBuscarFecha.addActionListener(this);
		butBuscarFecha.setOpaque(true);
		butBuscarFecha.setBackground(azulClaro);
		
		JLabel labEspacio1 = new JLabel("");
		labEspacio1.setOpaque(true);
		labEspacio1.setBackground(azulClaro);
		labSemana = new JLabel("Semana: ");
		labSemana.setFont(new Font("Calabri", Font.ITALIC, 15));
		labDia = new JLabel("Día: ");
		labDia.setFont(new Font("Calabri", Font.ITALIC, 15));
		txtSemana = new JTextField();
		txtSemana.setEditable(true);
		txtSemana.setForeground(Color.BLUE);
		txtDia = new JTextField();
		txtDia.setEditable(true);
		txtDia.setForeground(Color.BLUE);
		//organizamos los elementos.
		add(butSuperAtras);
		add(butAtras);
		add(labEspacio1);
		add(butAdelante);
		add(butSuperAdelante);
		add(labSemana);
		add(txtSemana);
		add(butIr);
		add(labDia);
		add(txtDia);
		add(butAddTrabajo);
		add(butAddTarea);
		add(butAddObjetivo);
		add(butBuscarFecha);
		add(butCalcular);
	}
	//METODOS
	public void actionPerformed(ActionEvent evento) {
		String comando = evento.getActionCommand();
		if (comando.equals(SUPER_ATRAS)) {
			
			interfaz.pasoContadorSemana(-1);
			
		}if (comando.equals(ATRAS)) {
			
			interfaz.pasoContadorDia(-1);
			
		}if (comando.equals(ADELANTE)) {
			interfaz.pasoContadorDia(1);
			
		}if (comando.equals(SUPER_ADELANTE)) {
			interfaz.pasoContadorSemana(1);
			
		}if (comando.equals(ADD_TRABAJO)) {
			
			interfaz.crearTrabajo();
			
		}if (comando.equals(ADD_TAREA)) {
			
			interfaz.crearTarea();
			
		}if (comando.equals(ADD_OBJETIVO)) {
			
			interfaz.crearObjetivo();
			
		}if (comando.equals(CALCULAR)) {
			interfaz.calcular();
		}if (comando.equals(IR)) {
			String diaString = txtDia.getText();
			String semanaString = txtSemana.getText();
			if (diaString != null && semanaString != null) {
				int d = Integer.parseInt(diaString);
				int s = Integer.parseInt(semanaString);
				interfaz.cambiarContadores(s, d);
			}
		}if (comando.equals(BUSCAR_FECHA)) {
			interfaz.buscarFecha();
		}
	}
}
