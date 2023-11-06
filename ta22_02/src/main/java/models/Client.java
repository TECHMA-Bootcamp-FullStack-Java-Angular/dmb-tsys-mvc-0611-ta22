package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Client {
	
	private int id;
	private String nombre;
	private String apellido;
	private String direccion;
	private int DNI;
	private String date;

	
	public Client(int id, String nombre, String apellido, String direccion, int DNI, String date) {
		
		this.id = id;
		setNombre(nombre);
		setApellido(apellido); 
		setDireccion(direccion);
	    setDNI(DNI);
	    setDate(date);
		
	}
	
		public Client( String nombre, String apellido, String direccion, int DNI, String date) {
		
		setNombre(nombre);
		setApellido(apellido); 
		setDireccion(direccion);
	    setDNI(DNI);
	    setDate(date);
		
	}


	public void setId(int id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = strLenValidator(nombre, 250);
	}

	public void setApellido(String apellido) {
		this.apellido = strLenValidator(apellido, 250);
	}

	public void setDireccion(String direccion) {
		this.direccion = strLenValidator(direccion, 250);
	}

	public void setDNI(int DNI) {
		this.DNI = DNIValidator(DNI);
	}

	public void setDate(String date) {
		this.date = dateValidator(date);
	}


	// Valida que le String no pase de n caracteres
	public String strLenValidator(String string, int n) {
		if (string.length() > n) {
			throw new IllegalArgumentException(string + " debe tener " + n + " characters como máximo");
		}
		return string;
	}

	// Valida que el DNI tenga entre 8 y 11 digitos
	public int DNIValidator(int DNI) {
		int length = String.valueOf(DNI).length();
		if (length < 7 || length > 11) {
			throw new IllegalArgumentException("Dni no valido debe tener entre 9 y 11");
		}
		return DNI;
	}

	// Validate date format is valid and returns as String date
	public String dateValidator(String date) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		String formattedDate;

		try {
			Date SQLdate = (Date) sdf.parse(date);
			formattedDate = sdf.format(SQLdate);
			if (formattedDate.equals(date)) {
				return formattedDate;
			}
		} catch (ParseException pe ) {
			  throw new IllegalArgumentException("La fecha introducida no es válida.");
		} catch (NullPointerException npe) {
			  throw new IllegalArgumentException("La fecha introducida no es válida.");
		}
		return formattedDate;
	}
	
	
	
	@Override
	public String toString() {
		return "ID : " + id + "\n" + nombre.toUpperCase() + " " + apellido + "\n" + direccion
				+ "\nDNI :" + DNI + "\nFecha :" + date + "";
	}

}
