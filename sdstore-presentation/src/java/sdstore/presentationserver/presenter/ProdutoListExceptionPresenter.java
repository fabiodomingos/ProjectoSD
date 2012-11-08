package sdstore.presentationserver.presenter;

import sdstore.presentationserver.exception.ProdutoListException;

public class ProdutoListExceptionPresenter {

	public static void present(ProdutoListException e) {
		System.out.println("Nao existem produtos");
		
	}

}
