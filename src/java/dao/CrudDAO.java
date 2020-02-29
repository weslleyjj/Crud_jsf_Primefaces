package dao;

import java.util.List;

public interface CrudDAO<E> { 
	
	public void salvar(E entidade) throws Exception;
	
	public void remover(E entidade) throws Exception;
	
	public List<E> buscar() throws Exception;
	
}