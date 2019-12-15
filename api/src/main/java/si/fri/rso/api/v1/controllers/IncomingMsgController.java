package si.fri.rso.api.v1.controllers;
import com.google.gson.Gson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Timed;
import si.fri.rso.api.v1.MainController;
import si.fri.rso.lib.MessageStatuses;
import si.fri.rso.lib.MongoMessageObject;
import si.fri.rso.lib.responses.ResponseDTO;
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
    @Operation(description = "Get all messages", summary = "Get msg", tags = "get, msg, all", responses = {
            @ApiResponse(responseCode = "200",
                    description = "get all msg",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = MongoMessageObject.class)))
            )
    })
    @Timed(name = "incomingMsg_time_get")
    @Counted(name = "incomingMsg_counted_get")
    @Metered(name = "incomingMsg_metered_get")
    public Response getAllMessages(){
        LOG.info("Inside get all messages");
        List<MongoMessageObject> mongoMessageObjectList = this.mongoQuery.getAllMessages();

        return Response.status(200).entity(this.responseOk("", mongoMessageObjectList)).build();
    }

    @GET
    @Operation(description = "Get message channel", summary = "Get msg channel", tags = "get, msg, channel", responses = {
            @ApiResponse(responseCode = "200",
                    description = "get messages on channel",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = MongoMessageObject.class)))
            )
    })
    @Timed(name = "incomingMsg_time_get_channel")
    @Counted(name = "incomingMsg_counted_get_channel")
    @Metered(name = "incomingMsg_metered_get_channel")
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
    @Operation(description = "Post msg on channel", summary = "Post msg", tags = "post, msg", responses = {
            @ApiResponse(responseCode = "200",
                    description = "post msg success",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            ),
            @ApiResponse(responseCode = "400",
                    description = "No params given",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            )
    })
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
