/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package database.dao;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 *
 * @author Emilio
 */
public interface DataAccessObject {

    public ArrayList verTodos();
    public Object buscaPorPK(Integer primaryKey);
    public ArrayList buscaPorColuna(String coluna, String chave);
    public Object inserir(Object o);
    public Object inserir(Object o, ByteArrayInputStream dadosImpressaoDigital, int tamanhoDadosImpressaoDigital);
    public void finalizaConexao();

}
