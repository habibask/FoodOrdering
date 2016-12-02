package foodOrdering.rest;

import foodOrdering.database.CreateStatements;
import foodOrdering.database.MakeConnection;
import foodOrdering.model.*;
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
            System.out.println(customer.getPassword()+" -- "+customer.getPhone());
            return CreateStatements.registerCustomer(MakeConnection.getConnection(), customer);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @POST
    @Path("restregister")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Restaurant restaurantRegistration(Restaurant restaurant) {
        try {
            return CreateStatements.registerRestaurant(MakeConnection.getConnection(), restaurant);
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
    @Path("custupdate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response custUpdate(Customer customer) {
        try {
            System.out.println("In restUpdate method");
            CreateStatements.updateCustomerAccount(MakeConnection.getConnection(), customer);//, MediaType.APPLICATION_JSON);
            return makeResponse("success", MediaType.TEXT_PLAIN);
        } catch (Exception e) {
            e.printStackTrace();
            return makeResponse(e.getCause(), MediaType.TEXT_PLAIN);
        }
    }

    @POST
    @Path("restupdate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response restUpdate(Restaurant restaurant) {
        try {
            System.out.println("In restUpdate method");
            CreateStatements.updateRestaurantAccount(MakeConnection.getConnection(), restaurant);//, MediaType.APPLICATION_JSON);
            return makeResponse("success", MediaType.TEXT_PLAIN);
        } catch (Exception e) {
            e.printStackTrace();
            return makeResponse(e.getCause(), MediaType.TEXT_PLAIN);
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

    @POST
    @Path("deleteitem")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteItem(@QueryParam("rest") String rest, MenuItem item) {
        try {
            System.out.println("In deleteItem method");
            CreateStatements.deleteItem(MakeConnection.getConnection(), rest, item);
            return makeResponse("success", MediaType.TEXT_PLAIN);
        } catch (Exception e) {
            e.printStackTrace();
            return makeResponse(e.getCause(), MediaType.TEXT_PLAIN);
        }
    }

    @POST
    @Path("addreview")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addReview(@QueryParam("rest") String rest, Review item) {
        try {
            System.out.println("In addReview method");
            CreateStatements.addReview(MakeConnection.getConnection(), rest, item);
            return makeResponse("success", MediaType.TEXT_PLAIN);
        } catch (Exception e) {
            e.printStackTrace();
            return makeResponse(e.getCause(), MediaType.TEXT_PLAIN);
        }
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

    @POST
    @Path("custdelete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response custDelete(Customer customer) {
        try {
            System.out.println("In custDelete method");
            CreateStatements.deleteCustomerAccount(MakeConnection.getConnection(), customer);//, MediaType.APPLICATION_JSON);
            return makeResponse("success", MediaType.TEXT_PLAIN);
        } catch (Exception e) {
            e.printStackTrace();
            return makeResponse(e.getCause(), MediaType.TEXT_PLAIN);
        }
    }

    @POST
    @Path("restdelete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response restDelete(Restaurant restaurant) {
        try {
            System.out.println("In restDelete method");
            CreateStatements.deleteRestaurantAccount(MakeConnection.getConnection(), restaurant);//, MediaType.APPLICATION_JSON);
            return makeResponse("success", MediaType.TEXT_PLAIN);
        } catch (Exception e) {
            e.printStackTrace();
            return makeResponse(e.getCause(), MediaType.TEXT_PLAIN);
        }
    }


}
