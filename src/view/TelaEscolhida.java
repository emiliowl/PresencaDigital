/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

/**
 *
 * @author Emilio
 */
public enum TelaEscolhida {

    CADASTROS(1),
    CADASTRO_ALUNO(2),
    CADASTRO_PROFESSOR(3),
    INICIA_AULA(4),
    CADASTRA_IMPRESSAO_ALUNO(5),
    CADASTRA_IMPRESSAO_PROFESSOR(6),
    AULA(7);
    
    private int opcao;

    TelaEscolhida(int opcao) {
        this.opcao = opcao;
    }

    public int getOpcao() {
        return opcao;
    }

    public void setOpcao(int opcao) {
        this.opcao = opcao;
    }

}
