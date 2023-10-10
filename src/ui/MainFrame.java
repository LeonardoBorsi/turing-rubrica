package ui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import classes.*;

public class MainFrame extends JFrame {
    private User owner;
    private Rubrica rubrica;
    private JTable table;
    private JButton newButton, editButton, deleteButton;

    public MainFrame(Rubrica rubrica, User owner) {
        this.rubrica = rubrica;
        this.owner = owner;

        setTitle(owner.getUsername() + " - Rubrica");
        setSize(1000, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        table = new JTable(createTableModel());
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JToolBar toolbar = new JToolBar();

        newButton = new JButton("Add contact", Utils.getImageIcon("/user-add.png", 13, 13));
        editButton = new JButton("Edit contact", Utils.getImageIcon("/user-pen.png", 13, 13));
        deleteButton = new JButton("Delete contact", Utils.getImageIcon("/user-xmark.png", 13, 13));

        toolbar.add(newButton);
        toolbar.add(editButton);
        toolbar.add(deleteButton);

        add(toolbar, BorderLayout.NORTH);

        newButton.addActionListener(e -> handleNewButton());
        editButton.addActionListener(e -> handleEditButton());
        deleteButton.addActionListener(e -> handleDeleteButton());
    }

    private void handleNewButton() {
        EditorFrame editorFrame = new EditorFrame(MainFrame.this, rubrica, null, false);
        editorFrame.setVisible(true);
        updateTable();
    }

    private void handleEditButton() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) table.getValueAt(selectedRow, 0);
            Person personToEdit = rubrica.getContactById(id);
            EditorFrame editorFrame = new EditorFrame(MainFrame.this, rubrica, personToEdit, true);
            editorFrame.setVisible(true);
            updateTable();
        } else {
            JOptionPane.showMessageDialog(
                    MainFrame.this,
                    "Select a contact to edit",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void handleDeleteButton() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) table.getValueAt(selectedRow, 0);
            rubrica.deleteContact(id);
            updateTable();
        } else {
            JOptionPane.showMessageDialog(
                    MainFrame.this,
                    "Select a contact to delete",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    private void updateTable() {
        table.setModel(createTableModel());
    }

    private DefaultTableModel createTableModel() {
        String[] columnNames = {"ID", "Name", "Surname", "Age", "Address", "Phone"};

        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Person p : rubrica.getAllContacts()) {
            Object[] row = new Object[]{
                    p.getId(),
                    p.getName(),
                    p.getSurname(),
                    p.getAge(),
                    p.getAddress(),
                    p.getPhone(),
            };
            model.addRow(row);
        }

        return model;
    }
}