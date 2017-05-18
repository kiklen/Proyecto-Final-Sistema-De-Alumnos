package sistemadealumnos;


public class Grupo {
    private int claveGrupo;
    private String claveMateria, aula, maestro, Hl,Hm,Hmm,Hj,Hv;

    public Grupo(int claveGrupo, String claveMateria, String aula,
            String maestro, String Hl, String Hm, String Hmm, String Hj,
            String Hv) {
        this.claveGrupo = claveGrupo;
        this.claveMateria = claveMateria;
        this.aula = aula;
        this.maestro = maestro;
        this.Hl = Hl;
        this.Hm = Hm;
        this.Hmm = Hmm;
        this.Hj = Hj;
        this.Hv = Hv;
    }

    public int getClaveGrupo() {
        return claveGrupo;
    }

    public String getClaveMateria() {
        return claveMateria;
    }

    public String getAula() {
        return aula;
    }

    public String getMaestro() {
        return maestro;
    }

    public String getHl() {
        return Hl;
    }

    public String getHm() {
        return Hm;
    }

    public String getHmm() {
        return Hmm;
    }

    public String getHj() {
        return Hj;
    }

    public String getHv() {
        return Hv;
    }

    public void setClaveGrupo(int claveGrupo) {
        this.claveGrupo = claveGrupo;
    }

    public void setClaveMateria(String claveMateria) {
        this.claveMateria = claveMateria;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public void setMaestro(String maestro) {
        this.maestro = maestro;
    }

    public void setHl(String Hl) {
        this.Hl = Hl;
    }

    public void setHm(String Hm) {
        this.Hm = Hm;
    }

    public void setHmm(String Hmm) {
        this.Hmm = Hmm;
    }

    public void setHj(String Hj) {
        this.Hj = Hj;
    }

    public void setHv(String Hv) {
        this.Hv = Hv;
    }
    
}
