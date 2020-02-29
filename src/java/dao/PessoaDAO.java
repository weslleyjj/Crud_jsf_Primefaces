package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entidades.Pessoa;
import persistencia.Conexao;
/**
 *
 * @author WeslleyJJ
 */
public class PessoaDAO implements CrudDAO<Pessoa> {
    Conexao con = new Conexao();
    private final String ADDPESSOA = "INSERT INTO pessoa (nome, endereco, email, cpf, telefone, idade) VALUES (?, ?, ?, ?, ?, ?)";
    private final String UPDATEPESSOA = "UPDATE pessoa SET nome = ?, endereco = ?, email = ?, telefone = ? WHERE codigo = ?";
    private final String RMPESSOA = "DELETE FROM pessoa WHERE cpf = ?";
    private final String LISTPESSOA = "SELECT * FROM pessoa";
    private final String SEARCHPESSOA = "SELECT * FROM pessoa WHERE (cpf) LIKE ?";
    
    public boolean addPessoa(Pessoa p){
        try {
            //conectar ao banco e preparar a string do sql pelo objeto de PreparedStatement
            con.conecta();
            PreparedStatement sqlquery;
            
            sqlquery = con.getConexao().prepareStatement(ADDPESSOA);
            
            //atribuir os parâmetros que irão substituir os '?' das queries sql em ordem
            sqlquery.setString(1, p.getNome());            
            sqlquery.setString(2, p.getEndereco());
            sqlquery.setString(3, p.getEmail());
            sqlquery.setInt(4, p.getCpf());
            sqlquery.setInt(5, p.getTelefone());
            sqlquery.setInt(6, p.getIdade());
            //executar
            sqlquery.execute();
            con.desconecta();
            return true;
        } catch (SQLException ex) {
            System.err.println(ex);
            return false;
        }
        
    }
    
    public boolean updatePessoa(Pessoa p){
        try{
            con.conecta();
            PreparedStatement sqlquery;
            sqlquery = con.getConexao().prepareStatement(UPDATEPESSOA);
            sqlquery.setString(1, p.getNome());            
            sqlquery.setString(2, p.getEndereco());
            sqlquery.setString(3, p.getEmail());
            sqlquery.setInt(4, p.getTelefone());
            sqlquery.setInt(5, getCodigo(p.getCpf()));
            
            sqlquery.execute();
            
            con.desconecta();
            
            return true;
        } catch (SQLException ex){
            System.out.println("Exceção no update");
            System.err.println(ex);
            return false;
        }
    }
    
    public boolean removePessoa(int cpf){
        try {
            con.conecta();
            PreparedStatement remover;            
            remover = con.getConexao().prepareStatement(RMPESSOA);
            remover.setInt(1, cpf);            
            remover.execute();           
            con.desconecta();
            return true;
        } catch(SQLException ex){
            System.err.println(ex);
            return false;
        }
    }
    public ArrayList<Pessoa> listAll(){
        ArrayList<Pessoa> lista = new ArrayList<>();
        try {
            con.conecta();
            PreparedStatement listar;
            listar = con.getConexao().prepareStatement(LISTPESSOA);
            ResultSet rs = listar.executeQuery();
            while(rs.next()){
                Pessoa i = new Pessoa(rs.getString("NOME"), rs.getString("ENDERECO"), rs.getString("EMAIL"), rs.getInt("cpf"), rs.getInt("TELEFONE"), rs.getInt("IDADE") );
                lista.add(i);
            }
            con.desconecta();
        } catch (SQLException ex){
            System.err.println(ex);            
        }
        return lista;
    }
    public ArrayList<Pessoa> searchItem(int cpf){
        ArrayList<Pessoa> lista = new ArrayList<>();
        try {
            con.conecta();
            PreparedStatement listar;
            listar = con.getConexao().prepareStatement(SEARCHPESSOA);
            listar.setInt(1, cpf);
            ResultSet rs = listar.executeQuery();
            while(rs.next()){
            	Pessoa i = new Pessoa(rs.getString("NOME"), rs.getString("ENDERECO"), rs.getString("EMAIL"), rs.getInt("cpf"), rs.getInt("TELEFONE"), rs.getInt("IDADE") );
                lista.add(i);
            }
            con.desconecta();
        } catch (SQLException ex){
            System.err.println(ex);            
        }
        return lista;
    }
    
    public int getCodigo(int p){
        try {
            con.conecta();
            PreparedStatement buscarid;
            buscarid = con.getConexao().prepareStatement(SEARCHPESSOA);
            //System.out.println("1");
            
            buscarid.setInt(1, p);
            //System.out.println("2");
            ResultSet rs = buscarid.executeQuery();
            //System.out.println("3");
            if(rs.next()){
                //System.out.println(rs.getInt("ID_USUARIO"));
                               
                return rs.getInt("CODIGO");
            }
            
           
            
            
        } catch (SQLException ex) {
            System.out.println("Exceção no getCodigo");
            System.err.println(ex);
            
        }
        return 0;
    }

	@Override
	public void salvar(Pessoa entidade) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remover(Pessoa entidade) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Pessoa> buscar() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}