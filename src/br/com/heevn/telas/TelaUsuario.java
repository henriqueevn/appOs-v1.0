/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.com.heevn.telas;

/**
 *
 * @author henrique
 */

import java.sql.*;
import br.com.heevn.dal.ModuloConexao;
import com.sun.source.tree.TryTree;
import javax.swing.JOptionPane;

public class TelaUsuario extends javax.swing.JInternalFrame {
    
    // Usando a variavel conexão do DAL ( MODULO QUE CONECTA NO BD )
    Connection conexao = null;
    // Criando variaveis especiais para conexão com o BD,
    // Prepared Statement e ResultSet são frameworks do pacote java.sql,
    // servem para preparar e exutar instruções do SQL
    
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaUsuario
     */
    public TelaUsuario() {
        initComponents();
        conexao = ModuloConexao.conector();
    }
    
        private void consultar(){
        // O código abaixo é responsável por consultar os dados no BD
        // e setar as credenciais na tela de Usuários.
        String sql = "select * from usuarios where id=?";
        try {
            pst=conexao.prepareStatement(sql);
            pst.setString(1,txtId.getText());
            rs=pst.executeQuery();
            if (rs.next()) {
                txtNome.setText(rs.getString(2));
                txtFone.setText(rs.getString(3));
                txtLogin.setText(rs.getString(4));
                txtSenha.setText(rs.getString(5));
                //A linha abaixo se refere a setar o combobox
                cboPerfil.setSelectedItem(rs.getString(6));
            } else {
                JOptionPane.showMessageDialog(null,"Usuário não cadastrado.");
                // As linhas abaixos vão limpar os campos depois da consulta caso não tenha usuários cadastrados no ID pesquisado
                txtNome.setText(null);
                txtFone.setText(null);
                txtLogin.setText(null);
                txtSenha.setText(null);
            }
         } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
         }
        }
        // Método para adicionar usuários
        private void adicionar(){
            String sql="insert into usuarios(id,usuario,fone,login,senha,perfil) values (?,?,?,?,?,?)";
            try {
                pst=conexao.prepareStatement(sql);
                pst.setString(1,txtId.getText());
                pst.setString(2,txtNome.getText());
                pst.setString(3,txtFone.getText());
                pst.setString(4,txtLogin.getText());
                pst.setString(5,txtSenha.getText());
                pst.setString(6,cboPerfil.getSelectedItem().toString());
                // Validação de campos obrigatórios
                if (txtId.getText().isEmpty() || (txtNome.getText().isEmpty() || (txtLogin.getText().isEmpty() || (txtSenha.getText().isEmpty() )))) {
                    JOptionPane.showMessageDialog(null,"Preencha todos os campos obrigatórios!");
                } else {
                
                
                // Estruta abaixo limpa os campos e mostra mensagem de cadastrado com sucesso
                // Linha abaixo (pst.executeUpdate) insere/altera os dados na tabela
                int adicionado = pst.executeUpdate();
                        if (adicionado>0){
                            JOptionPane.showMessageDialog(null,"Usuário cadastrado com sucesso!");
                // As linhas abaixos vão limpar os campos depois de cadastrar o novo usuário
                txtId.setText(null);
                txtNome.setText(null);
                txtFone.setText(null);
                txtLogin.setText(null);
                txtSenha.setText(null);
                        }   
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,e);
            }
        }
        
        
        // Método para alterar dados do usuário
        private void alterar(){
        String sql= "update usuarios set usuario=?, fone=?, login=?, senha=?, perfil=? where id=?";
            try {
                pst=conexao.prepareStatement(sql);
                pst.setString(1,txtNome.getText());
                pst.setString(2,txtFone.getText());
                pst.setString(3,txtLogin.getText());
                pst.setString(4,txtSenha.getText());
                pst.setString(5,cboPerfil.getSelectedItem().toString());
                pst.setString(6,txtId.getText());
                if (txtId.getText().isEmpty() || (txtNome.getText().isEmpty() || (txtLogin.getText().isEmpty() || (txtSenha.getText().isEmpty())))) {
                    JOptionPane.showMessageDialog(null,"Preencha todos os campos obrigatórios!");
                } else {
                // Estruta abaixo limpa os campos e mostra mensagem de que os dados foram alterados com sucesso
                // Linha abaixo (pst.executeUpdate) insere/altera os dados na tabela
                int adicionado = pst.executeUpdate();
                        if (adicionado>0){
                            JOptionPane.showMessageDialog(null,"Dados do usuário alterados com sucesso!");
                // As linhas abaixos vão limpar os campos depois de alterar os dados
                txtId.setText(null);
                txtNome.setText(null);
                txtFone.setText(null);
                txtLogin.setText(null);
                txtSenha.setText(null);
                        }   
                }
            } catch (Exception e) {
            }
    }
        
        // Método responsável por deletar usuários
        private void deletar(){
            // A estrutura abaixo confirma a exclusão do usuário
            int confirma=JOptionPane.showConfirmDialog(null, "Tem certeza que deseja deletar este usuário?","Atenção", JOptionPane.YES_NO_OPTION);
            if (confirma==JOptionPane.YES_OPTION){
                String sql="delete from usuarios where id=?";
                try {
                    pst=conexao.prepareStatement(sql);
                    pst.setString(1,txtId.getText());
                // Estruta abaixo limpa os campos e mostra mensagem de deletado com sucesso
                // Linha abaixo (pst.executeUpdate) insere/altera os dados na tabela
                int apagado = pst.executeUpdate();
                        if (apagado>0){
                            JOptionPane.showMessageDialog(null,"Usuário deletado com sucesso!");
                // As linhas abaixos vão limpar os campos depois de deletar o usuário
                txtId.setText(null);
                txtNome.setText(null);
                txtFone.setText(null);
                txtLogin.setText(null);
                txtSenha.setText(null);
                        }                       
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,e);
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
        txtNome = new javax.swing.JTextField();
        txtFone = new javax.swing.JTextField();
        txtSenha = new javax.swing.JTextField();
        txtLogin = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        cboPerfil = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        btnAddUser = new javax.swing.JButton();
        btnDelUser = new javax.swing.JButton();
        btnConsultUser = new javax.swing.JButton();
        btnEditUser = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Usuários");
        setToolTipText("");
        setPreferredSize(new java.awt.Dimension(640, 480));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Nome*");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Fone");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Login*");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Senha*");

        txtNome.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtFone.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtSenha.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSenhaActionPerformed(evt);
            }
        });

        txtLogin.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("ID*");

        txtId.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        cboPerfil.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cboPerfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "admin", "vendas" }));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 51, 51));
        jLabel6.setText("Perfil*");

        btnAddUser.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnAddUser.setText("Criar");
        btnAddUser.setToolTipText("Adicionar um novo usuário.");
        btnAddUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddUserActionPerformed(evt);
            }
        });

        btnDelUser.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnDelUser.setText("Deletar");
        btnDelUser.setToolTipText("Deletar um usuário.");
        btnDelUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelUserActionPerformed(evt);
            }
        });

        btnConsultUser.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnConsultUser.setText("Consultar");
        btnConsultUser.setToolTipText("Consultar os cadastros existentes.");
        btnConsultUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnConsultUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultUserActionPerformed(evt);
            }
        });

        btnEditUser.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnEditUser.setText("Editar");
        btnEditUser.setToolTipText("Editar um cadastro já existente.");
        btnEditUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditUserActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("Os campos com \" * \" na frente, são obrigatórios.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(214, 214, 214))
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtFone, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNome, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtId, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(63, 63, 63))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAddUser)
                        .addGap(39, 39, 39)
                        .addComponent(btnConsultUser)
                        .addGap(43, 43, 43)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtSenha)
                            .addComponent(cboPerfil, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnEditUser)
                        .addGap(35, 35, 35)
                        .addComponent(btnDelUser)))
                .addContainerGap(56, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAddUser, btnConsultUser, btnDelUser, btnEditUser});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel7)
                .addGap(77, 77, 77)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(188, 188, 188)
                        .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 5, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6)
                                .addComponent(cboPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(64, 64, 64)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(txtLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(58, 58, 58)
                            .addComponent(jLabel4))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5)
                                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(70, 70, 70)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(61, 61, 61)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(txtFone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 112, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddUser)
                    .addComponent(btnDelUser)
                    .addComponent(btnConsultUser)
                    .addComponent(btnEditUser))
                .addGap(56, 56, 56))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAddUser, btnConsultUser, btnDelUser, btnEditUser});

        setBounds(0, 0, 790, 595);
    }// </editor-fold>//GEN-END:initComponents

    private void txtSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSenhaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSenhaActionPerformed

    private void btnConsultUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultUserActionPerformed
        // Chamando método consultar
        consultar();
    }//GEN-LAST:event_btnConsultUserActionPerformed

    private void btnAddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddUserActionPerformed
        // Chamando método adicionar
        adicionar();
    }//GEN-LAST:event_btnAddUserActionPerformed

    private void btnEditUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditUserActionPerformed
        // Chamando método alterar
        alterar();
    }//GEN-LAST:event_btnEditUserActionPerformed

    private void btnDelUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelUserActionPerformed
        // Chamando método deletar
        deletar();
    }//GEN-LAST:event_btnDelUserActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddUser;
    private javax.swing.JButton btnConsultUser;
    private javax.swing.JButton btnDelUser;
    private javax.swing.JButton btnEditUser;
    private javax.swing.JComboBox<String> cboPerfil;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField txtFone;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtLogin;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtSenha;
    // End of variables declaration//GEN-END:variables
}
