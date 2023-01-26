/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.com.heevn.telas;
import java.sql.*;
import br.com.heevn.dal.ModuloConexao;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author henrique
 */
public class TelaOS extends javax.swing.JInternalFrame {
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    // A linha abaixo cria uma variavel para armazenar um texto de acordo com o radio button selecionado
    private String tipo;
    
    
    private void pesquisar_clientes(){
        String sql = "select id as Id, nome as Nome, fone as Contato from cliente where nome like ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1,txtCliPesquisar.getText() + "%");
            rs=pst.executeQuery();
            tblClientes.setModel(DbUtils.resultSetToTableModel(rs));            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
        }
                
    }
    
    
    // Método para cadastrar uma O.S.
    private void emitir_os(){
        String sql = " insert into ordemservicos (tipo,situacao,equipamento,defeito,servico,tecnico,valor,idcliente) values (?,?,?,?,?,?,?,?)";
        try {
            pst=conexao.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2,cboOsSit.getSelectedItem().toString());
            pst.setString(3,txtOsEquip.getText());
            pst.setString(4,txtOsDef.getText());
            pst.setString(5,txtOsServ.getText());
            pst.setString(6,txtOsTec.getText());
            // Replace serve para formatar e trocar o ponto pela vírgula, pois o ponto iria dar problemas com o BD
            pst.setString(7, txtOsValor.getText().replace(",", "."));
            pst.setString(8,txtCliId.getText());
            
            //Validação dos campos obrigatórios
            if ((txtCliId.getText().isEmpty()) || (txtOsEquip.getText().isEmpty()) || (txtOsDef.getText().isEmpty()) || (cboOsSit.getSelectedItem().equals(" "))) {
                JOptionPane.showMessageDialog(null,"Preencha todos os campos obrigatórios!");
            } else {
                int adicionado = pst.executeUpdate();
                if ( adicionado>0){
                    JOptionPane.showMessageDialog(null,"O.S. emitida com sucesso!");
                    recuperarOs();
                    btnOsAdd.setEnabled(false);
                    btnOsPesq.setEnabled(false);
                    btnOsImprimir.setEnabled(true);
                                       
                }
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
        }
    }

    /**
     * Creates new form TelaOS
     */
    public TelaOS() {
        initComponents();
        conexao = ModuloConexao.conector();
        
    }
    
    // Método para pesquisar OS
    private void pesquisar_os(){
        // A linha abaixo vai criar uma caixa de entrada do tipo JOp para informar o número da O.S.
        String num_os = JOptionPane.showInputDialog(" Número da O.S.");
        String sql= "select os, date_format(data_os,'%d/%m/%y - %H:%i'),tipo,situacao,equipamento,defeito,servico,tecnico,valor,idcliente from ordemservicos where os= " + num_os;
        try {
            pst=conexao.prepareStatement(sql);
            rs=pst.executeQuery();
            if (rs.next()) {
                txtOs.setText(rs.getString(1));
                txtData.setText(rs.getString(2));
                //Setando os radion buttons
                String rbtTipo = rs.getString(3);
                if (rbtTipo.equals("O.S.")) {
                    rbtOs.setSelected(true);
                    tipo="O.S.";
                    
                } else {
                    rbtOrc.setSelected(true);
                    tipo="Orçamento";
                }
                cboOsSit.setSelectedItem(rs.getString(4));
                txtOsEquip.setText(rs.getString(5));
                txtOsDef.setText(rs.getString(6));
                txtOsServ.setText(rs.getString(7));
                txtOsTec.setText(rs.getString(8));
                txtOsValor.setText(rs.getString(9));
                txtCliId.setText(rs.getString(10));
                
                // Evitando problemas,
                // desativar botões quando estiver consultando uma O.S
                btnOsAdd.setEnabled(false);
                txtCliPesquisar.setEnabled(false);
                tblClientes.setVisible(false);
                btnOsExc.setEnabled(true);
                btnOsEdit.setEnabled(true);
                btnOsImprimir.setEnabled(true);
                
                
            } else {
                JOptionPane.showMessageDialog(null," O.S. não cadastrada!");
            }
            
            
        } catch (java.sql.SQLSyntaxErrorException e) {
            JOptionPane.showMessageDialog(null,"O.S. Inválida");
            
        } catch (Exception e2) {
            JOptionPane.showMessageDialog(null,e2);
        }
    }
    
    // Método para alterar O.S.
    private void alterar_os(){
        String sql="update ordemservicos set tipo=?,situacao=?,equipamento=?,defeito=?,servico=?,tecnico=?,valor=? where os=?";
        try {
            pst=conexao.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2,cboOsSit.getSelectedItem().toString());
            pst.setString(3,txtOsEquip.getText());
            pst.setString(4,txtOsDef.getText());
            pst.setString(5,txtOsServ.getText());
            pst.setString(6,txtOsTec.getText());
            // Replace serve para formatar e trocar o ponto pela vírgula, pois o ponto iria dar problemas com o BD
            pst.setString(7, txtOsValor.getText().replace(",", "."));
            pst.setString(8,txtOs.getText());
            
            //Validação dos campos obrigatórios
            if ((txtCliId.getText().isEmpty()) || (txtOsEquip.getText().isEmpty()) || (txtOsDef.getText().isEmpty()) || (cboOsSit.getSelectedItem().equals(" "))) {
                JOptionPane.showMessageDialog(null,"Preencha todos os campos obrigatórios!");
            } else {
                int adicionado = pst.executeUpdate();
                if ( adicionado>0){
                    JOptionPane.showMessageDialog(null,"O.S. alterada com sucesso!");
                    limparcampos_os();
                    
                }
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
        }

    }
    
    // Método responsável por deletar O.S.
    private void deletar_os(){
        int confirma = JOptionPane.showConfirmDialog(null,"Tem certeza que deseja excluir esta O.S.?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION){
            String sql = " delete from ordemservicos where os=?";
            try {
            pst=conexao.prepareStatement(sql);
            pst.setString(1, txtOs.getText());
            int apagado=pst.executeUpdate();
            if (apagado>0){
                JOptionPane.showMessageDialog(null, " O.S. Excluída com sucesso!");
                limparcampos_os();
            }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,e);
            }
            }
 {

            
        }
        
    }
    
    // Método para limpar os campos
    private void limparcampos_os(){
        
                    cboOsSit.setSelectedItem(" ");
                    txtCliId.setText(null);
                    txtOsEquip.setText(null);
                    txtCliPesquisar.setText(null);
                    txtOsDef.setText(null);
                    txtOsServ.setText(null);
                    txtOsTec.setText(null);
                    txtOsValor.setText(null);
                    txtOs.setText(null);
                    txtData.setText(null);
                    //Habilitar objetos
                    btnOsAdd.setEnabled(true);
                    txtCliPesquisar.setEnabled(true);
                    tblClientes.setVisible(true);
                    
                    btnOsExc.setEnabled(false);
                    btnOsEdit.setEnabled(false);
                    btnOsImprimir.setEnabled(false);
                    
                    ((DefaultTableModel) tblClientes.getModel()).setRowCount(0);
    }
    
    
    private void imprimir_os(){
        // Gerar relatório de clientes
        int confirma = JOptionPane.showConfirmDialog(null, "Gerar relatório ?","Atenção", JOptionPane.YES_NO_OPTION);
        if ( confirma == JOptionPane.YES_OPTION) {
            try {
                HashMap filtro = new HashMap();
                filtro.put("os",Integer.parseInt(txtOs.getText()));
                JasperPrint print = JasperFillManager.fillReport("C:/reports/O.S.jasper", filtro, conexao);
                JasperViewer.viewReport(print,false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,e);
            }
        }
    }                                    
    
    private void recuperarOs(){
        String sql = " select max(os) from ordemservicos ";
        try {
            pst=conexao.prepareStatement(sql);
            rs=pst.executeQuery();
            if(rs.next()){
                txtOs.setText(rs.getString(1));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtOs = new javax.swing.JTextField();
        txtData = new javax.swing.JTextField();
        rbtOrc = new javax.swing.JRadioButton();
        rbtOs = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        cboOsSit = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtCliPesquisar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txtOsTec = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtOsEquip = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtOsDef = new javax.swing.JTextField();
        txtOsServ = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtOsValor = new javax.swing.JTextField();
        btnOsAdd = new javax.swing.JButton();
        btnOsPesq = new javax.swing.JButton();
        btnOsEdit = new javax.swing.JButton();
        btnOsExc = new javax.swing.JButton();
        btnOsImprimir = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        txtCliNome = new javax.swing.JTextField();
        txtCliId = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Ordens de Serviço - O.S.");
        setPreferredSize(new java.awt.Dimension(720, 580));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Nº O.S.");

        jLabel2.setText("Data");

        txtOs.setEditable(false);

        txtData.setEditable(false);

        buttonGroup1.add(rbtOrc);
        rbtOrc.setText("Orçamento");
        rbtOrc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtOrcActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtOs);
        rbtOs.setText("Ordem de Serviço");
        rbtOs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtOsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(txtOs, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jLabel2)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(30, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rbtOrc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rbtOs)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtOrc)
                    .addComponent(rbtOs))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel3.setText("Situação");

        cboOsSit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Sem Status Definido", "Aguardando Orçamento", "Orçamento Aprovado", "Orçamento Reprovado", "Aguardando Peça", "Em Execução", "Finalizado" }));

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setText("Pesquisa de clientes");

        txtCliPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCliPesquisarKeyReleased(evt);
            }
        });

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
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
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblClientes);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCliPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(128, 128, 128)))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCliPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE))
        );

        jLabel6.setText("Técnico");

        jLabel7.setText("Equipamento*");

        jLabel8.setText("Defeito/Obs*");

        jLabel9.setText("Serviço");

        jLabel10.setText("Valor Total:");

        txtOsValor.setText("0");
        txtOsValor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOsValorActionPerformed(evt);
            }
        });

        btnOsAdd.setText("Adicionar");
        btnOsAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsAddActionPerformed(evt);
            }
        });

        btnOsPesq.setText("Procurar O.S.");
        btnOsPesq.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsPesq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsPesqActionPerformed(evt);
            }
        });

        btnOsEdit.setText("Editar");
        btnOsEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsEdit.setEnabled(false);
        btnOsEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsEditActionPerformed(evt);
            }
        });

        btnOsExc.setText("Excluir");
        btnOsExc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsExc.setEnabled(false);
        btnOsExc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsExcActionPerformed(evt);
            }
        });

        btnOsImprimir.setText("Imprimir");
        btnOsImprimir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsImprimir.setEnabled(false);
        btnOsImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsImprimirActionPerformed(evt);
            }
        });

        jButton1.setText("Limpar campos");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtCliNome.setEditable(false);

        txtCliId.setEditable(false);
        txtCliId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCliIdActionPerformed(evt);
            }
        });

        jLabel5.setText("*ID");

        jLabel11.setText("Nome");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnOsAdd)
                        .addGap(71, 71, 71)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnOsEdit)
                                .addGap(18, 18, 18)
                                .addComponent(btnOsExc)
                                .addGap(260, 260, 260))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnOsImprimir)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1))))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jLabel8)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtOsDef, javax.swing.GroupLayout.PREFERRED_SIZE, 590, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(56, 56, 56)
                                .addComponent(txtOsServ, javax.swing.GroupLayout.PREFERRED_SIZE, 590, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(txtOsEquip, javax.swing.GroupLayout.PREFERRED_SIZE, 590, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(46, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnOsPesq)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jLabel10)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtOsValor, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(55, 55, 55))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(6, 6, 6)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(cboOsSit, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(jLabel6)
                                                .addGap(18, 18, 18)
                                                .addComponent(txtOsTec, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(txtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtCliNome))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(jLabel5)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel11))))))
                            .addGap(18, 18, 18)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(44, 44, 44)))))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnOsAdd, btnOsEdit, btnOsExc, btnOsImprimir, btnOsPesq, jButton1});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(btnOsPesq)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cboOsSit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtOsTec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCliNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtOsEquip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtOsDef, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtOsServ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtOsValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOsImprimir)
                    .addComponent(jButton1)
                    .addComponent(btnOsAdd)
                    .addComponent(btnOsEdit)
                    .addComponent(btnOsExc))
                .addGap(95, 95, 95))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnOsAdd, btnOsEdit, btnOsExc, btnOsImprimir, btnOsPesq, jButton1});

        setBounds(0, 0, 787, 598);
    }// </editor-fold>//GEN-END:initComponents

    private void txtOsValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOsValorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOsValorActionPerformed

    private void txtCliPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCliPesquisarKeyReleased
        // Chamando método para pesquisar clientes no BD
        pesquisar_clientes();
    }//GEN-LAST:event_txtCliPesquisarKeyReleased

    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        // Chamando método para setar os campos pesquisados nas credenciais dos clientes
        setar_campos();
    }//GEN-LAST:event_tblClientesMouseClicked

    private void rbtOrcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtOrcActionPerformed
        // Atribuindo um texto a variavel tipo selecionado ( radio button )
        tipo = "Orçamento";
    }//GEN-LAST:event_rbtOrcActionPerformed

    private void rbtOsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtOsActionPerformed
        // Atribuindo um texto a variavel tipo selecionado ( radio button )
        tipo = "O.S.";
    }//GEN-LAST:event_rbtOsActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // Programar uma ação ao abrir o forme, marcar o radio button
        rbtOrc.setSelected(true);
        tipo = "Orçamento";
    }//GEN-LAST:event_formInternalFrameOpened

    private void btnOsAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsAddActionPerformed
        // Chamar metodo emitir O.S.
        emitir_os();
    }//GEN-LAST:event_btnOsAddActionPerformed

    private void btnOsPesqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsPesqActionPerformed
        // Chamando método para pesquisar O.S.
        pesquisar_os();
    }//GEN-LAST:event_btnOsPesqActionPerformed

    private void btnOsEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsEditActionPerformed
        // Chamando o método para alterar a O.S.
        alterar_os();
    }//GEN-LAST:event_btnOsEditActionPerformed

    private void btnOsExcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsExcActionPerformed
        // Chamando método excluir O.S.
        deletar_os();
    }//GEN-LAST:event_btnOsExcActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        limparcampos_os();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnOsImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsImprimirActionPerformed
        // Chamando método para imprimir O.S.
        imprimir_os();
    }//GEN-LAST:event_btnOsImprimirActionPerformed

    private void txtCliIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCliIdActionPerformed

    
    private void setar_campos(){
        int setar = tblClientes.getSelectedRow();
        txtCliId.setText(tblClientes.getModel().getValueAt(setar,0).toString());
        txtCliNome.setText(tblClientes.getModel().getValueAt(setar,1).toString());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOsAdd;
    private javax.swing.JButton btnOsEdit;
    private javax.swing.JButton btnOsExc;
    private javax.swing.JButton btnOsImprimir;
    private javax.swing.JButton btnOsPesq;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboOsSit;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbtOrc;
    private javax.swing.JRadioButton rbtOs;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtCliId;
    private javax.swing.JTextField txtCliNome;
    private javax.swing.JTextField txtCliPesquisar;
    private javax.swing.JTextField txtData;
    private javax.swing.JTextField txtOs;
    private javax.swing.JTextField txtOsDef;
    private javax.swing.JTextField txtOsEquip;
    private javax.swing.JTextField txtOsServ;
    private javax.swing.JTextField txtOsTec;
    private javax.swing.JTextField txtOsValor;
    // End of variables declaration//GEN-END:variables
}
