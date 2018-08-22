/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ossystem.telas;

import java.sql.*;
import br.com.ossystem.dao.ModuloConexao;
import javax.swing.JOptionPane;
//a linha abaixo importa recursos da biblioteca rs2xml.jar

import net.proteanit.sql.DbUtils;

/**
 *
 * @author wesley vila seca sanches
 * 
 */
public class TelaCliente extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaCliente
     */
    public TelaCliente() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    private void LimparCamposCliente() {
        txtIdCliente.setText(null);
        txtNomeCliente.setText(null);
        txtEnderecoCliente.setText(null);
        txtTelefoneCliente.setText(null);
        txtEmailCliente.setText(null);
        txtPesquisarCliente.setText(null);
        btnCadastrarCliente.setEnabled(true);
    }

    private void CadastrarCliente() {
        String sql = "insert into tbClientes(nomeCliente,enderecoCliente,telefoneCliente,emailCliente) values (?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtNomeCliente.getText());
            pst.setString(2, txtEnderecoCliente.getText());
            pst.setString(3, txtTelefoneCliente.getText());
            pst.setString(4, txtEmailCliente.getText());
            int cadastrado = pst.executeUpdate();

            if (cadastrado > 0) {
                JOptionPane.showMessageDialog(null, "O cliente " + txtNomeCliente.getText() + " foi cadastrado com sucesso!");
                LimparCamposCliente();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //pesquisa cliente com filtro
    private void PesquisarCliente() {
        String sql = "select * from tbClientes where nomeCliente like ?";
        try {
            pst = conexao.prepareStatement(sql);
            //passando o nome para tabela para o ? do sql
            //atenção ao porcentagem que significa a continuaçao do comendo
            pst.setString(1, txtPesquisarCliente.getText() + "%");
            rs = pst.executeQuery();
            //a linha abaixo usa a biblioteca para pesquisa avançada 
            tblClientes.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // metodo para setar o conteudo da tabela
    public void SetarCampos() {
        int SetarCampos = tblClientes.getSelectedRow();
        txtIdCliente.setText(tblClientes.getModel().getValueAt(SetarCampos, 0).toString());
        txtNomeCliente.setText(tblClientes.getModel().getValueAt(SetarCampos, 1).toString());
        txtEnderecoCliente.setText(tblClientes.getModel().getValueAt(SetarCampos, 2).toString());
        txtTelefoneCliente.setText(tblClientes.getModel().getValueAt(SetarCampos, 3).toString());
        txtEmailCliente.setText(tblClientes.getModel().getValueAt(SetarCampos, 4).toString());
        
        // a linha abaixo desabilita o botão adicionar
        
        btnCadastrarCliente.setEnabled(false);
    }

    private void AlterarDadosCliente() {
        String sql = "update tbClientes set nomeCliente=?,enderecoCliente=?,telefoneCliente=?,emailCliente=? where idCliente=?";

        try {
            pst = conexao.prepareStatement(sql);
            //pst.setString(0, txtIdCliente.getText());
            pst.setString(1, txtNomeCliente.getText());
            pst.setString(2, txtEnderecoCliente.getText());
            pst.setString(3, txtTelefoneCliente.getText());
            pst.setString(4, txtEmailCliente.getText());
            pst.setString(5, txtIdCliente.getText());
            
            int DadosClienteAlterado = pst.executeUpdate();
            
            if (DadosClienteAlterado>0){
                JOptionPane.showMessageDialog(null, "Dados do Cliente "+txtNomeCliente.getText()+" foi alterado com sucesso!");
                LimparCamposCliente();
                
                        
            }

        } catch (Exception e) {
        }
    }
    
    //deletando cliente
    private void DeletarCliente(){
        int ConfirmaExclusaoCliente= JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este cliente ?","Atenção",JOptionPane.YES_NO_OPTION);
        
        if (ConfirmaExclusaoCliente == JOptionPane.YES_OPTION){
            String sql = "delete from tbClientes where idCliente=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtIdCliente.getText());
                int ClienteDeletado = pst.executeUpdate();
                if(ClienteDeletado>0){
                    JOptionPane.showMessageDialog(null, "O cliente "+txtNomeCliente.getText()+" foi apagado com sucesso!");
                    LimparCamposCliente();
                    
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
            
            
        }}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNomeCliente = new javax.swing.JTextField();
        txtEmailCliente = new javax.swing.JTextField();
        txtTelefoneCliente = new javax.swing.JTextField();
        txtEnderecoCliente = new javax.swing.JTextField();
        btnCadastrarCliente = new javax.swing.JButton();
        btnAlterarDadosCliente = new javax.swing.JButton();
        btnDeletarCliente = new javax.swing.JButton();
        txtPesquisarCliente = new javax.swing.JTextField();
        lblPesquisarCliente = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        txtIdCliente = new javax.swing.JTextField();
        btnLimparCamposCliente = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Clientes");
        setPreferredSize(new java.awt.Dimension(750, 600));

        jLabel1.setText("Nome* :");

        jLabel2.setText("Endereço* :");

        jLabel3.setText("Teleforne* :");

        jLabel4.setText("email: ");

        txtNomeCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeClienteActionPerformed(evt);
            }
        });

        txtTelefoneCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefoneClienteActionPerformed(evt);
            }
        });

        btnCadastrarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/adcionar-2.png"))); // NOI18N
        btnCadastrarCliente.setText("Cadastrar Cliente");
        btnCadastrarCliente.setToolTipText("Cadastrar Cliente");
        btnCadastrarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarClienteActionPerformed(evt);
            }
        });

        btnAlterarDadosCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/editar-2.png"))); // NOI18N
        btnAlterarDadosCliente.setText("Alterar Dados do Cliente");
        btnAlterarDadosCliente.setToolTipText("Alterar dados do Cliente");
        btnAlterarDadosCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarDadosClienteActionPerformed(evt);
            }
        });

        btnDeletarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/deletar-2.png"))); // NOI18N
        btnDeletarCliente.setText("Deletar Cliente");
        btnDeletarCliente.setToolTipText("Deletar Cliente");
        btnDeletarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletarClienteActionPerformed(evt);
            }
        });

        txtPesquisarCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarClienteKeyReleased(evt);
            }
        });

        lblPesquisarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/procurar-2.png"))); // NOI18N
        lblPesquisarCliente.setText("Procurar");

        tblClientes = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int columnIndex){
                return false;
            }
        };
        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
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
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblClientes);

        jLabel5.setText("ID Cliente:");

        txtIdCliente.setEditable(false);

        btnLimparCamposCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/limpartudo.png"))); // NOI18N
        btnLimparCamposCliente.setText("Limpar Campos");
        btnLimparCamposCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparCamposClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtPesquisarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPesquisarCliente)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNomeCliente, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtTelefoneCliente, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtIdCliente, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtEnderecoCliente, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEmailCliente, javax.swing.GroupLayout.Alignment.LEADING)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLimparCamposCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 12, Short.MAX_VALUE)
                        .addComponent(btnCadastrarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 101, Short.MAX_VALUE)
                        .addComponent(btnAlterarDadosCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(76, 76, 76)
                        .addComponent(btnDeletarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPesquisarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPesquisarCliente))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtEnderecoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtTelefoneCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(txtEmailCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(91, 91, 91)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCadastrarCliente)
                    .addComponent(btnAlterarDadosCliente)
                    .addComponent(btnDeletarCliente))
                .addGap(18, 18, 18)
                .addComponent(btnLimparCamposCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNomeClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeClienteActionPerformed

    private void txtTelefoneClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefoneClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefoneClienteActionPerformed

    private void btnCadastrarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarClienteActionPerformed
        // TODO add your handling code here:
        if (txtNomeCliente.getText().equals("") || txtEnderecoCliente.getText().equals("") || txtTelefoneCliente.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Os campos com asterisco (*) são obrigatórios o preenchimento");
        } else {
            CadastrarCliente();
        }

    }//GEN-LAST:event_btnCadastrarClienteActionPerformed

    private void txtPesquisarClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarClienteKeyReleased
        // este evento pesquisa enquanto digita:
        PesquisarCliente();
    }//GEN-LAST:event_txtPesquisarClienteKeyReleased

    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        // evento que será usado para setar os campos da tabela clicando com o mouse:
        SetarCampos();

    }//GEN-LAST:event_tblClientesMouseClicked

    private void btnAlterarDadosClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarDadosClienteActionPerformed
        // chamando o metodo para alterar cliente:
        
        if (txtNomeCliente.getText().equals("") || txtEnderecoCliente.getText().equals("") || txtTelefoneCliente.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Os campos com asterisco (*) são obrigatórios o preenchimento");
        } else {
            AlterarDadosCliente();
        }
        
    }//GEN-LAST:event_btnAlterarDadosClienteActionPerformed

    private void btnLimparCamposClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparCamposClienteActionPerformed
        // TODO add your handling code here:
        LimparCamposCliente();
    }//GEN-LAST:event_btnLimparCamposClienteActionPerformed

    private void btnDeletarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletarClienteActionPerformed
        // TODO add your handling code here:
        DeletarCliente();
    }//GEN-LAST:event_btnDeletarClienteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterarDadosCliente;
    private javax.swing.JButton btnCadastrarCliente;
    private javax.swing.JButton btnDeletarCliente;
    private javax.swing.JButton btnLimparCamposCliente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblPesquisarCliente;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtEmailCliente;
    private javax.swing.JTextField txtEnderecoCliente;
    private javax.swing.JTextField txtIdCliente;
    private javax.swing.JTextField txtNomeCliente;
    private javax.swing.JTextField txtPesquisarCliente;
    private javax.swing.JTextField txtTelefoneCliente;
    // End of variables declaration//GEN-END:variables
}
