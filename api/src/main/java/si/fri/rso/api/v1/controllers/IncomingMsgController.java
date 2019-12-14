package si.fri.rso.api.v1.controllers;


import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/msg")
public class IncomingMsgController {

    @GET
    public Response getChannelMessages(){

        return Response.status(200).entity("ok").build();
    }
}
