package ta22_03.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Proyecto {

	private String id; // 4 digitos
	private String nombre; // 250 varchar
	private int horas;

}
