package source.modelo;

import java.util.ArrayList;
import java.util.HashMap;



import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;


import java.io.*;
import java.net.MalformedURLException; 



public class GetToPDF {
	
	private static GetToPDF miPDF = new GetToPDF();
	private int codV;
    private static final Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 26, Font.BOLDITALIC);
    private static final Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
        
    private static final Font categoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static final Font subcategoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static final Font blueFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);    
    private static final Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    
    private static final String iTextExampleImage = "/home/xules/codigoxules/iText-Example-image.png";
	
	private GetToPDF(){
		
	}
	

	public static GetToPDF getPdf(){
		return miPDF;
	}
	
	
	
	public int getCodV() {
		return codV;
	}


	public void setCodV(int codV) {
		this.codV = codV;
	}


	public void resultadosGeneral(ArrayList<String> alterYpunt, String rutaPdf,String descrip){
		
		rutaPdf=rutaPdf+"_RGenerales.pdf";
		
		try{
		Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(rutaPdf));

        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("No such file was found to generate the PDF "
                    + "(No se encontró el fichero para generar el pdf)" + fileNotFoundException);
        }
	        document.open();
	        document.addTitle("Resultados Generales "+descrip);
	        document.addSubject("Using iText (usando iText)");
	        document.addKeywords("Java, PDF, iText");
	        document.addAuthor("SEV-MK");
	        
	        Chunk chunk = new Chunk("Resultados Generales \n"+descrip, chapterFont);
            chunk.setBackground(BaseColor.GRAY);
            Chapter chapter = new Chapter(new Paragraph(chunk), 1);
            chapter.setNumberDepth(0);
            Paragraph paragraphG = new Paragraph("");
            chapter.addSection(paragraphG);
            Image image;
            String ruta= imagenGenerales(getCodV());
            try {
            	
                image = Image.getInstance(ruta);  
                paragraphG.add(image);
            } catch (BadElementException ex) {
                System.out.println("Image BadElementException" +  ex);
            } catch (IOException ex) {
                System.out.println("Image IOException " +  ex);
            }
            File fichero = new File(ruta);
            fichero.delete();
            Paragraph paragraphE = new Paragraph("");
            chapter.addSection(paragraphE);
            PdfPTable table = new PdfPTable(2);
            PdfPCell columnHeader;
            
            columnHeader = new PdfPCell(new Phrase("Alternativa"));
            columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(columnHeader);
            columnHeader = new PdfPCell(new Phrase("Numero de votos"));
            columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(columnHeader);
            table.setHeaderRows(1);
            Section paragraphMore = chapter.addSection(paragraphE);
            for (int row = 0; row < alterYpunt.size(); row++) {

				String[] split = alterYpunt.get(row).split(",");
				table.addCell(split[0]);
				table.addCell(split[1]);
                
            }
            paragraphMore.add(table);
            document.add(chapter);
            document.close();
		} catch (DocumentException documentException) {
	        System.out.println("The file not exists (Se ha producido un error al generar un documento): " + documentException);
	    }
			
	}
	
	public void actasLocales(HashMap<String, ArrayList<String>> puntPorCole, String rutaPdf, String Localidad,String descrip){
		rutaPdf=rutaPdf+"_"+Localidad+"_ResLocales.pdf";
		
		try{
		Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(rutaPdf));

        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("No such file was found to generate the PDF "
                    + "(No se encontró el fichero para generar el pdf)" + fileNotFoundException);
        }
	        document.open();
	        document.addTitle("Resultados Generales "+descrip);
	        document.addSubject("Using iText (usando iText)");
	        document.addKeywords("Java, PDF, iText");
	        document.addAuthor("SEV-MK");
	        
	        Chunk chunk = new Chunk("Resultados "+Localidad+"\n "+descrip, chapterFont);
            chunk.setBackground(BaseColor.GRAY);
            Chapter chapter = new Chapter(new Paragraph(chunk), 1);
            chapter.setNumberDepth(0);
            Paragraph paragraphE = new Paragraph("");
            DottedLineSeparator dottedline = new DottedLineSeparator();
            dottedline.setOffset(-2);
            dottedline.setGap(2f);
            paragraphE.add(dottedline);
            chapter.addSection(paragraphE);
            
            for (String cole :  puntPorCole.keySet()) {
            	
            	Paragraph paragraphL = new Paragraph(cole, subcategoryFont);
            	chapter.addSection(paragraphL);
            	PdfPTable table = new PdfPTable(2);
                PdfPCell columnHeader;
                
                columnHeader = new PdfPCell(new Phrase("Alternativa"));
                columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(columnHeader);
                columnHeader = new PdfPCell(new Phrase("Numero de votos"));
                columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(columnHeader);
                table.setHeaderRows(1);
                Image image;
                String ruta=graficoLocal(puntPorCole.get(cole));
                try {
                	
                    image = Image.getInstance(ruta);  
                    paragraphL.add(image);
                } catch (BadElementException ex) {
                    System.out.println("Image BadElementException" +  ex);
                } catch (IOException ex) {
                    System.out.println("Image IOException " +  ex);
                }
                File fichero = new File(ruta);
                fichero.delete();
                for(int i=0; i<puntPorCole.get(cole).size();i++){
                	String[] split = puntPorCole.get(cole).get(i).split(",");
    				table.addCell(split[0]);
    				table.addCell(split[1]);
                }
                paragraphL.add(table);
            }
            
            document.add(chapter);
            document.close();
		} catch (DocumentException documentException) {
	        System.out.println("The file not exists (Se ha producido un error al generar un documento): " + documentException);
	    }
	}
	
	public void generarPDF(ArrayList<String> l, String rute,File pdfNewFile){

		        try {
		            Document document = new Document();
		            try {

		                PdfWriter.getInstance(document, new FileOutputStream(pdfNewFile));

		            } catch (FileNotFoundException fileNotFoundException) {
		                System.out.println("No such file was found to generate the PDF "
		                        + "(No se encontró el fichero para generar el pdf)" + fileNotFoundException);
		            }
		            document.open();
		            // We add metadata to PDF
		            // Añadimos los metadatos del PDF
		            document.addTitle("Table export to PDF (Exportamos la tabla a PDF)");
		            document.addSubject("Using iText (usando iText)");
		            document.addKeywords("Java, PDF, iText");
		            document.addAuthor("Código Xules");
		            document.addCreator("Código Xules");
		            
		            // First page
		            // Primera página 
		            Chunk chunk = new Chunk("This is the title", chapterFont);
		            chunk.setBackground(BaseColor.GRAY);
		            // Let's create de first Chapter (Creemos el primer capítulo)
		            Chapter chapter = new Chapter(new Paragraph(chunk), 1);
		            chapter.setNumberDepth(0);
		            chapter.add(new Paragraph("This is the paragraph", paragraphFont));
		            // We add an image (Añadimos una imagen)
		            Image image;
		            try {
		            	
		                image = Image.getInstance(iTextExampleImage);  
		                image.setAbsolutePosition(2, 150);
		                chapter.add(image);
		            } catch (BadElementException ex) {
		                System.out.println("Image BadElementException" +  ex);
		            } catch (IOException ex) {
		                System.out.println("Image IOException " +  ex);
		            }
		            document.add(chapter);
		            
		            // Second page - some elements
		            // Segunda página - Algunos elementos
		            Chapter chapSecond = new Chapter(new Paragraph(new Anchor("Some elements (Añadimos varios elementos)")), 1);
		            Paragraph paragraphS = new Paragraph("Do it by Xules (Realizado por Xules)", subcategoryFont);
		            
		            // Underline a paragraph by iText (subrayando un párrafo por iText)
		            Paragraph paragraphE = new Paragraph("This line will be underlined with a dotted line (Está línea será subrayada con una línea de puntos).");
		            DottedLineSeparator dottedline = new DottedLineSeparator();
		            dottedline.setOffset(-2);
		            dottedline.setGap(2f);
		            paragraphE.add(dottedline);
		            chapSecond.addSection(paragraphE);
		            
		            Section paragraphMoreS = chapSecond.addSection(paragraphS);
		            // List by iText (listas por iText)
		            String text = "test 1 2 3 ";
		            for (int i = 0; i < 5; i++) {
		                text = text + text;
		            }
		            List list = new List(List.UNORDERED);
		            ListItem item = new ListItem(text);
		            item.setAlignment(Element.ALIGN_JUSTIFIED);
		            list.add(item);
		            text = "a b c align ";
		            for (int i = 0; i < 5; i++) {
		                text = text + text;
		            }
		            item = new ListItem(text);
		            item.setAlignment(Element.ALIGN_JUSTIFIED);
		            list.add(item);
		            text = "supercalifragilisticexpialidocious ";
		            for (int i = 0; i < 3; i++) {
		                text = text + text;
		            }
		            item = new ListItem(text);
		            item.setAlignment(Element.ALIGN_JUSTIFIED);
		            list.add(item);
		            paragraphMoreS.add(list);
		            document.add(chapSecond);
		            
		            // How to use PdfPTable
		            // Utilización de PdfPTable
		            
		            // We use various elements to add title and subtitle
		            // Usamos varios elementos para añadir título y subtítulo
		            Anchor anchor = new Anchor("Table export to PDF (Exportamos la tabla a PDF)", categoryFont);
		            anchor.setName("Table export to PDF (Exportamos la tabla a PDF)");            
		            Chapter chapTitle = new Chapter(new Paragraph(anchor), 1);
		            Paragraph paragraph = new Paragraph("Do it by Xules (Realizado por Xules)", subcategoryFont);
		            Section paragraphMore = chapTitle.addSection(paragraph);
		            paragraphMore.add(new Paragraph("This is a simple example (Este es un ejemplo sencillo)"));
		            Integer numColumns = 6;
		            Integer numRows = 120;
		            // We create the table (Creamos la tabla).
		            PdfPTable table = new PdfPTable(numColumns); 
		            // Now we fill the PDF table 
		            // Ahora llenamos la tabla del PDF
		            PdfPCell columnHeader;
		            // Fill table rows (rellenamos las filas de la tabla).                
		            for (int column = 0; column < numColumns; column++) {
		                columnHeader = new PdfPCell(new Phrase("COL " + column));
		                columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
		                table.addCell(columnHeader);
		            }
		            table.setHeaderRows(1);
		            // Fill table rows (rellenamos las filas de la tabla).                
		            for (int row = 0; row < numRows; row++) {
		                for (int column = 0; column < numColumns; column++) {
		                    table.addCell("Row " + row + " - Col" + column);
		                }
		            }
		            // We add the table (Añadimos la tabla)
		            paragraphMore.add(table);
		            // We add the paragraph with the table (Añadimos el elemento con la tabla).
		            document.add(chapTitle);
		            document.close();
		            System.out.println("Your PDF file has been generated!(¡Se ha generado tu hoja PDF!");
		        } catch (DocumentException documentException) {
		            System.out.println("The file not exists (Se ha producido un error al generar un documento): " + documentException);
		        }
		    }
	
	private String imagenGenerales(int codV){
		DefaultPieDataset data = new DefaultPieDataset();
		ArrayList<String> rFinal = SistemaDeVotaciones.getSistema().obtListaResultadosGenerales(codV);
		for(int i=0;i<rFinal.size();i++){
        	String[]split = rFinal.get(i).split(",");
        	data.setValue(split[0], Integer.valueOf(split[1]));
        }

        JFreeChart chart = ChartFactory.createPieChart(
         " ", 
         data, 
         true, 
         true, 
         false);
        
        try {
			ChartUtilities.saveChartAsPNG(new File("Rgeneral.png"), chart, 300, 300);
		} catch (IOException e) {e.printStackTrace();}
        
        
        return "Rgeneral.png";
	}
	
	private String graficoLocal(ArrayList<String> l){
		DefaultPieDataset data = new DefaultPieDataset();
		for(int i=0;i<l.size();i++){
        	String[]split = l.get(i).split(",");
        	data.setValue(split[0], Integer.valueOf(split[1]));
        }

        JFreeChart chart = ChartFactory.createPieChart(
         " ", 
         data, 
         true, 
         true, 
         false);
        
        try {
			ChartUtilities.saveChartAsPNG(new File("RLocal.png"), chart, 200, 200);
		} catch (IOException e) {e.printStackTrace();}
        
        
        return "RLocal.png";
	}
	

}