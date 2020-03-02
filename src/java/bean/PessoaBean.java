package bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import dao.PessoaDAO;
import entidades.Pessoa;

@ManagedBean
@SessionScoped
public class PessoaBean extends CrudBean<Pessoa, PessoaDAO> {

    private PessoaDAO pessoaDAO;
    
    @Override
    public PessoaDAO getDao() {
        if(pessoaDAO == null){
            pessoaDAO = new PessoaDAO();
        }
        return pessoaDAO;
    }

    @Override
    public Pessoa criarNovaEntidade() {
        return new Pessoa();
    }

}