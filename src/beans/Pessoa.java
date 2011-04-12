/*
 * Presenca digital v1.0 - Leitor de impressoes
 */

package beans;

/**
 *
 * @author Emilio
 */
public abstract class Pessoa {

    private String nome;
    private String cpf;
    private Long rg;
    private String rua;
    private String cidade;
    private String estado;
    private String telefone;
    private byte[] impressaoDigital;

    //Construtor
    public Pessoa(String nome, String cpf, Long rg, String rua, String cidade, String estado, String telefone, byte[] impressaoDigital) {
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.rua = rua;
        this.cidade = cidade;
        this.estado = estado;
        this.telefone = telefone;
        this.impressaoDigital = impressaoDigital;
    }

    //Métodos Getters
    public String getCidade() {
        return cidade;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEstado() {
        return estado;
    }

    public byte[] getImpressaoDigital() {
        return impressaoDigital;
    }

    public String getNome() {
        return nome;
    }

    public Long getRg() {
        return rg;
    }

    public String getRua() {
        return rua;
    }

    public String getTelefone() {
        return telefone;
    }

    //Métodos Setters

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setImpressaoDigital(byte[] impressaoDigital) {
        this.impressaoDigital = impressaoDigital;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setRg(Long rg) {
        this.rg = rg;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "Sou pessoa: " + this.getNome() + " " + this.getCpf();
    }
}
