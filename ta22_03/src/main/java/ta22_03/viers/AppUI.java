package ta22_03.viers;


import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import lombok.Data;

@Data
public class AppUI {

	private JFrame frame;

	// Cientificos
	private JTable cientificos;
	private JTextField inputCientificoDIN;
	private JTextField inputCientificoFullName;
	
	private JButton btnCientifico_Add;
	private JButton btnCientifico_Edit;
	private JButton btnCientifico_Find;
	private JButton btnCientifico_Delete;
	public String[] arrCientificos = {"dni", "NomApels"};
	
	// Proyectos
	private JTable proyectos;
	private JTextField inputProyectoID;
	private JTextField inputNameProjet;
	private JTextField inputHours;

	private JButton btnProyectos__Delete;
	private JButton btnProyectos__Find;
	private JButton btnProyectos_Edit;
	private JButton btnProyectos_Add;
	public String[] arrProyectos = {"id", "nombre", "horas"};
	
	// Asignados
	private JTable asignados;
	private JSpinner spinnerCientifico;
	private JSpinner spinnerProjetID;
	
	private JButton btnAsingados_Delete;
	private JButton btnAsingados_Find;
	private JButton btnAsingados_Edit;
	private JButton btnAsingados_Add;
	public String[] arrAsignado = {"DNI Cientifico", "ID Proyecto"};



	/**
	 * Create the application.
	 */
	public AppUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.getContentPane().setEnabled(false);
		frame.getContentPane().setLayout(null);
		
		//------------------- TABLA CIENTIFICOS --------------------
		
		// inpusts and lables
		JLabel lblCientificos = new JLabel("CIENTIFICOS");
		lblCientificos.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblCientificos.setHorizontalAlignment(SwingConstants.CENTER);
		lblCientificos.setBounds(30, 38, 119, 14);
		frame.getContentPane().add(lblCientificos);
		
		JLabel lblDIN = new JLabel("DNI");
		lblDIN.setBounds(10, 91, 45, 13);
		frame.getContentPane().add(lblDIN);
		
		inputCientificoDIN = new JTextField();
		inputCientificoDIN.setBounds(10, 107, 191, 19);
		frame.getContentPane().add(inputCientificoDIN);
		inputCientificoDIN.setColumns(10);
		
		JLabel lblNomApels = new JLabel("Nombre Completo");
		lblNomApels.setBounds(10, 136, 191, 13);
		frame.getContentPane().add(lblNomApels);
		
		inputCientificoFullName = new JTextField();
		inputCientificoFullName.setColumns(10);
		inputCientificoFullName.setBounds(10, 152, 191, 19);
		frame.getContentPane().add(inputCientificoFullName);
		
	    DefaultTableModel cientificosModel = new DefaultTableModel(null, arrCientificos);
	    cientificos = new JTable(cientificosModel);

	    JScrollPane scrollPaneCientifiacos = new JScrollPane(cientificos); 
	    scrollPaneCientifiacos.setBounds(211, 38, 423, 203);
	    frame.getContentPane().add(scrollPaneCientifiacos);
	    
		btnCientifico_Add = new JButton("Añadir");
		btnCientifico_Add.setBounds(211, 10, 108, 21);
		frame.getContentPane().add(btnCientifico_Add);
		
		btnCientifico_Edit = new JButton("Editar");
		btnCientifico_Edit.setBounds(329, 10, 96, 21);
		frame.getContentPane().add(btnCientifico_Edit);
		
		btnCientifico_Find = new JButton("Buscar");
		btnCientifico_Find.setBounds(435, 10, 96, 21);
		frame.getContentPane().add(btnCientifico_Find);
		
		btnCientifico_Delete = new JButton("Borrar");
		btnCientifico_Delete.setBounds(538, 10, 96, 21);
		frame.getContentPane().add(btnCientifico_Delete);

	    //------------------- PROYECTOS --------------------

		// inpusts and lables		
		JLabel lblProyectos = new JLabel("PROYECTOS");
		lblProyectos.setHorizontalAlignment(SwingConstants.CENTER);
		lblProyectos.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblProyectos.setBounds(30, 272, 119, 13);
		frame.getContentPane().add(lblProyectos);
		
		JLabel lblID = new JLabel("ID");
		lblID.setBounds(10, 330, 45, 13);
		frame.getContentPane().add(lblID);
		
		inputProyectoID = new JTextField();
		inputProyectoID.setColumns(10);
		inputProyectoID.setBounds(10, 346, 191, 19);
		frame.getContentPane().add(inputProyectoID);
		
