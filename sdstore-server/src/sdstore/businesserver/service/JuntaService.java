package sdstore.businesserver.service;

import sdstore.businesserver.exception.ProdutoUnvaliableException;
import sdstore.businesserver.service.dto.ProdutoDto;
import sdstore.businesserver.service.dto.ProdutoSelectionDto;

public class JuntaService extends PortalService{

	private ProdutoDto codigoDto;
	private Integer quantidade;
	
	public JuntaService(ProdutoDto codigo, Integer quantidade){
		this.codigoDto = codigo;
		this.quantidade = quantidade;
	}
	
	public final void dispatch() throws ProdutoUnvaliableException{
		try{
			String id = codigoDto.getCodigo();
			
		}
		
		/*try {
			String prefix = mobileDto.getOperator().getPrefix();
			String number = mobileDto.getNumber();
			Operator operator = Operator.getOperator(prefix);
			Mobile mobile = operator.getMobile(number);
			MobileState mobileState = mobile.getState();
			result = new MobileStateDto(mobileDto, mobileState.toString());
		} catch (OperatorPrefixException exception) {
			throw exception;
		} catch (MobileNumberException exception) {
			throw exception;
		}*/
	}
	
	
	public ProdutoDto getProduto() {
		// TODO Auto-generated method stub
		return null;
	}

}
