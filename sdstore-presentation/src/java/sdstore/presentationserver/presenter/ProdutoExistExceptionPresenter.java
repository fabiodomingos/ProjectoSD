package sdstore.presentationserver.presenter;

import sdstore.presentationserver.exception.ProdutoExistException;


public class ProdutoExistExceptionPresenter {
	
	public static void present(ProdutoExistException e) {
		output("nao existe o produto a juntar!");
		
	}
	
	private static void output(String msg){
		System.out.println("[SDStore]> " + msg);
	}

}
