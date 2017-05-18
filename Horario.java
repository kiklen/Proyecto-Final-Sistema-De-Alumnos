package sistemadealumnos;


public class Horario {
    
    private final int grupo;
    private final String clvMateria,materia;
    private String hL,hM,hMM,hJ,hV;
    private String creditos, semestre,maestro,aula;
    

    public Horario(int clave, String clvMateria, String materia, String hL,
            String hM, String hMM, String hJ, String hV, String creditos, 
            String semestre, String maestro,String aula){
        this.grupo = clave;
        this.clvMateria = clvMateria;
        this.materia = materia;
        this.hL = hL;
        this.hM = hM;
        this.hMM = hMM;
        this.hJ = hJ;
        this.hV = hV;
        this.creditos = creditos;
        this.semestre = semestre;
        this.maestro= maestro;
        this.aula= aula;
    }
    
    public void setAula(String aula){
        this.aula=aula;
    }
    
    public void setMaestro(String maestro){
        this.maestro = maestro;
    }
    
    public void sethL(String hL) {
        this.hL = hL;
    }

    public void sethM(String hM) {
        this.hM = hM;
    }

    public void sethMM(String hMM) {
        this.hMM = hMM;
    }

    public void sethJ(String hJ) {
        this.hJ = hJ;
    }

    public void sethV(String hV) {
        this.hV = hV;
    }

    public void setCreditos(String creditos) {
        this.creditos = creditos;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }
    
    
    public int getGrupo() {
        return grupo;
    }

    public String getclvMateria() {
        return clvMateria;
    }

    public String getMateria() {
        return materia;
    }

    public String gethL() {
        return hL;
    }

    public String gethM() {
        return hM;
    }

    public String gethMM() {
        return hMM;
    }

    public String gethJ() {
        return hJ;
    }

    public String gethV() {
        return hV;
    }

    public String getCreditos() {
        return creditos;
    }

    public String getSemestre() {
        return semestre;
    }
    
    public String getMaestro(){
        return maestro;
    }
    
    public String getAula(){
        return aula;
    }
    
}
