package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        if(args.length!=2){
            System.out.println("Please provide arguments to args");
            System.exit(1);
        }

        String username = args[0];
        String password = args[1];

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/northwind");

        dataSource.setUsername(username);
        dataSource.setPassword(password);

        DataManager dataManager = new DataManager(dataSource);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter the new shipper name:");
        String name = scanner.nextLine();
        System.out.println("Please enter the new shipper phone:");
        String phone = scanner.nextLine();

        dataManager.addShipper(name,phone);

        List<Shipper> shippers = dataManager.getAllShippers();
        displayShippers(shippers);

        System.out.println("Please enter id of shipper to change the phone:");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Please enter new phone:");
        phone = scanner.nextLine();

        dataManager.updatePhone(id,phone);

        shippers = dataManager.getAllShippers();
        displayShippers(shippers);

        System.out.println("Please enter the id of shipper to delete it:");
        id = scanner.nextInt();
        scanner.nextLine();

        dataManager.deleteRecord(id);

        shippers = dataManager.getAllShippers();
        displayShippers(shippers);
    }
    public static void displayShippers(List<Shipper> shippers){
        for (Shipper shipper : shippers) {
            System.out.println(shipper.toString());
        }
    }

}