package sdstore.presentationserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import sdstore.presentationserver.presenter.ExceptionPresenter;
import sdstore.presentationserver.service.bridge.ApplicationServerBridge;
import sdstore.presentationserver.service.bridge.RemoteApplicationServer;

public class SdStoreApplication {
	
	private static ApplicationServerBridge serviceBridge = null;
	
	public static void main(String[] args){
		System.out.println("Benvindo a applicação SDStore\n\n"
				+ "Insira o comando:");
	
		
		serviceBridge = new RemoteApplicationServer();
		
		while(true){
			System.out.println("[SDStore]> ");
			String input = readInputFromConsole();
			try {
				processCommand(input);
			} catch (Exception e) {
				ExceptionPresenter.present(e);
			}
		}
	}

	private static void processCommand(String input) {
		String[] token = input.split(" ");
		String command = token[0];
		Integer argCount = token.length - 1;
		
		if(command.equals("lista-categorias")){
			//faz algo
		}else if (command.equals("lista-produtos")) {
//			faz algo
		}else if(command.equals("carrinho")){
//			faz algo
		}else if(command.equals("junta")){
//			faz algo
		}else if(command.equals("limpa")){
//			faz algo
		}else if(command.equals("encomenda")){
//			faz algo
		}else if(command.equals("sair")){
//			faz algo - sai do programa
		}
		
	}

	private static String readInputFromConsole() {
		BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
		String input = null;
		try {
			input = buffer.readLine();
		} catch (Exception e) {
			System.out.println("Erro na tentativa de leitura");
		}
		return input;
	}

}
