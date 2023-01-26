/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.com.heevn.telas;

import java.sql.*;
import br.com.heevn.dal.ModuloConexao;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
//A linha abaixo importa recurso da biblioteca
import net.proteanit.sql.DbUtils;

/**
 *
 * @author henri
 */
public class TelaCliente extends javax.swing.JInternalFrame {
    Connection conexao=null;
    PreparedStatement pst=null;
    ResultSet rs=null;

    /**
     * Creates new form TelaCliente
     */
    public TelaCliente() {
        initComponents();
        conexao=ModuloConexao.conector();
    }
    
    // Método para adicionar clientes
        private void adicionar(){
            String sql="insert into cliente (nome,endereco,fone,email) values (?,?,?,?)";
            try {
                pst=conexao.prepareStatement(sql);
                pst.setString(1,txtCliNome.getText());
                pst.setString(2,txtCliEndereco.getText());
                pst.setString(3,txtCliFone.getText());
                pst.setString(4,txtCliEmail.getText());
                //validação de campos obrigatórios
                if (txtCliNome.getText().isEmpty() || (txtCliFone.getText().isEmpty())) {
                    JOptionPane.showMessageDialog(null,"Preencha todos os campos obrigatórios!");
                } else {
                
                
                //Estruta abaixo limpa os campos e mostra mensagem de cadastrado com sucesso
                // Linha abaixo (pst.executeUpdate) insere/altera os dados na tabela
                int adicionado = pst.executeUpdate();
                        if (adicionado>0){
                            JOptionPane.showMessageDialog(null,"Cliente cadastrado com sucesso!");
                // As linhas abaixos vão limpar os campos depois da conuslta não ter usuários cadastrados no ID pesquisado
                txtCliNome.setText(null);
                txtCliEndereco.setText(null);
                txtCliFone.setText(null);
                txtCliEmail.setText(null);
                        }   
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,e);
            }
        }
        
