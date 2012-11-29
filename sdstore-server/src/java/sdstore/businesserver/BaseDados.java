package sdstore.businesserver;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import sdstore.businesserver.domain.Produto;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityCursor;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.StoreConfig;

public class BaseDados {
	
	
	private Environment env;
	private EntityStore store;
	private ProductAccessor dao;
	
	public BaseDados(File ficheiro) throws DatabaseException{
		
		/* Open a transactional Berkeley DB engine environment. */
		EnvironmentConfig envConfig = new EnvironmentConfig();
		envConfig.setAllowCreate(true);
		envConfig.setTransactional(true);
		env = new Environment(ficheiro, envConfig);

		/* Open a transactional entity store. */
		StoreConfig storeConfig = new StoreConfig();
        storeConfig.setAllowCreate(true);
        storeConfig.setTransactional(true);
        store = new EntityStore(env, "ProductStore", storeConfig);
        
        /* Initialize the data access object. */
        dao = new ProductAccessor(store);   
		
	}
	
	
	public void run(Produto prod)
	        throws DatabaseException {

	        /*
	         * Add a parent and two children using the Person primary index.
	         * Specifying a non-null parentSsn adds the child Person to the
	         * sub-index of children for that parent key.
	         */
	        dao.produtoById.put(prod);
	    }
	
	
	public Produto get(String codigo) throws DatabaseException{
		
		Produto resposta = dao.produtoById.get(codigo);
		return resposta;
	}
	
	public List<Produto> getCategoria() throws DatabaseException{
		EntityCursor<Produto> produtos = dao.produtoById.entities();
		List<Produto> listaProdutos = new ArrayList<Produto>();
		for(Produto prod:produtos){
			listaProdutos.add(prod);
		}
		return listaProdutos;
	}

}
