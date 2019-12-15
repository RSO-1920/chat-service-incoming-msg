package si.fri.rso.config;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@ConfigBundle("app-properties")
public class AppConfigProperties {
    @ConfigValue(value = "mqtt-broker.url-connection")
    private String mqttUrlConnection;

    @ConfigValue(value = "mqtt-broker.topic")
    private String mqttTopic;

    @ConfigValue(value = "mongo.url-connection")
    private String mongoUrlConnection;

    @ConfigValue(value = "mongo.database")
    private String mongoDatabase;

    public String getMongoDatabase() {
        return mongoDatabase;
    }

    public String getMongoUrlConnection() {
        return mongoUrlConnection;
    }

    public String getMqttTopic() {
        return mqttTopic;
    }

    public String getMqttUrlConnection() {
        return mqttUrlConnection;
    }

    public void setMongoDatabase(String mongoDatabase) {
        this.mongoDatabase = mongoDatabase;
    }

    public void setMongoUrlConnection(String mongoUrlConnection) {
        this.mongoUrlConnection = mongoUrlConnection;
    }

    public void setMqttTopic(String mqttTopic) {
        this.mqttTopic = mqttTopic;
    }

    public void setMqttUrlConnection(String mqttUrlConnection) {
        this.mqttUrlConnection = mqttUrlConnection;
    }
}
