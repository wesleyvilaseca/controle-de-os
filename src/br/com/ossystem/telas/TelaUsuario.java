/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ossystem.telas;

/**
 *
 * @author wesley parei na aula 15
 */
import java.sql.*;
import br.com.ossystem.dao.ModuloConexao;
import com.mysql.cj.protocol.Message;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

public class TelaUsuario extends javax.swing.JInternalFrame {

    //criando conexao com o banco de dados
    Connection conexao = null; //tem haver com o modulo de conecsão com o mysql
    PreparedStatement pst = null;//prepara a conexao com bd
    ResultSet rs = null;//exibe a conexao feita com o banco pelo id
    ResultSet rsLogin = null;//exibe a conexao feita com o banco pelo loguin

    /**
     * Creates new form TelaUsuario
     */
    public TelaUsuario() {//construtor
        initComponents();
        conexao = ModuloConexao.conector();//chamando o metodo conector 
    }

    //privete permite que o método seja executado somente dentre da classe
    //void porque é um método sem retorno
    //criando metodo consultar da tela de usuário pelo id
    //metodo para limpar campos
    /*private void LimparCamposPesquisaID() {
        //txtUsuarioID.setText(null);
        txtUsuarioNome.setText(null);
        txtUsuarioTelefone.setText(null);
        txtUsuarioLogin.setText(null);
        txtUsuarioSenha.setText(null);
        ComboBoxUsuarioPerfil.setSelectedItem(null);

    }*/

 /*private void LimparCamposPesquisaLoguin() {
        //txtUsuarioID.setText(null);
        txtUsuarioNome.setText(null);
        txtUsuarioTelefone.setText(null);
        //txtUsuarioLogin.setText(null);
        txtUsuarioSenha.setText(null);
        ComboBoxUsuarioPerfil.setSelectedItem(null);

    }*/
    private void LimparTodosCampos() {
        txtUsuarioID.setText(null);
        txtUsuarioNome.setText(null);
        txtUsuarioTelefone.setText(null);
        txtUsuarioLogin.setText(null);
        txtUsuarioSenha.setText(null);
        ComboBoxUsuarioPerfil.setSelectedItem(null);
        btnAdicionarUsuario.setEnabled(true);
        txtPesquisarUsuario.setText(null);
        ((DefaultTableModel) tblUsuarios.getModel()).setRowCount(0);//limpa a tabela

        }

