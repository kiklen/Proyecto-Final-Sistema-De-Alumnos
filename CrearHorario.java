package sistemadealumnos;

/**
 *
 * @author José Miguel Becerra Vázquez, Edith Noriega, Andrés Romero Smith
 * Tópicos Avanzados de Programación
 * Proyecto Final: Gestor de horarios de Alumnos
 * Elaborar una aplicación de escritorio que permita a los alumnos crear su 
 * horario seleccionando sus materias y teniendo la posibilidad de imprimirlo
 * en formato PDF
 * Martes- Jueves 12:15 - 13:55 Ene-Junio 2017
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class CrearHorario {
}

class frame extends JFrame{
    public frame(String numControl){
        
        try{
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }catch(Exception e){
        e.printStackTrace();
    }
        
        
    setBounds(20,20,1200,700);
   
    Panel pane = new Panel(numControl);
    JButton terminar= new JButton("Terminar");
    
    terminar.addActionListener(new ActionListener(){
        
        
        @Override
        public void actionPerformed(ActionEvent e) {
           Horario hor[] = pane.getHorario();
           Alumno alumno = pane.getAlumno();
           
           Panel2 pane2= new Panel2(hor,alumno);
           pane.setVisible(false);
           add(pane2);
           pane2.setVisible(true);
        }
        
    });
    add(pane);
    add(terminar,BorderLayout.SOUTH);
    } 
}

class Panel2 extends JPanel{
    private JLabel [] componentes;
    private JPanel [] paneles;
    private JButton imprimir;
    private final GridBagLayout layout; 
    private final GridBagConstraints constraints;
    
    public Panel2(Horario []h,Alumno a){
        
        layout = new GridBagLayout();
        setLayout(layout); 
        constraints = new GridBagConstraints();
        paneles= new JPanel[h.length];
        super.setBackground(Color.cyan);
        componentes = new JLabel[9];
        
        
        
        for (int i = 0; i < h.length; i++) {
            componentes = agregarComponentes(h[i]);
            paneles[i]= new JPanel();
            paneles[i].setLayout(new FlowLayout());
            paneles[i].setBackground(Color.cyan);
            for (int j = 0; j < componentes.length; j++) {
                if(componentes[j]!=null){
                    paneles[i].add(componentes[j]);
                }
            }
        }
        
        imprimir= new JButton("Crear PDF");
        imprimir.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                PDF pdf = new PDF();
                try{
                    JFileChooser archivo = new JFileChooser();
                    String direccion = "";
                    int opcion = archivo.showSaveDialog(imprimir);
                    if(opcion == JFileChooser.APPROVE_OPTION){
                        File file= archivo.getSelectedFile();
                        direccion = file.toString();
                    }
                pdf.crear(a, h,direccion);
                }catch(Exception ex){ }
            }
            
        });
        int l =0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        for (int i = 0; i < paneles.length; i++) {
            if(paneles[i]!=null){
            addComponent(paneles[i],i,0,9,1);
            l=i;
            }
        }
        addComponent(imprimir,l+1,0,3,1);
    }
    
    private void addComponent(Component component,
      int row, int column, int width, int height)
   {
      constraints.gridx = column; 
      constraints.gridy = row;
      constraints.gridwidth = width;
      constraints.gridheight = height;
      layout.setConstraints(component, constraints); 
      add(component); 
   }
    
    private JLabel [] agregarComponentes(Horario h){
        JLabel comp[] = new JLabel[9];
        if(h!=null){
        comp[0]= new JLabel(h.getGrupo()+" ");
        comp[0].setSize(100, 35);
        comp[0].setBackground(Color.blue);
        comp[1]= new JLabel(h.getclvMateria()+" ");
        comp[1].setSize(100, 35);
        comp[1].setBackground(Color.green);
        comp[2]= new JLabel(h.getMateria()+"\n "+h.getMaestro());
        comp[2].setSize(250, 35);
        comp[2].setBackground(Color.blue);
        comp[3]= new JLabel("Cred. "+h.getCreditos()+"\n Sem. "+h.getSemestre());
        comp[3].setSize(100, 35);
        comp[3].setBackground(Color.green);
        comp[4]= new JLabel("Lunes "+h.gethL());
        comp[4].setSize(100, 35);
        comp[4].setBackground(Color.blue);
        comp[5]= new JLabel(" Martes "+h.gethM());
        comp[5].setSize(100, 35);
        comp[5].setBackground(Color.green);
        comp[6]= new JLabel(" Miercoles "+h.gethMM());
        comp[6].setSize(100, 35);
        comp[6].setBackground(Color.blue);
        comp[7]= new JLabel(" Jueves "+h.gethJ());
        comp[7].setSize(100, 35);
        comp[7].setBackground(Color.green);
        comp[8]= new JLabel(" Viernes "+h.gethV());
        comp[8].setSize(100, 35);
        comp[8].setBackground(Color.blue);
        }
        return comp;
    }
}

class Panel extends JPanel{
    private DBManager manager;
    private ArrayList <Materia> materiasPosibles;
    private ArrayList <Alumno> alumnos;
    private Alumno alumno;
    private int index;
    private Horario [] horario;
    private ArrayList<Horario> horarios;
    
    public Horario[] getHorario(){
        return horario;
    }
    public Alumno getAlumno(){
        return alumno;
    }
    
    public Alumno identificarAlumno(ArrayList<Alumno> a, String numControl){
        Alumno alumno= null;
        for (int i = 0; i <a.size() ; i++) {
            if (a.get(i).getNumeroDeControl().equals(numControl)){
                alumno = a.get(i);
                break;
            }
        }
        return alumno;
    }
    
    public ArrayList<Horario> materiasDeHorario(ArrayList<Materia> m){
        ArrayList<Horario> hor= new ArrayList();
        for (int i = 0; i < m.size(); i++) {
            hor.add(manager.crearHorario(m.get(i)));
        }
        return hor;
    }
    
    public String calcularHora(Horario h){
        String horas="";
        if(!h.gethL().equals("")){
            horas=h.gethL();
        }else{
            horas= h.gethM();
        }
        return horas;
    }
    
    public String calcularDia1(Horario h){
        String dias="";
        if(!h.gethL().equals("")){
            dias="Lunes";
        }else{
            dias= "Martes";
        }
        return dias;
    }
    public String calcularDia2(Horario h){
        String dias="";
        if(!h.gethL().equals("")){
            dias="Miercoles";
        }else{
            dias= "Jueves";
        }
        return dias;
    }
    public boolean calcularDia3(Horario h){
        if(!h.gethV().equals("")){
            return true;
        }else{
            return false;
        }
    }
    
    public Horario identificarHorario(String s){
        Horario ho=null;
        for (int i = 0; i < horarios.size(); i++) {
            if (horarios.get(i).getMateria().equals(s)){
                ho= horarios.get(i);
                break;
            }
        }
        return ho;
    }
    
    public Panel(String numControl){
        setLayout(new BorderLayout());
        
        manager= new DBManager();
        
        //identificacion del alumno y asugnacion de materias posibles
        alumnos = manager.llenarAlumnos();
        alumno = identificarAlumno(alumnos,numControl);
        materiasPosibles = manager.materiasPosibles(alumno);
        horarios= materiasDeHorario(materiasPosibles);
        horario= new Horario[horarios.size()];
        index=0;
        
        String hora;
        //BOTONES PARA MATERIAS
        JPanel botones = new JPanel();
        botones.setLayout(new GridLayout(horarios.size(),1));
        
        for (int i = 0; i < horarios.size(); i++) {
            JButton boton = new JButton(horarios.get(i).getMateria());
            botones.add(boton);
            boolean b= calcularDia3(horarios.get(i));
            hora = calcularHora(horarios.get(i));
            ponerMat poner = new ponerMat(horarios.get(i).getMateria(),
                    hora,calcularDia1(horarios.get(i)),
                    calcularDia2(horarios.get(i)),
                    b);
            boton.addActionListener(poner);
        }
        add(botones,BorderLayout.WEST);
        
        
        //---------------------------------------------------------------------
        JPanel dias = new JPanel();
        dias.setLayout(new GridLayout(1,6));
        JLabel esp = new JLabel("");
        JLabel lun= new JLabel("Lunes");    
        JLabel mar= new JLabel("Martes");
        JLabel mie= new JLabel("Miercoles");
        JLabel jue= new JLabel("Jueves");
        JLabel vie= new JLabel("Viernes");
        JLabel esp2= new JLabel("");
        dias.add(esp);dias.add(lun);dias.add(mar);dias.add(mie);dias.add(jue);dias.add(vie);
        
        add(dias,BorderLayout.NORTH);
        // HORARIOS
        JPanel horario = new JPanel();
        horario.setLayout(new GridLayout(4,1));
        JLabel hor1 = new JLabel("7:00 ----- 8:40");
        JLabel hor2 = new JLabel("8:45 ----- 10:25");
        JLabel hor3 = new JLabel("10:30 ----- 12:10");
        JLabel hor4 = new JLabel("12:15 ----- 13:55");
        horario.add(hor1);horario.add(hor2);horario.add(hor3);horario.add(hor4);
        add(horario,BorderLayout.EAST);
    
    //Agregar Materias
    JPanel addMat= new JPanel();
        addMat.setLayout(new GridLayout(4,5,10,10));
    //HORARIO 1
        lun1 = new JButton("Vacío");
        mar1 = new JButton("Vacío");
        mie1 = new JButton("Vacío");
        jue1 = new JButton("Vacío");
        vie1 = new JButton("Vacío");
        
        //HORARIO 2
        lun2 = new JButton("Vacío");
        mar2 = new JButton("Vacío");
        mie2 = new JButton("Vacío");
        jue2 = new JButton("Vacío");
        vie2 = new JButton("Vacío");
    //HOARIO 3
        lun3 = new JButton("Vacío");
        mar3 = new JButton("Vacío");
        mie3 = new JButton("Vacío");
        jue3 = new JButton("Vacío");
        vie3 = new JButton("Vacío");
    //HORARIO 4
        lun4 = new JButton("Vacío");
        mar4 = new JButton("Vacío");
        mie4 = new JButton("Vacío");
        jue4 = new JButton("Vacío");
        vie4 = new JButton("Vacío");
        
        
        
        //AGREGAR TODOS LOS BOTONES;
        
        addMat.add(lun1);addMat.add(mar1);addMat.add(mie1);addMat.add(jue1);addMat.add(vie1);
        addMat.add(lun2);addMat.add(mar2);addMat.add(mie2);addMat.add(jue2);addMat.add(vie2);
        addMat.add(lun3);addMat.add(mar3);addMat.add(mie3);addMat.add(jue3);addMat.add(vie3);
        addMat.add(lun4);addMat.add(mar4);addMat.add(mie4);addMat.add(jue4);addMat.add(vie4);
        
        //FONDO DE LOS BOTONES
        
        lun1.setBackground(Color.red);mar1.setBackground(Color.red);mie1.setBackground(Color.red);jue1.setBackground(Color.red);vie1.setBackground(Color.red);
        lun2.setBackground(Color.red);mar2.setBackground(Color.red);mie2.setBackground(Color.red);jue2.setBackground(Color.red);vie2.setBackground(Color.red);
        lun3.setBackground(Color.red);mar3.setBackground(Color.red);mie3.setBackground(Color.red);jue3.setBackground(Color.red);vie3.setBackground(Color.red);
        lun4.setBackground(Color.red);mar4.setBackground(Color.red);mie4.setBackground(Color.red);jue4.setBackground(Color.red);vie4.setBackground(Color.red);
        
        
        //AGREGAR PANEL DE BOTONES
        add(addMat,BorderLayout.CENTER);
        
        //eventos quitar MAteria
        quitarMat quitar = new quitarMat();
        lun1.addActionListener(quitar);mar1.addActionListener(quitar);mie1.addActionListener(quitar);jue1.addActionListener(quitar);vie1.addActionListener(quitar);
        lun2.addActionListener(quitar);mar2.addActionListener(quitar);mie2.addActionListener(quitar);jue2.addActionListener(quitar);vie2.addActionListener(quitar);    
        lun3.addActionListener(quitar);mar3.addActionListener(quitar);mie3.addActionListener(quitar);jue3.addActionListener(quitar);vie3.addActionListener(quitar);
        lun4.addActionListener(quitar);mar4.addActionListener(quitar);mie4.addActionListener(quitar);jue4.addActionListener(quitar);vie4.addActionListener(quitar);
        
        }
    
   
    
        JButton lun1 ;
        JButton mar1 ;
        JButton mie1 ;
        JButton jue1 ;
        JButton vie1 ;
        
        //HORARIO 2
        JButton lun2 ;
        JButton mar2 ;
        JButton mie2 ;
        JButton jue2 ;
        JButton vie2 ;
    //HORARIO 3
        JButton lun3 ;
        JButton mar3 ;
        JButton mie3 ;
        JButton jue3 ;
        JButton vie3 ;
    //HORARIO 4
        JButton lun4 ;
        JButton mar4 ;
        JButton mie4 ;
        JButton jue4 ;
        JButton vie4 ;
    
 private class quitarMat implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==lun1 || e.getSource()==mie1){
                lun1.setText("Vacío");
                lun1.setBackground(Color.red);
                mie1.setText("Vacío");
                mie1.setBackground(Color.red);
                if(lun1.getText().equals("Vacío")&&mar1.getText().equals("Vacío")){
                    vie1.setText("Vacío");
                    vie1.setBackground(Color.red);
                }else{
                    vie1.setText(mar1.getText());
                }
                horario[--index]=null;
                
            }else if(e.getSource()==mar1 || e.getSource()==jue1){
                mar1.setText("Vacío");
                mar1.setBackground(Color.red);
                jue1.setText("Vacío");
                jue1.setBackground(Color.red);
                if(lun1.getText().equals("Vacío")&&mar1.getText().equals("Vacío")){
                    vie1.setText("Vacío");
                    vie1.setBackground(Color.red);
                }else{
                    vie1.setText(lun1.getText());
                }
                    horario[--index]=null;
            }else if(e.getSource()==lun2 || e.getSource()==mie2){
                lun2.setText("Vacío");
                lun2.setBackground(Color.red);
                mie2.setText("Vacío");
                mie2.setBackground(Color.red);
                if(lun2.getText().equals("Vacío")&&mar2.getText().equals("Vacío")){
                    vie2.setText("Vacío");
                    vie2.setBackground(Color.red);
                }else{
                    vie2.setText(mar2.getText());
                }
                    horario[--index]=null;
            }else if(e.getSource()==mar2 || e.getSource()==jue2){
                mar2.setText("Vacío");
                mar2.setBackground(Color.red);
                jue2.setText("Vacío");
                jue2.setBackground(Color.red);
                if(lun2.getText().equals("Vacío")&&mar2.getText().equals("Vacío")){
                    vie2.setText("Vacío");
                    vie2.setBackground(Color.red);
                }else{
                    vie2.setText(lun2.getText());
                }
                    horario[index--]=null;
            }else if(e.getSource()==lun3 || e.getSource()==mie3){
                lun3.setText("Vacío");
                lun3.setBackground(Color.red);
                mie3.setText("Vacío");
                mie3.setBackground(Color.red);
                if(lun3.getText().equals("Vacío")&&mar3.getText().equals("Vacío")){
                    vie3.setText("Vacío");
                    vie3.setBackground(Color.red);
                }else{
                    vie3.setText(mar3.getText());
                }
                    horario[--index]=null;
            }else if(e.getSource()==mar3 || e.getSource()==jue3){
                mar3.setText("Vacío");
                mar3.setBackground(Color.red);
                jue3.setText("Vacío");
                jue3.setBackground(Color.red);
                if(lun3.getText().equals("Vacío")&&mar3.getText().equals("Vacío")){
                    vie3.setText("Vacío");
                    vie3.setBackground(Color.red);
                }else{
                    vie3.setText(lun3.getText());
                }
                    horario[--index]=null;
            }else if(e.getSource()==lun4 || e.getSource()==mie4){
                lun4.setText("Vacío");
                lun4.setBackground(Color.red);
                mie4.setText("Vacío");
                mie4.setBackground(Color.red);
                if(lun4.getText().equals("Vacío")&&mar4.getText().equals("Vacío")){
                    vie4.setText("Vacío");
                    vie4.setBackground(Color.red);
                }else{
                    vie4.setText(mar4.getText());
                }
                    horario[--index]=null;
            }else if(e.getSource()==mar4 || e.getSource()==jue4){
                mar4.setText("Vacío");
                mar4.setBackground(Color.red);
                jue4.setText("Vacío");
                jue4.setBackground(Color.red);
                if(lun4.getText().equals("Vacío")&&mar4.getText().equals("Vacío")){
                    vie4.setText("Vacío");
                    vie4.setBackground(Color.red);
                }else{
                    vie4.setText(lun4.getText());
                }
                    horario[--index]=null;
            }
        }
     
 }   
    
    
    
private class ponerMat implements ActionListener{
    String hora,dia1,dia2,materia;
    boolean dia3;
    public ponerMat(String materia,String hora,String dia1,String dia2,
            boolean dia3){
        this.materia=materia;
        this.hora=hora;
        this.dia1=dia1;
        this.dia2=dia2;
        this.dia3=dia3;
    }
        @Override
        public void actionPerformed(ActionEvent e) {
            if(hora.equals("7:00-08:40") && dia1.equals("Lunes") ){
             if(lun1.getText().equals("Vacío") ){
                lun1.setText(materia); 
                lun1.setBackground(Color.green);
                mie1.setText(materia);
                mie1.setBackground(Color.green);
                if(dia3){
                    if(mar1.getText().equals("Vacío")){
                        vie1.setText(materia);
                        vie1.setBackground(Color.green);
                    }else{
                        vie1.setText(materia+"\n"+mar1.getText());
                        vie1.setBackground(Color.green);
                    }
                }
                horario[index++]=identificarHorario(lun1.getText());
             }else JOptionPane.showMessageDialog(null,"El horario esta ocupado"); 
            }else if(hora.equals("7:00-08:40") && dia1.equals("Martes") ){
                if(mar1.getText().equals("Vacío") ){
                mar1.setText(materia); 
                mar1.setBackground(Color.green);
                jue1.setText(materia);
                jue1.setBackground(Color.green);
                if(dia3){
                    if(lun1.getText().equals("Vacío")){
                        vie1.setText(materia);
                        vie1.setBackground(Color.green);
                    }else{
                        vie1.setText(materia+"\n"+lun1.getText());
                        vie1.setBackground(Color.green);
                    }
                }
                horario[index++]=identificarHorario(mar1.getText());
             }else JOptionPane.showMessageDialog(null,"El horario esta ocupado"); 
            }else if(hora.equals("8:45-10:25") && dia1.equals("Lunes") ){
                if(lun2.getText().equals("Vacío") ){
                lun2.setText(materia); 
                lun2.setBackground(Color.green);
                mie2.setText(materia);
                mie2.setBackground(Color.green);
                if(dia3){
                    if(mar2.getText().equals("Vacío")){
                        vie2.setText(materia);
                        vie2.setBackground(Color.green);
                    }else{
                        vie2.setText(materia+"\n"+mar2.getText());
                        vie2.setBackground(Color.green);
                    }
                }
                horario[index++]=identificarHorario(lun2.getText());
             }else JOptionPane.showMessageDialog(null,"El horario esta ocupado"); 
            }else if(hora.equals("8:45-10:25") && dia1.equals("Martes") ){
                if(mar2.getText().equals("Vacío") ){
                mar2.setText(materia); 
                mar2.setBackground(Color.green);
                jue2.setText(materia);
                jue2.setBackground(Color.green);
                if(dia3){
                    if(lun2.getText().equals("Vacío")){
                        vie2.setText(materia);
                        vie2.setBackground(Color.green);
                    }else{
                        vie2.setText(materia+"\n"+lun2.getText());
                        vie2.setBackground(Color.green);
                    }
                }
                horario[index++]=identificarHorario(mar2.getText());
             }else JOptionPane.showMessageDialog(null,"El horario esta ocupado"); 
            }else if(hora.equals("10:30-12:10") && dia1.equals("Lunes") ){
                if(lun3.getText().equals("Vacío") ){
                lun3.setText(materia); 
                lun3.setBackground(Color.green);
                mie3.setText(materia);
                mie3.setBackground(Color.green);
                if(dia3){
                    if(mar3.getText().equals("Vacío")){
                        vie3.setText(materia);
                        vie3.setBackground(Color.green);
                    }else{
                        vie3.setText(materia+"\n"+mar3.getText());
                        vie3.setBackground(Color.green);
                    }
                }
                horario[index++]=identificarHorario(lun3.getText());
             }else JOptionPane.showMessageDialog(null,"El horario esta ocupado"); 
            }else if(hora.equals("10:30-12:10") && dia1.equals("Martes") ){
                if(mar3.getText().equals("Vacío") ){
                mar3.setText(materia); 
                mar3.setBackground(Color.green);
                jue3.setText(materia);
                jue3.setBackground(Color.green);
                if(dia3){
                    if(lun3.getText().equals("Vacío")){
                        vie3.setText(materia);
                        vie3.setBackground(Color.green);
                    }else{
                        vie3.setText(materia+"\n"+lun3.getText());
                        vie3.setBackground(Color.green);
                    }
                }
                horario[index++]=identificarHorario(mar3.getText());
             }else JOptionPane.showMessageDialog(null,"El horario esta ocupado"); 
            }else if(hora.equals("12:15-13:55") && dia1.equals("Lunes") ){
                if(lun4.getText().equals("Vacío") ){
                lun4.setText(materia); 
                lun4.setBackground(Color.green);
                mie4.setText(materia);
                mie4.setBackground(Color.green);
                if(dia3){
                    if(mar4.getText().equals("Vacío")){
                        vie4.setText(materia);
                        vie4.setBackground(Color.green);
                    }else{
                        vie4.setText(materia+"\n"+mar4.getText());
                        vie4.setBackground(Color.green);
                    }
                }
                horario[index++]=identificarHorario(lun4.getText());
             }else JOptionPane.showMessageDialog(null,"El horario esta ocupado"); 
            }else if(hora.equals("12:15-13:55") && dia1.equals("Martes") ){
                if(mar4.getText().equals("Vacío") ){
                mar4.setText(materia); 
                mar4.setBackground(Color.green);
                jue4.setText(materia);
                jue4.setBackground(Color.green);
                if(dia3){
                    if(lun4.getText().equals("Vacío")){
                        vie4.setText(materia);
                        vie4.setBackground(Color.green);
                    }else{
                        vie4.setText(materia+"\n"+lun4.getText());
                        vie4.setBackground(Color.green);
                    }
                }
                horario[index++]=identificarHorario(mar4.getText());
             }else JOptionPane.showMessageDialog(null,"El horario esta ocupado"); 
            }

        }

    }
       
}
