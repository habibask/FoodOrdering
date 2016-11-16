package foodOrdering.rest;

import foodOrdering.domain.Customer;
import foodOrdering.service.CustomerService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public class CustomerRestService {

    @GET
    @Path("hello")
    @Produces(MediaType.TEXT_PLAIN)//APPLICATION_JSON)
    public Response getDefaultCustomerInJSON() {
        System.out.println("In here");
        //CustomerService service = new CustomerService();
        return Response.ok("Test", MediaType.TEXT_PLAIN)
                .header("Access-control-Allow-Origin","*")
                .build();

    }
}
