package dao;

import entidades.Pessoa;


import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import persistencia.Conexao;

/**
 *
 * @author Danilo Souza Almeida
 */
public class PessoaDAO implements CrudDAO<Pessoa>{
    
    Conexao con = new Conexao();
    private final String ADDPESSOA = "INSERT INTO pessoa (nome, endereco, email, idade, telefone, cpf) VALUES (?, ?, ?, ?, ?, ?)";
    private final String UPDATEPESSOA = "UPDATE pessoa SET nome = ?, endereco = ?, email = ?, idade = ?, telefone = ? WHERE cpf = ?";
    private final String RMPESSOA = "DELETE FROM pessoa WHERE cpf = ?";
    private final String LISTPESSOA = "SELECT * FROM pessoa";
    
    
    @Override
    public void salvar(Pessoa p) throws Exception{
        try {
            
            PreparedStatement ps;
            
            if(buscaCpf(p.getCpf()) == false){
                
                con.conecta();
                ps = con.getConexao().prepareStatement(ADDPESSOA); 
                
                
                
            } else {
                con.conecta();
                ps = con.getConexao().prepareStatement(UPDATEPESSOA);
                
            }
            ps.setString(1, p.getNome());
            ps.setString(2, p.getEndereco());
            ps.setString(3, p.getEmail());
            ps.setInt(4, p.getTelefone());
            ps.setInt(5, p.getTelefone());
            ps.setInt(6, p.getCpf());          
            ps.execute();
            con.desconecta();
        } catch (SQLException ex) {
            throw new Exception("Erro ao tentar salvar!", ex);
        }
    }
    
    @Override
    public void deletar(Pessoa p) throws Exception{
        try {
            con.conecta();
            PreparedStatement remover;            
            remover = con.getConexao().prepareStatement(RMPESSOA);
            remover.setInt(1, p.getCpf());            
            remover.execute();           
            con.desconecta();
            
        } catch(SQLException ex){
            System.err.println(ex);
            
        }
    }
    
    @Override
    public List<Pessoa> buscar() throws Exception{
        try {
            con.conecta();
            PreparedStatement ps = con.getConexao().prepareStatement(LISTPESSOA);
            ResultSet rs = ps.executeQuery();
            List<Pessoa> pessoas = new ArrayList<>();
            while(rs.next()){
                Pessoa p = new Pessoa();
                p.setCpf(rs.getInt("cpf"));
                p.setNome(rs.getString("nome"));
                p.setEndereco(rs.getString("endereco"));
                p.setEmail(rs.getString("email"));
                p.setTelefone(rs.getInt("telefone"));
                p.setIdade(rs.getInt("idade"));
                pessoas.add(p);
            }
            con.desconecta();
            return pessoas;
            
        } catch (SQLException ex) {
            
            throw new Exception("Erro ao buscar pessoas!",ex);
        }
               
    }
    private boolean buscaCpf(int cpf) throws Exception{
        try{
            con.conecta();
            PreparedStatement ps = con.getConexao().prepareStatement(LISTPESSOA);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                if(rs.getInt("cpf") == cpf){
                    con.desconecta();
                    return true;
                }
            }
            
        }catch (SQLException ex){
            System.out.println(ex);
        }
        con.desconecta();
        return false;
    }
    
}
