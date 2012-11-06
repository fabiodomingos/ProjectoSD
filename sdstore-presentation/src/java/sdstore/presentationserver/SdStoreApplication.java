package sdstore.presentationserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import sdstore.presentationserver.exception.PresenterArgumentCountException;
import sdstore.presentationserver.exception.PresenterArgumentException;
import sdstore.presentationserver.exception.PresenterCommandException;
import sdstore.presentationserver.presenter.AjudaPresenter;
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
			} catch (PresenterCommandException e) {
				ExceptionPresenter.present(e);
			}catch(PresenterArgumentCountException e){
				ExceptionPresenter.present(e);
			}catch(PresenterArgumentException e){
				ExceptionPresenter.present(e);
			}
		}
	}
	
//	nao faz nada
//	private static String readFromConsole(){
//		BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
//		String input = null;
//		try {
//			input = buffer.readLine();
//		} catch (IOException e) {
//			System.out.println("Erro de leitura");
//			System.out.println(e.getMessage());
//			System.exit(1);
//		}
//		return input;
//	}

	private static void processCommand(String input) throws PresenterArgumentCountException, PresenterCommandException,PresenterArgumentException {
		String[] token = input.split(" ");
		String command = token[0];
		Integer argCount = token.length - 1;
		
		if(command.equals("lista-categorias")){
//			falta algo
			Integer validArg = 1;
			checkCommandArg(command,argCount,validArg);
//			String categoria = token[1];
			listCategoriaCommand();
		}else if (command.equals("lista-produtos")) {
			Integer validArg = 2;
			checkCommandArg(command,argCount,validArg);
			String categoria = token[1];
			listProdutosByCategoriaCommand(categoria);
//			falta algo
		}else if(command.equals("carrinho")){
			Integer validArg = 1;
			checkCommandArg(command,argCount,validArg);
			carrinhoCommand();
//			faz algo
		}else if(command.equals("junta")){
			Integer validArg = 3;
			checkCommandArg(command,argCount,validArg);
			Integer codigo = Integer.parseInt(token[1]);
			Integer quantidade = Integer.parseInt(token[2]);
			juntaCommand(codigo,quantidade);
//			faz algo
		}else if(command.equals("limpa")){
			Integer validArg = 1;
			checkCommandArg(command,argCount,validArg);
			limpaCommand();
//			faz algo
		}else if(command.equals("encomenda")){
			Integer validArg = 1;
			checkCommandArg(command,argCount,validArg);
			encomendaCommand();
//			faz algo
		}else if(command.equals("ajuda")){
			ajudaCommand();
		}else if(command.equals("sair")){
			exit();
		}else{
			throw new PresenterCommandException(command);
		}
		
	}

	private static void ajudaCommand() {
		AjudaPresenter.present();
	}

	private static void exit() {
		System.out.println("A sair do programa");
		System.exit(0);
	}

	private static void encomendaCommand() {
//		Cria o dto
//		ClienteSelectionDto = DtoConstructor.createEncomendaDto(cliente);
		try {
//			serviceBridge.Encomenda(dto);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	private static void limpaCommand() {
//		cria o dto
//		ClienteSelectionDto dto = DtoConstructor.createLimpaDto(cliente);
		try {
//			serviceBridge.Limpa(dto);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	private static void juntaCommand(Integer codigo, Integer quantidade) {
//		cria o dto
//		ProdutoSelectDto dto = DtoConstructor.createProdutoSelectDto(codigo,quantidade);
		try {
//			serviceBridge.Junta(dto);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private static void carrinhoCommand() {
//		cria o dto
//		ClienteSelectionDto dto = DtoConstructor.createCarrinhoDto(cliente);
		try {
//			ProdutoListDto resultado = serviceBridge.Carrinho(dto);
//			Carrinho.present(resultado);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	private static void listProdutosByCategoriaCommand(String categoria) {
//		cria o dto
//		CategoriaDto dto = DtoConstructor.createCategoriaDto(categoria);
		try {
//			ProdutoListDto resultado = serviceBridge.ListaProdutosCategoria(dto);
//			ListaProdutosPresenter.present(resultado);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	private static void listCategoriaCommand() {
//		cria o dto
//		ClienteSelectionDto dto = DtoConstructor.createCategoriaDto(cliente);
		try {
//			CategoriaListDto resultado = serviceBridge.ListaCategoria(dto);
//			ListaCategoriasPresenter.present(resultado);
		} catch (Exception e) {
//			ExceptionPresenter.present(e);
		}
		
	}

	private static void checkCommandArg(String command, Integer argCount,
			Integer validArg) throws PresenterArgumentCountException {
		if(argCount != validArg){
			throw new PresenterArgumentCountException(command,argCount);
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
