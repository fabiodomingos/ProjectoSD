package sdstore.presentationserver.presenter;

import java.util.List;

import sdstore.presentationserver.service.stubs.CategoriaListDto;

public class ListaCategoriasPresenter {

	public static void present(CategoriaListDto dto){
		List<String> categoriaList = dto.getCategoriaList();
		for(String categoriaDto : categoriaList){
			System.out.println(categoriaDto);
		}
	}
}
