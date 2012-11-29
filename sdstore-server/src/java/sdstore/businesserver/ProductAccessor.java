package sdstore.businesserver;

import sdstore.businesserver.domain.Produto;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;

public class ProductAccessor {
	
	/* Person accessors */
    PrimaryIndex<String,Produto> produtoById;


    /* Opens all primary and secondary indices. */
    public ProductAccessor(EntityStore store)
        throws DatabaseException {

        produtoById = store.getPrimaryIndex(
            String.class, Produto.class);
    }

}
