package si.fri.rso.services;

import org.eclipse.paho.client.mqttv3.*;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;

@ApplicationScoped
public class MessageListenerTest implements MqttCallback {
    MqttClient client;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object o){
        System.out.println("NEW CLIENT");
        try {
            client = new MqttClient("tcp://35.225.233.75:1883", "Sending");
            client.connect();
            client.setCallback(this);
            client.subscribe("java/message");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connectionLost(Throwable throwable) {
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        System.out.println("MESSAGE RECEIVED: " + mqttMessage.toString());
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
