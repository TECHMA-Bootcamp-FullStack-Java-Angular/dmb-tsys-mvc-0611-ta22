package ta22_03.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import ta22_03.models.Asignado;
import ta22_03.models.Cientifico;
import ta22_03.models.Proyecto;
import ta22_03.services.AsignarService;
import ta22_03.services.CientificoService;
import ta22_03.services.FieldValidator;

import ta22_03.viers.AppUI;

public class AsignadoController {

	public AppUI ui;
	public FieldValidator chk;
	public AsignarService serv;
	private boolean actualizado;
	public CientificoService cientificoServ;
	private String cientifico;

	public AsignadoController(AppUI ui, AsignarService serv, CientificoService cientificoServ) {
		this.cientificoServ = cientificoServ;
		this.ui = ui;
		this.chk = new FieldValidator();
		this.serv = serv;
		this.actualizado = false;
		this.ui.getBtnAsingados_Add().addActionListener(create);
		this.ui.getBtnAsingados_Delete().addActionListener(delete);
		this.ui.getBtnAsingados_Edit().addActionListener(update);
		this.ui.getBtnAsingados_Find().addActionListener(find);
		fillTable();
	}

	private void showBtn(boolean active) {
		ui.getBtnAsingados_Delete().setEnabled(active);
		ui.getBtnAsingados_Add().setEnabled(active);
		ui.getBtnAsingados_Find().setEnabled(active);
	}

    
	ActionListener update = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
		

			if (actualizado == false) {
				
				try {
					cientifico = getByDNI().getDni();
					System.out.println(cientifico);
					ui.getSpinnerCientifico().setVisible(false);
					showBtn(!actualizado);
					actualizado = true;
				} catch (Exception e2) {
					
					JOptionPane.showMessageDialog(null, "Asegurae de poner un DNI valido");
				}
				
				
			} else {

				try {
					System.out.println(cientifico);
					String proyecto = (String) ui.getSpinnerProjetID().getValue();
					Asignado asinginado = new Asignado(cientifico , proyecto );
				
					serv.updateAsigando(asinginado);
					JOptionPane.showMessageDialog(null, "Cientifico actualizado");
					ui.getSpinnerCientifico().setVisible(true);
					actualizado = false;
					fillTable();
					showBtn(!actualizado);

				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());

				}

			}

		}
	};

	ActionListener find = new ActionListener() {
		public void actionPerformed(ActionEvent e) {

			try {
				Asignado obj = serv.getAsiganado(getByDNI().getDni());
				if (obj == null) {
					JOptionPane.showMessageDialog(null, "No tenomos proyecto asignados para este cientifico");
				} else {
					JOptionPane.showMessageDialog(null, obj.toString());
				}

			} catch (SQLException e1) {

				JOptionPane.showMessageDialog(null, e1.getMessage());
			}
			;
		}
	};

	// DELETE
	ActionListener delete = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String dni = getByDNI().getDni();
			if (dni != null) {
				try {
					serv.deleteAsiganado(dni);
					;
					fillTable();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
			;

		}
	};

	private Cientifico getByDNI() {
		Cientifico model = null;
		String dni = JOptionPane.showInputDialog(null, "Introduzca el DNI del cientfico:", "BUSCAR", 3);

		try {
			model = cientificoServ.getCientifico(dni);
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
		}
		if (model == null)
			JOptionPane.showMessageDialog(null, "Científico no encontrado.");

		return model;

	}

	// CREATE
	ActionListener create = new ActionListener() {
		public void actionPerformed(ActionEvent e) {

			String dni = (String) ui.getSpinnerCientifico().getValue();
			String id = (String) ui.getSpinnerProjetID().getValue();

			if (chk.isAsingadoValid(id, dni)) {

				try {
					serv.insertAsignado(new Asignado(dni, id));

				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}

			} else {

				JOptionPane.showMessageDialog(null, "Los datos no son validos");
			}
			fillTable();
		}
	};

	// Obtnenemos todos los cientififcos de ProyectosService
	public List<Asignado> getAll() {
		try {
			return serv.getAllAsigando();
		} catch (SQLException ex) {
			ex.printStackTrace();
			return new ArrayList<>();
		}
	}

	// Llena la taba de la vista pasanado una lista de proyectos
	public void fillTable() {

		DefaultTableModel tableModel = (DefaultTableModel) ui.getAsignados().getModel();

		tableModel.setRowCount(0);

		for (Asignado asig : getAll()) {
			Object[] rowData = { asig.getCientifico(), asig.getProyecto() };

			tableModel.addRow(rowData);
		}
	}
}
