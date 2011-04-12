/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JPanelAula.java
 *
 * Created on 15/10/2009, 23:05:42
 */

package view;

import beans.Aluno;
import beans.Aula;
import beans.Materia;
import controlador.ControladorImpressaoDigital;
import controlador.ControladorJanelas;
import database.dao.AulaDAO;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Emilio
 */
public class JPanelAula extends javax.swing.JPanel implements JPanelImpressao {

    private ControladorJanelas controladorJanelas;
    //objeto usado para realizar todas as operacoes relacionadas ao Fingerprint-SDK
    private ControladorImpressaoDigital procedimentosSDK;
    //Painel para mostrar a impressao digital
    private JPanel fingerprintViewPanel = null;
    //Imagen da impressao atual
    private BufferedImage fingerprintImage = null;
    boolean controleImpressao = true;
    ArrayList<Aluno> alunosMatriculados = new ArrayList();
    ArrayList<Aluno> alunosPresentes = new ArrayList();
    Aula aula;
    AulaDAO aulaDAO = new AulaDAO();
    //variavel utilizada para guardar um aluno quando este for encontrado pelo metodo verificaAlunos
    Aluno alunoEncontrado;

     /** Creates new form JPanelCadastraImpressaoAluno */
    public JPanelAula(ControladorJanelas controladorJanelas) {
        this.controladorJanelas = controladorJanelas;
        this.procedimentosSDK = this.controladorJanelas.getUi().registrarPainel(this);
        initComponents();
        jPainelContainer.add(criarPainelImpressao());
    }

    public JPanelAula(ControladorJanelas controladorJanelas, Aula aula) {
        this.controladorJanelas = controladorJanelas;
        this.procedimentosSDK = this.controladorJanelas.getUi().registrarPainel(this);
        initComponents();
        jPainelContainer.add(criarPainelImpressao());
        this.aula = aula;
        this.jLabel2.setText(aula.getMateria().getNome());
        this.jLabel4.setText(aula.getMateria().getProfessor().getNome());
        this.jLabel6.setText(aula.getHoraInicio().getHours() + ":" + aula.getHoraInicio().getMinutes() + ":" + aula.getHoraInicio().getSeconds());
        this.jLabel8.setText(aula.getData().getDate() + "/" + (aula.getData().getMonth()+1) + "/" + (aula.getData().getYear()+1900));
        carregaAlunos(aula.getMateria());
        if(aula.getAulaID() != 0) {
            this.carregaAlunosPresentes(aula);
        }
        
    }

    //Carrega os alunos que podem assistir esta aula
    public void carregaAlunos(Materia materia) {
        this.alunosMatriculados = materia.getAlunos();
        Aluno[] listData = new Aluno[materia.getAlunos().size()];
        for (int i = 0; i < materia.getAlunos().size(); i++) {
            listData[i] = materia.getAlunos().get(i);
        }
        this.jList1.setListData(listData);
    }

    //Carrega os alunos que estao na aula
    public void carregaAlunosPresentes(Aula aula) {
        this.alunosPresentes = aula.getAlunos();
        Aluno[] listData = new Aluno[aula.getAlunos().size()];
        for (int i = 0; i < aula.getAlunos().size(); i++) {
            listData[i] = aula.getAlunos().get(i);
        }
        this.jList2.setListData(listData);
    }

    /**
     * Cria o painel que conterá a imagem da impressao digital
     */
    public JComponent criarPainelImpressao() {
        //Cria um painel novo para mostrar a impressao
        this.fingerprintViewPanel = new JPanel() {

            //Se sobreescreve o método paintComponent
            //para habilitar a mostra da imagem da impressao
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                //Se nao houver uma imagem para ser mostrada
                if (fingerprintImage != null) {
                    //Calcula o tamanho e posicao da imagem para ser pintada
                    //o tamanho eh ajustado para que ocupe todo o tamanho do painel
                    Insets insets = getInsets();
                    int transX = insets.left;
                    int transY = insets.top;
                    int width = getWidth() - getInsets().right - getInsets().left;
                    int height = getHeight() - getInsets().bottom - getInsets().top;

                    //Se desenha a imagem
                    g.drawImage(fingerprintImage, transX, transY, width, height, null);
                }

            }
        };

        //Se agrega uma borda ao redor do painel
        this.fingerprintViewPanel.setBorder(new CompoundBorder(
                new EmptyBorder(2, 2, 2, 2),
                new BevelBorder(BevelBorder.LOWERED)));

