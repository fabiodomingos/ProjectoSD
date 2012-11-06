package sdstore.presentationserver.presenter;

public class AjudaPresenter {

	public static void present(){
		System.out.println("Comandos disponiveis");
		System.out.println("lista-categorias -> Apresenta lista das categorias.");
		System.out.println("lista-produtos <categoria> -> Apresenta os produtos pela categoria pretendida");
		System.out.println("carrinho -> Apresenta o conteudo do carrinho");
		System.out.println("junta <codigo produto> <quantidade> -> Junta o produto indicado ao carrinho de compras");
		System.out.println("limpa -> Esvazia o carrinho de compras");
		System.out.println("encomenda -> Finaliza a compra");
		System.out.println("sair -> sai do programa");
	}
}
