package mainApp;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import controllers.ClientController;
import controllers.VideoController;
import models.Client;
import models.Video;
import services.ClientService;
import services.VideoService;
import views.ClienteView;
import views.VideoUI;



class App {
	
	public static void main(String[] args) {		
		
		try {
			
			Client modelo = new Client();
			ClienteView vista = new ClienteView();
			ClientService serv = new ClientService("clientes"); 
			Video videoMod = new Video();
			VideoUI videoWin = new VideoUI();
			VideoService videoServ = new VideoService("video_club"); 
			
			ClientController controlador = new ClientController(modelo, vista ,serv ,videoServ );
			controlador.view.frame.setVisible(true);
			
			
			VideoController videoContr = new VideoController(videoMod, videoWin ,videoServ);
			videoContr.view.frame.setVisible(true);
			
		} catch (Exception e) {
			
			JOptionPane.showMessageDialog(null, "Recuerde tener el servicio de SQL en ejecución");
			Logger.getLogger(ClientService.class.getName()).log(Level.SEVERE, "FALLO en la conecxion\n", e);
		}
			
		
	}
}
