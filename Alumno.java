package sistemadealumnos;

public class Alumno {
    private final String nombre, Apellido1, Apellido2;
    private final String numeroDeControl;
    private String carrera, turno, campus;
    private int semestre;

    public Alumno(String nombre, String Apellido1, String Apellido2, 
            String numeroDeControl, String carrera, int semestre, String turno, 
            String campus){
        this.nombre = nombre;
        this.Apellido1 = Apellido1;
        this.Apellido2 = Apellido2;
        this.numeroDeControl = numeroDeControl;
        this.carrera = carrera;
        this.semestre = semestre;
        this.turno = turno;
        this.campus = campus;
    }

    public String getTurno() {
        return turno;
    }

    public String getCampus() {
        return campus;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }
    
    
    public String getNombre() {
        return nombre;
    }
    
    public String getNumeroDeControl(){
        return numeroDeControl;
    }

    public String getApellido1() {
        return Apellido1;
    }

    public String getApellido2() {
        return Apellido2;
    }

    public String getCarrera() {
        return carrera;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }
    
    
    
}
