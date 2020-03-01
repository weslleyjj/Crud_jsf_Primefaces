package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author weslleyJJ
 */
public class Conexao {
    private static final String USUARIO = "admin";
    private static final String SENHA = "admin";
    private static final String CAMINHO = "jdbc:h2:~/jdbc_h2/banco_pessoas;INIT=runscript from '~/jdbc_h2/banco.sql'";
    private static final String DRIVER = "org.h2.Driver";
    private Connection conexao;
    
    public void conecta(){
        try{
            Class.forName(DRIVER);
            conexao = DriverManager.getConnection(CAMINHO, USUARIO, SENHA);
            
            
        } catch ( ClassNotFoundException  | SQLException e) {
            System.err.println(e);
            
        };
    }
    
    public void desconecta(){
        try{
            conexao.close();
            System.out.println("Desconectou!");
        }catch (SQLException ex){
            System.err.println(ex);
        }
    }
    
    public Connection getConexao(){
        return conexao;
    }
    
            
}