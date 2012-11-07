package sdstore.businesserver.service;


import sdstore.businesserver.domain.Catalogo;
import sdstore.businesserver.domain.Produto;
import sdstore.businesserver.service.dto.ProdutoDto;

public class PedeProdutoService extends PortalService{

	private String _codigo;
	private ProdutoDto result;
	
	public PedeProdutoService(String codigo){
		_codigo=codigo;
	}
	
	@Override
	public final void dispatch() {
		Produto prodPedido = Catalogo.getProduto(_codigo);
		ProdutoDto resultado = new ProdutoDto();
		resultado.setId(prodPedido.getId());
		resultado.setQuantidade(prodPedido.getQuantidade());
		resultado.setPreco(prodPedido.getPreco());
		resultado.setCategoria(prodPedido.getCategoria());
		resultado.setDescricao(prodPedido.getDescricao());
		result = resultado;
	}
	
	public ProdutoDto getProdutoDto(){
		return this.result;
	}
}
