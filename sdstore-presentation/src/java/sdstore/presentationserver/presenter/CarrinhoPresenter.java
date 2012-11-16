package sdstore.presentationserver.presenter;

import java.text.DecimalFormat;
import java.util.List;

import sdstore.presentationserver.service.stubs.CarrinhoDto;
import sdstore.presentationserver.service.stubs.ProdutoDto;

public class CarrinhoPresenter {

	public static void present(CarrinhoDto result) {
		List<ProdutoDto> lista = result.getListaDto();
		
		System.out.println("Imprimir carrinho");
		
		for(ProdutoDto produto : lista){
			DecimalFormat precoTotal = new DecimalFormat("0.00");
			String str = precoTotal.format(produto.getPreco()*produto.getQuantidade());
			System.out.println(produto.getId() + " "+produto.getCategoria()+" "+produto.getDescricao()+" "+produto.getQuantidade()+" "+str);
		}
		
		DecimalFormat precoCarrinho = new DecimalFormat("0.00");
		String str2 = precoCarrinho.format(result.getTotalPreco());
		System.out.println("Preco Total: "+str2);
		
	}

}
