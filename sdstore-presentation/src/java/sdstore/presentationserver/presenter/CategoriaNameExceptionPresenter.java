package sdstore.presentationserver.presenter;

import sdstore.presentationserver.exception.CategoriaNameException;


public class CategoriaNameExceptionPresenter {

	public static void present(CategoriaNameException e) {
		output("nao existe a categoria " + e.getNome());
		
	}
	
	private static void output(String msg){
		System.out.println("[SDStore]> " + msg);
	}
	
}
