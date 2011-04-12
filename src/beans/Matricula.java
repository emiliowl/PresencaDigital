/*
 *  Presenca Digital v1.0 - Leitor de Impressoes
 */

package beans;

/**
 *
 * @author Emilio
 */
public class Matricula {

    private Integer matriculaID;
    private Materia materia;
    private Aluno aluno;
    private boolean status;

    //Construtor
    public Matricula(Integer matriculaID, Materia materia, Aluno aluno, boolean status) {
        this.matriculaID = matriculaID;
        this.materia = materia;
        this.aluno = aluno;
        this.status = status;
    }

    //métodos getters
    public Aluno getAluno() {
        return aluno;
    }

    public Materia getMateria() {
        return materia;
    }

    public Integer getMatriculaID() {
        return matriculaID;
    }

    public boolean isStatus() {
        return status;
    }

    //métodos setters
    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public void setMatriculaID(Integer matriculaID) {
        this.matriculaID = matriculaID;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    //toString
    @Override
    public String toString() {
        return "Matricula nº: " + this.getMatriculaID() + "\n Aluno: " + this.getAluno().getNome() + "\n Matéria: " + this.getMateria().getNome() 
                + "\n Professor: " + this.getMateria().getProfessor().getNome() + (this.isStatus()?"\n Status: Destrancado":"\n Status: trancado");
    }

}