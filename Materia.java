

package sistemadealumnos;

public class Materia {
    private String nombre,claveMateria, claveCarrera;
    private int creditos;
    private int semestre;

    public Materia(String nombre, String claveMateria, String claveCarrera, 
        int creditos, int semestre) {
        this.nombre = nombre;
        this.claveMateria = claveMateria;
        this.claveCarrera = claveCarrera;
        this.creditos = creditos;
        this.semestre = semestre;
    }

    public String getNombre() {
        return nombre;
    }

    public String getClaveMateria() {
        return claveMateria;
    }

    public String getClaveCarrera() {
        return claveCarrera;
    }

    public int getCreditos() {
        return creditos;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setClaveMateria(String claveMateria) {
        this.claveMateria = claveMateria;
    }

    public void setClaveCarrera(String claveCarrera) {
        this.claveCarrera = claveCarrera;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }
    
    
}
