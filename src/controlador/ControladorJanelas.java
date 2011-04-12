/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador;

import beans.Aula;
import beans.Pessoa;
import view.TelaEscolhida;
import view.TelaPrincipal;

/**
 *
 * @author Emilio
 */
public class ControladorJanelas {

    private TelaPrincipal ui;

    public ControladorJanelas(TelaPrincipal ui) {
        this.ui = ui;
    }

    public void navegar(TelaEscolhida telaEscolhida) {
        ui.atualizarTela(telaEscolhida);
    }

    public void navegar(TelaEscolhida telaEscolhida, Pessoa pessoa) {
        ui.atualizarTela(telaEscolhida, pessoa);
    }

    public void navegar(TelaEscolhida telaEscolhida, Aula aula) {
        ui.atualizarTela(telaEscolhida, aula);
    }

    public TelaPrincipal getUi() {
        return ui;
    }
    
}
