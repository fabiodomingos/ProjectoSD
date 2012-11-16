package sdstore.presentationserver.presenter;


import sdstore.presentationserver.exception.QuantidadeException;

public class QuantidadeExceptionPresenter {

	public static void present(QuantidadeException e) {
		output("nao existe a quantidade " + e.getQuantidade());
		
	}
	
	private static void output(String msg){
		System.out.println("[SDStore]> " + msg);
	}
	
}
