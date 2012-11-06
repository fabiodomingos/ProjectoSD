package sdstore.businesserver.service;

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
		Produto prodPedido = Catalogo.getProduto(_codigo);

		result.setId(prodPedido.getId());
		result.setQuantidade(prodPedido.getQuantidade());
		result.setPreco(prodPedido.getPreco());
		result.setCategoria(prodPedido.getCategoria());
		result.setDescricao(prodPedido.getDescricao());
	}
	

	public ProdutoDto getProdutoDto(){
		return this.result;
	}
}
