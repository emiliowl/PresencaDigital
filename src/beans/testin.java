package beans;

import database.dao.AulaDAO;
import database.dao.MateriaDAO;
import java.util.Date;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Emilio
 */
public class testin {

    public static void main(String[] args) {

        Date data = new Date();
        AulaDAO aulaDAO = new AulaDAO();
        MateriaDAO materiaDAO = new MateriaDAO();
        Aula aula = new Aula(0, materiaDAO.buscaPorPK(16), data, data, data);
        aula  = aulaDAO.inserir(aula);
        System.out.println("id da aula inserida: " + aula.getAulaID());
        aula = aulaDAO.finalizarAula(aula);
        System.out.println("id da aula finailizada:\n" + aula.getAulaID() + "\nAula encerrada as: " + aula.getHoraFim().toString());
    }

}
