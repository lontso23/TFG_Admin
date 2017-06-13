package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import source.modelo.Cronometro;
import source.modelo.SistemaDeVotaciones;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VotacionActiva extends JFrame implements Observer {

	private Fondo panel;
	private Fondo contentPane;
	private int codVotacion;
	private String descripcion;
	private JButton btnDetener;
	private JLabel lblDescripcion;
	private JTextPane txtpnTiempo;
	private JLabel lblHorainicio;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VotacionActiva frame = new VotacionActiva();
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
	public VotacionActiva() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		contentPane = new Fondo();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.setBackgroundImage(contentPane.createImage("/resources/azul.jpg").getImage());
		setContentPane(contentPane);
		contentPane.add(getPanel(), BorderLayout.CENTER);
		Cronometro.getCronometro().anadirObservador(this);
		this.setResizable(false);
	}
	
	private JPanel getPanel() {
		if (panel == null) {
			panel = new Fondo();
			panel.setLayout(null);
			panel.add(getBtnDetener());
			panel.add(getLblDescripcion());
			panel.add(getTxtpnTiempo());
			panel.add(getLblHorainicio());
			
		}
		return panel;
	}

	@Override
	public void update(Observable o, Object arg) {
		String[] split = arg.toString().split(",");
		int h = Integer.valueOf(split[0]);
		int m = Integer.valueOf(split[1]);
		int s = Integer.valueOf(split[2]);
		getTxtpnTiempo().setText(h+" : "+m+" : "+s);
		
	}
	
	

	public int getCodVotacion() {
		return codVotacion;
	}

	public void setCodVotacion(int codVotacion) {
		this.codVotacion = codVotacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	private JButton getBtnDetener() {
		if (btnDetener == null) {
			btnDetener = new JButton("Detener");
			btnDetener.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					SistemaDeVotaciones.getSistema().detener();
					Cronometro.getCronometro().parar();
					ArrayList<String> res = SistemaDeVotaciones.getSistema().obtListaResultadosGenerales(getCodVotacion());
					Resultados r = new Resultados(res);
					r.setCodVotacion(getCodVotacion());
					r.setVisible(true);
					dispose();
				}
			});
			btnDetener.setBounds(391, 409, 298, 98);
		}
		return btnDetener;
	}
	private JLabel getLblDescripcion() {
		if (lblDescripcion == null) {
			lblDescripcion = new JLabel();
			lblDescripcion.setBounds(499, 45, 61, 16);
			Font fuente=new Font("Arial", Font.ITALIC, 30);
			lblDescripcion.setForeground(Color.BLACK);
			lblDescripcion.setText(getDescripcion());
			System.out.println(getDescripcion());
		}
		return lblDescripcion;
	}
	private JTextPane getTxtpnTiempo() {
		if (txtpnTiempo == null) {
			txtpnTiempo = new JTextPane();
			txtpnTiempo.setBounds(499, 101, 86, 21);
			Font fuente=new Font("Arial", Font.ITALIC, 300);
			txtpnTiempo.setForeground(Color.GREEN);
			txtpnTiempo.setBackground(Color.BLACK);
			txtpnTiempo.setEditable(false);
		}
		return txtpnTiempo;
	}
	private JLabel getLblHorainicio() {
		if (lblHorainicio == null) {
			Date d = new Date();
			lblHorainicio = new JLabel("");
			lblHorainicio.setBounds(425, 73, 264, 16);
			Font fuente=new Font("Arial", Font.ITALIC, 30);
			lblHorainicio.setForeground(Color.BLACK);
			lblHorainicio.setText(d.toString());
		}
		return lblHorainicio;
	}
}
