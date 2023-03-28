package org.example;
import java.sql.*;
import java.lang.Thread;

public class Main {
    public static void main(String[] args) {
        try {
            //Required
            String username = System.getenv("USERNAME");
            String password = System.getenv("PASSWORD");
            String servername = System.getenv("SERVERNAME");
            String dbname = System.getenv("DBNAME");
            String port = System.getenv("PORT");
            String usefastack = System.getenv("USEFASTACK");
            
            System.out.println(NewSocketFactory.class.getName());
            String connectionUrl = "jdbc:sqlserver://" + servername + ".database.windows.net" + ":" + port + ";database=" + dbname + ";user=" + username + "@" + servername + ";password=" + password + ";encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30";
            if(usefastack.compareToIgnoreCase("true") == 0) {
                connectionUrl += ";socketFactoryClass=" + NewSocketFactory.class.getName();
            }
            
            ResultSet resultSet = null;
            try (Connection connection = DriverManager.getConnection(connectionUrl);
                Statement statement = connection.createStatement();)
            {
                // Create and execute a SELECT SQL statement.
                String selectSql = "SELECT TOP 3 Title, FirstName, LastName from SalesLT.Customer";
                
                int i = 0;
                while(i < 5)
                {
                    System.out.println("Iteration: " + i + "\n");
                    resultSet = statement.executeQuery(selectSql);

                    // Print results from select statement
                    while (resultSet.next()) {
                        System.out.println(resultSet.getString(2) + " " + resultSet.getString(3));
                    }
                    System.out.println("\n");
                    Thread.sleep(2000);
                    i++;
                }
            }    
            catch (SQLException e) {
                e.printStackTrace();
            }     
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}