package si.fri.rso.api.v1.health;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import si.fri.rso.config.AppConfigProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class IsMongoUpCheck implements HealthCheck {

    @Override
    public HealthCheckResponse call() {

        AppConfigProperties appConfigProperties = CDI.current().select(AppConfigProperties.class).get();

        try {
            MongoClientURI uri = new MongoClientURI(appConfigProperties.getMongoUrlConnection());
            MongoClient mongo = new MongoClient( uri );

            System.out.println("Connected to the database successfully");
            MongoDatabase database = mongo.getDatabase(appConfigProperties.getMongoDatabase());

            return HealthCheckResponse
                    .named(IsMongoUpCheck.class.getSimpleName())
                    .up()
                    .withData("mongoURI", appConfigProperties.getMongoUrlConnection().split("@")[1])
                    .build();

        } catch (Exception e) {
            return HealthCheckResponse
                    .named(IsMongoUpCheck.class.getSimpleName())
                    .down()
                    .withData("mongoURI", appConfigProperties.getMongoUrlConnection().split("@")[1])
                    .build();
        }
    }
}
