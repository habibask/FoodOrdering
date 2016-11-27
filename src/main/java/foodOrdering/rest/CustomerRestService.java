package foodOrdering.rest;

import foodOrdering.database.CreateStatements;
import foodOrdering.database.MakeConnection;
import foodOrdering.domain.Customer;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/foodapp")
public class CustomerRestService {

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkLogin(Customer customer) {
        try {
            System.out.println("Connection status - " + MakeConnection.getConnection().getMetaData().getUserName());
            System.out.println("In login method");
            return makeResponse(CreateStatements.getUserAccount(MakeConnection.getConnection(),customer), MediaType.APPLICATION_JSON);
        }catch (Exception e){
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

    public Response makeResponse(Object o, String type){
        return Response.ok(o, type)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .build();
    }
}
