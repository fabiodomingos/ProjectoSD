package sdstore.businesserver.service;

import java.util.List;

import sdstore.businesserver.domain.Catalogo;
import sdstore.businesserver.domain.Produto;
import sdstore.businesserver.service.dto.ProdutoDto;

public class PedeProdutoService extends PortalService{

	private String _codigo;
	private Integer _quantidade;
	private ProdutoDto result;
	
	public PedeProdutoService(String codigo,Integer quantidade){
		_codigo=codigo;
		_quantidade=quantidade;
	}
	
	@Override
	public final void dispatch() {
//		System.out.println("Aqui entrei");
//		List<Produto> lista = Catalogo.getProdutoList();
//		
//		for(Produto prod : lista){
//			System.out.println("Agarreiiiiiiiiii "+prod.getId());
//			if(prod.getId().equals(_codigo)){
//				System.out.println("aquiiiiiiiiiiiiiiiiiii"+prod.getId());
//				result.setId(prod.getId());
//				result.setQuantidade(prod.getQuantidade());
//				result.setPreco(prod.getPreco());
//				result.setCategoria(prod.getCategoria());
//				result.setDescricao(prod.getDescricao());
//			}
//		}
		
		Produto prodPedido = Catalogo.getProduto(_codigo);
		System.out.println(prodPedido);
		System.out.println("IDPEDIDO "+prodPedido.getId());
		ProdutoDto resultado = new ProdutoDto();
		System.out.println("passei");
		resultado.setId(prodPedido.getId());
//		System.out.println("ENVIA "+result.getId());
		resultado.setQuantidade(prodPedido.getQuantidade());
//		System.out.println("ENVIA "+result.getQuantidade());
		resultado.setPreco(prodPedido.getPreco());
//		System.out.println("ENVIA "+result.getPreco());
		resultado.setCategoria(prodPedido.getCategoria());
//		System.out.println("ENVIA "+result.getCategoria());
		resultado.setDescricao(prodPedido.getDescricao());
//		System.out.println("ENVIA "+result.getDescricao());
		result = resultado;
	}
	

	public ProdutoDto getProdutoDto(){
		return this.result;
	}
}
