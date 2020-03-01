package bean;

import java.util.List;


import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import dao.CrudDAO;

public abstract class CrudBean<E, D extends CrudDAO> {
	private String estadoDaTela = "buscar";
	
	private E entidade;
	private List<E> entidades;
	
	public void novo() {
		entidade = criarNovaEntidade();
		mudarParaInserir();
	}
	public void salvar(){
        try {
            getDao().salvar(entidade);
            entidade = criarNovaEntidade();
            adicionarMensagem("Cadastro com sucesso!", FacesMessage.SEVERITY_INFO);
            mudarParaBusca();
        } catch (Exception ex) {
            adicionarMensagem(ex.toString(), FacesMessage.SEVERITY_ERROR);
            adicionarMensagem("Não se pôde efetuar o cadastro!", FacesMessage.SEVERITY_ERROR);
        }
    }
    public void editar(E entidade){
        this.entidade = entidade;
        mudarParaEdita();
    }
    public void deletar(E entidade){
        try {
            getDao().deletar(entidade);
            entidades.remove(entidade);
            adicionarMensagem("Deletado com sucesso!", FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
        	System.err.println(ex);
        }
    }
    public void buscar(){
        if(isBusca() == false){
           mudarParaBusca();
           return;
        }
        try {
            entidades = getDao().buscar();
            if(entidades == null || entidades.size() < 1){
                adicionarMensagem("Não temos nada cadastrado!", FacesMessage.SEVERITY_WARN);
            }
        } catch (Exception ex) {
        	System.err.println(ex);
        }
    }
    
    public void adicionarMensagem(String mensagem, FacesMessage.Severity tipoErro){
        FacesMessage fm = new FacesMessage(tipoErro, mensagem, null);
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }
	
	
	
	//getters e setters
    public E getEntidade() {
        return entidade;
    }

    public void setEntidade(E entidade) {
        this.entidade = entidade;
    }

    public List<E> getEntidades() {
        return entidades;
    }

    public void setEntidades(List<E> entidades) {
        this.entidades = entidades;
    }
    
    //Responsvel por criar os métodos nas classes bean
    public abstract D getDao();
    public abstract E criarNovaEntidade();
    
    //Metodos para controle da tela
    public boolean isInserir(){
        return "inserir".equals(estadoDaTela);
    }
    public boolean isEdita(){
        return "editar".equals(estadoDaTela);
    }
    public boolean isBusca(){
        return "buscar".equals(estadoDaTela);
    }
    public boolean isLista(){
        return "lista".equals(estadoDaTela);
    }
    public boolean isRemover(){
        return "remover".equals(estadoDaTela);
    }
    
    public void mudarParaInserir(){
        estadoDaTela = "inserir";
    }
    public void mudarParaEdita(){
        estadoDaTela = "editar";
    }
    public void mudarParaBusca(){
        estadoDaTela = "buscar";
    }
    public void mudarParaRemover(){
        estadoDaTela = "remover";
    }
    public void mudarParaLista(){
        estadoDaTela = "lista";
    }
}