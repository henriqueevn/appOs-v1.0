/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.heevn.dal;

import java.sql.*;

/**
 *
 * @author henrique
 */

public class ModuloConexao {
    // Responsavel por estabelecer conexão com o BD
    public static Connection conector(){
        java.sql.Connection conexao = null;
        
        
        // A linha abaixo chama o driver
        String driver = "com.mysql.jdbc.Driver";
        
        
        // Armazenar infos BD
        String url="jdbc:mysql://localhost:3306/dbinfox?characterEncoding=utf-8";
        
        // Nome de usuário do BD
        String user = "dba";
        
        // Senha do BD
        String password = "henrique@123";
        
        
        // Estabelendo conexão com o banco
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url,user,password);
            return conexao;
        } catch (Exception e) {
            // A linha abaixo serve para demonstrar o erro caso necessário, basta descomentar.
            //System.out.println(e);
            
            return null;
        }
            
    }
    
}
