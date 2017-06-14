package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import source.modelo.SistemaDeVotaciones;
import source.modelo.Votacion;

public class Historico extends JFrame {

	private Fondo panel;
	private Fondo contentPane;
	private JList lista;
	private JPanel CrearHist;
	private JButton btnLocal;
	private JButton btnGeneral;

	
	/**
	 * Create the frame.
	 */
	public Historico() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		contentPane = new Fondo();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.setBackgroundImage(contentPane.createImage("/resources/azul.jpg").getImage());
		setContentPane(contentPane);
		contentPane.add(getPanel(), BorderLayout.CENTER);
		contentPane.add(getCrearHist(), BorderLayout.EAST);
		obtVotaciones();
		this.setResizable(false);
		
	}
	
	private JPanel getPanel() {
		if (panel == null) {
			panel = new Fondo();
			panel.add(getLista());
		}
		return panel;
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
	
	private void obtVotaciones(){
		ArrayList<Votacion> v = new ArrayList<Votacion>();
		v = SistemaDeVotaciones.getSistema().obtVotacionesFinalizadas();
		DefaultListModel modelo = new DefaultListModel();
		for(int i=0;i<v.size();i++){
			int cod = v.get(i).getCod();
			String descr= v.get(i).getDescrip();
			modelo.addElement(cod + "-" + descr+"\n");				
		}
		getLista().setModel(modelo);
		
	}
	
	private JPanel getCrearHist() {
		if (CrearHist == null) {
			CrearHist = new Fondo();
			CrearHist.setLayout(new GridLayout(2, 1));
			CrearHist.add(getBtnLocal());
			CrearHist.add(getBtnGeneral());
		}
		return CrearHist;
	}

	public JButton getBtnLocal() {
		if(btnLocal==null){
			btnLocal = new JButton("Actas Locales");
			
		}
		return btnLocal;
	}

	public JButton getBtnGeneral() {
		if(btnGeneral==null){
			btnGeneral = new JButton("Resultados Generales");
		}
		return btnGeneral;
	}
	
	

}
