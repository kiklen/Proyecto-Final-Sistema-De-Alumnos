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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class DBManager {
   private static final String URL = "jdbc:mysql://localhost/alumnosl";
   private static final String USERNAME = "root";
   private static final String PASSWORD = "root";

   private Connection connection; 
   private PreparedStatement selectAlumno; 
   private PreparedStatement selectMateria; 
   private PreparedStatement selectGrupo; 
   private PreparedStatement selectCarrera;
   private PreparedStatement datosHorario, materiasPosibles;
   private static final String dbclassname = "com.mysql.jdbc.Driver";
   
   public DBManager(){
       try{
           Class.forName(dbclassname);
       }catch(Exception e){}
       try{
       connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
       
       selectAlumno = connection.prepareStatement("select * from alumno");
       selectMateria= connection.prepareStatement("select * from materias");
       selectGrupo= connection.prepareStatement(  "select * from grupo");
       selectCarrera= connection.prepareStatement("select * from carrera");
       
       }catch(Exception e){}
   }
   
   public Horario crearHorario(Materia m){
       Horario h= null;
       ResultSet tabla= null;
       try{
           datosHorario= connection.prepareStatement("select * from materias "
                   + "join grupo where materias.claveMateria = '" + m.getClaveMateria()+"'"+
                   "and grupo.claveMateria = '"+ m.getClaveMateria()+"'");
           tabla = datosHorario.executeQuery();
           while(tabla.next()){
               h= new Horario(tabla.getInt("claveGrupo"),tabla.getString("claveMateria"),
               tabla.getString("nombre"),tabla.getString("Hlunes"),tabla.getString("Hmartes"),
               tabla.getString("Hmiercoles"),tabla.getString("Hjueves"),tabla.getString("Hviernes"),
               tabla.getString("creditos"),tabla.getString("semestre"),tabla.getString("maestro"),
               tabla.getString("aula"));
           }
       }catch(Exception e){}
       finally{
           try{
           tabla.close();
           }catch(SQLException ex){
               close();
           }
       }
       return h;
   }
   
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
