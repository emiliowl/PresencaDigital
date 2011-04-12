/*
 * Presenca Digital v1.0 - Leitor de Impressoes
 */

package database;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Emilio
 */
public class FabricaConexoes {

    private Connection conexao;

    //Construtor
    public FabricaConexoes() {
        try {
               //Carrega o driver ODBC
               Class.forName("com.mysql.jdbc.Driver");

               //Se conecta a base de dados
               conexao = DriverManager.getConnection("jdbc:mysql://localhost/presencadigitaldb","root", "");

           } catch (Exception e) {
               e.printStackTrace();
           }
    }

    //Métodos Getters
    public Connection getConexao() {
        return conexao;
    }

    //Métodos Setters
    public void setConexao(Connection conexao) {
        this.conexao = conexao;
    }
    
}
