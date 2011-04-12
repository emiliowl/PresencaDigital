/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database.dao;

import beans.Professor;
import database.FabricaConexoes;
import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Emilio
 */
public class ProfessorDAO implements DataAccessObject {

    private PreparedStatement sql;
    private Connection conexao;
    private ArrayList<Professor> professores = new ArrayList();
    private ResultSet rsProfessores;

    public ArrayList<Professor> verTodos() {

        try {

            professores = new ArrayList();
            conexao = new FabricaConexoes().getConexao();
            sql = conexao.prepareStatement("select * from tb_professores order by nome");
            rsProfessores = sql.executeQuery();

            while (rsProfessores.next()) {
                this.professores.add(new Professor(rsProfessores.getString("nome"), rsProfessores.getString("cpf"), rsProfessores.getLong("rg"), rsProfessores.getString("rua"),
                        rsProfessores.getString("cidade"), rsProfessores.getString("estado"), rsProfessores.getString("telefone"),
                        rsProfessores.getBytes("impressaoDigital"), rsProfessores.getInt("id_professor")));
            }
            finalizaConexao(sql, conexao);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return this.professores;
    }

    public Professor buscaPorPK(Integer primaryKey) {

        Professor professor = null;

        try {

            conexao = new FabricaConexoes().getConexao();
            sql = conexao.prepareStatement("select * from tb_professores where id_professor = ?");
            sql.setInt(1, primaryKey);
            rsProfessores = sql.executeQuery();

            if (rsProfessores.next()) {
                professor = new Professor(rsProfessores.getString("nome"), rsProfessores.getString("cpf"), rsProfessores.getLong("rg"), rsProfessores.getString("rua"),
                        rsProfessores.getString("cidade"), rsProfessores.getString("estado"), rsProfessores.getString("telefone"),
                        rsProfessores.getBytes("impressaoDigital"), rsProfessores.getInt("id_professor"));
            }
            finalizaConexao(sql, conexao);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return professor;

    }

    public ArrayList buscaPorColuna(String coluna, String chave) {

        try {

            if(!coluna.equals("CPF")) {
                chave = "%" + chave + "%";
                professores = new ArrayList();
                conexao = new FabricaConexoes().getConexao();
                sql = conexao.prepareStatement("select * from tb_professores where " + coluna + " like ? order by nome");
            } else {
                professores = new ArrayList();
                conexao = new FabricaConexoes().getConexao();
                sql = conexao.prepareStatement("select * from tb_professores where " + coluna + " = ? order by nome");
            }
            sql.setString(1, chave);
            rsProfessores = sql.executeQuery();

            while (rsProfessores.next()) {
                this.professores.add(new Professor(rsProfessores.getString("nome"), rsProfessores.getString("cpf"), rsProfessores.getLong("rg"), rsProfessores.getString("rua"),
                        rsProfessores.getString("cidade"), rsProfessores.getString("estado"), rsProfessores.getString("telefone"),
                        rsProfessores.getBytes("impressaoDigital"), rsProfessores.getInt("id_professor")));
            }
            finalizaConexao(sql, conexao);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return this.professores;
    }

    public Professor inserir(Object o) {
        throw new UnsupportedOperationException("Operacao nao suportada para professor use: inserir(Object, ByteArrayInputStream, int)");
    }

    public Professor inserir(Object pro, ByteArrayInputStream dadosImpressaoDigital, int tamanhoDadosImpressaoDigital) {

        Professor professor = (Professor) pro;

        try {

            Professor professor1 = this.buscaPorPK(professor.getProfessorID());
            System.out.println("Professor antes: " + professor1.getImpressaoDigital());
            conexao = new FabricaConexoes().getConexao();
            sql = conexao.prepareStatement("update tb_professores set impressaoDigital = ? where id_professor = ?");
            sql.setBinaryStream(1, dadosImpressaoDigital, tamanhoDadosImpressaoDigital);
            sql.setInt(2, professor.getProfessorID());
            sql.execute();
            professor = this.buscaPorPK(professor.getProfessorID());
            System.out.println("Professor depois: " + professor.getImpressaoDigital());
            finalizaConexao(sql, conexao);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return professor;
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
