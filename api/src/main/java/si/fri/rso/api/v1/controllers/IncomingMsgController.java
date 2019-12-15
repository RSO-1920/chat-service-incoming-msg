package si.fri.rso.api.v1.controllers;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Timed;
import si.fri.rso.api.v1.MainController;
import si.fri.rso.lib.MessageStatuses;
import si.fri.rso.lib.MongoMessageObject;
import si.fri.rso.mongo.MongoQueryBean;
import si.fri.rso.lib.MessageObject;
import si.fri.rso.services.MessageToMqttBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("/msg")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class IncomingMsgController extends MainController {

    private static final Logger LOG = LogManager.getLogger(IncomingMsgController.class.getName());
    @Inject
    private MessageToMqttBean messageToMqttBean;

    @Inject
    private MongoQueryBean mongoQuery;

    @GET
    @Timed(name = "incomingMsg_time_get")
    @Counted(name = "incomingMsg_counted_get")
    @Metered(name = "incomingMsg_metered_get")
    public Response getAllMessages(){
        LOG.info("Inside get all messages");
        List<MongoMessageObject> mongoMessageObjectList = this.mongoQuery.getAllMessages();

        return Response.status(200).entity(this.responseOk("", mongoMessageObjectList)).build();
    }

    @GET
    @Timed(name = "incomingMsg_time_get_one")
    @Counted(name = "incomingMsg_counted_get_one")
    @Metered(name = "incomingMsg_metered_get_one")
    @Path("{channelId}")
    public Response getMessagesOnChannel(@PathParam("channelId") Integer channelId){

        LOG.info("Inside get channel messages");
        if (channelId == null) {
            return  Response.status(400).entity(this.responseError(400, "channel id not provided")).build();
        }

        List<MongoMessageObject> mongoMessageObjectList = this.mongoQuery.getMessagesOnChannel(channelId);

        return Response.status(200).entity(this.responseOk("", mongoMessageObjectList)).build();
    }

    @POST
    @Timed(name = "incomingMsg_time_post")
    @Counted(name = "incomingMsg_counted_post")
    @Metered(name = "incomingMsg_metered_post")
    @Path("{channelId}")
    public Response messageOnChannel(@PathParam("channelId") Integer channelId, String body){
        LOG.info("Posting message");
        Gson gson = new Gson();
        MessageObject messageObject = gson.fromJson(body, MessageObject.class);
        if (messageObject == null || messageObject.getChannelId() == null || channelId == null ) {
            return  Response.status(400).entity(this.responseError(400, "channel id or message not given")).build();
        }

        boolean isSendToMessageQueue = this.messageToMqttBean.sendMessageToMqtt(messageObject);
        boolean isInsertedInMongoDB = this.mongoQuery.insertNewMessage(messageObject);
        MessageStatuses messageStatuses = new MessageStatuses(isSendToMessageQueue, isInsertedInMongoDB);

        return Response.status(200).entity(this.responseOk("message send", messageStatuses)).build();
    }
}
