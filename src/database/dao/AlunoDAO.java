/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package database.dao;

import beans.Aluno;
import java.sql.PreparedStatement;
import database.FabricaConexoes;
import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Emilio
 */
public class AlunoDAO implements DataAccessObject {

    private PreparedStatement sql;
    private Connection conexao;
    private ArrayList<Aluno> alunos = new ArrayList();
    private ResultSet rsAlunos;

    public ArrayList<Aluno> verTodos() {
        try {

            alunos = new ArrayList();
            conexao = new FabricaConexoes().getConexao();
            sql = conexao.prepareStatement("select * from tb_alunos");
            rsAlunos = sql.executeQuery();
          
            while(rsAlunos.next()) {
                this.alunos.add(new Aluno(rsAlunos.getString("nome"), rsAlunos.getString("cpf"), rsAlunos.getLong("rg"), rsAlunos.getString("rua"),
                        rsAlunos.getString("cidade"), rsAlunos.getString("estado"), rsAlunos.getString("telefone"),
                        rsAlunos.getBytes("impressaoDigital"), rsAlunos.getString("ra"), rsAlunos.getInt("id_aluno")));
            }
            finalizaConexao(sql, conexao);
            
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        return this.alunos;
    }

    public Aluno buscaPorPK(Integer primaryKey) {
        
        Aluno aluno = null;
        
        try {

            conexao = new FabricaConexoes().getConexao();
            sql = conexao.prepareStatement("select * from tb_alunos where id_aluno = ?");
            sql.setInt(1, primaryKey);
            rsAlunos = sql.executeQuery();

            if(rsAlunos.next()) {
                aluno = new Aluno(rsAlunos.getString("nome"), rsAlunos.getString("cpf"), rsAlunos.getLong("rg"), rsAlunos.getString("rua"),
                        rsAlunos.getString("cidade"), rsAlunos.getString("estado"), rsAlunos.getString("telefone"),
                        rsAlunos.getBytes("impressaoDigital"), rsAlunos.getString("ra"), rsAlunos.getInt("id_aluno"));
            }
            finalizaConexao(sql, conexao);

        } catch(SQLException ex) {
            ex.printStackTrace();
        }

        return aluno;
        
    }

    public ArrayList buscaPorColuna(String coluna, String chave) {
        try {

            if(!coluna.equals("CPF")) {
                chave = "%" + chave + "%";
                alunos = new ArrayList();
                conexao = new FabricaConexoes().getConexao();
                sql = conexao.prepareStatement("select * from tb_alunos where " + coluna + " LIKE ? order by nome");
            } else {
                alunos = new ArrayList();
                conexao = new FabricaConexoes().getConexao();
                sql = conexao.prepareStatement("select * from tb_alunos where " + coluna + "= ? order by nome");
            }
            sql.setString(1, chave);
            rsAlunos = sql.executeQuery();

            while(rsAlunos.next()) {
                this.alunos.add(new Aluno(rsAlunos.getString("nome"), rsAlunos.getString("cpf"), rsAlunos.getLong("rg"), rsAlunos.getString("rua"),
                        rsAlunos.getString("cidade"), rsAlunos.getString("estado"), rsAlunos.getString("telefone"),
                        rsAlunos.getBytes("impressaoDigital"), rsAlunos.getString("ra"), rsAlunos.getInt("id_aluno")));
            }
            finalizaConexao(sql, conexao);

        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        return this.alunos;
    }

    public Aluno inserir(Object o) {
        throw new UnsupportedOperationException("Operacao nao suportada por AlunoDAO, use: inserir(Object, ByteArrayInputStream, int)");
    }

    public Aluno inserir(Object al, ByteArrayInputStream dadosImpressaoDigital, int tamanhoDadosImpressaoDigital) {

        Aluno aluno = (Aluno)al;

        try {
            
            Aluno aluno1 = this.buscaPorPK(aluno.getAlunoID());
            System.out.println("Aluno antes: " + aluno1.getImpressaoDigital());
            conexao = new FabricaConexoes().getConexao();
            sql = conexao.prepareStatement("update tb_alunos set impressaoDigital = ? where id_aluno = ?");
            sql.setBinaryStream(1, dadosImpressaoDigital, tamanhoDadosImpressaoDigital);
            sql.setInt(2, aluno.getAlunoID());
            sql.execute();
            aluno = this.buscaPorPK(aluno.getAlunoID());
            System.out.println("Aluno depois: " + aluno.getImpressaoDigital());
            finalizaConexao(sql, conexao);

        } catch(SQLException ex) {
            ex.printStackTrace();
        }

        return aluno;
    }

    public void finalizaConexao() {
        throw new UnsupportedOperationException("Not supported yet");
    }

    public void finalizaConexao(PreparedStatement preparedStatement, Connection conexao) {
        try {
            preparedStatement.close();
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(AlunoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

}
