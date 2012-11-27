package sdstore.businesserver;

import sdstore.businesserver.domain.Produto;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;

public class ProductAccessor {
	
	/* Person accessors */
    PrimaryIndex<String,Produto> produtoById;
//    SecondaryIndex<String,String,Produto> produtoByCategoria;
//    SecondaryIndex<String,String,Produto> produtoByDescricao;


    /* Opens all primary and secondary indices. */
    public ProductAccessor(EntityStore store)
        throws DatabaseException {

        produtoById = store.getPrimaryIndex(
            String.class, Produto.class);

//        produtoByCategoria = store.getSecondaryIndex(
//            produtoById, String.class, "Categoria");
//
//        produtoByDescricao = store.getSecondaryIndex(
//            produtoById, String.class, "Descricao");


    }

}
