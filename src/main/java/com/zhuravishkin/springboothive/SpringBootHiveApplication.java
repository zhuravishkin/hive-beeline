package com.zhuravishkin.springboothive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.*;

@SpringBootApplication
public class SpringBootHiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootHiveApplication.class, args);

        try {
            Class.forName("org.apache.hive.jdbc.HiveDriver");
            Connection connection = DriverManager.getConnection("jdbc:hive2://192.168.0.14:10000/", "user", "sudo");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT a.* FROM invites a");
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columns = metaData.getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i < columns; i++) {
                    if (i > 1) {
                        System.out.print(", ");
                    }
                    System.out.print(metaData.getColumnName(i) + ":" + resultSet.getString(i));
                }
                System.out.println();
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

}
