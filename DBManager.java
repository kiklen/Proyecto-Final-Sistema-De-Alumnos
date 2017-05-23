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

/**
 * Clase para hacer la consultas y traer los datos desde la BD
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class DBManager {
   private static final String URL = "jdbc:mysql://localhost/alumnossl";//Direccion y nombre de la BD
   private static final String USERNAME = "root";//Nombre de usuario de la BD
   private static final String PASSWORD = "root";//Contraseña de la conexion de la BD

   private Connection connection; //Conexión que hace la consultas
   private PreparedStatement selectAlumno; //Argumento para la consulta de la tabla de alumnos
   private PreparedStatement selectMateria; //Argumento para la consulta de la tabla de materias
   private PreparedStatement selectGrupo; // Argumento para la consulta de Grupos
   private PreparedStatement selectCarrera;//Argumento para la consulta de carreras
   private PreparedStatement datosHorario, materiasPosibles;//Argumentos para la creacion de la lista de materias posibles de un alumno y la creación de su posible horario
   private static final String dbclassname = "com.mysql.jdbc.Driver";//Llamada al controlador de la BD
   
   public DBManager(){
       
       //Se prepara el controlador para la BD
       try{
           Class.forName(dbclassname);
       }catch(Exception e){}
       
       //Se inicializa la conexión y se preparan los argumentos de consultas básicas
       try{
       connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
       
       selectAlumno = connection.prepareStatement("select * from alumno");
       selectMateria= connection.prepareStatement("select * from materias");
       selectGrupo  = connection.prepareStatement("select * from grupo");
       selectCarrera= connection.prepareStatement("select * from carrera");
       
       }catch(Exception e){}
   }
   
   //Consulta que devuelve los datos necesarios para una de las materias del horario
   public Horario crearHorario(Materia m){
       Horario h= null;
       ResultSet tabla= null;
       try{
           //Se hace la preparación del argumento de consulta
           datosHorario= connection.prepareStatement("select * from materias "
                   + "join grupo where materias.claveMateria = '" + m.getClaveMateria()+"'"+
                   "and grupo.claveMateria = '"+ m.getClaveMateria()+"'");
           //Se hace la consulta
           tabla = datosHorario.executeQuery();
           
           //Se llenan los campos del Horario mediante su constructor
           while(tabla.next()){
               h= new Horario(tabla.getInt("claveGrupo"),tabla.getString("claveMateria"),
               tabla.getString("nombre"),tabla.getString("Hlunes"),tabla.getString("Hmartes"),
               tabla.getString("Hmiercoles"),tabla.getString("Hjueves"),tabla.getString("Hviernes"),
               tabla.getString("creditos"),tabla.getString("semestre"),tabla.getString("maestro"),
               tabla.getString("aula"));
           }
       }catch(Exception e){}
       
       //Se cierra la consulta
       finally{
           try{
           tabla.close();
           }catch(SQLException ex){
               close();
           }
       }
       return h;
   }
   
   //Método para la creacion de la lista de materias posibles de un alumno de acuerdo a su semestre
   public ArrayList<Materia> materiasPosibles(Alumno a){
       ArrayList <Materia> materia= new ArrayList();
   
       ResultSet tabla= null;
       try{
           materiasPosibles= connection.prepareStatement("select * from materias "
                   + "where semestre =" + a.getSemestre()+ 
                   " and claveCarrera = '" +a.getCarrera()+"'");
           tabla = materiasPosibles.executeQuery();
           while(tabla.next()){
               materia.add(  new Materia(tabla.getString("nombre"),
                       tabla.getString("claveMateria"),
               tabla.getString("claveCarrera"),tabla.getInt("creditos"),
                       tabla.getInt("semestre")));
           }
       }catch(Exception e){}
       finally{
           try{
           tabla.close();
           }catch(SQLException ex){
               close();
           }
       }
       return materia;
   }
   
   //Kétodo para obtener la lista de alumnos disponibles
   public ArrayList<Alumno> llenarAlumnos(){
       ArrayList <Alumno> alumnos = new ArrayList();
       ResultSet tabla = null;
       try{
           tabla = selectAlumno.executeQuery();
           while(tabla.next()){
               alumnos.add(new Alumno(tabla.getString("nombre"),
                tabla.getString("apPaterno"),tabla.getString("apMaterno"),
                tabla.getString("numControl"),tabla.getString("claveCarrera"),
                tabla.getInt("semestre"),tabla.getString("turno"),
                       tabla.getString("campus")));
           }
       }catch(Exception e){}
       finally{
           try{
           tabla.close();
           }catch(SQLException ex){
               close();
           }
       }
       return alumnos;
   }
   
   //Método que llena la lista total de materias
   public ArrayList<Materia> llenarMaterias(){
       ArrayList <Materia> materias = new ArrayList();
       ResultSet tabla = null;
       try{
           tabla = selectMateria.executeQuery();
           while(tabla.next()){
               materias.add(new Materia(tabla.getString("nombre"),
               tabla.getString("claveMateria"),tabla.getString("claveCarrera"),
               tabla.getInt("creditos"),tabla.getInt("semestre")));
           }
       }catch(Exception e){}
       finally{
           try{
           tabla.close();
           }catch(SQLException ex){
               close();
           }
       }
       return materias;
   }
   
   public ArrayList<Grupo> llenarGrupos(){
       ArrayList <Grupo> grupos = new ArrayList();
       ResultSet tabla = null;
       try{
           tabla = selectGrupo.executeQuery();
           while(tabla.next()){
               grupos.add(new Grupo(tabla.getInt("claveGrupo"),
               tabla.getString("claveMateria"),tabla.getString("aula"),
               tabla.getString("maestro"),tabla.getString("Hlunes"),
               tabla.getString("Hmartes"),tabla.getString("Hmiercoles"),
               tabla.getString("Hjueves"),tabla.getString("Hviernes")));
           }
       }catch(Exception e){}
       finally{
           try{
           tabla.close();
           }catch(SQLException ex){
               close();
           }
       }
       return grupos;
   }
   
   public ArrayList<Carrera> llenarCarrera(){
       ArrayList <Carrera> carreras = new ArrayList();
       ResultSet tabla = null;
       try{
           tabla = selectCarrera.executeQuery();
           while(tabla.next()){
               carreras.add(new Carrera(tabla.getString("NombreCarrera"),
               tabla.getString("claveCarrera")));
           }
       }catch(Exception e){}
       finally{
           try{
           tabla.close();
           }catch(SQLException ex){
               close();
           }
       }
       return carreras;
   }
   
   public void close()
   {
      try 
      {
         connection.close();
      } 
      catch (SQLException sqlException)
      {
         sqlException.printStackTrace();
      } 
   } 
   
} 
