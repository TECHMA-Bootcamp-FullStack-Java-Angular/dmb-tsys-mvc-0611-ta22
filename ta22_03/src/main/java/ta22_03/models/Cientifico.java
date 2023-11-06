package ta22_03.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cientifico {

	private String dni; // 11 digitos
	private String nomApels; // 250 Varchar

}
