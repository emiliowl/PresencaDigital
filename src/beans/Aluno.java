/*
 * Presenca Digital v1.0 - Leitor de Impressoes
 */

package beans;

//import com.griaule.grfingerjava.FingerprintImage;

import java.io.ByteArrayInputStream;

/**
 *
 * @author Emilio
 */
public class Aluno extends Pessoa {

    private String ra;
    private Integer alunoID;
    
	//Construtor
	public Aluno(String nome, String cpf, Long rg, String rua, String cidade, String estado, String telefone, byte[] impressaoDigital, String ra, Integer alunoID) {

        super(nome, cpf, rg, rua, cidade, estado, telefone, impressaoDigital);
		this.ra = ra;
        this.alunoID = alunoID;
	}

	//Métodos Getters
    public Integer getAlunoID() {
        return alunoID;
    }

    public String getRa() {
        return ra;
    }

	//Métodos Setters
    public void setAlunoID(Integer alunoID) {
        this.alunoID = alunoID;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    @Override
    public String toString() {
        return this.getNome() + " " + this.getRa();
    }

}