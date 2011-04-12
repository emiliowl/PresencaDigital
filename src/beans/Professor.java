/*
 * Presenca Digital v1.0 - Leitor de Impressoes
 */

package beans;

/**
 *
 * @author Emilio
 */
public class Professor extends Pessoa {

	private Integer professorID;

	//Construtor
	public Professor(String nome, String cpf, Long rg, String rua, String cidade, String estado, String telefone, byte[] impressaoDigital, Integer professorID) {
		super(nome, cpf, rg, rua, cidade, estado, telefone, impressaoDigital);
		this.professorID = professorID;
	}

    //Métodos Gettters
    public Integer getProfessorID() {
        return professorID;
    }

    //métodos Setters
    public void setProfessorID(Integer professorID) {
        this.professorID = professorID;
    }

    @Override
    public String toString() {
        return this.getNome();
    }

}
