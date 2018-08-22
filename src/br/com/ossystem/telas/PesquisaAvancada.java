/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ossystem.telas;

import java.sql.*;
import br.com.ossystem.dao.ModuloConexao;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author wesley
 */
public class PesquisaAvancada extends javax.swing.JDialog {

    //a linha abaixo faz a conexao desta caixa de dialogo com a jframe telaos
    public static TelaOs telaos;

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form caixadialogo
     */
    //alterando para teste
    /*public caixadialogo(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }*/
    //no metodo abaixo foi alterado para que a conversassão com a tela os ocorresse com sucesso
    //no caso foi adicionado TelaOs no livar de jav.awt.alguma coisa - a mudança se faz necessário para que RecebePesquisaAvancada não fique com erro
    //o erro se da porque o metodo espera um retorno de uma jframe e estamos enviando de uma caixa de dialogo, por isso a alteração de parametros
    public PesquisaAvancada(TelaOs parent, boolean modal) {
        //super(parent, modal);
        this.telaos = parent;
        this.setModal(modal);
        initComponents();
        setLocationRelativeTo(this);
        conexao = ModuloConexao.conector();
    }

    public String SetaCamposDialog() {
        String a = txtNumeroOSCaixaDialogo.getText();
        //telaos.pegaDeLa();
        return a;

    }

