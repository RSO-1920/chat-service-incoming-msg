package si.fri.rso.api.v1.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import si.fri.rso.config.AppConfigProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class IsMqttUpCheck implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        AppConfigProperties appConfigProperties = CDI.current().select(AppConfigProperties.class).get();

        String broker       = appConfigProperties.getMqttUrlConnection();
        String clientId     = "JavaMessageTest";
        try {
            MqttClient sampleClient = new MqttClient(broker, clientId);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            sampleClient.connect(connOpts);
            sampleClient.disconnect();
            return HealthCheckResponse
                    .named(IsMqttUpCheck.class.getSimpleName())
                    .up()
                    .withData("mqttBroker: ", broker)
                    .build();

        } catch(MqttException e) {
            e.printStackTrace();
            return HealthCheckResponse
                    .named(IsMqttUpCheck.class.getSimpleName())
                    .down()
                    .withData("mqttBroker: ", broker)
                    .build();
        }
    }
}
