package foodOrdering.rest;

import foodOrdering.database.CreateStatements;
import foodOrdering.database.MakeConnection;
import foodOrdering.model.Customer;
import foodOrdering.model.Order;
import foodOrdering.model.Restaurant;
import org.json.JSONObject;

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
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkLogin(Customer customer) {
        try {
            System.out.println("In login method");
            return makeResponse(CreateStatements.getUserAccount(MakeConnection.getConnection(), customer), MediaType.APPLICATION_JSON);
        } catch (Exception e) {
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
            return makeResponse(e.getCause(), MediaType.TEXT_PLAIN);
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
