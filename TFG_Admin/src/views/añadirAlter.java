package views;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class añadirAlter extends JFrame {

	private Fondo panel;
	private Fondo contentPane;
	private JTable table;
	private JTable table_1;
	private ArrayList<String> almacenadas;
	private ArrayList<String> inscritas;
	private JButton btnSig;
	private JButton btnAadirNuevo;
	private JButton btnSiguiente;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					añadirAlter frame = new añadirAlter();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public añadirAlter() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		contentPane = new Fondo();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackgroundImage(contentPane.createImage("/resources/azul.jpg").getImage());
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPanel(), BorderLayout.CENTER);
		almacenadas=new ArrayList<String>();
		inscritas=new ArrayList<String>();
		this.setResizable(false);
	}
	
	private JPanel getPanel() {
		if (panel == null) {
			panel = new Fondo();
			panel.setLayout(null);
			panel.add(getTable());
			panel.add(getTable_1());
			panel.add(getButton_1());
			panel.add(getBtnAadirNuevo());
			panel.add(getBtnSiguiente());
			
		}
		return panel;
	}
	private JTable getTable() {
		if (table == null) {
			table = new JTable();
			table.setBounds(60, 433, 209, -380);
		}
		return table;
	}
	private JTable getTable_1() {
		if (table_1 == null) {
			table_1 = new JTable();
			table_1.setBounds(721, 441, 221, -368);
		}
		return table_1;
	}
	private JButton getButton_1() {
		if (btnSig == null) {
			btnSig = new JButton("");
			btnSig.setBounds(475, 262, 37, 29);
			btnSig.setIcon(new ImageIcon(Inicio.class.getResource("/resources/proximo.png")));
		}
		return btnSig;
	}
	private JButton getBtnAadirNuevo() {
		if (btnAadirNuevo == null) {
			btnAadirNuevo = new JButton("Añadir Nuevo");
			btnAadirNuevo.setBounds(190, 470, 117, 29);
		}
		return btnAadirNuevo;
	}
	private JButton getBtnSiguiente() {
		if (btnSiguiente == null) {
			btnSiguiente = new JButton("Siguiente");
			btnSiguiente.setBounds(746, 470, 117, 29);
		}
		return btnSiguiente;
	}
}
