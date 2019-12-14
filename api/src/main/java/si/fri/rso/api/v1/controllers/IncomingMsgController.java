package si.fri.rso.api.v1.controllers;


import com.google.gson.Gson;
import si.fri.rso.api.v1.MainController;
import si.fri.rso.lib.MessageObject;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/msg")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class IncomingMsgController extends MainController {

    @GET
    public Response getChannelMessages(){

        return Response.status(200).entity("ok").build();
    }

    @POST
    @Path("{channelId}")
    public Response messageOnChannel(@PathParam("channelId") Integer channelId, String body){
        Gson gson = new Gson();
        MessageObject messageObject = gson.fromJson(body, MessageObject.class);
        if (messageObject == null || messageObject.getChannelId() == null || channelId == null ) {

            return  Response.status(400).entity(this.responseError(400, "channel id or message not given")).build();
        }

        return Response.status(200).entity(this.responseOk("message send", messageObject)).build();
    }
}
