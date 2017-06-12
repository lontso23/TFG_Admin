package views;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import source.modelo.SistemaDeVotaciones;

import javax.swing.JTextPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

public class AñadirVotantes extends JFrame {

	private Fondo contentPane;
	private JTextPane txtpnExplicacion;
	private JTextPane txtpnRutatxt;
	private JButton btnRuta;
	private JButton btnAadir;
	private JButton btnSiguiente;
	private String rutatxt;
	private String comunidad;
	private String descripcionVotacion;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AñadirVotantes frame = new AñadirVotantes();
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
	public AñadirVotantes() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		contentPane = new Fondo();
		contentPane.setBackgroundImage(contentPane.createImage("/resources/azul.jpg").getImage());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getTxtpnExplicacion());
		contentPane.add(getTxtpnRutatxt());
		contentPane.add(getBtnRuta());
		contentPane.add(getBtnAadir());
		contentPane.add(getBtnSiguiente());
		this.setResizable(false);
	}
	
	
	
	public String getRutatxt() {
		return rutatxt;
	}

	public void setRutatxt(String rutatxt) {
		this.rutatxt = rutatxt;
	}
	
	public String getComunidad() {
		return comunidad;
	}

	public void setComunidad(String comunidad) {
		this.comunidad = comunidad;
	}

	

	public String getDescripcionVotacion() {
		return descripcionVotacion;
	}

	public void setDescripcionVotacion(String descripcionVotacion) {
		this.descripcionVotacion = descripcionVotacion;
	}

	private JTextPane getTxtpnExplicacion() {
		if (txtpnExplicacion == null) {
			txtpnExplicacion = new JTextPane();
			txtpnExplicacion.setText("Si desea añadir votantes nuevos a los ya registrados hágalo de la siguiente forma \n"
					+ "Genere un archivo .txt con el siguiente formato e indique la ruta de dicho archivo: \n"
					+ "                DNI del Votante,Nombre,Apellidos,Direccion,Calle\n"
					+ "Si no necesita añadir nuevos votantes pulse el botón Siguiente");
			txtpnExplicacion.setBounds(242, 18, 520, 69);
			txtpnExplicacion.setEditable(false);
		}
		return txtpnExplicacion;
	}
	private JTextPane getTxtpnRutatxt() {
		if (txtpnRutatxt == null) {
			txtpnRutatxt = new JTextPane();
			txtpnRutatxt.setText("");
			txtpnRutatxt.setBounds(242, 204, 401, 26);
			txtpnRutatxt.setEditable(false);
		}
		return txtpnRutatxt;
	}
	private JButton getBtnRuta() {
		if (btnRuta == null) {
			btnRuta = new JButton("");
			btnRuta.setBounds(642, 195, 52, 46);
			btnRuta.setIcon(new ImageIcon(Inicio.class.getResource("/resources/proximo.png")));
			btnRuta.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFileChooser filechooser = new JFileChooser();
					int returnVal = filechooser.showOpenDialog(AñadirVotantes.this);
				    if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = filechooser.getSelectedFile();
					setRutatxt(file.toString());
					getBtnAadir().setEnabled(true);
					getTxtpnRutatxt().setText(getRutatxt());
					System.out.println(file.toString() );
				    } else {
				    	System.out.println("error ");
				    }
				}
			});
		}
		return btnRuta;
	}
	private JButton getBtnAadir() {
		if (btnAadir == null) {
			btnAadir = new JButton("Añadir");
			btnAadir.setBounds(401, 301, 117, 29);
			btnAadir.setEnabled(false);
			btnAadir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					SistemaDeVotaciones.getSistema().addVotanes(getRutatxt());
					btnAadir.setEnabled(false);
				}
			});
		}
		return btnAadir;
	}
	
	private JButton getBtnSiguiente() {
		if (btnSiguiente == null) {
			btnSiguiente = new JButton("Siguiente");
			btnSiguiente.setBounds(747, 484, 117, 29);
			btnSiguiente.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					añadirAlter add = new añadirAlter();
					add.setVisible(true);
					add.setComunidad(getComunidad());
					add.setDescripcionVotacion(getDescripcionVotacion());
					dispose();
				}
			});
		}
		return btnSiguiente;
	}
}
