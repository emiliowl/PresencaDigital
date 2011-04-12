/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import beans.Aluno;
import beans.Pessoa;
import beans.Professor;
import com.griaule.grfingerjava.FingerprintImage;
import com.griaule.grfingerjava.GrFingerJava;
import com.griaule.grfingerjava.GrFingerJavaException;
import com.griaule.grfingerjava.IFingerEventListener;
import com.griaule.grfingerjava.IImageEventListener;
import com.griaule.grfingerjava.IStatusEventListener;
import com.griaule.grfingerjava.MatchingContext;
import com.griaule.grfingerjava.Template;
import database.dao.AlunoDAO;
import database.dao.ProfessorDAO;
import java.io.ByteArrayInputStream;
import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import view.JPanelAula;
import view.JPanelImpressao;

/**
 *
 * @author Emilio
 */
public class ControladorImpressaoDigital implements IStatusEventListener, IImageEventListener, IFingerEventListener {

    /**Contexto utilizado para a captura, extracao e coincidencia de impressoes digitais */
    private MatchingContext fingerprintSDK;
    /**Interface de usuario onde se mostra a imagem da impressao*/
    private JPanelImpressao ui;
    /** Indica se o template deve ser extraída automaticamente*/
    private boolean autoExtract = true;
    /** Contem localmente os dados da impressao digital capturada */
    private ByteArrayInputStream fingerprintData;
    /**Contem a longitude do dado da impressao digital*/
    private int fingerprintDataLength;
    /** A imagen da última impressão digital capturada. */
    private FingerprintImage fingerprint;
    /** O template da última imagem de impressao digital capturada */
    public Template template;

    /**
     *
     * @param ui
     * Contrutor da Classe utilitaria de impressao digital, deve receber um painel para que possa trabalhar
     */
    public ControladorImpressaoDigital(JPanelImpressao ui) {
        this.ui = ui;
    }

    /**
     *
     * @param idSensor
     * Este evento é gerado cada vez que conectamos um novo sensor, e serve para identificá-lo.
     */
    public void onSensorPlug(String idSensor) {
        try {
            GrFingerJava.startCapture(idSensor, this, this);
        } catch (GrFingerJavaException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param idSensor
     * Este evento é gerado cada vez que desconectamos um novo sensor e serve para identificá-lo e excluí-lo da lista de leitores válidos.
     */
    public void onSensorUnplug(String idSensor) {
        try {
            GrFingerJava.stopCapture(idSensor);
        } catch (GrFingerJavaException e) {
            e.printStackTrace();
        }
    }

    /**
     * Este método e chamado cada vez que a imagem de uma impressao tiver sido capturada.
     */
    public void onImageAcquired(String idSensor, FingerprintImage impressaoDigital) {
        //Armazena a imagem da impressao
        this.fingerprint = impressaoDigital;

        //Mostra a imagem obtida
        ui.showImage(impressaoDigital);

        //chama o metodo que obtem a planta
        extract();
    }

    public void onFingerDown(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onFingerUp(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @param diretorio
     * Estabelece o diretorio onde ficam as bibliotecas nativas do SDK da Griaule
     */
    public static void setFingerprintSDKNativeDirectory(String diretorio) {
        File directory = new File(diretorio);

        try {
            GrFingerJava.setNativeLibrariesDirectory(directory);
            GrFingerJava.setLicenseDirectory(directory);
        } catch (GrFingerJavaException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inicializa o Fingerprint SDK e habilita a captura de impressoes.
     */
    public void inicializarCaptura() {
        try {
            fingerprintSDK = new MatchingContext();
            //Inicializa a captura de impressao digital.
            GrFingerJava.initializeCapture(this);
        } catch (Exception e) {
            //Se ocorrer um erro, encerra a aplicacao.
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Extrai a planta da imagem da impressao atual.
     */
    public void extract() {

        try {
            //Extrai a planta da imagem.
            template = fingerprintSDK.extract(fingerprint);
            //Mostra a planta na imagem
            ui.showImage(GrFingerJava.getBiometricImage(template, fingerprint));
        } catch (GrFingerJavaException e) {
            e.printStackTrace();
        }

    }

    //PROCEDIMENTOS DA BASE DE DADOS
   /*
     * Guarda os dados da impressao digital atual na base de dados
     */
    public void salvarImpressao(Pessoa pessoa) {

        //Obtem os dados do template da impressao atual
        fingerprintData = new ByteArrayInputStream(template.getData());
        fingerprintDataLength = template.getData().length;
        if (pessoa instanceof Aluno) {
            AlunoDAO alunoDAO = new AlunoDAO();
            alunoDAO.inserir((Aluno) pessoa, fingerprintData, fingerprintDataLength);
        } else {
            ProfessorDAO professorDAO = new ProfessorDAO();
            professorDAO.inserir((Professor) pessoa, fingerprintData, fingerprintDataLength);
        }
    }

    /**
     *
     * @param pessoa
     * verifica se uma impressao digital e a mesma de uma pessoa especifica na base de dados
     */
    public boolean verificarImpressao(Pessoa pessoa) {
        try {
            
            //Obtem a planta correspondente a pessoa indicada
            Template referenceTemplate = new Template(pessoa.getImpressaoDigital());

            //compara as plantas (atual vs base de dados)
            boolean coincidem = fingerprintSDK.verify(template, referenceTemplate);

            //Se correspondem, desenha o mapa de correspondencia e retorna true
            if (coincidem) {
                ((JPanelAula)ui).mostrarSemelhancas(GrFingerJava.getBiometricImage(template, fingerprint, fingerprintSDK));
                return true;
            } else {
                //Se nao corresponder retorna false
                return false;
            }
        } catch (GrFingerJavaException e) {
            e.printStackTrace();
        }
        return false;
    }

    //devolve o painel principal
    public JPanelImpressao getUi() {
        return ui;
    }

    //cadastra um painel para a classe de conexao com o leitor
    public void setUi(JPanelImpressao ui) {
        this.ui = ui;
    }

}
