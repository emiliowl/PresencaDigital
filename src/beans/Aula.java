/*
 * Presenca Digital v1.0 - Leitor de Impressoes
 */

package beans;

import database.dao.AulaDAO;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Emilio
 */
public class Aula {

	private Integer aulaID;
	private Materia materia;
	private Date horaInicio;
	private Date horaFim;
    private Date data;
	private ArrayList<Aluno> alunos;

    //Construtor
    public Aula(Integer aulaID, Materia materia, Date horaInicio, Date horaFim, Date data) {
		this.aulaID = aulaID;
        this.materia = materia;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.data = data;
        this.alunos = new ArrayList();
    }

    //Métodos Getters
    public ArrayList<Aluno> getAlunos() {
        return alunos;
    }

    public Integer getAulaID() {
        return aulaID;
    }

    public Date getData() {
        return data;
    }

    public Date getHoraFim() {
        return horaFim;
    }
    
    public Date getHoraInicio() {
        return horaInicio;
    }

    public Materia getMateria() {
        return materia;
    }

    //métodos Setters
    public void setAlunos(ArrayList<Aluno> alunos) {
        this.alunos = alunos;
    }

    public void setAulaID(Integer aulaID) {
        this.aulaID = aulaID;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setHoraFim(Date horaFim) {
        this.horaFim = horaFim;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    @Override
    public String toString() {
        String retorno = "Aula de: " + materia.getNome() + "\n Professor: " + materia.getProfessor().getNome();
        retorno += "\n Aula iniciada as: " + this.getHoraInicio().getHours() + ":" + this.getHoraInicio().getMinutes() + ":" + this.getHoraInicio().getSeconds();
        retorno += " e finalizada as: " + this.getHoraFim().getHours() + ":" + this.getHoraFim().getMinutes() + ":" + this.getHoraFim().getSeconds();
        retorno += ", do dia: " + this.getData().toString();
        retorno += "\n Alunos Presentes: \n";
        for(int i = 0; i < alunos.size(); i++) {
            if (alunos.get(i) != null) {
                retorno += " " + alunos.get(i).getNome() + "\n";
            }
        }
        return retorno;
    }

    public String toString(AulaDAO aulaDAO) {
        String retorno = "Aula de: " + materia.getNome() + "\n Professor: " + materia.getProfessor().getNome();
        retorno += "\n Aula iniciada as: " + this.getHoraInicio().getHours() + ":" + this.getHoraInicio().getMinutes() + ":" + this.getHoraInicio().getSeconds();
        retorno += " e finalizada as: " + this.getHoraFim().getHours() + ":" + this.getHoraFim().getMinutes() + ":" + this.getHoraFim().getSeconds();
        retorno += ", do dia: " + this.getData().toString();
        retorno += "\n Alunos Presentes: \n";
        for(int i = 0; i < alunos.size(); i++) {
            if (alunos.get(i) != null) {
                retorno += " " + alunos.get(i).getNome() + ", horario de entrada: " + aulaDAO.capturaHorarioEntrada(this, alunos.get(i));
            }
        }
        return retorno;
    }

}