        //Metódo para pesquisar clientes(utiliza biblioteca)
        private void pesquisa_cliente(){
            String sql="select id as ID, nome as Nome, endereco as Endereço, fone as Contato, email as Email from cliente where nome like ?";
            try {
                pst=conexao.prepareStatement(sql);
                //passando conteudo da caixa de pesquisa para o "?"
                pst.setString(1,txtCliProcurar.getText() + "%");
                // % (Porcentagem acima para terminar a query
                rs=pst.executeQuery();
                
                tblClientes.setModel(DbUtils.resultSetToTableModel(rs));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,e);
            }
        }
        
        
        
        // Metodo para preencher a tabela com o cliente selecionado
        private void setar_campos(){
            int setar = tblClientes.getSelectedRow();
            txtCliId.setText(tblClientes.getModel().getValueAt(setar,0).toString());
            txtCliNome.setText(tblClientes.getModel().getValueAt(setar,1).toString());
            txtCliEndereco.setText(tblClientes.getModel().getValueAt(setar, 2).toString());
            txtCliFone.setText(tblClientes.getModel().getValueAt(setar, 3).toString());
            txtCliEmail.setText(tblClientes.getModel().getValueAt(setar, 4).toString());
            btnAddCli.setEnabled(false);
        }
        
        
        
        
         private void editar(){
                     String sql= "update cliente set nome=?, endereco=?, fone=?, email=? where Id=?";
            try {
                pst=conexao.prepareStatement(sql);
                pst.setString(1,txtCliNome.getText());
                pst.setString(2,txtCliEndereco.getText());
                pst.setString(3,txtCliFone.getText());
                pst.setString(4,txtCliEmail.getText());
                pst.setString(5,txtCliId.getText());
                                //validação de campos obrigatórios
                if (txtCliNome.getText().isEmpty() || (txtCliFone.getText().isEmpty())) {
                    JOptionPane.showMessageDialog(null,"Preencha todos os campos obrigatórios!");
                } else {
                
                
                //Estruta abaixo limpa os campos e mostra mensagem de cadastrado com sucesso
                // Linha abaixo (pst.executeUpdate) insere/altera os dados na tabela
                int adicionado = pst.executeUpdate();
                        if (adicionado>0){
                            JOptionPane.showMessageDialog(null,"Dados do cliente alterado(s) com sucesso!");
                // As linhas abaixos vão limpar os campos depois da conuslta não ter usuários cadastrados no ID pesquisado
                txtCliNome.setText(null);
                txtCliEndereco.setText(null);
                txtCliFone.setText(null);
                txtCliEmail.setText(null);
                btnAddCli.setEnabled(true);
                        }   
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,e);
            }
         }
         
         
         private void deletar(){
                         //A estrutura abaixo confirma a exclusão do usuário
            int confirma=JOptionPane.showConfirmDialog(null, "Tem certeza que deseja deletar este Cliente?","Atenção", JOptionPane.YES_NO_OPTION);
            if (confirma==JOptionPane.YES_OPTION){
                String sql="delete from cliente where id=?";
                try {
                    pst=conexao.prepareStatement(sql);
                    pst.setString(1,txtCliId.getText());
                //Estruta abaixo limpa os campos e mostra mensagem de cadastrado com sucesso
                // Linha abaixo (pst.executeUpdate) insere/altera os dados na tabela
                int apagado = pst.executeUpdate();
                        if (apagado>0){
                            JOptionPane.showMessageDialog(null,"Cliente deletado com sucesso!");
                // As linhas abaixos vão limpar os campos depois de alterar os dados
                            limpar();
                        }                       
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,e);
                }
            }
         }
         
         // Método para limpar campos depois de consultar
         private void limpar(){
                txtCliProcurar.setText(null);
                txtCliId.setText(null);
                txtCliNome.setText(null);
                txtCliFone.setText(null);
                txtCliEndereco.setText(null);
                txtCliEmail.setText(null);
                ((DefaultTableModel) tblClientes.getModel()).setRowCount(0);
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
        txtCliNome = new javax.swing.JTextField();
        txtCliEndereco = new javax.swing.JTextField();
        txtCliFone = new javax.swing.JTextField();
        txtCliEmail = new javax.swing.JTextField();
        btnAddCli = new javax.swing.JButton();
        btnRemoveCli = new javax.swing.JButton();
        btnEditCli = new javax.swing.JButton();
        txtCliProcurar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txtCliId = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setPreferredSize(new java.awt.Dimension(790, 595));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("*Campos obrigatórios");

        jLabel2.setText("Nome*");

        jLabel3.setText("Endereço");

        jLabel4.setText("Telefone*");

        jLabel5.setText("E-mail");

        btnAddCli.setText("Adicionar");
        btnAddCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCliActionPerformed(evt);
            }
        });

        btnRemoveCli.setText("Remover");
        btnRemoveCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveCliActionPerformed(evt);
            }
        });

        btnEditCli.setText("Editar");
        btnEditCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditCliActionPerformed(evt);
            }
        });

        txtCliProcurar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCliProcurarKeyReleased(evt);
            }
        });

        tblClientes = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Nome", "Endereço", "Contato", "E-mail"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblClientes.setFocusable(false);
        tblClientes.getTableHeader().setReorderingAllowed(false);
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblClientes);

        jLabel6.setText("ID");

        txtCliId.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel2)
                    .addComponent(jLabel6))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtCliFone, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE)
                    .addComponent(txtCliEndereco, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCliId, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCliNome, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCliEmail))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(145, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(326, 326, 326))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtCliProcurar, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(80, 80, 80))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnAddCli)
                        .addGap(45, 45, 45)
                        .addComponent(btnEditCli)
                        .addGap(48, 48, 48)
                        .addComponent(btnRemoveCli)
                        .addGap(208, 208, 208))))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAddCli, btnEditCli, btnRemoveCli});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addComponent(txtCliProcurar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCliNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtCliEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtCliFone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtCliEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddCli)
                    .addComponent(btnRemoveCli)
                    .addComponent(btnEditCli))
                .addGap(27, 27, 27))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAddCli, btnEditCli, btnRemoveCli});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCliActionPerformed
    // Chamando método para adicionar clientes
    adicionar();
    }//GEN-LAST:event_btnAddCliActionPerformed

    private void txtCliProcurarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCliProcurarKeyReleased
        // O evento abaixo pesquisa o cliente em tempo real que o nome é editado
        pesquisa_cliente();
    }//GEN-LAST:event_txtCliProcurarKeyReleased

    
    // Evento que irá preencher a tabela com o click do mouse nos clientes
    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        // Chamando método para setar os campos
        setar_campos();
    }//GEN-LAST:event_tblClientesMouseClicked

    private void btnEditCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditCliActionPerformed
        // Chamando método editar
        editar();
    }//GEN-LAST:event_btnEditCliActionPerformed

    private void btnRemoveCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveCliActionPerformed
        // Chamando método deletar
        deletar();
    }//GEN-LAST:event_btnRemoveCliActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddCli;
    private javax.swing.JButton btnEditCli;
    private javax.swing.JButton btnRemoveCli;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtCliEmail;
    private javax.swing.JTextField txtCliEndereco;
    private javax.swing.JTextField txtCliFone;
    private javax.swing.JTextField txtCliId;
    private javax.swing.JTextField txtCliNome;
    private javax.swing.JTextField txtCliProcurar;
    // End of variables declaration//GEN-END:variables
}
