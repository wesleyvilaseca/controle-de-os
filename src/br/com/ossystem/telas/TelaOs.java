/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ossystem.telas;

import java.sql.*;
import br.com.ossystem.dao.ModuloConexao;
//import br.com.ossystem.modelo.modeloJframe1;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import static jdk.nashorn.internal.objects.NativeRegExp.test;
import net.proteanit.sql.DbUtils;
import java.awt.event.*;
import java.util.HashMap;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Wesley Vila Seca Sanches
 */
public class TelaOs extends javax.swing.JInternalFrame {

    //a linha abaxio cria uma conexao com Caixa de dialogo PesquisaAvancada
    public static PesquisaAvancada caixaDialogo;

    //private String NumeroQualquer;
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaOs
     */
    public TelaOs() {
        super();
        initComponents();

        conexao = ModuloConexao.conector();

    }

    //o metodo abaixo é para acionar a caixa de dialogo PesquisaAvancada
    public void mostraCaixaDialogo() {
        caixaDialogo = new PesquisaAvancada(this, true);
        caixaDialogo.setVisible(true);
    }

    private void PesquisarClienteOs() {
        String sql = "select IdCliente as ID,nomeCliente as Nome,telefoneCliente as Telefone from tbClientes where nomeCliente like ?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtProcurarClienteOs.getText() + "%");
            rs = pst.executeQuery();

            tblClienteOs.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
        }
    }

    //metodo para pesquisar uma os
    //esse metodo recebe iformação da caixa de dialogo
    //este método foi feito para testas de os dados da pesquisa avançada chegavam aqui, porem ele não está sendo utilizado
    public void RecebeDadosPesquisaAvancada() {
        String a = caixaDialogo.SetaCamposDialog();//seta campo é o metodo que captura o valor da jtext da caixa de dialogo
        //System.out.println(a);
        //txtOrdemServico.setText(a);
    }

    public void test(java.awt.event.ActionEvent evt) {
        caixaDialogo = new PesquisaAvancada(this, true);
        caixaDialogo.setVisible(true);
    }

    //este metodo é acionado e efetua a procura com o valor da metodo recebeDadosPesquisaAvancada
    public void PesquisarOs() {

        /*
        //a linha abaixo cria uma caixa de entrada do tipo joption pane
        //String numeroOS = JOptionPane.showInputDialog("Número da Ordem de Serviço");
        //txtOrdemServico.setText(recebendoNumero);*/
        String a = caixaDialogo.SetaCamposDialog();
        String sql = "select * from tbOrdemServico where ordemServico=" + a;
        String sqlNomeCliente = "select * from tbClientes where idCliente=?";

        try {

            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                txtOrdemServico.setText(rs.getString(1));
                txtDataOs.setText(rs.getString(2));
                ComboBoxSituacaoOs.setSelectedItem(rs.getString(10));

                txtEquipamentoOs.setText(rs.getString(4));
                txtDefeitoEquipamentoOs.setText(rs.getString(5));
                txtServicoOs.setText(rs.getString(6));
                txtTecnicoOs.setText(rs.getString(7));
                txtValorTotalOs.setText(rs.getString(8));
                txtIdClienteOs.setText(rs.getString(9));
                btnEmitirOs.setEnabled(false);
                txtProcurarClienteOs.setEditable(false);
                tblClienteOs.setVisible(false);
                
                String rbtTipo = rs.getString(3);
                if (rbtTipo.equals("Ordem de Serviço")) {
                    btnGroupOrdemServicoOs.setSelected(true);
                    tipo = "Ordem de Serviço";
                } else {
                    btnGroupOrcamentoOs.setSelected(true);
                    tipo = "Oçamento";
                }

                //esta parte preencho o campo procurar cliente com o nome a que a os está vinculada
                pst = conexao.prepareStatement(sqlNomeCliente);//aqui ali vai preeche o campo baseado no id
                pst.setString(1, txtIdClienteOs.getText());
                rs = pst.executeQuery();
                if (rs.next()) {
                    txtProcurarClienteOs.setText(rs.getString(2));

                }



            } else {
                System.out.println("estou no else vou fechar e ativar a caixa de dialogo");
                JOptionPane.showMessageDialog(null, "Ordem de serviço não cadastrada");

                caixaDialogo.dispose();

                mostraCaixaDialogo();
            }
        } catch (java.sql.SQLSyntaxErrorException e) {
            System.out.println("entrou no catch deve finalizar a atual janela e ativar outra vez");
            JOptionPane.showMessageDialog(null, "Ordem de serviço inválida!");
            caixaDialogo.dispose();
            mostraCaixaDialogo();

            //System.out.println(e);
        } catch (Exception e2) {
            JOptionPane.showMessageDialog(null, e2);
        }

    }

    private void SetarCamposClienteOs() {
        int setar = tblClienteOs.getSelectedRow();
        txtIdClienteOs.setText(tblClienteOs.getModel().getValueAt(setar, 0).toString());

    }

    private void LimparCamposOs() {
        txtEquipamentoOs.setText(null);
        txtDefeitoEquipamentoOs.setText(null);
        txtServicoOs.setText(null);
        txtTecnicoOs.setText(null);
        txtValorTotalOs.setText(null);
        txtIdClienteOs.setText(null);
        txtProcurarClienteOs.setText(null);
        ComboBoxSituacaoOs.setSelectedItem(null);
        txtDataOs.setText(null);
        txtOrdemServico.setText(null);
        btnGroupOrcamentoOs.setSelected(true);
        ((DefaultTableModel) tblClienteOs.getModel()).setRowCount(0); //limpa a tabela
        btnEmitirOs.setEnabled(true);
        txtProcurarClienteOs.setEditable(true);
        tblClienteOs.setVisible(true);
    }

    private void EmitirOs() {
        String sql = "insert into tbOrdemServico(tipo,equipamento,defeito,servico,tecnico,valor,idCliente,Situacao) values (?,?,?,?,?,?,?,?)";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, txtEquipamentoOs.getText());
            pst.setString(3, txtDefeitoEquipamentoOs.getText());
            pst.setString(4, txtServicoOs.getText());
            pst.setString(5, txtTecnicoOs.getText());
            pst.setString(6, txtValorTotalOs.getText().replace(",", "."));
            pst.setString(7, txtIdClienteOs.getText());
            pst.setString(8, ComboBoxSituacaoOs.getSelectedItem().toString());

            int osEmitida = pst.executeUpdate();
            if (osEmitida > 0) {
                JOptionPane.showMessageDialog(null, "A Ordem de Serviço foi emitida com sucesso!");
                LimparCamposOs();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //metodo alterar os
    private void AlterarOS() {
        String sql = "update tbOrdemServico set tipo=?,equipamento=?,defeito=?,servico=?,tecnico=?,valor=?,Situacao=? where ordemServico=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, txtEquipamentoOs.getText());
            pst.setString(3, txtDefeitoEquipamentoOs.getText());
            pst.setString(4, txtServicoOs.getText());
            pst.setString(5, txtTecnicoOs.getText());
            pst.setString(6, txtValorTotalOs.getText().replace(",", "."));
            pst.setString(7, ComboBoxSituacaoOs.getSelectedItem().toString());
            pst.setString(8, txtOrdemServico.getText());

            int osAlterada = pst.executeUpdate();
            if (osAlterada > 0) {
                JOptionPane.showMessageDialog(null, "A Ordem de Serviço foi alterada com sucesso!");
                LimparCamposOs();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    //metodo para excluir uma OS
    private void ExcluirOs() {
        int confirmaExclusao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir esta ordem de serviço?", "ATENÇÂO!", JOptionPane.YES_NO_OPTION);
        if (confirmaExclusao == JOptionPane.YES_OPTION) {
            String sql = "delete from tbOrdemServico where ordemServico=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtOrdemServico.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Ordem de serviço excluida com sucesso!");
                    LimparCamposOs();
                }
            } catch (Exception e) {
            }
        }
    }

    //metodo para imprimir uma OS
    private void ImprimirOs() {
        //imprimindo uma os
        int Confirma = JOptionPane.showConfirmDialog(null, "Confirma a impressão desta ordem de serviço?", "Atenção", JOptionPane.YES_NO_OPTION);

        if (Confirma == JOptionPane.YES_OPTION) {
            //imprimindo o relatório com o frameworc jasper
            try {
                //usando a classe hashmap para criar um filtro
                HashMap filtro = new HashMap();
                filtro.put("os",Integer.parseInt(txtOrdemServico.getText()));
                
                //usando a classe jasper print/media/wesley/OS/OsSystem
                JasperPrint imprime = JasperFillManager.fillReport("/home/wesley/Área de Trabalho/reports/os.jasper", filtro, conexao);

                //a linha abaixo exibe o relatorio atravéz da classe jasper viewer
                JasperViewer.viewReport(imprime, false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    // a linha abaixo cria uma variavel para armazenar um texto de acordo com o radio button selecionado
    private String tipo;

    //chamando tela pesquisa avançada
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtOrdemServico = new javax.swing.JTextField();
        txtDataOs = new javax.swing.JTextField();
        btnGroupOrcamentoOs = new javax.swing.JRadioButton();
        btnGroupOrdemServicoOs = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        ComboBoxSituacaoOs = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        txtProcurarClienteOs = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtIdClienteOs = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClienteOs = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtEquipamentoOs = new javax.swing.JTextField();
        txtDefeitoEquipamentoOs = new javax.swing.JTextField();
        txtServicoOs = new javax.swing.JTextField();
        txtTecnicoOs = new javax.swing.JTextField();
        txtValorTotalOs = new javax.swing.JTextField();
        btnEmitirOs = new javax.swing.JButton();
        btnAlterarOs = new javax.swing.JButton();
        btnApagarOs = new javax.swing.JButton();
        btnImprimirOs = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        btnPesquisaAvancada = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Ordem de Serviço");
        setPreferredSize(new java.awt.Dimension(750, 600));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Nº OS");

        jLabel2.setText("Data");

        txtOrdemServico.setEditable(false);

        txtDataOs.setEditable(false);
        txtDataOs.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N

        buttonGroup1.add(btnGroupOrcamentoOs);
        btnGroupOrcamentoOs.setText("Orçamento");
        btnGroupOrcamentoOs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGroupOrcamentoOsActionPerformed(evt);
            }
        });

        buttonGroup1.add(btnGroupOrdemServicoOs);
        btnGroupOrdemServicoOs.setText("Ordem de Serviço");
        btnGroupOrdemServicoOs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGroupOrdemServicoOsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnGroupOrcamentoOs)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGroupOrdemServicoOs))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtOrdemServico, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(txtDataOs, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtOrdemServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDataOs, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGroupOrdemServicoOs)
                    .addComponent(btnGroupOrcamentoOs))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        jLabel3.setText("*Situação");

        ComboBoxSituacaoOs.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Entrega ok", "Orçamento REPROVADO", "Aguardando aprovação", "Aguardando peças", "Abandonado pelo cliente", "Na bancada", "Retornou" }));
        ComboBoxSituacaoOs.setSelectedItem(null);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Cliente"));

        txtProcurarClienteOs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtProcurarClienteOsKeyTyped(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtProcurarClienteOsKeyReleased(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/procurar-2.png"))); // NOI18N
        jLabel4.setText("Procurar");

        jLabel5.setText("ID");

        txtIdClienteOs.setEditable(false);

        tblClienteOs = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int columnIndex){
                return false;
            }
        };
        tblClienteOs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Nome", "Contato"
            }
        ));
        tblClienteOs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClienteOsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblClienteOs);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtProcurarClienteOs)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addGap(4, 4, 4)
                        .addComponent(txtIdClienteOs, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProcurarClienteOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(txtIdClienteOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE))
        );

        jLabel6.setText("*Equipamento:");

        jLabel7.setText("*Defeito:");

        jLabel8.setText("Serviço:");

        jLabel9.setText("Técnico:");

        jLabel10.setText("Valor Total:");

        txtTecnicoOs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTecnicoOsActionPerformed(evt);
            }
        });

        txtValorTotalOs.setText("0");

        btnEmitirOs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/adcionar-2.png"))); // NOI18N
        btnEmitirOs.setText("Emitir OS");
        btnEmitirOs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmitirOsActionPerformed(evt);
            }
        });

        btnAlterarOs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/editar-2.png"))); // NOI18N
        btnAlterarOs.setText("Alterar OS");
        btnAlterarOs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarOsActionPerformed(evt);
            }
        });

        btnApagarOs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/deletar-2.png"))); // NOI18N
        btnApagarOs.setText("Apagar OS");
        btnApagarOs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApagarOsActionPerformed(evt);
            }
        });

        btnImprimirOs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/imprimir-2.png"))); // NOI18N
        btnImprimirOs.setText("Imprimir OS");
        btnImprimirOs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirOsActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/limpartudo.png"))); // NOI18N
        jButton1.setText("Limpar Campos");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnPesquisaAvancada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/procurar-2.png"))); // NOI18N
        btnPesquisaAvancada.setText("Procurar OS");
        btnPesquisaAvancada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisaAvancadaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ComboBoxSituacaoOs, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEquipamentoOs)
                            .addComponent(txtDefeitoEquipamentoOs)
                            .addComponent(txtServicoOs)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtTecnicoOs, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(87, 87, 87)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtValorTotalOs))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEmitirOs, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnImprimirOs, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addComponent(btnPesquisaAvancada, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(btnAlterarOs, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnApagarOs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ComboBoxSituacaoOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtEquipamentoOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtDefeitoEquipamentoOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtServicoOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtTecnicoOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtValorTotalOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(66, 66, 66)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPesquisaAvancada, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnEmitirOs)
                        .addComponent(btnApagarOs)
                        .addComponent(btnAlterarOs)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnImprimirOs, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        setBounds(0, 0, 750, 600);
    }// </editor-fold>//GEN-END:initComponents

    private void txtProcurarClienteOsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProcurarClienteOsKeyReleased
        // chamando o metodo pesquisar cliente:
        PesquisarClienteOs();
    }//GEN-LAST:event_txtProcurarClienteOsKeyReleased

    private void tblClienteOsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClienteOsMouseClicked
        // setando os campos da tabela
        SetarCamposClienteOs();
    }//GEN-LAST:event_tblClienteOsMouseClicked

    private void btnGroupOrcamentoOsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGroupOrcamentoOsActionPerformed
        // atribuindo um texto a variável tipo se selecionado
        tipo = "Orçamento";
    }//GEN-LAST:event_btnGroupOrcamentoOsActionPerformed

    private void btnGroupOrdemServicoOsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGroupOrdemServicoOsActionPerformed
        // a linha abaixo atribui um texto a variável tipo sse o radiobutton estiver selecionado
        tipo = "Ordem de Serviço";

    }//GEN-LAST:event_btnGroupOrdemServicoOsActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // ao abrir o form marcar o radiobutton orçamento
        btnGroupOrcamentoOs.setSelected(true);
        tipo = "Orçamento";
    }//GEN-LAST:event_formInternalFrameOpened

    private void btnEmitirOsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmitirOsActionPerformed
        // TODO add your handling code here:
        if (txtEquipamentoOs.getText().equals("") || txtDefeitoEquipamentoOs.getText().equals("") || ComboBoxSituacaoOs.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Os campos com asterisco (*) são obrigatórios o preenchimento");
        } else {
            EmitirOs();
        }
    }//GEN-LAST:event_btnEmitirOsActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        LimparCamposOs();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtProcurarClienteOsKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProcurarClienteOsKeyTyped
        // TODO add your handling code here:
        PesquisarClienteOs();
    }//GEN-LAST:event_txtProcurarClienteOsKeyTyped

    private void btnAlterarOsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarOsActionPerformed
        // TODO add your handling code here:
        if (txtEquipamentoOs.getText().equals("") || txtDefeitoEquipamentoOs.getText().equals("") || ComboBoxSituacaoOs.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Os campos com asterisco (*) são obrigatórios o preenchimento");
        } else {
            AlterarOS();
        }
    }//GEN-LAST:event_btnAlterarOsActionPerformed

    private void btnApagarOsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApagarOsActionPerformed
        // chamando botão deletar os
        ExcluirOs();
    }//GEN-LAST:event_btnApagarOsActionPerformed

    private void btnImprimirOsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirOsActionPerformed
        // TODO add your handling code here:
        //pesquisaAvancada.setVisible(true);
        /*TelaPesaquisaAvancada pesquisa = new TelaPesaquisaAvancada();
        TelaPrincipal.Desktop.add(pesquisa);

        pesquisa.setVisible(true);*/
        ImprimirOs();


    }//GEN-LAST:event_btnImprimirOsActionPerformed

    private void txtTecnicoOsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTecnicoOsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTecnicoOsActionPerformed

    private void btnPesquisaAvancadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisaAvancadaActionPerformed
        // TODO add your handling code here:

        /*String n = getNumeroQualquer();
        System.out.println("-------------------------------");
        System.out.println(n);
        System.out.println("-------------------------------");*/
        mostraCaixaDialogo();

    }//GEN-LAST:event_btnPesquisaAvancadaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboBoxSituacaoOs;
    private javax.swing.JButton btnAlterarOs;
    private javax.swing.JButton btnApagarOs;
    private javax.swing.JButton btnEmitirOs;
    private javax.swing.JRadioButton btnGroupOrcamentoOs;
    private javax.swing.JRadioButton btnGroupOrdemServicoOs;
    private javax.swing.JButton btnImprimirOs;
    private javax.swing.JButton btnPesquisaAvancada;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblClienteOs;
    private javax.swing.JTextField txtDataOs;
    private javax.swing.JTextField txtDefeitoEquipamentoOs;
    private javax.swing.JTextField txtEquipamentoOs;
    private javax.swing.JTextField txtIdClienteOs;
    private javax.swing.JTextField txtOrdemServico;
    private javax.swing.JTextField txtProcurarClienteOs;
    private javax.swing.JTextField txtServicoOs;
    private javax.swing.JTextField txtTecnicoOs;
    private javax.swing.JTextField txtValorTotalOs;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the NumeroQualquer
     */
    /*public String getNumeroQualquer() {
        return NumeroQualquer;
    }

    /**
     * @param NumeroQualquer the NumeroQualquer to set
     *//*
    public void setNumeroQualquer(String NumeroQualquer) {
        this.NumeroQualquer = NumeroQualquer;
    }*/

}