    //o metodo abaixo utiliza aciona a pesquisa inteligente pela caixa de texto pesquisar
    private void PesquisarUsuario() {
        String sql = "select * from tbUsuarios where nomeUsuario like ?";
        try {
            pst = conexao.prepareStatement(sql);
            //passando o nome para tabela para o ? do sql
            //atenção ao porcentagem que significa a continuaçao do comendo
            pst.setString(1, txtPesquisarUsuario.getText() + "%");
            rs = pst.executeQuery();
            //a linha abaixo usa a biblioteca para pesquisa avançada 
            tblUsuarios.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //o metodo abaixo seta as informações ao clickar na tabela para as caixa de textos a que elas se referem
    public void SetarCampos() {
        int SetarCampos = tblUsuarios.getSelectedRow();
        txtUsuarioID.setText(tblUsuarios.getModel().getValueAt(SetarCampos, 0).toString());
        txtUsuarioNome.setText(tblUsuarios.getModel().getValueAt(SetarCampos, 1).toString());
        txtUsuarioTelefone.setText(tblUsuarios.getModel().getValueAt(SetarCampos, 2).toString());
        txtUsuarioLogin.setText(tblUsuarios.getModel().getValueAt(SetarCampos, 3).toString());
        txtUsuarioSenha.setText(tblUsuarios.getModel().getValueAt(SetarCampos, 4).toString());
        ComboBoxUsuarioPerfil.setSelectedItem(tblUsuarios.getModel().getValueAt(SetarCampos, 5));
        btnAdicionarUsuario.setEnabled(false);
    }

    /*
    private void ConsultaID() throws SQLException{
        String sql = "select * from tbUsuarios where idUser=?";
        String sqlTest = "select * from tbUsuarios where loginUsuario=?";
        
        boolean Testador = false;
        try {
            //essa primeira parte vai fazer a procura no bd atravez do id do usuário
            pst=conexao.prepareStatement(sql);
            pst.setString(1, txtUsuarioID.getText());           
            rsID=pst.executeQuery();
            if(rsID.next()){
                // caso o id seja registrado ele vai retornar os dados do usuário
                Testador = true;
                txtUsuarioNome.setText(rsID.getString(2));
                txtUsuarioTelefone.setText(rsID.getString(3));
                txtUsuarioLogin.setText(rsID.getString(4));
                txtUsuarioSenha.setText(rsID.getString(5));
                ComboBoxUsuarioPerfil.setSelectedItem(rsID.getString(6));//refere-se ao combo box
                //caso o admin opite por consultar por meio de login este parametro será acionado
                //a lógica possui uma falha, pois se id possuir cadastro, e o login também, a prioridade será sempre o retordo dos dados referente ao id
            }else if(Testador == false){
            pst=conexao.prepareStatement(sqlTest);
            pst.setString(1, txtUsuarioLogin.getText());
            rsLogin=pst.executeQuery();
                if(rsLogin.next()){
                    txtUsuarioNome.setText(rsLogin.getString(2));
                    txtUsuarioTelefone.setText(rsLogin.getString(3));
                    txtUsuarioID.setText(rsLogin.getString(1));
                    txtUsuarioSenha.setText(rsLogin.getString(5));
                    ComboBoxUsuarioPerfil.setSelectedItem(rsLogin.getString(6));//refere-se ao combo box
                    
                }else{
                    JOptionPane.showMessageDialog(null, "Usuário não cadastrado!");
                    LimparCamposPesquisaLoguin();
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "Usuário não cadastrado!");
                LimparCamposPesquisaID();
                Testador=false;
            }
            
        
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
    }*/
    private void AdicionarUsuario() {
        String sql = "insert into tbUsuarios(nomeUsuario,telefoneUsuario,loginUsuario,senhaUsuario,perfil) values(?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            //pst.setString(1,txtUsuarioID.getText());
            pst.setString(1, txtUsuarioNome.getText());
            pst.setString(2, txtUsuarioTelefone.getText());
            pst.setString(3, txtUsuarioLogin.getText());
            pst.setString(4, txtUsuarioSenha.getText());
            pst.setString(5, ComboBoxUsuarioPerfil.getSelectedItem().toString());

            //a linha abaixo atualiza o banco de dados com os dados do formulario
            //a estrutura abaixo é usada para confirmar a inserção dos dados na tabela
            int adicionado = pst.executeUpdate();
            if (adicionado > 0) {
                JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
                LimparTodosCampos();

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "O login " + txtUsuarioLogin.getText() + " já possui cadastro!"/* e*/);

        }

    }

    //criando o método para alterar dados do usuário
    private void AlterarDadosUsuario() {
        String sql = "update tbUsuarios set NomeUsuario=?,telefoneUsuario=?,loginUsuario=?,senhaUsuario=?,perfil=? where idUser=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsuarioNome.getText());
            pst.setString(2, txtUsuarioTelefone.getText());
            pst.setString(3, txtUsuarioLogin.getText());
            pst.setString(4, txtUsuarioSenha.getText());
            pst.setString(5, ComboBoxUsuarioPerfil.getSelectedItem().toString());
            pst.setString(6, txtUsuarioID.getText());

            int adicionado = pst.executeUpdate();
            if (adicionado > 0) {
                JOptionPane.showMessageDialog(null, "Usuário alterado com sucesso!");
                LimparTodosCampos();

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, /*"O usuário de login "+txtUsuarioLogin.getText()+" já possui cadastro!"*/ e);
        }
    }