        //se nao existe o painel da impressao nao devolve nada...
        if (this.fingerprintViewPanel == null) {
            return null;
        } else { // do contrario o devolve

            return this.fingerprintViewPanel;
        }

    }
    /** Método utilizado para mostrar as semelhancas encontradas
     *
     */
    public void mostrarSemelhancas(BufferedImage image) {
        //Utiliza o imageProducer para criar uma imagem da impressao digital
        fingerprintImage = image;
        //Se desenha a nova imagem
        repaint();
    }

    /**
     * Método utilizado para mostrar a imagem da impressao
     * no painel correspondente.
     */
    public void showImage(BufferedImage image) {
        controleImpressao = !controleImpressao;
        //Utiliza o imageProducer para criar uma imagem da impressao digital
        fingerprintImage = image;
        //Se desenha a nova imagem
        repaint();
        //os usuarios que passarem o dedo no leitor serao reconhecidos
        if(this.aula.getAulaID() == 0 && controleImpressao && procedimentosSDK.verificarImpressao(this.aula.getMateria().getProfessor())) {
            //se o usuario for o professor e a aula nao tiver sido iniciada, entao sera executado este codigo, iniciando a aula
            this.aula = aulaDAO.inserir(this.aula);
            JOptionPane.showMessageDialog((JComponent)procedimentosSDK.getUi(), "Aula iniciada com sucesso!");
        } else if (controleImpressao && procedimentosSDK.verificarImpressao(this.aula.getMateria().getProfessor())) {
            //se o usuario for o professor e a aula tiver sido iniciada, entao sera encerrada!
            switch(JOptionPane.showConfirmDialog((JComponent)procedimentosSDK.getUi(), "Finalizar aula?")) {
                case 0:
                    this.aula = aulaDAO.finalizarAula(this.aula);
                    JOptionPane.showMessageDialog((JComponent)procedimentosSDK.getUi(), "Aula de " + this.aula.getMateria().getNome() + " finalizada com sucesso!" + "\nHora de Inicio: " + this.aula.getHoraInicio().toString() + "\nHora de Fim: " + this.aula.getHoraFim().toString(), "Aula finalizada com sucesso!", JOptionPane.PLAIN_MESSAGE);
                    break;
                case 1:
                case 2:
            }
        } else if (this.aula.getAulaID() != 0 && controleImpressao && verificaAlunos()) {
            //se o usuario for um aluno matriculado e a aula tiver sido iniciada, entao inicia sua presenca
            //verifica se o aluno esta em alguma aula
            Aula aulaAluno = aulaDAO.verificarPresencaAulaIniciada(this.alunoEncontrado);
            if(aulaAluno == null) {
                //se o aluno nao estiver em nenhuma aula inicia sua presenca
                aulaDAO.aferirPresenca(this.alunoEncontrado, this.aula);
                alunoEncontrado = null;
                aulaDAO.carregaAlunosPresentes(this.aula);
                this.carregaAlunosPresentes(this.aula);
            } else {
                JOptionPane.showMessageDialog(controladorJanelas.getUi(), "Aluno ja esta em aula: \nMateria: " + aulaAluno.getMateria().getNome() +
                        "\nProfessor: " + aulaAluno.getMateria().getProfessor().getNome() + "\nData: " + aulaAluno.getData().toString() +
                        "\nHorario: " + aulaDAO.capturaHorarioEntrada(aulaAluno, alunoEncontrado).toString());
            }
        } else if(controleImpressao) {
            JOptionPane.showMessageDialog(this, "Impressao digital nao confere", "ERRO", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Realiza uma busca nos alunosMatriculados e retorna true se encontrar nesta lista a digital adquirida
     */
    public boolean verificaAlunos() {
        boolean encontrei = false;
        for(int i = 0; i < alunosMatriculados.size(); i++) {
            if(procedimentosSDK.verificarImpressao(alunosMatriculados.get(i))) {
                alunoEncontrado = alunosMatriculados.get(i);
                encontrei = true;
            }
        }
        return encontrei;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPainelContainer = new javax.swing.JPanel();

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 24));
        jLabel1.setText("Aula de");
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setFont(new java.awt.Font("Verdana", 0, 24));
        jLabel2.setText("jLabel2");
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setText("PROFESSOR");
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel4.setText("jLabel4");
        jLabel4.setName("jLabel4"); // NOI18N

        jLabel5.setText("INICIADA ÀS");
        jLabel5.setName("jLabel5"); // NOI18N

        jLabel6.setText("jLabel6");
        jLabel6.setName("jLabel6"); // NOI18N

        jLabel7.setText("DATA");
        jLabel7.setName("jLabel7"); // NOI18N

        jLabel8.setText("jLabel8");
        jLabel8.setName("jLabel8"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList1.setName("jList1"); // NOI18N
        jScrollPane1.setViewportView(jList1);

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jList2.setName("jList2"); // NOI18N
        jScrollPane2.setViewportView(jList2);

        jLabel9.setText("Alunos Matriculados");
        jLabel9.setName("jLabel9"); // NOI18N

        jLabel10.setText("Alunos Presentes");
        jLabel10.setName("jLabel10"); // NOI18N

        jPainelContainer.setBackground(new java.awt.Color(204, 204, 204));
        jPainelContainer.setBorder(javax.swing.BorderFactory.createTitledBorder("Impressao Digital"));
        jPainelContainer.setName("jPainelContainer"); // NOI18N
        jPainelContainer.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addGap(41, 41, 41)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPainelContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2))
                    .addComponent(jLabel7)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel6))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(48, 48, 48))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPainelContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JPanel jPainelContainer;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables

}