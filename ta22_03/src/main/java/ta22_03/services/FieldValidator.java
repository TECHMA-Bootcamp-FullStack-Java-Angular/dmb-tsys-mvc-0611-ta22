package ta22_03.services;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FieldValidator {
	
	private final int DNI_VALID_FIELD_LENGTH = 11;
	private final int VARCAHAR_DEFAULD = 250;
	
	public boolean isDniValid (String dni){
		return dni.length()<=DNI_VALID_FIELD_LENGTH;
	}
	
	public boolean isVarchar250 (String nomApels){
		return nomApels.length()<=VARCAHAR_DEFAULD;
	}
	
	public boolean isProyectoValid (String id){
		return id.length()<5;
	}
	
	public boolean isAsingadoValid (String id , String dni ){
		return  isDniValid (dni) && isProyectoValid (id);
	}
}
