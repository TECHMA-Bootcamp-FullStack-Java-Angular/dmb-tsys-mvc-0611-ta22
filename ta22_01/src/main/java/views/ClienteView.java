package views;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import models.ClienteModels;


public class ClienteView {
	
    public JFrame frame;
    public JPanel buttonPanel;
    public JButton crearButton; 
    public JButton buscarButton;
    public JButton actualizarButton;
    public JButton eliminarButton;
    public JTable dataTable;
    public List<JTextField> inputsList = new ArrayList<>();
    public String[] arrAtttutes = {"id", "nombre", "apellido", "direccion", "DNI", "date"};
    
    public ClienteView() {
        initialize();
    }

    private void initialize() {
    	

    	
    	
        frame = new JFrame();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(860, 460);
        frame.setLocationRelativeTo(null);
        

        // Panel para los botones en la parte superior
        JLabel tittel = new JLabel(" Clientes ");
        tittel.setForeground(Color.MAGENTA);
        
        buttonPanel = new JPanel();
        crearButton = new JButton("Crear");
       
        buscarButton = new JButton("Buscar");
        actualizarButton = new JButton("Actualizar");
        eliminarButton = new JButton("Eliminar");
        
        // Alineadores
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Establecer el tamaño de la fuente a 18px
        tittel.setFont(new Font("Arial", Font.BOLD, 18));
        tittel.setPreferredSize(new Dimension(180, 20));
        crearButton.setPreferredSize(new Dimension(150, 20));
        buscarButton.setPreferredSize(new Dimension(150, 20));
        actualizarButton.setPreferredSize(new Dimension(150, 20));
        eliminarButton.setPreferredSize(new Dimension(150, 20));
        
        buttonPanel.add(tittel);
        buttonPanel.add(crearButton);
        buttonPanel.add(buscarButton);
        buttonPanel.add(actualizarButton);
        buttonPanel.add(eliminarButton);

        // Crear un modelo de tabla con los títulos de las columnas
        DefaultTableModel tableModel = new DefaultTableModel();
        for (String attribute : arrAtttutes) {
            tableModel.addColumn(attribute);
        }
         
       
        // Crear la tabla con el modelo de tabla
        dataTable = new JTable(tableModel);
        dataTable.setOpaque(true);
        
        for (int i = 0; i < arrAtttutes.length; i++) {
        	
        	if(i==0)dataTable.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
        	else dataTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            
        }
        
        // Panel para etiquetas e inputs
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        int inputPanelWidth = 200; // Ancho del contendedor de los inputs
        int verticalSpacing = 20;  // Espacio vertical deseado entre conjuntos de etiqueta y campo de entrada        
        
        for (String attribute : arrAtttutes) {
            JLabel lbl = new JLabel(attribute);
            lbl.setHorizontalAlignment(SwingConstants.RIGHT);
            JTextField input = new JTextField();
            inputsList.add(input); // añadimos ArrayList para tener acceso fuares de la clase
           
            inputPanel.add(Box.createRigidArea(new Dimension(0, verticalSpacing)));  // Agregar margen entre etiqueta y campo de entrada  
            inputPanel.add(lbl);
            input.setHorizontalAlignment(JTextField.RIGHT);
            inputPanel.add(input);
        }

        // Establecer el ancho y el margen del inputPanel
        inputPanel.setPreferredSize(new Dimension(inputPanelWidth, frame.getHeight()));
        inputPanel.setBorder(new EmptyBorder(0, 10, 100, 10));  // Margen bottom de 100px

        // Panel principal
        JPanel mainPanel = new JPanel();
        
        
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(dataTable), BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.WEST);
        
        
        frame.getContentPane().add(mainPanel);
        //frame.setVisible(true);
    }

}

