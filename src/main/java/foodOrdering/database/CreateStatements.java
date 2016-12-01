package foodOrdering.database;

import foodOrdering.model.Customer;
import foodOrdering.model.MenuItem;
import foodOrdering.model.Order;
import foodOrdering.model.Restaurant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class CreateStatements {

    public static Customer registerCustomer(Connection conn, Customer customer) throws Exception{
        System.out.println("In customer registration: " + customer.getName()+" - "+customer.getEmail());
        PreparedStatement stmt = conn.prepareStatement("Insert into Customer (name,address,email,password,phone) values(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        try {
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getAddress());
            stmt.setString(3, customer.getEmail());
            stmt.setString(3, customer.getPassword());
            stmt.setString(3, customer.getPhone());
            System.out.println(stmt.toString());
            int success = stmt.executeUpdate();
            System.out.println("Success in customer registration:" +success);
            if (success != 1)
                return null;
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int key = rs.getInt(1);
                customer.setId(key);
                return customer;
            }
        } finally {
            stmt.close();
        }
        return null;
    }


    public static Customer getCustomerAccount(Connection conn, Customer customerToCheck) throws Exception {
        PreparedStatement stmt = conn.prepareStatement("select id,name,address,email,phone from Customer where email=? and password=?");
        ResultSet rset = null;
        try {
            stmt.setString(1, customerToCheck.getEmail());
            stmt.setString(2, customerToCheck.getPassword());
            rset = stmt.executeQuery();
            if (rset.next()) {
                Customer c = new Customer(rset.getInt(1), rset.getString(2), rset.getString(3), rset.getString(4), rset.getString(5));
                ArrayList<Order> orders = getOrders(conn, rset.getInt(1), "customer");
                c.setOrders(orders.toArray(new Order[orders.size()]));
                return c;
            }

        } finally {
            //rset.close();
            stmt.close();
        }

        return null;
    }

    public static Restaurant getRestaurantAccount(Connection conn, Customer customerToCheck) throws Exception {
        PreparedStatement stmt = conn.prepareStatement("select id,name,address,email,password,phone,id from Restaurant where email=? and password=?");
        ResultSet rset = null;
        try {
            stmt.setString(1, customerToCheck.getEmail());
            stmt.setString(2, customerToCheck.getPassword());
            rset = stmt.executeQuery();
            if (rset.next()) {
                Restaurant c = new Restaurant(rset.getInt(1), rset.getString(2), rset.getString(3), rset.getString(4), rset.getString(5));
                ArrayList<Order> orders = getOrders(conn, rset.getInt(1), "restaurant");
                c.setOrders(orders.toArray(new Order[orders.size()]));
                c.setMenuItems(getRestaurantList(conn,rset.getString(2)).get(rset.getInt(1)).getMenuItems());
                return c;
            }

        } finally {
//            rset.close();
            stmt.close();
        }

        return null;
    }

    public static ArrayList<Order> getOrders(Connection conn, int id, String table) throws Exception {
        ArrayList<Order> orders = new ArrayList<Order>();
        PreparedStatement stmt2 = null;
        ResultSet rset2 = null;
        if (table.equalsIgnoreCase("customer"))
            stmt2 = conn.prepareStatement("select o.id,c.id,c.name,r.id,r.name,o.time,o.totalCost,mi.id,mi.name,mi.type,oi.quantity,oi.cost,o.status " +
                    "from orders o, orderitem oi, menuitem mi, customer c, restaurant r " +
                    "where o.id = oi.orderNumber and mi.id = oi.menuItem " +
                    "and r.id = o.orderedFrom and c.id = o.orderedBy " +
                    "and o.orderedBy = ? order by o.id,mi.name;", ResultSet.TYPE_SCROLL_INSENSITIVE);
        else
            stmt2 = conn.prepareStatement("select o.id,c.id,c.name,r.id,r.name,o.time,o.totalCost,mi.id,mi.name,mi.type,oi.quantity,oi.cost,o.status " +
                    "from orders o, orderitem oi, menuitem mi, customer c, restaurant r " +
                    "where o.id = oi.orderNumber and mi.id = oi.menuItem " +
                    "and c.id = o.orderedBy and r.id = o.orderedFrom " +
                    "and o.orderedFrom = ? order by o.id,mi.name;", ResultSet.TYPE_SCROLL_INSENSITIVE);
        try {
            stmt2.setInt(1, id);
            rset2 = stmt2.executeQuery();
            Order o;
            int orderId = 0;
            ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
            while (rset2.next()) {
                if (orderId != rset2.getInt(1) && orderId != 0) {
                    rset2.previous();
                    o = new Order(rset2.getInt(1), rset2.getInt(2), rset2.getInt(4), rset2.getDouble(7));
                    o.setCustomerName(rset2.getString(3));
                    o.setRestaurantName(rset2.getString(5));
                    o.setTime(rset2.getTimestamp(6));
                    o.setFoodItems(menuItems.toArray(new MenuItem[menuItems.size()]));
                    o.setStatus(rset2.getString(13));
                    orders.add(o);
                    menuItems.clear();
                    rset2.next();
                }
                menuItems.add(new MenuItem(rset2.getInt(8), rset2.getString(9), rset2.getString(10), rset2.getInt(11), rset2.getDouble(12)));
                orderId = rset2.getInt(1);
            }
            if (!menuItems.isEmpty()) {
                rset2.previous();
                o = new Order(rset2.getInt(1), rset2.getInt(2), rset2.getInt(4), rset2.getDouble(7));
                o.setCustomerName(rset2.getString(3));
                o.setRestaurantName(rset2.getString(5));
                o.setTime(rset2.getTimestamp(6));
                o.setFoodItems(menuItems.toArray(new MenuItem[menuItems.size()]));
                o.setStatus(rset2.getString(13));
                orders.add(o);
            }

        } finally {
//            rset2.close();
            stmt2.close();
        }
        return orders;
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
            System.out.println(stmt.toString());
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

        PreparedStatement stmt = conn.prepareStatement("insert into orders (orderedby, orderedfrom, time, totalCost, status)" +
                " values (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        PreparedStatement stmt2 = conn.prepareStatement("insert into OrderItem values (?,?,?,?)");
        try {
            stmt.setInt(1, o.getCustomerId());
            stmt.setInt(2, o.getRestaurantId());
            stmt.setTimestamp(3, new java.sql.Timestamp(new java.util.Date().getTime()));
            stmt.setDouble(4, o.getTotalCost());
            stmt.setString(5, "Received");
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
                        if (success > 0) {
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

    public static void updateOrder(Connection conn, Order o) throws Exception {
        System.out.println("In updateOrder" + o.toString());
        PreparedStatement stmt = conn.prepareStatement("update orders set status = ? where id = ?");
        try {
            stmt.setString(1, o.getStatus());
            stmt.setInt(2, o.getId());
            System.out.println(stmt.toString());
            int success = stmt.executeUpdate();
            System.out.println(success);
        } finally {
            stmt.close();
        }
    }

    public static void updateItem(Connection conn, String rest, MenuItem mi) throws Exception{
        System.out.println("In updateItem" + mi.getCost()+" - "+mi.getName());
        PreparedStatement stmt1 = conn.prepareStatement("update MenuItem set name = ?, type=?, description=? where id = ?");
        PreparedStatement stmt2 = conn.prepareStatement("update Menu set cost = ? where restaurantId = ? and menuitem = ?");
        try {
            stmt1.setString(1, mi.getName());
            stmt1.setString(2, mi.getCuisine());
            stmt1.setString(3, mi.getDescr());
            stmt1.setInt(4, mi.getId());
            System.out.println(stmt1.toString());
            int success = stmt1.executeUpdate();
            System.out.println(success);
            stmt2.setDouble(1,mi.getCost());
            stmt2.setInt(2,Integer.parseInt(rest));
            stmt2.setInt(3, mi.getId());
            System.out.println(stmt2.toString());
            success = stmt2.executeUpdate();
            System.out.println(success);
        } finally {
            stmt1.close();
            stmt2.close();
        }
    }

    public static MenuItem addItem(Connection conn, String rest, MenuItem mi) throws Exception{
        System.out.println("In addItem" + mi.getCost()+" - "+mi.getName());
        PreparedStatement stmt1 = conn.prepareStatement("Insert into MenuItem (name,type,description) values(?,?,?)", Statement.RETURN_GENERATED_KEYS);
        PreparedStatement stmt2 = conn.prepareStatement("Insert into Menu (cost, restaurantId, menuitem) values(?,?,?)");
        try {
            stmt1.setString(1, mi.getName());
            stmt1.setString(2, mi.getCuisine());
            stmt1.setString(3, mi.getDescr());
            System.out.println(stmt1.toString());
            int success = stmt1.executeUpdate();
            ResultSet rs = stmt1.getGeneratedKeys();
            if (rs.next()) {
                int key = rs.getInt(1);
                System.out.println(success);
                stmt2.setDouble(1, mi.getCost());
                stmt2.setInt(2, Integer.parseInt(rest));
                stmt2.setInt(3, key);
                mi.setId(key);
                System.out.println(stmt2.toString());
                success = stmt2.executeUpdate();
                System.out.println(success);
                return mi;
            }
        } finally {
            stmt1.close();
            stmt2.close();
        }
        return null;
    }
}