package com.pluralsight;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private DataSource dataSource;

    public DataManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Shipper> getAllShippers() {
        List<Shipper> shippers = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("""
                     Select * from shippers"""
             )) {
            int id;
            String name;
            String phone;
            try (ResultSet results = statement.executeQuery()) {
                while (results.next()) {
                    id = results.getInt(1);
                    name = results.getString(2);
                    phone = results.getString(3);
                    Shipper shipper = new Shipper(id, name, phone);
                    shippers.add(shipper);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return shippers;
    }

    public void addShipper(String name, String phone){
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "insert into shippers (CompanyName,Phone) values (?,?)", Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, phone);

            int rows = preparedStatement.executeUpdate();

            System.out.println("Rows inserted: " + rows);

            try(ResultSet keys = preparedStatement.getGeneratedKeys()){
                if(keys.next()){
                    System.out.println("A new key was added: " + keys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateRecord(){

    }

}
