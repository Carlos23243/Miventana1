package com.mycompany.miventana1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Miventana1 extends JFrame {

    // 1. Componentes Requeridos
    private DefaultListModel<String> listModel;
    private JList<String> taskList;
    private JTextField taskInputField;
    private JButton addButton;
    private JButton completeButton;
    private JButton deleteButton;

    public Miventana1() {
        // --- Configuración de la Ventana Principal ---
        setTitle("Cronograma de tareas ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout(10, 10)); // Usamos BorderLayout para la ventana principal
        
        // Inicializar el modelo de la lista y la JList
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        
        // Configurar la lista para ser desplazable
        JScrollPane scrollPane = new JScrollPane(taskList);
        
        // --- 2. Componentes de Entrada y Botón "Añadir Tarea" ---
        taskInputField = new JTextField(30);
        addButton = new JButton("Añadir Tarea");
        
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.add(new JLabel("Nueva Tarea:"));
        inputPanel.add(taskInputField);
        inputPanel.add(addButton);

        // --- 3. Componentes de Acción (Completar y Eliminar) ---
        completeButton = new JButton("Marcar como Completada");
        deleteButton = new JButton("Eliminar Tarea");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        buttonPanel.add(completeButton);
        buttonPanel.add(deleteButton);

        // --- 4. Ensamblar la Interfaz ---
        add(inputPanel, BorderLayout.NORTH);  // Campo de entrada y Añadir arriba
        add(scrollPane, BorderLayout.CENTER); // Lista de tareas en el centro
        add(buttonPanel, BorderLayout.SOUTH); // Botones de acción abajo

        // --- 5. Implementación de Listeners ---
        setupListeners();
        
        // Mostrar la ventana
        setLocationRelativeTo(null); // Centrar la ventana
        setVisible(true);
    }

    private void setupListeners() {
        // Listener para el botón "Añadir Tarea"
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });

        // Listener para el botón "Eliminar Tarea"
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTask();
            }
        });

        // Listener para el botón "Marcar como Completada"
        completeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                completeTask();
            }
        });
        
        // Opcional: Permitir añadir tarea presionando Enter en el campo de texto
        taskInputField.addActionListener(new ActionListener() {
             @Override
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });
    }

    

    private void addTask() {
        String taskText = taskInputField.getText().trim();

        if (!taskText.isEmpty()) {
            // Añadir la tarea al modelo y limpiar el campo de texto
            listModel.addElement("☐ " + taskText);
            taskInputField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, 
                "Por favor, ingrese una tarea válida.", 
                "Error de Entrada", 
                JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteTask() {
        int selectedIndex = taskList.getSelectedIndex();
        
        if (selectedIndex != -1) {
            // Eliminar la tarea del modelo
            listModel.remove(selectedIndex);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Seleccione una tarea para eliminar.", 
                "Selección Requerida", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void completeTask() {
        int selectedIndex = taskList.getSelectedIndex();

        if (selectedIndex != -1) {
            String currentTask = listModel.getElementAt(selectedIndex);
            String completedTask;
            
            // Lógica para marcar/desmarcar
            if (currentTask.startsWith("☐ ")) {
                // Marcar como completada (usando '☑' o '✓')
                completedTask = "☑ " + currentTask.substring(2);
            } else if (currentTask.startsWith("☑ ")) {
                 // Desmarcar si ya está completada
                 completedTask = "☐ " + currentTask.substring(2);
            } else {
                 // Caso base si no tiene símbolo
                 completedTask = "☑ " + currentTask;
            }
            
            // Reemplazar la tarea en el modelo para actualizar la JList
            listModel.set(selectedIndex, completedTask);

        } else {
            JOptionPane.showMessageDialog(this, 
                "Seleccione una tarea para marcar/desmarcar.", 
                "Selección Requerida", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // --- Método Main para ejecutar la aplicación ---
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(() -> new Miventana1());
    }
}