package views;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

import source.modelo.SistemaDeVotaciones;

import javax.swing.JButton;
import javax.swing.JDialog;


public class añadirAlter extends JFrame {

	private Fondo panel;
	private Fondo contentPane;
	private ArrayList<String> almacenadas;
	private ArrayList<String> inscritas;
	private ArrayList<Checkbox> botones;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private String comunidad;
	private String descripcionVotacion;
	private JProgressBar dpb;
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
		contentPane.add(getPanel(), BorderLayout.SOUTH);
		almacenadas=new ArrayList<String>();
		inscritas=new ArrayList<String>();
		botones = new ArrayList<Checkbox>();
		dpb=new JProgressBar(0, 1500);
		llenarOpciones();
		this.setResizable(false);
	}
	
	private JPanel getPanel() {
		if (panel == null) {
			panel = new Fondo();
			panel.setLayout(new GridLayout(1, 0, 0, 0));
			panel.add(getBtnNewButton());
			panel.add(getBtnNewButton_1());
		}
		return panel;
	}
	
	public ArrayList<String> getAlmacenadas() {
		return almacenadas;
	}

	public void setAlmacenadas(ArrayList<String> almacenadas) {
		this.almacenadas = almacenadas;
	}

	public ArrayList<String> getInscritas() {
		return inscritas;
	}

	public void setInscritas(ArrayList<String> inscritas) {
		this.inscritas = inscritas;
	}

	
	public ArrayList<Checkbox> getBotones() {
		return botones;
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

	private JButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new JButton("Añadir");
			btnNewButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					AlterNueva alter = new AlterNueva();
					alter.setVisible(true);
					alter.setComunidad(getComunidad());
					alter.setDescripcionVotacion(getDescripcionVotacion());
					dispose();
				}
			});
		}
		return btnNewButton;
	}
	private JButton getBtnNewButton_1() {
		if (btnNewButton_1 == null) {
			btnNewButton_1 = new JButton("Siguiente");
			btnNewButton_1.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					for(Checkbox act : botones){
						if(act.getState()==true){
							getInscritas().add(act.getName());

						}
						
					}
					if(getInscritas().contains("Si")&&getInscritas().contains("No")){
						if(getInscritas().size()>=3){
							String msj = "Si elige la ópcion de voto 'NO' y 'SI' no puede añadir más alternativas ";
							JOptionPane.showMessageDialog(null,msj, "Mensaje de Error", JOptionPane.INFORMATION_MESSAGE);
							getInscritas().clear();
							System.out.println(getInscritas().size());
						}
						else{
							SistemaDeVotaciones.getSistema().generarVotacion(getComunidad(), getInscritas(), getDescripcionVotacion());
							Inicio frame = new Inicio();
							frame.setVisible(true);
							dispose();
						}
						
					}else{
						SistemaDeVotaciones.getSistema().generarVotacion(getComunidad(), getInscritas(), getDescripcionVotacion());
						Inicio frame = new Inicio();
						frame.setVisible(true);
						dispose();
					}
					
				}
			});
		}
		return btnNewButton_1;
	}
	
	
	private void llenarOpciones(){
		setAlmacenadas(SistemaDeVotaciones.getSistema().obtAlternativasAlmacenadas());
		GridLayout gPanel = new GridLayout(getAlmacenadas().size()/2, 2);
		gPanel.setVgap(0);
		gPanel.setHgap(0);
		JPanel gridPanel = new JPanel(gPanel);
		gridPanel.setBackground(new Color(0,0,0,65));
		for(int i=0; i<getAlmacenadas().size();i++){
			String act = getAlmacenadas().get(i);
			Checkbox lb = new Checkbox(act);
			lb.setName(act);
			Font fuente = new Font("Calibri", 3, 16);
			lb.setFont(fuente);
			lb.setForeground(Color.BLACK);
			lb.setBackground(Color.white);
			lb.setBounds(0, 0, 1, 1);
			gridPanel.add(lb);
			getBotones().add(lb);
		}
		getContentPane().add(gridPanel);
	}
	
}
