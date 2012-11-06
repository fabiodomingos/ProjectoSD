package sdstore.presentationserver.presenter;

public class ListaCategoriasPresenter {

	public static void present(CategoriaListDto dto){
		List<CategoriaDto> categoriaList = dto.getCategoriaList();
		for(CategoriaDto categoriaDto : categoriaList){
//			System.out.println(categoriaDto);
		}
	}
}
