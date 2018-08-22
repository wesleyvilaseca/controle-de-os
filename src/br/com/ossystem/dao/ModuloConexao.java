/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ossystem.dao;

/*primeiro passo importar biblioteca para o java trabalhar com o sql*/
import java.sql.*;

/**
 *
 * @author wesley vila seca
 */
public class ModuloConexao {

    //metodo responsável por estabelecer a conexão com o banco
    public static Connection conector() {
        java.sql.Connection conexao = null;

        // a linha abaixo chama o driver importado para a biblioteca no caso o mysql connector
        String driver = "com.mysql.cj.jdbc.Driver";

        //armazenando informações referente ao banco
       
        String url = "jdbc:mysql://localhost:3306/OsSystem";
        String user = "root";
        String password = "";

        //estabelecendo a conexão com BD
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (Exception e) {
            //a linha abaixo informa o erro (quando acontece)
            //System.out.println(e);
            return null;
        }

    }
}