    //metodo responsável pela remoção de usuários
    private void DeletarUsuario() {
        //a estrutura abaixo confirma a remoção do usuário
        int confirmaRemocao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover o usuário ", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirmaRemocao == JOptionPane.YES_OPTION) {
            String sql = "delete from tbUsuarios where loginUsuario=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtUsuarioLogin.getText());
                int deletado = pst.executeUpdate();
                if (deletado > 0) {
                    JOptionPane.showMessageDialog(null, "O usuário " + txtUsuarioNome.getText() + " foi removido com sucesso!");
                    LimparTodosCampos();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

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
        jLabel5 = new javax.swing.JLabel();
        txtUsuarioID = new javax.swing.JTextField();
        txtUsuarioNome = new javax.swing.JTextField();
        txtUsuarioLogin = new javax.swing.JTextField();
        ComboBoxUsuarioPerfil = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        txtUsuarioTelefone = new javax.swing.JTextField();
        btnAdicionarUsuario = new javax.swing.JButton();
        btnEditarUsuario = new javax.swing.JButton();
        btnDeletarUsuario = new javax.swing.JButton();
        txtUsuarioSenha = new javax.swing.JTextField();
        lblLimparCampos = new javax.swing.JButton();
        txtPesquisarUsuario = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Usuários");
        setPreferredSize(new java.awt.Dimension(750, 600));
        addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                formAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        jLabel1.setText("ID: ");

        jLabel2.setText("*Nome:");

        jLabel3.setText("*Login:");

        jLabel4.setText("*Senha:");

        jLabel5.setText("*Perfil:");

        txtUsuarioID.setEditable(false);

        ComboBoxUsuarioPerfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "admin", "user" }));
        ComboBoxUsuarioPerfil.setSelectedItem(null);
        ComboBoxUsuarioPerfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBoxUsuarioPerfilActionPerformed(evt);
            }
        });

        jLabel6.setText("Telefone");

        btnAdicionarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/adcionar-2.png"))); // NOI18N
        btnAdicionarUsuario.setText("Adicionar Usuário");
        btnAdicionarUsuario.setToolTipText("Adicionar Usuário");
        btnAdicionarUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAdicionarUsuario.setPreferredSize(new java.awt.Dimension(80, 80));
        btnAdicionarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarUsuarioActionPerformed(evt);
            }
        });

        btnEditarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/editar-2.png"))); // NOI18N
        btnEditarUsuario.setText("Alterar Dados do Usuário");
        btnEditarUsuario.setToolTipText("Editar Usuário");
        btnEditarUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditarUsuario.setPreferredSize(new java.awt.Dimension(80, 80));
        btnEditarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarUsuarioActionPerformed(evt);
            }
        });

        btnDeletarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/deletar-2.png"))); // NOI18N
        btnDeletarUsuario.setText("Deletar Usuario");
        btnDeletarUsuario.setToolTipText("Deletar Usuário");
        btnDeletarUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDeletarUsuario.setPreferredSize(new java.awt.Dimension(80, 80));
        btnDeletarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletarUsuarioActionPerformed(evt);
            }
        });

        lblLimparCampos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/limpartudo.png"))); // NOI18N
        lblLimparCampos.setText("Limpar Campos");
        lblLimparCampos.setToolTipText("Limpar Campos");
        lblLimparCampos.setMaximumSize(new java.awt.Dimension(30, 30));
        lblLimparCampos.setMinimumSize(new java.awt.Dimension(30, 30));
        lblLimparCampos.setPreferredSize(new java.awt.Dimension(30, 30));
        lblLimparCampos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblLimparCamposActionPerformed(evt);
            }
        });

        txtPesquisarUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarUsuarioKeyReleased(evt);
            }
        });

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/procurar-2.png"))); // NOI18N
        jLabel7.setText("Procurar");

        tblUsuarios = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int columnIndex){
                return false;
            }
        };
        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
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
        tblUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsuariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUsuarios);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtUsuarioID, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(ComboBoxUsuarioPerfil, 0, 203, Short.MAX_VALUE)
                                    .addComponent(txtUsuarioLogin))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtUsuarioTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtUsuarioSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(txtUsuarioNome)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 192, Short.MAX_VALUE)
                                .addComponent(btnEditarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(66, 66, 66)
                                .addComponent(btnDeletarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblLimparCampos, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtPesquisarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(btnAdicionarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPesquisarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(txtUsuarioID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(txtUsuarioNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(ComboBoxUsuarioPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtUsuarioTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(5, 5, 5)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtUsuarioLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)
                        .addComponent(txtUsuarioSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDeletarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdicionarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(lblLimparCampos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        setBounds(0, 0, 750, 600);
    }// </editor-fold>//GEN-END:initComponents

    private void formAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_formAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_formAncestorAdded

    private void ComboBoxUsuarioPerfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBoxUsuarioPerfilActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboBoxUsuarioPerfilActionPerformed

    private void btnAdicionarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarUsuarioActionPerformed
        // aciona o metodo adicionar usuário:
        if (txtUsuarioNome.getText().equals("") || txtUsuarioSenha.getText().equals("") || txtUsuarioLogin.getText().equals("") || ComboBoxUsuarioPerfil.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Os campos com asterisco (*) são obrigatórios o preenchimento");
        } else {
            AdicionarUsuario();
        }
    }//GEN-LAST:event_btnAdicionarUsuarioActionPerformed

    private void btnEditarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarUsuarioActionPerformed
        // TODO add your handling code here:
        // aciona o metodo alterar usuário:
        if (txtUsuarioNome.getText().equals("") || txtUsuarioSenha.getText().equals("") || txtUsuarioLogin.getText().equals("") || ComboBoxUsuarioPerfil.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Os campos com asterisco (*) são obrigatórios o preenchimento");
        } else {
            AlterarDadosUsuario();
        }
    }//GEN-LAST:event_btnEditarUsuarioActionPerformed

    private void lblLimparCamposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblLimparCamposActionPerformed
        // aciona o botão limpar campos
        LimparTodosCampos();

    }//GEN-LAST:event_lblLimparCamposActionPerformed

    private void btnDeletarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletarUsuarioActionPerformed
        // chamando o método remover
        DeletarUsuario();
    }//GEN-LAST:event_btnDeletarUsuarioActionPerformed

    private void txtPesquisarUsuarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarUsuarioKeyReleased
        // TODO add your handling code here:
        PesquisarUsuario();

    }//GEN-LAST:event_txtPesquisarUsuarioKeyReleased

    private void tblUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsuariosMouseClicked
        // TODO add your handling code here:
        SetarCampos();
    }//GEN-LAST:event_tblUsuariosMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboBoxUsuarioPerfil;
    private javax.swing.JButton btnAdicionarUsuario;
    private javax.swing.JButton btnDeletarUsuario;
    private javax.swing.JButton btnEditarUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton lblLimparCampos;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JTextField txtPesquisarUsuario;
    private javax.swing.JTextField txtUsuarioID;
    private javax.swing.JTextField txtUsuarioLogin;
    private javax.swing.JTextField txtUsuarioNome;
    private javax.swing.JTextField txtUsuarioSenha;
    private javax.swing.JTextField txtUsuarioTelefone;
    // End of variables declaration//GEN-END:variables
}
