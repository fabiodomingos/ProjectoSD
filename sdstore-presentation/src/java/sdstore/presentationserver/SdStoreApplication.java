package sdstore.presentationserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import sdstore.presentationserver.exception.PresenterArgumentCountException;
import sdstore.presentationserver.exception.PresenterArgumentException;
import sdstore.presentationserver.exception.PresenterCommandException;
import sdstore.presentationserver.presenter.AjudaPresenter;
import sdstore.presentationserver.presenter.CarrinhoPresenter;
import sdstore.presentationserver.presenter.ExceptionPresenter;
import sdstore.presentationserver.presenter.ListaCategoriasPresenter;
import sdstore.presentationserver.presenter.ListaProdutosPresenter;
import sdstore.presentationserver.service.bridge.ApplicationServerBridge;
import sdstore.presentationserver.service.bridge.RemoteApplicationServer;
import sdstore.presentationserver.service.stubs.CarrinhoDto;
import sdstore.presentationserver.service.stubs.CategoriaListDto;
import sdstore.presentationserver.service.stubs.ProdListDto;
import sdstore.presentationserver.service.stubs.ProdutoDto;

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
	
	private static void processCommand(String input) throws PresenterArgumentCountException, PresenterCommandException,PresenterArgumentException {
		String[] token = input.split(" ");
		String command = token[0];
		Integer argCount = token.length - 1;
		
		if(command.equals("lista-categorias")){
//			falta algo
			Integer validArg = 0;
			checkCommandArg(command,argCount,validArg);
//			String categoria = token[1];
			listCategoriaCommand();
		}else if (command.equals("lista-produtos")) {
			Integer validArg = 1;
			checkCommandArg(command,argCount,validArg);
			String categoria = token[1];
			listProdutosByCategoriaCommand(categoria);
//			falta algo
		}else if(command.equals("carrinho")){
			Integer validArg = 0;
			checkCommandArg(command,argCount,validArg);
			carrinhoCommand();
//			faz algo
		}else if(command.equals("junta")){
			Integer validArg = 2;
			checkCommandArg(command,argCount,validArg);
			String codigo = token[1];
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

	private static void juntaCommand(String codigo, Integer quantidade) {
//		cria o dto
//		ProdutoDto dto = DtoConstructor.createProdutoSelectDto(codigo,quantidade);
		try {
			serviceBridge.Junta(codigo,quantidade);
//			serviceBridge.Junta(dto);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private static void carrinhoCommand() {
//		cria o dto
		try {
			CarrinhoDto result = serviceBridge.Carrinho();
			CarrinhoPresenter.present(result);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	private static void listProdutosByCategoriaCommand(String categoria) {
		System.out.println(categoria);
		ProdListDto result = serviceBridge.listaProdutosCategoria(categoria);
		System.out.println("estou aki");
		ListaProdutosPresenter.present(result);
		
	}

	private static void listCategoriaCommand() {
		
		CategoriaListDto result = serviceBridge.listaCategoria();
		ListaCategoriasPresenter.present(result);
		
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
