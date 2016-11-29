package foodOrdering.database;

import foodOrdering.model.Customer;
import foodOrdering.model.MenuItem;
import foodOrdering.model.Order;
import foodOrdering.model.Restaurant;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class CreateStatements {

    public static Customer getUserAccount(Connection conn, Customer customerToCheck) throws Exception {
        PreparedStatement stmt = conn.prepareStatement("select name,address,email,password,phone,id from Customer where email=? and password=?");
        try {
            stmt.setString(1, customerToCheck.getEmail());
            stmt.setString(2, customerToCheck.getPassword());
            System.out.println(customerToCheck.getEmail());
            System.out.println(customerToCheck.getPassword());
            ;
            ResultSet rset = stmt.executeQuery();
            if (rset.next()) {
                return new Customer(rset.getInt(6), rset.getString(1), rset.getString(2), rset.getString(3), rset.getString(4), rset.getString(5));
            }
        } finally {
            stmt.close();
        }
        return null;
    }

    public static HashMap<Integer, Restaurant> getRestaurantList(Connection conn, String filter) throws Exception {

        HashMap<Integer, Restaurant> restMap = new HashMap<Integer, Restaurant>();
        StringBuilder query = new StringBuilder("select  distinct r.name,r.address,r.email,r.phone,mi.type,mi.id,mi.name,mi.description,m.cost,r.id " +
                "from Restaurant r, Menuitem mi, Menu m where r.id = m.restaurantId and mi.id = m.menuitem");
        if (!filter.isEmpty()) {
            query.append(" and r.name like ?");
        }
        PreparedStatement stmt = conn.prepareStatement(query.toString());
        try {
            if (!filter.isEmpty())
                stmt.setString(1, "%" + filter.trim() + "%");
            ResultSet rset = stmt.executeQuery();
            while (rset.next()) {
                int restId = rset.getInt(10);
                Restaurant rest = null;
                if (restMap.containsKey(restId)) {
                    rest = restMap.get(restId);
                } else {
                    rest = new Restaurant(rset.getInt(10), rset.getString(1), rset.getString(2), rset.getString(3), rset.getString(4));
                    restMap.put(restId, rest);
                }
                MenuItem item = new MenuItem(rset.getInt(6), rset.getString(7), rset.getString(5), rset.getString(8), rset.getDouble(9));
                rest.addMenuItem(item);
                rest.addCuisines(rset.getString(5));
            }

            return restMap;

        } finally {
            stmt.close();
        }
    }

    public static void createOrder(Connection conn, Order o) throws Exception {
        System.out.println("In createOrder" + o.toString());
        System.out.println(o.getCustomerId() + " - " + o.getRestaurantId());
        System.out.println(o.getFoodItems().length);
        System.out.println(o.getTotalCost());

        PreparedStatement stmt = conn.prepareStatement("insert into orders (orderedby, orderedfrom,time, totalCost)" +
                " values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        PreparedStatement stmt2 = conn.prepareStatement("insert into OrderItem values (?,?,?,?)");
        try {
            stmt.setInt(1, o.getCustomerId());
            stmt.setInt(2, o.getRestaurantId());
            stmt.setTimestamp(3,new java.sql.Timestamp(new java.util.Date().getTime()));
            stmt.setDouble(4, o.getTotalCost());
            System.out.println(stmt.toString());
            int success = stmt.executeUpdate();
            System.out.println(success);
            if (success > 0) {
                System.out.println("Inserted into orders table");
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int key = rs.getInt(1);
                    for (MenuItem mi : o.getFoodItems()) {
                        stmt2.setInt(1, key);
                        stmt2.setInt(2, mi.getId());
                        stmt2.setInt(3, mi.getQuantity());
                        stmt2.setDouble(4, mi.getCost());
                        success = stmt2.executeUpdate();
                        if(success>0){
                            System.out.println("Inserted into orderItems table");
                        }
                    }
                }
            }
        } finally {
            stmt2.close();
            stmt.close();
        }
    }
}
