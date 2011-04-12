/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JPanelCadastraImpressaoProfessor.java
 *
 * Created on 12/10/2009, 10:38:42
 */

package view;

import beans.Professor;
import controlador.ControladorImpressaoDigital;
import controlador.ControladorJanelas;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
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
public class JPanelCadastraImpressaoProfessor extends javax.swing.JPanel implements JPanelImpressao{

    private ControladorJanelas controladorJanelas;
    //objeto usado para realizar todas as operacoes relacionadas ao Fingerprint-SDK
    private ControladorImpressaoDigital procedimentosSDK;
    //Painel para mostrar a impressao digital
    private JPanel fingerprintViewPanel = null;
    //Imagen da impressao atual
    private BufferedImage fingerprintImage = null;
    Professor professor;

    /** Creates new form JPanelCadastraImpressaoAluno */
    public JPanelCadastraImpressaoProfessor(ControladorJanelas controladorJanelas) {
        this.controladorJanelas = controladorJanelas;
        this.procedimentosSDK = this.controladorJanelas.getUi().registrarPainel(this);
        //inicializar();
        initComponents();
        jPainelContainer.add(criarPainelImpressao());
        /*Inicializa a captura de impressoes*/
        //procedimentosSDK.inicializarCaptura();
    }

    public JPanelCadastraImpressaoProfessor(Professor professor, ControladorJanelas controladorJanelas) {
        this.controladorJanelas = controladorJanelas;
        this.procedimentosSDK = this.controladorJanelas.getUi().registrarPainel(this);
        //inicializar();
        initComponents();
        jPainelContainer.add(criarPainelImpressao());
        /*Inicializa a captura de impressoes*/
        //procedimentosSDK.inicializarCaptura();
        this.professor = professor;
        this.carregaProfessor();
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

    /**
     * Método utilizado para mostrar a imagem da impressao
     * no painel correspondente.
     */
    public void showImage(BufferedImage image) {
        //Utiliza o imageProducer para criar uma imagem da impressao digital
        fingerprintImage = image;
        //Se desenha a nova imagem
        repaint();
    }

    /**
     * Realiza a inicializacao da classe que contem os procedimentos principais.
     **/
    /**
    public void inicializar() {
        String grFingerNativeDirectory = new File("C:\\Windows\\System32").getAbsolutePath();
        ControladorImpressaoDigital.setFingerprintSDKNativeDirectory(grFingerNativeDirectory);
        //Cria uma instancia de ControladorImpressaoDigital
        this.procedimentosSDK = new ControladorImpressaoDigital(this);
    }
    */
    
    public void carregaProfessor() {
        this.jLabel7.setText(this.professor.getNome());
        this.jLabel8.setText(this.professor.getCpf());
        this.jLabel9.setText(this.professor.getRg().toString());
        this.jLabel10.setText(this.professor.getCidade());
        this.jLabel11.setText(this.professor.getEstado());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel6 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPainelContainer = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        jLabel6.setFont(new java.awt.Font("Verdana", 0, 24)); // NOI18N
        jLabel6.setText("CADASTRAR IMPRESSAO DO PROFESSOR");
        jLabel6.setName("jLabel6"); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, new java.awt.Color(204, 204, 204), new java.awt.Color(153, 153, 153)));
        jPanel1.setName("jPanel1"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 161, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 166, Short.MAX_VALUE)
        );

        jPainelContainer.setBackground(new java.awt.Color(204, 204, 204));
        jPainelContainer.setBorder(javax.swing.BorderFactory.createTitledBorder("Impressao Digital"));
        jPainelContainer.setName("jPainelContainer"); // NOI18N
        jPainelContainer.setLayout(new java.awt.BorderLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados do Aluno"));
        jPanel2.setName("jPanel2"); // NOI18N

        jLabel1.setText("NOME");
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText("CPF");
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setText("RG");
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel4.setText("CIDADE");
        jLabel4.setName("jLabel4"); // NOI18N

        jLabel5.setText("ESTADO");
        jLabel5.setName("jLabel5"); // NOI18N

        jLabel7.setText("jLabel7");
        jLabel7.setName("jLabel7"); // NOI18N

        jLabel8.setText("jLabel8");
        jLabel8.setName("jLabel8"); // NOI18N

        jLabel9.setText("jLabel9");
        jLabel9.setName("jLabel9"); // NOI18N

        jLabel10.setText("jLabel10");
        jLabel10.setName("jLabel10"); // NOI18N

        jLabel11.setText("jLabel11");
        jLabel11.setName("jLabel11"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10)))
                            .addComponent(jLabel8)
                            .addComponent(jLabel7))))
                .addContainerGap(261, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel11))
                .addContainerGap())
        );

        jButton1.setText("SALVAR");
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 558, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPainelContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPainelContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        /**
         * Aqui sera salvo na base de dados a impressao digital colhida do professor
         */
        procedimentosSDK.salvarImpressao(professor);
        String texto = "Impressão digital de " + professor.getNome() + " inserida com sucesso!";
        JOptionPane.showMessageDialog(null, texto, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
}//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPainelContainer;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables

}
