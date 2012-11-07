package sdstore.presentationserver.presenter;

import java.util.List;

import sdstore.presentationserver.service.stubs.CarrinhoDto;
import sdstore.presentationserver.service.stubs.ProdutoDto;

public class CarrinhoPresenter {

	public static void present(CarrinhoDto result) {
		List<ProdutoDto> lista = result.getListaDto();
		
		System.out.println("Imprimir carrinho");
		
		for(ProdutoDto produto : lista){
			System.out.println(produto.getId() + " "+produto.getCategoria()+" "+produto.getDescricao()+" "+produto.getQuantidade()+" "+produto.getPreco()*produto.getQuantidade());
		}
		
		System.out.println("Preco Total: "+result.getTotalPreco());
		
	}

}
