package ta22_03.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SpinnerListModel;
import javax.swing.table.DefaultTableModel;

import ta22_03.models.Cientifico;
import ta22_03.models.Proyecto;
import ta22_03.services.CientificoService;
import ta22_03.services.FieldValidator;
import ta22_03.viers.AppUI;

public class CientificosController {

	public AppUI ui;
	public FieldValidator chk;
	public CientificoService serv;
	private boolean actualizado = false;

	public CientificosController(AppUI ui, CientificoService serv) {

		this.serv = serv;
		this.chk = new FieldValidator();
		this.ui = ui;
		this.ui.getBtnCientifico_Add().addActionListener(create);
		this.ui.getBtnCientifico_Delete().addActionListener(delete);
		this.ui.getBtnCientifico_Edit().addActionListener(update);
		this.ui.getBtnCientifico_Find().addActionListener(find);
		fillTable();
		fillSlider();
	}

	ActionListener create = new ActionListener() {
		public void actionPerformed(ActionEvent e) {

			String dni = ui.getInputCientificoDIN().getText();
			String nomApels = ui.getInputCientificoFullName().getText();

			if (chk.isDniValid(dni) && chk.isVarchar250(nomApels)) {

				try {
					serv.insertCientifico(new Cientifico(dni, nomApels));
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}

				resetInputs();

			} else {

				JOptionPane.showMessageDialog(null, "Los datos no son validos");
			}
		}
	};

	ActionListener find = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Cientifico cientifico = getByID();
			if (cientifico != null) {
				JOptionPane.showMessageDialog(null, cientifico);
			}
			;
		}
	};

	ActionListener delete = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String dni = getByID().getDni();
			if (dni != null) {
				try {
					serv.deleteCientifico(dni);
					fillTable();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			};
		}
	};

	ActionListener update = new ActionListener() {
		public void actionPerformed(ActionEvent e) {

			Cientifico cientifico = null;

			if (actualizado == false) {
				
				cientifico = getByID();
				
				if (cientifico != null) {
					fillInputs(cientifico);
					actualizado = true;
					showBtn(!actualizado);
				}

			} else {

				String dni = ui.getInputCientificoDIN().getText();
				String nomApels = ui.getInputCientificoFullName().getText();
				actualizado = false;
				showBtn(!actualizado);

				try {
					serv.updateCientifico(new Cientifico(dni, nomApels));
					JOptionPane.showMessageDialog(null, "Cientifico actualizado");

					resetInputs();
					fillTable();

				} catch (SQLException e1) {

					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}

		}
	};
	
	private void fillSlider() {
	    List<Cientifico> cientificos = getAll(); 
	    List<String> cientificosDNI = new ArrayList<>();
	    
	    for (Cientifico proyecto : cientificos) {
	    	cientificosDNI.add(proyecto.getDni()); 
	    }
	    SpinnerListModel model = new SpinnerListModel(cientificosDNI.toArray());
	    ui.getSpinnerCientifico().setModel(model); 
	}

	private void showBtn(boolean active) {
		ui.getBtnCientifico_Delete().setEnabled(active);
		ui.getBtnCientifico_Add().setEnabled(active);
		ui.getBtnCientifico_Find().setEnabled(active);
	}

	private Cientifico getByID() {
		Cientifico model = null;
		String dni = JOptionPane.showInputDialog(null, "Introduzca el DNI del cientifico:", "BUSCAR", 3);

		try {
			model = serv.getCientifico(dni);
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
		}
		if (model == null)
			JOptionPane.showMessageDialog(null, "Cientifico no encontrado.");

		return model;

	}

	// Obtnenemos todos los cientififcos de CientificosService
	public List<Cientifico> getAll() {
		try {
			return serv.getAllCientificos();
		} catch (SQLException ex) {
			ex.printStackTrace();
			return new ArrayList<>();
		}
	}

	// setea en vacio los inputs con un Cientifico 
	private void resetInputs() {
		ui.getInputCientificoDIN().setText("");
		ui.getInputCientificoFullName().setText("");
	}

	// Llena los inputs con un Cientifico Objet
	private void fillInputs(Cientifico obj) {

		ui.getInputCientificoDIN().setText(obj.getDni());
		ui.getInputCientificoFullName().setText(obj.getNomApels());
	}

	// Llena la taba de la vista pasanado una lista de cientificos
	public void fillTable() {

		DefaultTableModel tableModel = (DefaultTableModel) ui.getCientificos().getModel();
		// Limpiar la tabla antes de agregar datos
		tableModel.setRowCount(0);

		// Iterar a través del ArrayList de cientificos y agregar los datos a la tabla
		for (Cientifico cientifico : getAll()) {
			Object[] rowData = { cientifico.getDni(), cientifico.getNomApels(), };
			tableModel.addRow(rowData);
		}
	}

}
