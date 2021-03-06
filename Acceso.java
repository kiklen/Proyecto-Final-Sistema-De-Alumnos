
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


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;


public class Acceso extends JFrame{
    private JButton acceder;
    private JTextField numControl;
    private JLabel nombre, bienvenida, fondo, instruccion;
    private DBManager manager;
    private ArrayList<Alumno> alumnos;
    private int indice;
    private CrearHorario horario;
    private frame fra;
    private Icon imagen;
    private JPanel panel;
    private String estilo1, estilo2, estilo3;
            
    public Acceso(){
        super ("Sistema de control de Alumnos");
        
        //Se aplica el tema Nimbus 
        try{
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }catch(Exception e){
        e.printStackTrace();
        }
        
        //Código HTML para darle formato a las letras de los textos
        estilo1= "<html><font size = '9' face = 'Harlow Solid Italic'; color = '#C5D300'>";
        estilo2= "<html><font size = '5' face = 'Algerian'; color = '#D8E9D6'>";
        estilo3= "<html><font size = '4' face = 'Lucida Calligraphy'; color = 'Yellow'>";
        
        //se inicializa el panel principal que conriene los componentes del acceso
        panel = new JPanel();
        panel.setLayout(null);
        
        //Se establece la imagen de fondo
        imagen= new ImageIcon(getClass().getResource("fondo.jpg"));
        fondo = new JLabel(imagen);
        fondo.setBounds(0, 0, 350, 350);
        
        //Se inicializa y prepara la siguiente ventana
        horario= new CrearHorario();
        
        //Se inicializa el boton para acceder al sistema mediante la validacion
        //de un numero de control
        acceder= new JButton("Acceder");
        acceder.setBounds(130, 200, 80, 30);
        
        //Se crea el evento escucha del boton que abrirá la nueva ventana en caso
        //de encontrar el numero de control en la BD
        acceder.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(e.getSource()==acceder){
                if (consultarAlumno()){
                    bienvenida.setText(estilo1+"Bienvenido "
                            +alumnos.get(indice).getNombre());
                    bienvenida.setVisible(true);
                    fra = new frame(numControl.getText());
                    fra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    fra.setVisible(true);
                    JOptionPane.showMessageDialog(null,"Selecciona tus Materias"
                            + alumnos.get(indice).getNombre());
                    setVisible(false);
                }else{
                    bienvenida.setText(estilo3+"No se encontro tu numero de control");
                    bienvenida.setBounds(50, 150, 300, 30);
                    bienvenida.setVisible(true);
                    
                }
                }
            }
        });
        
        //Se inicializa la lista de alumnos para la verificacion del numero capturado
        manager = new DBManager();
        alumnos = manager.llenarAlumnos();
        
        //Aquí es donde se ingresa el número de control
        numControl= new JTextField();
        numControl.setBounds(50,110,250,35);
        
        //Esta etiqueta da la bienvenida si el alumno fue encontrado o 
        //manda un mensaje de no aprobación en caso contrario
        nombre=new JLabel(estilo2+"Bienvenido al sistema de control de Alumnos");
        nombre.setBounds(50,10,300,50);
        
        //Inicialmente esta etiqueta no es visible
        bienvenida = new JLabel();
        bienvenida.setVisible(false);
        
        
        instruccion = new JLabel(estilo3+"Ingrese el numero de control");
        instruccion.setBounds(50, 80, 300, 30);
        
        //Se agragan los componentes al panel
         panel.add(nombre);
         panel.add(instruccion);
         panel.add(numControl);
         panel.add(acceder);
         panel.add(bienvenida);
         panel.add(fondo);
         
        //Se agrega el panel a la ventana
         add(panel);
    }
    
    //Método para hacer la validación del ingreso al sistema
    public boolean consultarAlumno(){
        String s = "";
        boolean b=false;
        for (int i = 0; i < alumnos.size(); i++) {
            s=alumnos.get(i).getNumeroDeControl();
            if(s.equals(numControl.getText())){
                b= true;
                indice=i;
                break;
            }
            System.out.println("no se encontro");
        }
        return b;
    }
    
    public static void main(String[] args) {
        Acceso acceso = new Acceso();
        acceso.setSize(350,300 );
        acceso.setVisible(true);
        acceso.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
