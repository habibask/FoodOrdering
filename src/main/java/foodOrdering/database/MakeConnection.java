package foodOrdering.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class MakeConnection {

    private static Connection connection = null;

    private MakeConnection(){

    }   //Singleton pattern

    public static Connection getConnection() {
        return connection;
    }

    public static boolean makeConnection(){
        try{
            System.out.println("Making connection");
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/FoodOrdering", "root", "Habbu*88");
        } catch (Exception s){
            s.printStackTrace();
            return false;
        }
        return true;
    }
}
