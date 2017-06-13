package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import source.modelo.Cronometro;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

public class Resultados extends JFrame {

	private Fondo panel;
	private Fondo contentPane;
	private int codVotacion;
	private JButton btnRgenerales;
	private JButton btnActasLocales;
	private JPanel pGrafico;
	private JPanel botones;
	private ArrayList<String> resFinal;

	

	/**
	 * Create the frame.
	 */
	public Resultados(ArrayList<String> rFinal) {
		setTitle("RESULTADOS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		contentPane = new Fondo();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.setBackgroundImage(contentPane.createImage("/resources/azul.jpg").getImage());
		setContentPane(contentPane);
		resFinal = rFinal;
		contentPane.add(getPanel(), BorderLayout.CENTER);
		crearGrafico();
		this.setResizable(false);
	}
	
	private JPanel getPanel() {
		if (panel == null) {
			panel = new Fondo();
			panel.setLayout(new BorderLayout(0, 0));
			panel.add(getPGrafico(), BorderLayout.CENTER);
			panel.add(getBotones(),BorderLayout.SOUTH);
			
		}
		return panel;
	}

	public int getCodVotacion() {
		return codVotacion;
	}

	public void setCodVotacion(int codVotacion) {
		this.codVotacion = codVotacion;
	}
	
	
	
	public ArrayList<String> getResFinal() {
		return resFinal;
	}

	public void setResFinal(ArrayList<String> rFinal) {
		this.resFinal = rFinal;
	}

	private JButton getBtnRgenerales() {
		if (btnRgenerales == null) {
			btnRgenerales = new JButton("R.Generales");
		}
		return btnRgenerales;
	}
	private JButton getBtnActasLocales() {
		if (btnActasLocales == null) {
			btnActasLocales = new JButton("Actas Locales");
		}
		return btnActasLocales;
	}
	
	
	
	public JPanel getBotones() {
		if(botones ==null){
			botones = new JPanel();
			botones.setLayout(new GridLayout(2, 1));
			botones.setBackground(new Color(0,0,0,65));
			botones.add(getBtnRgenerales());
			botones.add(getBtnActasLocales());
		}
		return botones;
	}

	private JPanel getPGrafico() {
		if (pGrafico == null) {
			pGrafico = new JPanel();
			pGrafico.setLayout(new BorderLayout(0, 0));
			pGrafico.setBackground(new Color(0,0,0,65));
		}
		return pGrafico;
	}
	
	private void crearGrafico(){
		DefaultPieDataset data = new DefaultPieDataset();
		System.out.println(getResFinal().size()+" "+getResFinal());
        for(int i=0;i<getResFinal().size();i++){
        	System.out.println("LLEGO GRAFICO "+getResFinal().get(i));
        	String[]split = getResFinal().get(i).split(",");
        	data.setValue(split[0], Integer.valueOf(split[1]));
        	System.out.println(split[0]+" "+ Integer.valueOf(split[1]));
        }

        JFreeChart chart = ChartFactory.createPieChart(
         "Grafico de los resultados Generales ", 
         data, 
         true, 
         true, 
         false);
        
        ChartPanel grafico = new ChartPanel(chart);
        getPGrafico().add(grafico);
	}
	
}
