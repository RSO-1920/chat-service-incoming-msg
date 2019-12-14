package si.fri.rso.api.v1.controllers;


import com.google.gson.Gson;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import si.fri.rso.api.v1.MainController;
import si.fri.rso.lib.MessageObject;
import si.fri.rso.services.MessageToMqttBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/msg")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class IncomingMsgController extends MainController {

    @Inject
    private MessageToMqttBean messageToMqttBean;

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

        boolean isSendToMessageQueue = this.messageToMqttBean.sendMessageToMqtt(messageObject);

        return Response.status(200).entity(this.responseOk("message send", isSendToMessageQueue)).build();
    }
}
