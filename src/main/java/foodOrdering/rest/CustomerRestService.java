package foodOrdering.rest;

import foodOrdering.database.CreateStatements;
import foodOrdering.database.MakeConnection;
import foodOrdering.model.Customer;
import foodOrdering.model.MenuItem;
import foodOrdering.model.Order;
import foodOrdering.model.Restaurant;
import org.json.JSONObject;
import sun.font.CreatedFontTracker;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/foodapp")
public class CustomerRestService {

    private HashMap<Integer, Restaurant> restMap = null;

    @POST
    @Path("custregister")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Customer customerRegistration(Customer customer) {
        try {
            System.out.println("In customer registration method");
            return CreateStatements.registerCustomer(MakeConnection.getConnection(), customer);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    @POST
    @Path("custlogin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Customer checkLogin(Customer customer) {
        try {
            System.out.println("In customer login method");
            return CreateStatements.getCustomerAccount(MakeConnection.getConnection(), customer);//, MediaType.APPLICATION_JSON);
        } catch (Exception e) {
            e.printStackTrace();
            return null; //makeResponse(e.getCause(), MediaType.TEXT_PLAIN);
        }
    }

    @POST
    @Path("restlogin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Restaurant checkRestLogin(Customer customer) {
        try {
            System.out.println("In rest login method");
            return CreateStatements.getRestaurantAccount(MakeConnection.getConnection(), customer);//, MediaType.APPLICATION_JSON);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @POST
    @Path("submit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response submitOrder(Order order) {
        try {
            System.out.println("In submitOrder method");
            CreateStatements.createOrder(MakeConnection.getConnection(), order);
            return makeResponse("success", MediaType.TEXT_PLAIN);
        } catch (Exception e) {
            e.printStackTrace();
            return makeResponse(e.getCause(), MediaType.TEXT_PLAIN);
        }
    }

    @POST
    @Path("updateorder")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateOrder(Order order) {
        try {
            System.out.println("In updateOrder method");
            CreateStatements.updateOrder(MakeConnection.getConnection(), order);
            return makeResponse("success", MediaType.TEXT_PLAIN);
        } catch (Exception e) {
            e.printStackTrace();
            return makeResponse(e.getCause(), MediaType.TEXT_PLAIN);
        }
    }

    @POST
    @Path("updateitem")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateItem(@QueryParam("rest") String rest, MenuItem item) {
        try {
            System.out.println("In updateItem method");
            CreateStatements.updateItem(MakeConnection.getConnection(), rest, item);
            return makeResponse("success", MediaType.TEXT_PLAIN);
        } catch (Exception e) {
            e.printStackTrace();
            return makeResponse(e.getCause(), MediaType.TEXT_PLAIN);
        }
    }

    @POST
    @Path("additem")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public MenuItem addItem(@QueryParam("rest") String rest, MenuItem item) {
        try {
            System.out.println("In addItem method");
            return CreateStatements.addItem(MakeConnection.getConnection(), rest, item);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GET
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response test() {
        System.out.println("In test method");
        return makeResponse("Successful", MediaType.TEXT_PLAIN);
    }

    @GET
    @Path("search")
    @Produces(MediaType.APPLICATION_JSON)
    public Restaurant[] searchRestaurants(@QueryParam("filter") String restName) {
        try {
            System.out.println("In searchRestaurants method");
            restMap = CreateStatements.getRestaurantList(MakeConnection.getConnection(), restName);
            Restaurant[] list = new Restaurant[restMap.size()];
            list = restMap.values().toArray(list);
            //System.out.println(list[0].getMenuItems()[0].getName());
            return list; //makeResponse(list, MediaType.APPLICATION_JSON);
        } catch (Exception e) {
            e.printStackTrace();
            return null;//makeResponse(e.getCause(), MediaType.TEXT_PLAIN);
        }
    }

    public Response makeResponse(Object o, String type) {
        return Response.ok(o, type)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .build();
    }


}
