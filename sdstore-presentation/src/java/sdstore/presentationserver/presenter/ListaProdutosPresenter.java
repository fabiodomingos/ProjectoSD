package sdstore.presentationserver.presenter;

import java.util.List;

import sdstore.presentationserver.service.stubs.ProdListDto;
import sdstore.presentationserver.service.stubs.ProdutoDto;

public class ListaProdutosPresenter {

	public static void present(ProdListDto dto){
		List<ProdutoDto> produtoList = dto.getListaDto();
		System.out.println("akiii");
		System.out.println(produtoList);
		for(ProdutoDto produtoDto : produtoList){
			System.out.println("estouuuuuu");
			System.out.println(produtoDto.getId().toString()+" "
		+produtoDto.getCategoria()+" "
		+produtoDto.getDescricao()+" "
		+produtoDto.getPreco()+ " "
		+produtoDto.getQuantidade());
		}
	}
}
