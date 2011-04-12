/*
 * Presenca Digital v1.0 - Leitor de Impressoes
 */
package beans;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Emilio
 */
public class Materia {

    private Integer materiaID;
    private String nome;
    private String cargaHoraria;
    private Professor professor;
    private Integer turmaID;
    private ArrayList<Aluno> alunos;

    public Materia(Integer materiaID, String nome, String cargaHoraria, Professor professor, Integer turmaID) {
        this.materiaID = materiaID;
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.professor = professor;
        this.turmaID = turmaID;
    }

    //Métodos Getters
    public ArrayList<Aluno> getAlunos() {
        return alunos;
    }

    public String getCargaHoraria() {
        return cargaHoraria;
    }

    public Integer getMateriaID() {
        return materiaID;
    }

    public String getNome() {
        return nome;
    }

    public Professor getProfessor() {
        return professor;
    }

    public Integer getTurmaID() {
        return turmaID;
    }

    //Métodos Setters
    public void setAlunos(ArrayList<Aluno> alunos) {
        this.alunos = alunos;
    }

    public void setCargaHoraria(String cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public void setMateriaID(Integer materiaID) {
        this.materiaID = materiaID;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public void setTurmaID(Integer turmaID) {
        this.turmaID = turmaID;
    }

    @Override
    public String toString() {
        return this.getNome();
    }
}
