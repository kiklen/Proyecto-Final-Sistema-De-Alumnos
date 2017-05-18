
package sistemadealumnos;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PDF {
    public final String institucion = "Instituto Tecnológico de León";
    private Document horarios;
    private FileOutputStream pdf;
    private Font titulo,materias,texto;
    private Paragraph parrafo;
    private PdfPTable table;
   
    private PdfPCell encabezados[];
    private Chunk chunk;
    
    public PDF(){
        horarios = new Document();
        titulo = FontFactory.getFont(FontFactory.TIMES, 30, Font.NORMAL);
        materias = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL);
        texto = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD);
        parrafo = new Paragraph();
        
    }
    
    public PdfPCell[] encabezados(){
        encabezados= new PdfPCell[9];
        encabezados[0]= new PdfPCell(new Phrase("Grupo",materias));
        encabezados[1]= new PdfPCell(new Phrase("C. Materia",materias));
        encabezados[2]= new PdfPCell(new Phrase("Materia",materias));
        encabezados[3]= new PdfPCell(new Phrase("          ",materias));
        encabezados[4]= new PdfPCell(new Phrase("Lunes",materias));
        encabezados[5]= new PdfPCell(new Phrase("Martes",materias));
        encabezados[6]= new PdfPCell(new Phrase("Miercoles",materias));
        encabezados[7]= new PdfPCell(new Phrase("Jueves",materias));
        encabezados[8]= new PdfPCell(new Phrase("Viernes",materias));
        return encabezados;
    }
    public PdfPCell[] agregarMateria(Horario h){
        PdfPCell [] materia = new PdfPCell[9];
        materia[0]= new PdfPCell(new Phrase(h.getGrupo()+"",materias));
        materia[1]= new PdfPCell(new Phrase(h.getclvMateria(),materias));
        materia[2]= new PdfPCell(new Phrase(h.getMateria()+"\n"+h.getMaestro(),
                materias));
        materia[3]= new PdfPCell(new Phrase("Cred. "+h.getCreditos()+
                "\nSem. "+h.getSemestre(),materias));
        materia[4]= asignaAula(h.gethL(),h);
        materia[5]= asignaAula(h.gethM(),h);
        materia[6]= asignaAula(h.gethMM(),h);
        materia[7]= asignaAula(h.gethJ(),h);
        materia[8]= asignaAula(h.gethV(),h);
        return materia;
    }
    
    public PdfPCell asignaAula(String dia,Horario h){
        PdfPCell aula;
            if(!dia.equals("")){
                aula= new PdfPCell(new Phrase(dia+"\n"+h.getAula(),materias));
            }else{
                aula= new PdfPCell(new Phrase(dia,materias));
            }
        return aula;
    }
    
    public PdfPCell[] agregarAlumno(Alumno a){
        PdfPCell alumno[] = new PdfPCell[6];
        alumno[0]=new PdfPCell(new Phrase("ALUMNO: "+a.getApellido1()+" "+
                                a.getApellido2()+" "+a.getNombre(),texto));
        alumno[0].setBorder(Rectangle.NO_BORDER);
        alumno[1]= new PdfPCell (new Phrase("N DE CONTROL: "+
                                 a.getNumeroDeControl(),texto));
        alumno[1].setBorder(Rectangle.NO_BORDER);
        alumno[2]= new PdfPCell(new Phrase("CARRERA: "+a.getCarrera(),texto));
        alumno[2].setBorder(Rectangle.NO_BORDER);
        alumno[3]= new PdfPCell(new Phrase("SEMESTRE: "+a.getSemestre(),texto));
        alumno[3].setBorder(Rectangle.NO_BORDER);
        alumno[4]= new PdfPCell(new Phrase("CAMPUS: "+a.getCampus(),texto));
        alumno[4].setBorder(Rectangle.NO_BORDER);
        alumno[5]= new PdfPCell(new Phrase("TURNO: "+a.getTurno(),texto));
        alumno[5].setBorder(Rectangle.NO_BORDER);
        return alumno;
    }
    
    public void crear(Alumno a, Horario[]h,String dir)throws IOException,
            DocumentException
    {
        
        float anchos[]={55,90,250,90,100,100,100,100,100};
        table= new PdfPTable(anchos);
        PdfPTable alumno = new PdfPTable(2);
        PdfPTable imagenes = new PdfPTable(4);
        imagenes.setTotalWidth(new float[]{300,100,100,300});
        pdf= new FileOutputStream(dir+".pdf");
        PdfWriter writer = PdfWriter.getInstance(horarios, pdf);
        horarios.setMargins(1, 1, 1, 1);
        horarios.left(0);
        horarios.open();
       
        parrafo= new Paragraph(institucion,titulo);
        parrafo.setAlignment(Element.TITLE);
        horarios.add(parrafo);
        
        
        chunk = new Chunk(Chunk.NEWLINE+"");
        horarios.add(chunk);
        
        PdfPCell alumnos[] = agregarAlumno(a);
        for (int i = 0; i < alumnos.length; i++) {
            alumno.addCell(alumnos[i]);
        }
        horarios.add(alumno);
        
                
        encabezados= encabezados();
        for (int i = 0; i < encabezados.length; i++) {
            table.addCell(encabezados[i]);;
        }
        
        for (int i = 0; i < h.length; i++) {
                if(h[i]!=null){
                   PdfPCell[] horario = agregarMateria(h[i]); 
                   for (int j = 0; j < 9; j++) {
                    table.addCell(horario[j]);
                }
           }
            

            
        }
        horarios.add(table);
        
        float anchos2[] = {170,170,250,100,100};
        table= new PdfPTable(5);
        table.setTotalWidth(anchos2);
        table.setLockedWidth(true);
        PdfContentByte cb = writer.getDirectContent();
        
        Barcode128 code128 = new Barcode128();
        code128.setCode(a.getNumeroDeControl()+"");
        code128.setCodeType(Barcode128.CODE128);
        Image code128Image = code128.createImageWithBarcode(cb, null, null);
        PdfPCell cell= new PdfPCell(new Phrase(""));
        cell.setBorder(Rectangle.NO_BORDER);
        
        table.addCell(cell);
        table.addCell(cell);
        table.addCell(cell);
        table.addCell(cell);
        table.addCell(cell);
        table.addCell(cell);
        table.addCell(cell);
        
        cell = new PdfPCell(code128Image, true);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setFixedHeight(45);
        
        table.addCell(cell);
        
        cell= new PdfPCell(new Phrase(""));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        table.addCell(cell);
        
        horarios.add(table);
        horarios.close();
    }
    
}
