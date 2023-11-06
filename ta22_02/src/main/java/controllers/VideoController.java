package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Video;
import services.VideoService;
import views.VideoUI;

public class VideoController {

	public Video model;
	public VideoUI view;
	public VideoService serv;
	private boolean actializado = false;

	public VideoController(Video model, VideoUI view, VideoService serv) {

		this.model = model;
		this.view = view;
		this.serv = serv;

		this.view.frame.addWindowListener(closedWin);
		this.view.actualizarButton.addActionListener(udpateAl);
		this.view.buscarButton.addActionListener(findAl);
		this.view.crearButton.addActionListener(createAl);
		this.view.eliminarButton.addActionListener(deleteAl);
		this.view.refrescar.addActionListener(refrescarAl);
		loadVideos();
		view.inputsList.get(0).setEnabled(false);
	}

	public VideoController() {
	}

	// BOTON Crea un Cliente
	ActionListener createAl = new ActionListener() {
		public void actionPerformed(ActionEvent e) {

			String title = view.inputsList.get(1).getText();
			String director = view.inputsList.get(2).getText();
			String cli_id = view.inputsList.get(3).getText();

			if (title.isEmpty() || director.isEmpty() || cli_id.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Por favor renellene los campos");
			} else {

				Video v = new Video(title, director, Integer.valueOf(cli_id));
				try {
					serv.insertVideo(v);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());

				}
				// Borramos los inputs
				for (int i = 0; i < view.arrAtttutes.length; i++) {
					view.inputsList.get(i).setText("");
				}

			}

			loadVideos();
		}
	};

	// BOTON Busca un cliente x id
	ActionListener refrescarAl = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			loadVideos();
		}

	};

	// BOTON Busca un cliente x id
	ActionListener findAl = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, getByID());
		}

	};

	// BOTON Edita un cliente x id
	ActionListener udpateAl = new ActionListener() {

		public void actionPerformed(ActionEvent e) {
			Video video = null;

			if (actializado == false) {

				video = getByID();
				fillInputs(video);

				view.inputsList.get(0).setEnabled(true);
				view.buscarButton.setEnabled(false);
				;
				view.crearButton.setEnabled(false);
				;
				view.eliminarButton.setEnabled(false);

				actializado = true;
			} else {

				try {
					String id = view.inputsList.get(0).getText();
					String title = view.inputsList.get(1).getText();
					String director = view.inputsList.get(2).getText();
					String cli_id = view.inputsList.get(3).getText();

					serv.updateVideo(new Video(Integer.valueOf(id), title, director, Integer.valueOf(cli_id)));

					view.inputsList.get(0).setEnabled(false);
					view.buscarButton.setEnabled(true);
					;
					view.crearButton.setEnabled(true);
					;
					view.eliminarButton.setEnabled(true);

					actializado = false;
					JOptionPane.showMessageDialog(null, "Video actualizado");
					// Borramos los inputs
					for (int i = 0; i < view.arrAtttutes.length; i++) {
						view.inputsList.get(i).setText("");
					}
					loadVideos();

				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
				;
			}

		}
	};

	// BOTON Borra un cliente x id
	ActionListener deleteAl = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			try {
				serv.deleteVideo(getByID().getId());
				loadVideos(); // Actualizamos la tabla
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
			}
			;

		}
	};

	// Llena la taba de la vista pasanado una lista de clientes
	public void fillTable(List<Video> videos) {

		DefaultTableModel tableModel = (DefaultTableModel) view.dataTable.getModel();
		// Limpiar la tabla antes de agregar datos
		tableModel.setRowCount(0);

		// Iterar a través del ArrayList de clientes y agregar los datos a la tabla
		for (Video v : videos) {
			Object[] rowData = { v.getId(), v.getTitle(), v.getDirector(), v.getCli_id(),

			};
			tableModel.addRow(rowData);
		}
	}

	// Obtiene los la lista de video y llena laas tablas
	public void loadVideos() {
		List<Video> videos = getAllVideos();
		fillTable(videos);
	}

	// Cierre la conecxion al cerrar la ventana
	WindowAdapter closedWin = new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
			serv.closeConnection();
		}
	};

	// Obtnenemos todos los clientes de ClientService
	public List<Video> getAllVideos() {
		try {
			return serv.getAllVideo();
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
			return new ArrayList<>();
		}
	}

	// Busca un cliente por ID reotna el Video Objet
	private Video getByID() {
		String idStr = JOptionPane.showInputDialog(null, "Introduzca el ID del video:", "BUSCAR", 3);

		try {
			int id = Integer.parseInt(idStr);
			Video videoEncontrado = serv.getVideo(id);
			if (videoEncontrado != null) {
				return videoEncontrado;
			} else {
				JOptionPane.showMessageDialog(null, "video no encontrado.");
			}
		} catch (NumberFormatException | SQLException ex) {
			JOptionPane.showMessageDialog(null, "ID de video no válido.");
		}
		return model;
	}

	// Llena los inputs con un Video Objet
	private void fillInputs(Video v) {

		view.inputsList.get(0).setText(String.valueOf(v.getId()));
		view.inputsList.get(1).setText(v.getTitle());
		view.inputsList.get(2).setText(v.getDirector());
		view.inputsList.get(3).setText(String.valueOf(v.getCli_id()));

	}

}
