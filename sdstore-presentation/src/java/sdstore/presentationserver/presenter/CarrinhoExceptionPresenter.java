package sdstore.presentationserver.presenter;

import sdstore.presentationserver.exception.ProdutoListException;

public class CarrinhoExceptionPresenter {

	public static void present(ProdutoListException e) {
		output("nao existe produtos no carrinho!");
		
	}
	
	private static void output(String msg){
		System.out.println("[SDStore]> " + msg);
	}
	
}
