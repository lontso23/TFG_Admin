package views;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javafx.scene.image.Image;
import source.modelo.SistemaDeVotaciones;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import javax.swing.JTextPane;

public class AlterNueva extends JFrame {

	private Fondo panel;
	private Fondo contentPane;
	private JTextField NombreAlter;
	private JLabel lblIntroduceElNombre;
	private JTextField txtRuta;
	private JButton btnRlogo;
	private JLabel lblIndicaLaRuta;
	private JTextField txtDescripcion;
	private JLabel lblIntroduceUnaDescripcin;
	private JButton btnFin;
	private String rutaLogoAlter;
	private JLabel lblLogo;
	private JTextPane textPane;
	private String comunidad;
	private String descripcionVotacion;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AlterNueva frame = new AlterNueva();
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
	public AlterNueva() {
		setTitle("Crear Alternativa Nueva");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		contentPane = new Fondo();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.setBackgroundImage(contentPane.createImage("/resources/azul.jpg").getImage());
		setContentPane(contentPane);
		contentPane.add(getPanel(), BorderLayout.CENTER);
		this.setResizable(false);
	}
	
	private JPanel getPanel() {
		if (panel == null) {
			panel = new Fondo();
			panel.setLayout(null);
			panel.add(getNombreAlter());
			panel.add(getLblIntroduceElNombre());
			panel.add(getTxtRuta());
			panel.add(getBtnRlogo());
			panel.add(getLblIndicaLaRuta());
			panel.add(getTxtDescripcion());
			panel.add(getLblIntroduceUnaDescripcin());
			panel.add(getBtnFin());
			panel.add(getLblLogo());
			panel.add(getTextPane());
		
		}
		return panel;
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
	
	public String getRutaLogoAlter() {
		return rutaLogoAlter;
	}

	public void setRutaLogoAlter(String rutaLogoAlter) {
		this.rutaLogoAlter = rutaLogoAlter;
	}

	private JTextField getNombreAlter() {
		if (NombreAlter == null) {
			NombreAlter = new JTextField();
			NombreAlter.setBounds(230, 131, 480, 26);
			NombreAlter.setColumns(10);
		}
		return NombreAlter;
	}
	private JLabel getLblIntroduceElNombre() {
		if (lblIntroduceElNombre == null) {
			lblIntroduceElNombre = new JLabel("Introduce el nombre de la nueva alternativa");
			lblIntroduceElNombre.setBounds(267, 93, 490, 26);
		}
		return lblIntroduceElNombre;
	}
	private JTextField getTxtRuta() {
		if (txtRuta == null) {
			txtRuta = new JTextField();
			txtRuta.setBounds(230, 224, 396, 26);
			txtRuta.setColumns(10);
			txtRuta.setEditable(false);
		}
		return txtRuta;
	}
	private JButton getBtnRlogo() {
		if (btnRlogo == null) {
			btnRlogo = new JButton("");
			btnRlogo.setIcon(new ImageIcon(AlterNueva.class.getResource("/resources/proximo.png")));
			btnRlogo.setBounds(624, 214, 45, 36);
			btnRlogo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFileChooser filechooser = new JFileChooser();
					int returnVal = filechooser.showOpenDialog(AlterNueva.this);
				    if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = filechooser.getSelectedFile();
					setRutaLogoAlter(file.toString());
					getTxtRuta().setText(getRutaLogoAlter());
				    } else {
				    	System.out.println("error ");
				    }
				}
			});
		}
		return btnRlogo;
	}
	private JLabel getLblIndicaLaRuta() {
		if (lblIndicaLaRuta == null) {
			lblIndicaLaRuta = new JLabel("Añade un logotipo a la alternativa, Dim. 102x102");
			lblIndicaLaRuta.setBounds(273, 196, 396, 16);
		}
		return lblIndicaLaRuta;
	}
	private JTextField getTxtDescripcion() {
		if (txtDescripcion == null) {
			txtDescripcion = new JTextField();
			txtDescripcion.setBounds(232, 319, 470, 26);
			txtDescripcion.setColumns(10);
		}
		return txtDescripcion;
	}
	private JLabel getLblIntroduceUnaDescripcin() {
		if (lblIntroduceUnaDescripcin == null) {
			lblIntroduceUnaDescripcin = new JLabel("Introduce una descripción de la alternativa");
			lblIntroduceUnaDescripcin.setBounds(273, 289, 381, 16);
		}
		return lblIntroduceUnaDescripcin;
	}
	private JButton getBtnFin() {
		if (btnFin == null) {
			btnFin = new JButton("Guardar");
			btnFin.setBounds(775, 501, 117, 29);
			btnFin.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println(getNombreAlter().getText()+" "+ getTxtDescripcion().getText()+" "+ getRutaLogoAlter());
					SistemaDeVotaciones.getSistema().addAlternativa(getNombreAlter().getText(), getTxtDescripcion().getText(), getRutaLogoAlter());
					añadirAlter add = new añadirAlter();
					add.setVisible(true);
					add.setComunidad(getComunidad());
					add.setDescripcionVotacion(getDescripcionVotacion());
					dispose();
				}
			});
		}
		return btnFin;
	}
	private JLabel getLblLogo() {
		if (lblLogo == null) {
			lblLogo = new JLabel("");
			lblLogo.setBounds(829, 98, 61, 16);
		}
		return lblLogo;
	}
	private JTextPane getTextPane() {
		if (textPane == null) {
			textPane = new JTextPane();
			textPane.setBounds(965, 54, -197, 126);
			textPane.setText("El logo de la alternativa debe ser de \n"
					+ "dimensión 102 x 102 o más pequeño.");
		}
		return textPane;
	}
}
