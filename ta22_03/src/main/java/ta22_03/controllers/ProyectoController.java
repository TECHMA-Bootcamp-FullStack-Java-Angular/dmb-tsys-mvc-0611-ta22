package ta22_03.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SpinnerListModel;
import javax.swing.table.DefaultTableModel;

import ta22_03.models.Proyecto;
import ta22_03.services.FieldValidator;
import ta22_03.services.ProyectoService;
import ta22_03.viers.AppUI;

public class ProyectoController {

	public AppUI ui;
	public FieldValidator chk;
	public ProyectoService serv;
	private boolean actualizado = false;

	public ProyectoController(AppUI ui, ProyectoService serv) {
		this.ui = ui;
		this.chk = new FieldValidator();
		this.serv = serv;
		this.ui.getBtnProyectos_Add().addActionListener(create);
		this.ui.getBtnProyectos__Delete().addActionListener(delete);
		this.ui.getBtnProyectos_Edit().addActionListener(update);
		this.ui.getBtnProyectos__Find().addActionListener(find);
		fillTable();
		fillSlider();
	}

	// READ
	ActionListener find = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Proyecto proyecto = getByID();
			if (proyecto != null) {
				JOptionPane.showMessageDialog(null, proyecto);
			}
			;
		}
	};

	// CREATE
	ActionListener create = new ActionListener() {
		public void actionPerformed(ActionEvent e) {

			String id = ui.getInputProyectoID().getText();
			String nombre = ui.getInputNameProjet().getText();
			String horas = ui.getInputHours().getText();

			if (chk.isProyectoValid(id) && chk.isVarchar250(nombre)&& !horas.isEmpty()) {

				try {
					serv.insertProyecto(new Proyecto(id, nombre, Integer.valueOf(horas)));
					
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}

				resetInputs();

			} else {

				JOptionPane.showMessageDialog(null, "Los datos no son validos");
			}
			fillTable();
		}
	};

	// UPDATE
	ActionListener update = new ActionListener() {
		public void actionPerformed(ActionEvent e) {

			Proyecto proyecto = null;

			if (actualizado == false) {

				proyecto = getByID();

				if (proyecto != null) {
					fillInputs(proyecto);
					actualizado = true;
					showBtn(!actualizado);
				}

			} else {

				String id = ui.getInputProyectoID().getText();
				String nombre = ui.getInputNameProjet().getText();
				String horas = ui.getInputHours().getText();
				;
				actualizado = false;
				showBtn(!actualizado);

				try {
					serv.updateProyecto(new Proyecto(id, nombre, Integer.valueOf(horas)));
					JOptionPane.showMessageDialog(null, "Proyecto actualizado");

					resetInputs();
					fillTable();

				} catch (SQLException e1) {

					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}

		}
	};

	// DELETE
	ActionListener delete = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String id = getByID().getId();
			if (id != null) {
				try {
					serv.deleteProyecto(id);
					fillTable();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
			;

		}
	};
	
	private void fillSlider() {
	    List<Proyecto> proyectos = getAll(); 
	    List<String> proyectoIDs = new ArrayList<>();
	    
	    for (Proyecto proyecto : proyectos) {
	        proyectoIDs.add(proyecto.getId()); 
	    }
	    
	    SpinnerListModel model = new SpinnerListModel(proyectoIDs.toArray());
	    ui.getSpinnerProjetID().setModel(model); 
	}
	


	// Llena los inputs con un Cientifico Objet
	private void fillInputs(Proyecto obj) {

		ui.getInputProyectoID().setText(obj.getId());
		ui.getInputNameProjet().setText(obj.getNombre());
		ui.getInputHours().setText(String.valueOf(obj.getHoras()));
		;
	}

	// Obtnenemos todos los cientififcos de ProyectosService
	public List<Proyecto> getAll() {
		try {
			return serv.getAllProyecto();
		} catch (SQLException ex) {
			ex.printStackTrace();
			return new ArrayList<>();
		}
	}

	// Llena la taba de la vista pasanado una lista de proyectos
	public void fillTable() {

		DefaultTableModel tableModel = (DefaultTableModel) ui.getProyectos().getModel();
		// Limpiar la tabla antes de agregar datos
		tableModel.setRowCount(0);

		// Iterar a través del ArrayList de cientificos y agregar los datos a la tabla
		for (Proyecto proyecto : getAll()) {
			Object[] rowData = { proyecto.getId(), proyecto.getNombre(), proyecto.getHoras() };
			tableModel.addRow(rowData);
		}
	}

	private void showBtn(boolean active) {
		ui.getBtnProyectos__Delete().setEnabled(active);
		ui.getBtnProyectos__Find().setEnabled(active);
		ui.getBtnProyectos_Add().setEnabled(active);
	}

	private Proyecto getByID() {
		Proyecto model = null;
		String id = JOptionPane.showInputDialog(null, "Introduzca el ID del proyecto:", "BUSCAR", 3);

		try {
			model = serv.getProyecto(id);
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
		}
		if (model == null)
			JOptionPane.showMessageDialog(null, "Proyecto no encontrado.");

		return model;

	}

	// setea en vacio los inputs con un Proyecto
	private void resetInputs() {
		ui.getInputProyectoID().setText("");
		ui.getInputNameProjet().setText("");
		ui.getInputHours().setText("");
	}	

}
