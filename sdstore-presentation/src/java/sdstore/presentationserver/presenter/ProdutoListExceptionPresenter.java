package sdstore.presentationserver.presenter;

import sdstore.presentationserver.exception.ProdutoListException;

public class ProdutoListExceptionPresenter {

	public static void present(ProdutoListException e) {
		output("nao existe lista de produtos!");
		
	}
	
	private static void output(String msg){
		System.out.println("[SDStore]> " + msg);
	}

}
