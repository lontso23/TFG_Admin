package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Inicio extends JFrame {

	private Fondo panel;
	private Fondo contentPane;
	private  JPanel CrearHist;
	private JPanel Votaciones; 
	private JPanel pFlow; 
	private JButton btnHist;
	private JButton btncrear;
	private JButton btnIniciar;
	private JList lista;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Inicio frame = new Inicio();
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
	public Inicio() {
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
			panel.add(getCrearHist(), BorderLayout.EAST);
			panel.add(getVotaciones(),BorderLayout.WEST);
		}
		return panel;
	}

	private JPanel getCrearHist() {
		if (CrearHist == null) {
			CrearHist = new Fondo();
			CrearHist.setLayout(new GridLayout(2, 1));
			CrearHist.add(getBtnHist());
			CrearHist.add(getBtnCrear());
		}
		return CrearHist;
	}
	
	private JPanel getVotaciones() {
		if (Votaciones == null) {
			Votaciones = new Fondo();
			Votaciones.setLayout(new GridLayout(2, 1));
			Votaciones.add(getLista());
			Votaciones.add(getBtnIniciar());
		}
		return Votaciones;
	}
	
	private JButton getBtnHist() {
		if (btnHist == null) {
			btnHist = new JButton("Historico de Votaciones");
			btnHist.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
		
	}
		return btnHist;
	}
	
	private JButton getBtnCrear() {
		if (btncrear == null) {
			btncrear = new JButton("Crear Votación");
			btncrear.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
		
	}
		return btncrear;
	}
	
	private JButton getBtnIniciar() {
		if (btnIniciar == null) {
			btnIniciar = new JButton("Iniciar");
			btnIniciar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
		
	}
		return btnIniciar;
	}
	
	private JList getLista(){
		if(lista==null){
			lista = new JList();
		}
		lista.setFont(new java.awt.Font("Jokerman",1,16));
		lista.setBackground(Color.gray);
		lista.setForeground(Color.WHITE);
		return lista;
	}
	
	
	
	
}