    //pesquisa pela jtext do nome do cliente
    private void PesquisarClienteAvancadoCaixaDialogo() {
        String sql = "select O.ordemServico,C.nomeCliente,O.equipamento,O.defeito,O.servico,O.valor"
                + " from tbOrdemServico as O inner join tbClientes as C on (O.idCliente=C.idCliente) where C.nomeCliente like ?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtProcuraAvancadaNome.getText() + "%");
            rs = pst.executeQuery();

            tblDadosClienteProcuraAvancada.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
        }
    }

    //pesquisa pelo numero da ordem
    private void PesquisarClienteJtextOs() {
        String sql = "select O.ordemServico,C.nomeCliente,O.equipamento,O.defeito,O.servico,O.valor"
                + " from tbOrdemServico as O inner join tbClientes as C on (O.idCliente=C.idCliente) where O.ordemServico like ?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtNumeroOSCaixaDialogo.getText() + "%");
            rs = pst.executeQuery();

            tblDadosClienteProcuraAvancada.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
        }
    }

    private void PesquisarClienteNomeEquipamento() {
        String sql = "select O.ordemServico,C.nomeCliente,O.equipamento,O.defeito,O.servico,O.valor"
                + " from tbOrdemServico as O inner join tbClientes as C on (O.idCliente=C.idCliente) where O.equipamento like ?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtNomeEquipamento.getText() + "%");
            rs = pst.executeQuery();

            tblDadosClienteProcuraAvancada.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
        }
    }

    private void SetarCampos() {
        int setar = tblDadosClienteProcuraAvancada.getSelectedRow();
        txtNumeroOSCaixaDialogo.setText(tblDadosClienteProcuraAvancada.getModel().getValueAt(setar, 0).toString());
        txtProcuraAvancadaNome.setText(tblDadosClienteProcuraAvancada.getModel().getValueAt(setar, 1).toString());
        txtNomeEquipamento.setText(tblDadosClienteProcuraAvancada.getModel().getValueAt(setar, 2).toString());

    }

    private void limparCamposPesquisaAvancada() {
        txtNumeroOSCaixaDialogo.setText(null);
        txtNomeEquipamento.setText(null);
        txtProcuraAvancadaNome.setText(null);
        ((DefaultTableModel) tblDadosClienteProcuraAvancada.getModel()).setRowCount(0);//limpa a tabela
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnOkProcura = new javax.swing.JButton();
        txtNumeroOSCaixaDialogo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnCancelarProcura = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtProcuraAvancadaNome = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDadosClienteProcuraAvancada = new javax.swing.JTable();
        txtNomeEquipamento = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnLimpaCamposPesquisaAvancada = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(750, 357));

        btnOkProcura.setText("OK");
        btnOkProcura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkProcuraActionPerformed(evt);
            }
        });

        txtNumeroOSCaixaDialogo.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
                txtNumeroOSCaixaDialogoAncestorMoved(evt);
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
                txtNumeroOSCaixaDialogoAncestorRemoved(evt);
            }
        });
        txtNumeroOSCaixaDialogo.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtNumeroOSCaixaDialogoInputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        txtNumeroOSCaixaDialogo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumeroOSCaixaDialogoKeyTyped(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNumeroOSCaixaDialogoKeyReleased(evt);
            }
        });

        jLabel1.setText("Nº OS");

        btnCancelarProcura.setText("Cancelar");
        btnCancelarProcura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarProcuraActionPerformed(evt);
            }
        });

        jLabel2.setText("Pesquisar equipamento");

        txtProcuraAvancadaNome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtProcuraAvancadaNomeMouseClicked(evt);
            }
        });
        txtProcuraAvancadaNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtProcuraAvancadaNomeKeyTyped(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtProcuraAvancadaNomeKeyReleased(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/procurar-2.png"))); // NOI18N
        jLabel3.setText("Pesquisar");

        tblDadosClienteProcuraAvancada.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblDadosClienteProcuraAvancada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDadosClienteProcuraAvancadaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDadosClienteProcuraAvancada);

        txtNomeEquipamento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNomeEquipamentoKeyTyped(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNomeEquipamentoKeyReleased(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/procurar-2.png"))); // NOI18N
        jLabel4.setText("Pesquisar");

        jLabel5.setText("Pesquisar nome do cliente");

        btnLimpaCamposPesquisaAvancada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/limpartudo.png"))); // NOI18N
        btnLimpaCamposPesquisaAvancada.setText("Limpar campos");
        btnLimpaCamposPesquisaAvancada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpaCamposPesquisaAvancadaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtNumeroOSCaixaDialogo, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnOkProcura, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelarProcura, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLimpaCamposPesquisaAvancada))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtProcuraAvancadaNome, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4))
                            .addComponent(jLabel1)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtNomeEquipamento, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3)))
                        .addGap(55, 55, 55)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNumeroOSCaixaDialogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOkProcura)
                    .addComponent(btnCancelarProcura)
                    .addComponent(btnLimpaCamposPesquisaAvancada, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProcuraAvancadaNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtNomeEquipamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnOkProcuraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkProcuraActionPerformed
        // TODO add your handling code here:
        System.out.println("entrei");
        SetaCamposDialog();
        System.out.println("passei pelo seta campos");
        telaos.RecebeDadosPesquisaAvancada();
        System.out.println("passei pelo recebe");
        telaos.PesquisarOs();
        System.out.println("agora deve fechar a tela de pesquisa");

        this.dispose();

    }//GEN-LAST:event_btnOkProcuraActionPerformed

    private void btnCancelarProcuraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarProcuraActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCancelarProcuraActionPerformed

    private void txtProcuraAvancadaNomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtProcuraAvancadaNomeMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_txtProcuraAvancadaNomeMouseClicked

    private void txtProcuraAvancadaNomeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProcuraAvancadaNomeKeyReleased
        // TODO add your handling code here:
        PesquisarClienteAvancadoCaixaDialogo();
    }//GEN-LAST:event_txtProcuraAvancadaNomeKeyReleased

    private void tblDadosClienteProcuraAvancadaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDadosClienteProcuraAvancadaMouseClicked
        // TODO add your handling code here:
        SetarCampos();
    }//GEN-LAST:event_tblDadosClienteProcuraAvancadaMouseClicked

    private void txtNumeroOSCaixaDialogoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroOSCaixaDialogoKeyReleased
        // TODO add your handling code here:
        
        PesquisarClienteJtextOs();
    }//GEN-LAST:event_txtNumeroOSCaixaDialogoKeyReleased

    private void txtNomeEquipamentoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeEquipamentoKeyReleased
        // TODO add your handling code here:
        PesquisarClienteNomeEquipamento();
    }//GEN-LAST:event_txtNomeEquipamentoKeyReleased

    private void btnLimpaCamposPesquisaAvancadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpaCamposPesquisaAvancadaActionPerformed
        // TODO add your handling code here:
        limparCamposPesquisaAvancada();
    }//GEN-LAST:event_btnLimpaCamposPesquisaAvancadaActionPerformed

    private void txtNumeroOSCaixaDialogoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroOSCaixaDialogoKeyTyped
        // TODO add your handling code here:
        txtNomeEquipamento.setText(null);
        txtProcuraAvancadaNome.setText(null);

        
    }//GEN-LAST:event_txtNumeroOSCaixaDialogoKeyTyped

    private void txtProcuraAvancadaNomeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProcuraAvancadaNomeKeyTyped
        // TODO add your handling code here:
        txtNumeroOSCaixaDialogo.setText(null);
        txtNomeEquipamento.setText(null);
        
    }//GEN-LAST:event_txtProcuraAvancadaNomeKeyTyped

    private void txtNomeEquipamentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeEquipamentoKeyTyped
        // TODO add your handling code here:
        txtNumeroOSCaixaDialogo.setText(null);
        txtProcuraAvancadaNome.setText(null);
        
        
    }//GEN-LAST:event_txtNomeEquipamentoKeyTyped

    private void txtNumeroOSCaixaDialogoAncestorMoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_txtNumeroOSCaixaDialogoAncestorMoved
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtNumeroOSCaixaDialogoAncestorMoved

    private void txtNumeroOSCaixaDialogoAncestorRemoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_txtNumeroOSCaixaDialogoAncestorRemoved
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumeroOSCaixaDialogoAncestorRemoved

    private void txtNumeroOSCaixaDialogoInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtNumeroOSCaixaDialogoInputMethodTextChanged
        // TODO add your handling code here:
        //txtProcuraAvancadaNome.setText(null);
    }//GEN-LAST:event_txtNumeroOSCaixaDialogoInputMethodTextChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PesquisaAvancada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PesquisaAvancada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PesquisaAvancada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PesquisaAvancada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //aqui tambem precisa alterar o metodo que espera um retorne de uma jframe para o metodo caixa de dialogo
                //no caso depois dos ( tudo que havia antes da virgula foi alterado pelo objeto que referencia ao TelaOs no caso o telaos
                PesquisaAvancada dialog = new PesquisaAvancada(telaos, true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelarProcura;
    private javax.swing.JButton btnLimpaCamposPesquisaAvancada;
    private javax.swing.JButton btnOkProcura;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblDadosClienteProcuraAvancada;
    private javax.swing.JTextField txtNomeEquipamento;
    private javax.swing.JTextField txtNumeroOSCaixaDialogo;
    private javax.swing.JTextField txtProcuraAvancadaNome;
    // End of variables declaration//GEN-END:variables
}