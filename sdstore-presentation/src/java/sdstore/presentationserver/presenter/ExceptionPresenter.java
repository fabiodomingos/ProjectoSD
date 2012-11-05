package sdstore.presentationserver.presenter;

import sdstore.presentationserver.exception.PresenterArgumentCountException;
import sdstore.presentationserver.exception.PresenterArgumentException;
import sdstore.presentationserver.exception.PresenterCommandException;

public class ExceptionPresenter {

	public static void present(PresenterCommandException e) {
		output("Comando desconhecido.");
	}
	
	public static void present(PresenterArgumentCountException e){
		output("Numero invalido de argumentos.");
	}
	
	public static void present(PresenterArgumentException e){
		output("Argumentos invalidos");
	}

	private static void output(String msg){
		System.out.println("[SDStore]> " + msg);
	}
}
