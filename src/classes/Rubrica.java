package classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Rubrica {
    private final Database db;
    private User owner;
    public Rubrica(Database db, User owner) {
        this.db = db;
        this.owner = owner;
    }
    public void addContact(Person person) {
        try {
            String sql = "INSERT INTO person (name, surname, address, phone, age, owner) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = db.connect().prepareStatement(sql);
            statement.setString(1, person.getName());
            statement.setString(2, person.getSurname());
            statement.setString(3, person.getAddress());
            statement.setString(4, person.getPhone());
            statement.setInt(5, person.getAge());
            statement.setInt(6, owner.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.disconnect();
        }
    }

    public void editContact(Person person) {
        try {
            String sql = "UPDATE person SET name=?, surname=?, address=?, phone=?, age=? WHERE id=?";
            PreparedStatement statement = db.connect().prepareStatement(sql);
            statement.setString(1, person.getName());
            statement.setString(2, person.getSurname());
            statement.setString(3, person.getAddress());
            statement.setString(4, person.getPhone());
            statement.setInt(5, person.getAge());
            statement.setInt(6, person.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.disconnect();
        }
    }

    public void deleteContact(int id) {
        try {
            String sql = "DELETE FROM person WHERE id=?";
            PreparedStatement statement = db.connect().prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.disconnect();
        }
    }

    public List<Person> getAllContacts() {
        List<Person> contacts = new ArrayList<>();
        try {
            String sql = "SELECT * FROM person WHERE owner = ?";
            PreparedStatement statement = db.connect().prepareStatement(sql);
            statement.setInt(1, owner.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");
                int age = resultSet.getInt("age");
                int ownerId = resultSet.getInt("owner");
                contacts.add(new Person(id, name, surname, address, phone, age, ownerId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.disconnect();
        }
        return contacts;
    }

    public Person getContactById(int personId) {
        Person p = null;
        try {
            String sql = "SELECT * FROM person WHERE id=?";
            PreparedStatement statement = db.connect().prepareStatement(sql);
            statement.setInt(1, personId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");
                int age = resultSet.getInt("age");
                int ownerId = resultSet.getInt("owner");
                p = new Person(id, name, surname, address, phone, age, ownerId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.disconnect();
        }
        return p;
    }
}