		JLabel lblMonbreProyecto = new JLabel("Nombre del proyecto");
		lblMonbreProyecto.setBounds(10, 375, 172, 13);
		frame.getContentPane().add(lblMonbreProyecto);
		
		inputNameProjet = new JTextField();
		inputNameProjet.setColumns(10);
		inputNameProjet.setBounds(10, 391, 191, 19);
		frame.getContentPane().add(inputNameProjet);
		
		JLabel lblHoras = new JLabel("Horas");
		lblHoras.setBounds(10, 418, 172, 13);
		frame.getContentPane().add(lblHoras);
		
		inputHours = new JTextField();
		inputHours.setColumns(10);
		inputHours.setBounds(10, 434, 191, 19);
		frame.getContentPane().add(inputHours);
	    
		// table
	    DefaultTableModel proyectosModel = new DefaultTableModel(null, arrProyectos);
		proyectos = new JTable(proyectosModel);
		
		JScrollPane scrollPane1Proyectos = new JScrollPane(proyectos);
		scrollPane1Proyectos.setBounds(211, 279, 423, 203);
		frame.getContentPane().add(scrollPane1Proyectos);
		
		// buttons
		btnProyectos__Delete = new JButton("Borrar");
		btnProyectos__Delete.setBounds(538, 252, 96, 21);
		frame.getContentPane().add(btnProyectos__Delete);
		
		btnProyectos__Find = new JButton("Buscar");
		btnProyectos__Find.setBounds(435, 252, 96, 21);
		frame.getContentPane().add(btnProyectos__Find);
		
		btnProyectos_Edit = new JButton("Editar");
		btnProyectos_Edit.setBounds(329, 252, 96, 21);
		frame.getContentPane().add(btnProyectos_Edit);
		
		btnProyectos_Add = new JButton("Añadir");
		btnProyectos_Add.setBounds(211, 252, 108, 21);
		frame.getContentPane().add(btnProyectos_Add);
		
		
		//------------------- TABLA ASIGANADOS --------------------
	    
		// inpusts and lables
		JLabel lblAsignados = new JLabel("ASIGNADOS");
		lblAsignados.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblAsignados.setHorizontalAlignment(SwingConstants.CENTER);
		lblAsignados.setBounds(30, 524, 119, 13);
		frame.getContentPane().add(lblAsignados);
		
		
		JLabel lblID_1 = new JLabel("Proyecto ID");
		lblID_1.setBounds(10, 631, 191, 13);
		frame.getContentPane().add(lblID_1);
		
		JLabel lblDIN_1 = new JLabel("Cientifico DNI");
		lblDIN_1.setBounds(10, 571, 191, 13);
		frame.getContentPane().add(lblDIN_1);
		
		
		spinnerCientifico = new JSpinner();
		spinnerCientifico.setModel(new SpinnerListModel(new String[] {" "}));
		spinnerCientifico.setBounds(10, 589, 191, 20);
		frame.getContentPane().add(spinnerCientifico);
		
		spinnerProjetID = new JSpinner();
		spinnerProjetID.setModel(new SpinnerListModel(new String[] {" "}));
		spinnerProjetID.setBounds(10, 645, 191, 20);
		frame.getContentPane().add(spinnerProjetID);
		
		// table
		DefaultTableModel asignadosModel = new DefaultTableModel(null, arrAsignado);
		asignados = new JTable(asignadosModel);
		
// DATOS DE PRUEBA
		asignadosModel.addRow(new Object[] {"12345", "ADAR"});
		asignadosModel.addRow(new Object[] {"67890" , "ASRR" });
		
		JScrollPane scrollPane1Asignados = new JScrollPane(asignados);
		scrollPane1Asignados.setBounds(211, 523, 423, 203);
		frame.getContentPane().add(scrollPane1Asignados);
		

		// buttons
		btnAsingados_Delete = new JButton("Borrar");
		btnAsingados_Delete.setBounds(538, 491, 96, 21);
		frame.getContentPane().add(btnAsingados_Delete);
		
		btnAsingados_Find = new JButton("Buscar");
		btnAsingados_Find.setBounds(435, 491, 96, 21);
		frame.getContentPane().add(btnAsingados_Find);
		
		btnAsingados_Edit = new JButton("Editar");
		btnAsingados_Edit.setBounds(329, 491, 96, 21);
		frame.getContentPane().add(btnAsingados_Edit);
		
		btnAsingados_Add = new JButton("Añadir");
		btnAsingados_Add.setBounds(211, 491, 108, 21);
		frame.getContentPane().add(btnAsingados_Add);
		
		//---------------
				
		frame.setBounds(100, 100, 658, 773);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		frame.setLocationRelativeTo(null);
	}
}
