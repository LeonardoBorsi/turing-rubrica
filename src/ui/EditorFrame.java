package ui;

import classes.Person;
import classes.Rubrica;
import classes.Utils;
import javax.swing.*;
import java.awt.*;

public class EditorFrame extends JDialog {
    private JTextField nameField, surnameField, addressField, phoneField, ageField;
    private JButton saveButton, cancelButton;
    private Person person;
    private Rubrica rubrica;
    private boolean isEditMode;

    public EditorFrame(Frame mainFrame, Rubrica rubrica, Person person, boolean isEditMode) {
        super(mainFrame, isEditMode ? "Edit Contact" : "New Contact", true);
        this.rubrica = rubrica;
        this.isEditMode = isEditMode;

        setLayout(new GridLayout(6, 2));
        setSize(500, 300);
        setLocationRelativeTo(mainFrame);

        this.person = (person == null) ? new Person() : person;


        nameField = new JTextField(this.person.getName());
        surnameField = new JTextField(this.person.getSurname());
        addressField = new JTextField(this.person.getAddress());
        phoneField = new JTextField(this.person.getPhone());
        ageField = new JTextField(String.valueOf(this.person.getAge() > 0 ? this.person.getAge() : ""));


        add(new JLabel("Name:"));
        add(nameField);
        add(new JLabel("Surname:"));
        add(surnameField);
        add(new JLabel("Age:"));
        add(ageField);
        add(new JLabel("Address:"));
        add(addressField);
        add(new JLabel("Phone:"));
        add(phoneField);

        cancelButton = new JButton("Cancel");
        saveButton = new JButton("Save");

        add(cancelButton);
        add(saveButton);

        cancelButton.addActionListener(e -> dispose());
        saveButton.addActionListener(e -> submitPerson());
    }

    private void submitPerson() {
        if(!nameField.getText().isEmpty() && !surnameField.getText().isEmpty()) {
            if(Utils.isInteger(ageField.getText())) {
                person.setName(nameField.getText());
                person.setSurname(surnameField.getText());
                person.setAddress(addressField.getText());
                person.setPhone(phoneField.getText());
                person.setAge(Integer.parseInt(ageField.getText()));

                if (isEditMode) {
                    rubrica.editContact(person);
                } else {
                    rubrica.addContact(person);
                }

                dispose();
            } else {
                JOptionPane.showMessageDialog(
                        EditorFrame.this,
                        "Age must be an integer",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        } else {
            JOptionPane.showMessageDialog(
                    EditorFrame.this,
                    "Missing name and surname",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

    }
}
