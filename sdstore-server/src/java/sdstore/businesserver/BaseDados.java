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
//	        dao.personBySsn.put
//	            (new Person("Mary Smith", "333-33-3333", "111-11-1111"));
//	        dao.personBySsn.put
//	            (new Person("Jack Smith", "222-22-2222", "111-11-1111"));

	        /* Print the children of a parent using a sub-index and a cursor. */
//	        EntityCursor<Person> children =
//	            dao.personByParentSsn.subIndex("111-11-1111").entities();
//	        try {
//	            for (Person child : children) {
//	                System.out.println(child.ssn + ' ' + child.name);
//	            }
//	        } finally {
//	            children.close();
//	        }

	        /* Get Bob by primary key using the primary index. */
//	        Person bob = dao.personBySsn.get("111-11-1111");
//	        assert bob != null;

	        /*
	         * Create two employers if they do not already exist.  Their primary
	         * keys are assigned from a sequence.
	         */
//	        Employer gizmoInc = dao.employerByName.get("Gizmo Inc");
//	        if (gizmoInc == null) {
//	            gizmoInc = new Employer("Gizmo Inc");
//	            dao.employerById.put(gizmoInc);
//	        }
//	        Employer gadgetInc = dao.employerByName.get("Gadget Inc");
//	        if (gadgetInc == null) {
//	            gadgetInc = new Employer("Gadget Inc");
//	            dao.employerById.put(gadgetInc);
//	        }

	        /* Bob has two jobs and two email addresses. */
//	        bob.employerIds.add(gizmoInc.id);
//	        bob.employerIds.add(gadgetInc.id);
//	        bob.emailAddresses.add("bob@bob.com");
//	        bob.emailAddresses.add("bob@gmail.com");

	        /* Update Bob's record. */
//	        dao.personBySsn.put(bob);

	        /* Bob can now be found by both email addresses. */
//	        bob = dao.personByEmailAddresses.get("bob@bob.com");
//	        assert bob != null;
//	        bob = dao.personByEmailAddresses.get("bob@gmail.com");
//	        assert bob != null;

	        /* Bob can also be found as an employee of both employers. */
//	        EntityIndex<String,Person> employees;
//	        employees = dao.personByEmployerIds.subIndex(gizmoInc.id);
//	        assert employees.contains("111-11-1111");
//	        employees = dao.personByEmployerIds.subIndex(gadgetInc.id);
//	        assert employees.contains("111-11-1111");

	        /*
	         * When an employer is deleted, the onRelatedEntityDelete=NULLIFY for
	         * the employerIds key causes the deleted ID to be removed from Bob's
	         * employerIds.
	         */
//	        dao.employerById.delete(gizmoInc.id);
//	        bob = dao.personBySsn.get("111-11-1111");
//	        assert bob != null;
//	        assert !bob.employerIds.contains(gizmoInc.id);
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
