package sdstore.presentationserver.presenter;

public class ListaProdutosPresenter {

	public static void present(ProdutoListDto dto){
		List<ProdutoDto> produtoList = dto.getProductList();
		for(ProdutoDto produtoDto : produtoList){
//			System.out.println(produtoDto);
		}
	}
}
