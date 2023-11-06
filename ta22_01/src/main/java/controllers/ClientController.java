package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import models.ClienteModels;
import services.ClientService;
import views.ClienteView;

public class ClientController {

	public ClienteModels model;
	public ClienteView view;
	public ClientService serv;
	private boolean actializado = false;

	public ClientController(ClienteModels model, ClienteView view, ClientService serv) {

		this.model = model;
		this.view = view;
		this.serv = serv;

		this.view.frame.addWindowListener(closedWin);
		this.view.actualizarButton.addActionListener(udpateAl);
		this.view.buscarButton.addActionListener(findAl);
		this.view.crearButton.addActionListener(createAl);
		this.view.eliminarButton.addActionListener(deleteAl);
		loadClients();
		view.inputsList.get(0).setEnabled(false);
	}
	

	// BOTON Crea un Cliente
	ActionListener createAl = new ActionListener() {
		public void actionPerformed(ActionEvent e) {

			String nombre = view.inputsList.get(1).getText();
			String apellido = view.inputsList.get(2).getText();
			String direccion = view.inputsList.get(3).getText();
			String DNI = view.inputsList.get(4).getText();
			String date = view.inputsList.get(5).getText();

			if (nombre.isEmpty() || apellido.isEmpty() || direccion.isEmpty() || DNI.isEmpty() || date.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Por favor renellene los campos");
			} else {

				ClienteModels cli = new ClienteModels(nombre, apellido, direccion, Integer.valueOf(DNI), date);
				try {
					serv.insertClient(cli);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				// Borramos los inputs
				for (int i = 0; i < view.arrAtttutes.length; i++) {
					view.inputsList.get(i).setText("");
				}

			}

			loadClients();
		}
	};
	
	//  BOTON  Busca un cliente x id
	ActionListener findAl = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, getByID());
		}

	};
	
	//  BOTON  Edita un cliente x id
	ActionListener udpateAl = new ActionListener() {
	
		
		public void actionPerformed(ActionEvent e) {
			ClienteModels cliente = null;
			
			if (actializado == false) {
				
				cliente = getByID();
				fillInputs(cliente);
				
				view.inputsList.get(0).setEnabled(true);
				view.buscarButton.setEnabled(false); ;
				view.crearButton.setEnabled(false); ;
				view.eliminarButton.setEnabled(false); 
				
				actializado = true;
			} else {
				
				try {
					String id = view.inputsList.get(0).getText();
					String nombre = view.inputsList.get(1).getText();
					String apellido = view.inputsList.get(2).getText();
					String direccion = view.inputsList.get(3).getText();
					String DNI = view.inputsList.get(4).getText();
					String date = view.inputsList.get(5).getText();
					
					serv.updateClient(new ClienteModels( Integer.valueOf(id) ,nombre, apellido, direccion , Integer.valueOf(DNI) , date ));
					
					view.inputsList.get(0).setEnabled(false);
					view.buscarButton.setEnabled(true); ;
					view.crearButton.setEnabled(true); ;
					view.eliminarButton.setEnabled(true); 
					
					JOptionPane.showMessageDialog(null, "Cliente actualizado");
					// Borramos los inputs
					for (int i = 0; i < view.arrAtttutes.length; i++) {
						view.inputsList.get(i).setText("");
					}
					loadClients(); 
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				};
			}
			
		}
	};
	
	// BOTON  Borra un cliente x id
	ActionListener deleteAl = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			try {
				serv.deleteClient( getByID().getId());
				loadClients(); // Actualizamos la tabla
				JOptionPane.showMessageDialog(null, "Cliente eliminado");
			} catch (SQLException e1) {
				e1.printStackTrace();
			};
			
			
		}
	};
	
	// Llena la taba de la vista pasanado una lista de clientes
	public void fillTable(List<ClienteModels> clientes) {
		
	    DefaultTableModel tableModel = (DefaultTableModel) view.dataTable.getModel();
	    // Limpiar la tabla antes de agregar datos
	    tableModel.setRowCount(0);
	    
	    // Iterar a través del ArrayList de clientes y agregar los datos a la tabla
	    for (ClienteModels cliente : clientes) {
	        Object[] rowData = {
	            cliente.getId(),
	            cliente.getNombre(),
	            cliente.getApellido(),
	            cliente.getDireccion(),
	            cliente.getDNI(),
	            cliente.getDate()
	        };
	        tableModel.addRow(rowData);
	    }
	}
	
	// Obtnenemos todos los clientes de ClientService
	public List<ClienteModels> getAllClients() {
	    try {
	        return serv.getAllClients(); 
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	        return new ArrayList<>();
	    }
	}
	
	// Obtiene los lalista de cientes y llena laas tablas
	public void loadClients() {
	    List<ClienteModels> clientes = getAllClients();
	    fillTable(clientes);
	}



	// Cierre la conecxion al cerrar la ventana
	WindowAdapter closedWin = new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
			serv.closeConnection();
		}
	};


	// Busca un cliente por ID reotna el Cliente Objet
	private ClienteModels getByID() {
		String idStr = JOptionPane.showInputDialog(null, "Introduzca el ID del cliente:", "BUSCAR", 3);

		try {
			int id = Integer.parseInt(idStr);
			ClienteModels clienteEncontrado = serv.getClient(id);
			if (clienteEncontrado != null) {
				return clienteEncontrado;
			} else {
				JOptionPane.showMessageDialog(null, "Cliente no encontrado.");
			}
		} catch (NumberFormatException | SQLException ex) {
			JOptionPane.showMessageDialog(null, "ID de cliente no válido.");
		}
		return model;
	}
	
	
	// Llena los inputs con un Client Objet
	private void fillInputs(ClienteModels cliente) {

		view.inputsList.get(0).setText(String.valueOf(cliente.getId()));
		view.inputsList.get(1).setText(cliente.getNombre());
		view.inputsList.get(2).setText(cliente.getApellido());
		view.inputsList.get(3).setText(cliente.getDireccion());
		view.inputsList.get(4).setText(String.valueOf(cliente.getDNI()));
		view.inputsList.get(5).setText(cliente.getDate());

	}	
}
