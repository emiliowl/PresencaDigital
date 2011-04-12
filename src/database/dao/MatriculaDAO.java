/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package database.dao;

import beans.Matricula;
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
public class MatriculaDAO implements DataAccessObject{

    private PreparedStatement sql;
    private Connection conexao;
    private ArrayList<Matricula> matriculas = new ArrayList();
    private ResultSet rsMatriculas;
    private MateriaDAO materiaDAO = new MateriaDAO();
    private AlunoDAO alunoDAO = new AlunoDAO();

    public ArrayList verTodos() {

        try {

            matriculas = new ArrayList();
            conexao = new FabricaConexoes().getConexao();
            sql = conexao.prepareStatement("select * from tb_matricula_materia order by id_matricula");
            rsMatriculas = sql.executeQuery();

            while (rsMatriculas.next()) {
                this.matriculas.add(new Matricula(rsMatriculas.getInt("id_matricula"), materiaDAO.buscaPorPK(rsMatriculas.getInt("id_materia")),
                        alunoDAO.buscaPorPK(rsMatriculas.getInt("id_aluno")), rsMatriculas.getBoolean("status")));
            }
            finalizaConexao(sql, conexao);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return this.matriculas;

    }

    public Matricula buscaPorPK(Integer primaryKey) {

        Matricula matricula = null;

        try {

            conexao = new FabricaConexoes().getConexao();
            sql = conexao.prepareStatement("select * from tb_matricula_materia where id_matricula = ?");
            sql.setInt(1, primaryKey);
            rsMatriculas = sql.executeQuery();

            if (rsMatriculas.next()) {
                matricula = new Matricula(rsMatriculas.getInt("id_matricula"), materiaDAO.buscaPorPK(rsMatriculas.getInt("id_materia")),
                        alunoDAO.buscaPorPK(rsMatriculas.getInt("id_aluno")), rsMatriculas.getBoolean("status"));
            }
            finalizaConexao(sql, conexao);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return matricula;

    }

    public ArrayList buscaPorColuna(String coluna, String chave) {

        try {

            this.matriculas = new ArrayList();
            conexao = new FabricaConexoes().getConexao();
            sql = conexao.prepareStatement("select * from tb_matricula_materia where " + coluna + " = ?");
            sql.setString(1, chave);
            rsMatriculas = sql.executeQuery();

            while (rsMatriculas.next()) {
                this.matriculas.add(new Matricula(rsMatriculas.getInt("id_matricula"), materiaDAO.buscaPorPK(rsMatriculas.getInt("id_materia")),
                        alunoDAO.buscaPorPK(rsMatriculas.getInt("id_aluno")), rsMatriculas.getBoolean("status")));
            }
            finalizaConexao(sql, conexao);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return this.matriculas;

    }

    public Object inserir(Object o) {
        throw new UnsupportedOperationException("Nao suportado nesta versao, utilize o sistema PHP para esta operacao!");
    }

    public Object inserir(Object o, ByteArrayInputStream dadosImpressaoDigital, int tamanhoDadosImpressaoDigital) {
        throw new UnsupportedOperationException("Nao suportado nesta versao, utilize o sistema PHP para esta operacao!");
    }

    public void finalizaConexao() {
        throw new UnsupportedOperationException("Not supported yet");
    }

    public void finalizaConexao(PreparedStatement preparedStatement, Connection conexao) {
        try {
            preparedStatement.close();
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(MatriculaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }



}
