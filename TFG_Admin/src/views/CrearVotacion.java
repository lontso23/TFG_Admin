package views;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import source.modelo.SistemaDeVotaciones;

import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;

public class CrearVotacion extends JFrame {

	private Fondo panel;
	private Fondo contentPane;
	private JTextField textField;
	private JComboBox comboBox;
	private JLabel lblIntroduceUnaDescripcin;
	private JLabel lblIndicaLaProvincia;
	private JButton btnNewButton;
	private String comunidad;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CrearVotacion frame = new CrearVotacion();
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
	public CrearVotacion() {
		setTitle("Crear Votación");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 450);
		contentPane = new Fondo();
		contentPane.setBackgroundImage(contentPane.createImage("/resources/azul.jpg").getImage());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPanel(), BorderLayout.CENTER);
		//this.setResizable(false);
	}
	
	private JPanel getPanel() {
		if (panel == null) {
			panel = new Fondo();
			panel.setLayout(null);
			panel.add(getTextField());
			panel.add(getComboBox());
			panel.add(getLblIntroduceUnaDescripcin());
			panel.add(getLblIndicaLaProvincia());
			panel.add(getBtnNewButton());
			
			
			
		}
		return panel;
	}
	
	
	public String getComunidad() {
		return comunidad;
	}

	public void setComunidad(String comunidad) {
		this.comunidad = comunidad;
	}

	private JTextField getTextField() {
		if (textField == null) {
			textField = new JTextField();
			textField.setBounds(199, 78, 389, 26);
			textField.setColumns(10);
		}
		return textField;
	}
	private JComboBox getComboBox() {
		if (comboBox == null) {
			comboBox = new JComboBox();
			comboBox.setBounds(199, 190, 389, 27);
			ArrayList<String> comus=SistemaDeVotaciones.getSistema().obtComunidades();
			comboBox.addItem("Seleccione una opcion");
			comboBox.addItem("Todas");
			for(String act : comus){
				comboBox.addItem(act);
			}
			comboBox.setSelectedIndex(0);
			comboBox.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					if (e.getSource()==comboBox) {
			            String seleccionado=(String)comboBox.getSelectedItem();
			            System.out.println(comboBox.getSelectedItem());
			            if(!seleccionado.equals("Seleccione una opcion")){
			            	setComunidad(seleccionado);
			            }
			        }
					
				}
			});
		}
		return comboBox;
	}
	
	private JLabel getLblIntroduceUnaDescripcin() {
		if (lblIntroduceUnaDescripcin == null) {
			lblIntroduceUnaDescripcin = new JLabel("Introduce una descripción para la votación a crear");
			lblIntroduceUnaDescripcin.setBounds(199, 37, 389, 16);
		}
		return lblIntroduceUnaDescripcin;
	}
	private JLabel getLblIndicaLaProvincia() {
		if (lblIndicaLaProvincia == null) {
			lblIndicaLaProvincia = new JLabel("Indica la provincia donde desea realizar la votación");
			lblIndicaLaProvincia.setBounds(205, 162, 383, 16);
		}
		return lblIndicaLaProvincia;
	}
	private JButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new JButton("Siguiente");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					AñadirVotantes vota = new AñadirVotantes();
					vota.setComunidad(getComunidad());
					vota.setDescripcionVotacion(getTextField().getText());
					System.out.println(vota.getComunidad()+" "+vota.getDescripcionVotacion());
					vota.setVisible(true);
				}
			});
			btnNewButton.setBounds(499, 331, 180, 26);
		}
		return btnNewButton;
	}
	
}
