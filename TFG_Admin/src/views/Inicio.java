package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import source.bd.SGBD;
import source.modelo.Cronometro;
import source.modelo.SistemaDeVotaciones;
import source.modelo.Votacion;

public class Inicio extends JFrame {

	private Fondo panel;
	private Fondo contentPane;
	private  JPanel CrearHist;
	private JPanel Votaciones; 
	private JLabel btnHist;
	private JLabel btncrear;
	private JLabel btnIniciar;
	private JList lista;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Inicio frame = new Inicio();
					SGBD.getConexion().conectar();
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
		setTitle("Panel de Administración");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		contentPane = new Fondo();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.setBackgroundImage(contentPane.createImage("/resources/azul.jpg").getImage());
		setContentPane(contentPane);
		contentPane.add(getPanel(), BorderLayout.CENTER);
		contentPane.add(getCrearHist(), BorderLayout.EAST);
		contentPane.add(getVotaciones(),BorderLayout.WEST);
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
	
	

	private JPanel getCrearHist() {
		if (CrearHist == null) {
			CrearHist = new Fondo();
			CrearHist.setLayout(new GridLayout(4, 1));
			CrearHist.add(getBtnHist());
			CrearHist.add(getBtnCrear());
		}
		return CrearHist;
	}
	
	
	private JPanel getVotaciones() {
		if (Votaciones == null) {
			Votaciones = new Fondo();
			Votaciones.setLayout(new GridLayout(2, 1));
			Votaciones.add(getBtnIniciar());
		}
		return Votaciones;
	}
	
	
	
	
	private JLabel getBtnHist() {
		if (btnHist == null) {
			btnHist = new JLabel("Historico de Votaciones");
			btnHist.setIcon(new ImageIcon(Inicio.class.getResource("/resources/historia.png")));
			btnHist.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseClicked(MouseEvent e) {
					Historico his = new Historico();
					his.setVisible(true);
					dispose();
					
				}
				
				@Override
				public void mouseReleased(MouseEvent e) {}
				
				@Override
				public void mousePressed(MouseEvent e) {
					btnHist.setIcon(new ImageIcon(Inicio.class.getResource("/resources/historiaP.png")));
				}
				
				@Override
				public void mouseExited(MouseEvent e) {}
				
				@Override
				public void mouseEntered(MouseEvent e) {}
				
				
			});
	
		
	}
		return btnHist;
	}
	
	private JLabel getBtnCrear() {
		if (btncrear == null) {
			btncrear = new JLabel("Crear Votación");
			btncrear.setIcon(new ImageIcon(Inicio.class.getResource("/resources/crear.png")));
			btncrear.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseClicked(MouseEvent e) {
					CrearVotacion init = new CrearVotacion();
					init.setVisible(true);
					dispose();
					
				}
				
				@Override
				public void mouseReleased(MouseEvent e) {}
				
				@Override
				public void mousePressed(MouseEvent e) {
					btncrear.setIcon(new ImageIcon(Inicio.class.getResource("/resources/crearP.png")));
				}
				
				@Override
				public void mouseExited(MouseEvent e) {}
				
				@Override
				public void mouseEntered(MouseEvent e) {}
				
				
			});
		
	}
		return btncrear;
	}
	
	private JLabel getBtnIniciar() {
		if (btnIniciar == null) {
			btnIniciar = new JLabel("Iniciar");
			btnIniciar.setForeground(UIManager.getColor("Button.highlight"));
			btnIniciar.setIcon(new ImageIcon(Inicio.class.getResource("/resources/potencia.png")));
			btnIniciar.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseClicked(MouseEvent e) {
					String select= (String) getLista().getSelectedValue();
					String[]split = select.split("-");
					VotacionActiva act = new VotacionActiva();
					SistemaDeVotaciones.getSistema().iniciar(Integer.valueOf(split[0]));
					act.setCodVotacion(Integer.valueOf(split[0]));
					act.setDescripcion(split[1]);
					act.setVisible(true);
					Cronometro.getCronometro().empezar();
					dispose();
				}
				
				@Override
				public void mouseReleased(MouseEvent e) {}
				
				@Override
				public void mousePressed(MouseEvent e) {
					btnIniciar.setIcon(new ImageIcon(Inicio.class.getResource("/resources/potenciaP.png")));
				}
				
				@Override
				public void mouseExited(MouseEvent e) {}
				
				@Override
				public void mouseEntered(MouseEvent e) {}
				
				
			});
	
		
	}
		return btnIniciar;
	}
	
	private void obtVotaciones(){
		ArrayList<Votacion> v = new ArrayList<Votacion>();
		v = SistemaDeVotaciones.getSistema().obtVotacionesSinRealizar();
		DefaultListModel modelo = new DefaultListModel();
		for(int i=0;i<v.size();i++){
			int cod = v.get(i).getCod();
			String descr= v.get(i).getDescrip();
			modelo.addElement(cod + "-" + descr+"\n");				
	}
		getLista().setModel(modelo);
		
		
	
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
