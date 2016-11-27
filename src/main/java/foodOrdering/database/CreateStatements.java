package foodOrdering.database;

import foodOrdering.domain.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CreateStatements {

    public static Customer getUserAccount(Connection conn, Customer customerToCheck) throws Exception{
        PreparedStatement stmt = conn.prepareStatement("select name,address,email,password,phone from Customer where email=? and password=?");
        try {
            stmt.setString(1, customerToCheck.getEmail());
            stmt.setString(2, customerToCheck.getPassword());
            System.out.println(customerToCheck.getEmail());
            System.out.println(customerToCheck.getPassword());;
            ResultSet rset = stmt.executeQuery();
            if(rset.next()) {
                System.out.println("Found - "+rset.getString(5));
                return new Customer(rset.getString(1),rset.getString(2),rset.getString(3),rset.getString(4),rset.getString(5));
            }
        } finally {
            stmt.close();
        }
        return null;
    }


}
