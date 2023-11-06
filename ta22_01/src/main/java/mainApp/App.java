package mainApp;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import controllers.ClientController;
import models.ClienteModels;
import services.ClientService;
import views.ClienteView;

class App {
	
	public static void main(String[] args) {		
		
		try {
			
			ClienteModels modelo = new ClienteModels();
			ClienteView vista = new ClienteView();
			ClientService serv = new ClientService("clientes"); 
			ClientController controlador = new ClientController(modelo, vista ,serv);
			controlador.view.frame.setVisible(true);
			
			
		} catch (Exception e) {
			
			JOptionPane.showMessageDialog(null, "Recuerde tener el servicio de SQL en ejecución");
			Logger.getLogger(ClientService.class.getName()).log(Level.SEVERE, "FALLO en la conecxion\n", e);
		}
			
		
	}
}
