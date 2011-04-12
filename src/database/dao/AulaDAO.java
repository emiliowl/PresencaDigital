/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package database.dao;

import beans.Aluno;
import beans.Aula;
import beans.Professor;
import database.FabricaConexoes;
import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Emilio
 */
public class AulaDAO implements DataAccessObject {

    private PreparedStatement sql;
    private Connection conexao;
    private ArrayList<Aula> aulas = new ArrayList();
    private ResultSet rsAulas;
    private AlunoDAO alunoDAO = new AlunoDAO();
    private MateriaDAO materiaDAO = new MateriaDAO();
    private Date[] datas = {new Date(), new Date(), new Date()};

    public ArrayList verTodos() {

        try {

            aulas = new ArrayList();
            conexao = new FabricaConexoes().getConexao();
            sql = conexao.prepareStatement("select * from tb_aulas");
            rsAulas = sql.executeQuery();

            while (rsAulas.next()) {
                this.aulas.add(new Aula(rsAulas.getInt("id_aula"), materiaDAO.buscaPorPK(rsAulas.getInt("id_materia")),
                        rsAulas.getTime("hora_inicio"),rsAulas.getTime("hora_fim"), rsAulas.getDate("data")));
            }
            for(int i = 0; i < this.aulas.size(); i++) {
                carregaAlunosPresentes(this.aulas.get(i));
            }
            finalizaConexao(sql, conexao);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return this.aulas;

    }

    public Aula buscaPorPK(Integer primaryKey) {

        Aula aula = null;

        try {

            conexao = new FabricaConexoes().getConexao();
            sql = conexao.prepareStatement("select * from tb_aulas where id_aula = ?");
            sql.setInt(1, primaryKey);
            rsAulas = sql.executeQuery();

            if (rsAulas.next()) {
                aula = new Aula(rsAulas.getInt("id_aula"), materiaDAO.buscaPorPK(rsAulas.getInt("id_materia")),
                        rsAulas.getTime("hora_inicio"),rsAulas.getTime("hora_fim"), rsAulas.getDate("data"));
            }
            if(aula != null) {
                carregaAlunosPresentes(aula);
            }
            finalizaConexao(sql, conexao);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return aula;

    }

    public ArrayList buscaPorColuna(String coluna, String chave) {

        try {

            this.aulas = new ArrayList();
            conexao = new FabricaConexoes().getConexao();
            sql = conexao.prepareStatement("select * from tb_aulas where " + coluna + " = ?");
            sql.setString(1, chave);
            rsAulas = sql.executeQuery();

            while (rsAulas.next()) {
                this.aulas.add(new Aula(rsAulas.getInt("id_aula"), materiaDAO.buscaPorPK(rsAulas.getInt("id_materia")),
                        rsAulas.getTime("hora_inicio"),rsAulas.getTime("hora_fim"), rsAulas.getDate("data")));
            }
            for(int i = 0; i < this.aulas.size(); i++) {
                carregaAlunosPresentes(this.aulas.get(i));
            }
            finalizaConexao(sql, conexao);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return this.aulas;

    }

    public Aula inserir(Object novaAula) {
        
        Aula aula = (Aula)novaAula;

        try {

            conexao = new FabricaConexoes().getConexao();
            sql = conexao.prepareStatement("insert into tb_aulas(id_aula, id_materia, hora_inicio, hora_fim, data) values (NULL, ?, ?, '00:00:00', ?)");
            sql.setInt(1, aula.getMateria().getMateriaID());
            sql.setTime(2, obterHoraSQL(aula.getHoraInicio()));
            sql.setDate(3, obterDataSQL(aula.getData()));
            sql.execute();
            ResultSet rsChaves = sql.getGeneratedKeys();
            if(rsChaves.next()) {
                aula = this.buscaPorPK(rsChaves.getInt(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            finalizaConexao(sql, conexao);
        }
        return aula;
    }

    public Aula finalizarAula(Aula aula) {
        try {
            conexao = new FabricaConexoes().getConexao();
            sql = conexao.prepareStatement("update tb_aulas set hora_fim = ? where id_aula = ?");
            sql.setTime(1, obterHoraSQL(new Date()));
            sql.setInt(2, aula.getAulaID());
            sql.executeUpdate();
            aula = buscaPorPK(aula.getAulaID());
        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            finalizaConexao(sql, conexao);
        }
        return aula;
    }

    public Object inserir(Object o, ByteArrayInputStream dadosImpressaoDigital, int tamanhoDadosImpressaoDigital) {
        throw new UnsupportedOperationException("Not supported yet");
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

    //métodos para trabalhar com date e SQL
    public Time obterHoraSQL(Date data) {
        Time hora = new Time(data.getHours(), data.getMinutes(), data.getSeconds());
        return hora;
    }

    public java.sql.Date obterDataSQL(java.util.Date data) {
        java.sql.Date dataSQL = new java.sql.Date(data.getYear(), data.getMonth(), data.getDate());
        return dataSQL;
    }

    //métodos para trabalhar com professores e alunos
    /**
     * Este método verifica se o professor passado possui alguma aula iniciada em curso
     * @param professor
     * @return aula
     */
    public Aula verificarAulaIniciada(Professor professor) {
        Aula aulaEmCurso = null;
        ArrayList<Aula> aulasEmCurso = new ArrayList();
        try {
            this.conexao = new FabricaConexoes().getConexao();
            this.sql = this.conexao.prepareStatement("select * from tb_aulas where hora_fim = '00:00:00'");
            this.rsAulas = this.sql.executeQuery();
            while(rsAulas.next()) {
                aulasEmCurso.add(new Aula(rsAulas.getInt("id_aula"), materiaDAO.buscaPorPK(rsAulas.getInt("id_materia")),
                        rsAulas.getTime("hora_inicio"),rsAulas.getTime("hora_fim"), rsAulas.getDate("data")));
                for(int i = 0; i < aulasEmCurso.size(); i++) {
                    if(aulasEmCurso.get(i).getMateria().getProfessor().getProfessorID() == professor.getProfessorID()) {
                        aulaEmCurso = aulasEmCurso.get(i);
                        this.carregaAlunosPresentes(aulaEmCurso);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return aulaEmCurso;
    }
    
    /**
     * Este método verifica se o aluno passado possui presenca em alguma aula iniciada em curso
     * @param aluno
     * @return aula
     */
    public Aula verificarPresencaAulaIniciada(Aluno aluno) {
        Aula aulaEmCurso = null;
        ArrayList<Aula> aulasEmCurso = new ArrayList();
        try {
            this.conexao = new FabricaConexoes().getConexao();
            this.sql = this.conexao.prepareStatement("select * from tb_aulas where hora_fim = '00:00:00'");
            this.rsAulas = this.sql.executeQuery();
            while(rsAulas.next()) {
                aulasEmCurso.add(new Aula(rsAulas.getInt("id_aula"), materiaDAO.buscaPorPK(rsAulas.getInt("id_materia")),
                        rsAulas.getTime("hora_inicio"),rsAulas.getTime("hora_fim"), rsAulas.getDate("data")));
            }
            for(int i = 0; i < aulasEmCurso.size(); i++) {
                if(this.verificarPresenca(aulasEmCurso.get(i), aluno) != null) {
                    aulaEmCurso = aulasEmCurso.get(i);
                    this.carregaAlunosPresentes(aulaEmCurso);
                }
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            finalizaConexao(sql, conexao);
        }
        return aulaEmCurso;
    }

    //metodo auxiliar para verificarPresencaAulaIniciada
    private Aula verificarPresenca(Aula aula, Aluno aluno) {
        Aula alunoAula = null;
        try {
            this.sql = this.conexao.prepareStatement("select * from tb_presencas where id_aluno = ? and id_aula = ?");
            this.sql.setInt(1, aluno.getAlunoID());
            this.sql.setInt(2, aula.getAulaID());
            ResultSet rsPresenca = this.sql.executeQuery();
            if(rsPresenca.next()) {
                alunoAula = this.buscaPorPK(rsPresenca.getInt("id_aula"));
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        return alunoAula;
    }

    //métodos para trabalhar com alunos
    public void carregaAlunosPresentes(Aula aula) {
        PreparedStatement sqlPresenca = null;
        try {
            this.conexao = new FabricaConexoes().getConexao();
            int aulaID = aula.getAulaID();
            ResultSet rsPresenca;
            sqlPresenca = conexao.prepareStatement("select * from tb_presencas where id_aula = ?");
            sqlPresenca.setInt(1, aulaID);
            rsPresenca = sqlPresenca.executeQuery();
            aula.setAlunos(new ArrayList());
            while(rsPresenca.next()) {
                aula.getAlunos().add(alunoDAO.buscaPorPK(rsPresenca.getInt("id_aluno")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            this.finalizaConexao(sqlPresenca, conexao);
        }

    }

    public void aferirPresenca(Aluno aluno, Aula aula) {
        PreparedStatement sqlPresenca = null;
        try {
            this.conexao = new FabricaConexoes().getConexao();
            sqlPresenca = conexao.prepareStatement("insert into tb_presencas(id_aluno, id_aula, hora_entrada) values (?, ?, ?)");
            sqlPresenca.setInt(1, aluno.getAlunoID());
            sqlPresenca.setInt(2, aula.getAulaID());
            sqlPresenca.setTime(3, obterHoraSQL(new Date()));
            sqlPresenca.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            finalizaConexao(sqlPresenca, conexao);
        }
    }

    public Date capturaHorarioEntrada(Aula aula, Aluno aluno) {

        Date horaEntrada = null;

        try {
            conexao = new FabricaConexoes().getConexao();
            int aulaID = aula.getAulaID();
            int alunoID = aluno.getAlunoID();
            ResultSet rsPresenca;
            PreparedStatement sqlPresenca = conexao.prepareStatement("select * from tb_presencas where id_aula = ? and id_aluno = ?");

            sqlPresenca.setInt(1, aulaID);
            sqlPresenca.setInt(2, alunoID);
            rsPresenca = sqlPresenca.executeQuery();
            if(rsPresenca.next()) {
                horaEntrada = rsPresenca.getTime("hora_entrada");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return horaEntrada;

    }

}
