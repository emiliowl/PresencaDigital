/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database.dao;

import beans.Aluno;
import beans.Materia;
import beans.Professor;
import database.dao.ProfessorDAO;
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
public class MateriaDAO implements DataAccessObject {

    private PreparedStatement sql;
    private Connection conexao;
    private ArrayList<Materia> materias = new ArrayList();
    private ResultSet rsMaterias;
    private ProfessorDAO professorDAO = new ProfessorDAO();

    public ArrayList verTodos() {

        try {

            materias = new ArrayList();
            conexao = new FabricaConexoes().getConexao();
            sql = conexao.prepareStatement("select * from tb_materias");
            rsMaterias = sql.executeQuery();

            while (rsMaterias.next()) {
                this.materias.add(new Materia(rsMaterias.getInt("id_materia"), rsMaterias.getString("nome"), rsMaterias.getString("carga_horaria"),
                        professorDAO.buscaPorPK(rsMaterias.getInt("id_professor")), rsMaterias.getInt("id_turma")));
            }
            finalizaConexao(sql, conexao);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return this.materias;

    }

    public Materia buscaPorPK(Integer primaryKey) {

        Materia materia = null;

        try {

            conexao = new FabricaConexoes().getConexao();
            sql = conexao.prepareStatement("select * from tb_materias where id_materia = ?");
            sql.setInt(1, primaryKey);
            rsMaterias = sql.executeQuery();

            if (rsMaterias.next()) {
                materia = new Materia(rsMaterias.getInt("id_materia"), rsMaterias.getString("nome"), rsMaterias.getString("carga_horaria"),
                        professorDAO.buscaPorPK(rsMaterias.getInt("id_professor")), rsMaterias.getInt("id_turma"));
            }
            finalizaConexao(sql, conexao);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return materia;

    }

    public ArrayList buscaPorColuna(String coluna, String chave) {

        try {

            this.materias = new ArrayList();
            conexao = new FabricaConexoes().getConexao();
            sql = conexao.prepareStatement("select * from tb_materias where " + coluna + " = ? order by nome");
            sql.setString(1, chave);
            rsMaterias = sql.executeQuery();
            
            while (rsMaterias.next()) {
                this.materias.add(new Materia(rsMaterias.getInt("id_materia"), rsMaterias.getString("nome"), rsMaterias.getString("carga_horaria"),
                        professorDAO.buscaPorPK(rsMaterias.getInt("id_professor")), rsMaterias.getInt("id_turma")));
            }
            finalizaConexao(sql, conexao);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return this.materias;

    }

    public ArrayList<Aluno> carregarAlunos(Materia materia) {

        ArrayList<Aluno> alunos = new ArrayList();
        AlunoDAO alunoDAO = new AlunoDAO();

        try {

            conexao = new FabricaConexoes().getConexao();
            sql = conexao.prepareStatement("select id_aluno from tb_matricula_materia where id_materia = ? and status != 0");
            sql.setInt(1, materia.getMateriaID());
            rsMaterias = sql.executeQuery();

            while (rsMaterias.next()) {
                alunos.add(alunoDAO.buscaPorPK(rsMaterias.getInt("id_aluno")));
            }

        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            finalizaConexao(sql, conexao);
        }
        return alunos;

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
            Logger.getLogger(MateriaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
