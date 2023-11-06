package ta22_03;

import java.awt.EventQueue;

import ta22_03.controllers.AsignadoController;
import ta22_03.controllers.CientificosController;
import ta22_03.controllers.ProyectoController;
import ta22_03.services.AsignarService;
import ta22_03.services.CientificoService;
import ta22_03.services.ProyectoService;
import ta22_03.viers.AppUI;

public class App {
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppUI window = new AppUI();
					CientificoService cientificosServive = new CientificoService("cientificos");
					ProyectoService proyectoService = new ProyectoService("cientificos");
					AsignarService asignadoService = new AsignarService("cientificos");
					
					CientificosController cientificoCtrl = new CientificosController(window ,cientificosServive );
					new ProyectoController(window, proyectoService);
					new AsignadoController(window, asignadoService , cientificosServive);
					
					cientificoCtrl.ui.getFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}