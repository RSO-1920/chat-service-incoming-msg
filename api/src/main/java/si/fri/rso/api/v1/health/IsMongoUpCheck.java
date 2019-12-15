package si.fri.rso.api.v1.health;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class IsMongoUpCheck implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        try {
            MongoClientURI uri = new MongoClientURI("mongodb://user:123456a@ds255728.mlab.com:55728/rso1920-messages?retryWrites=false");
            MongoClient mongo = new MongoClient( uri );

            System.out.println("Connected to the database successfully");
            MongoDatabase database = mongo.getDatabase("rso1920-messages");

            return HealthCheckResponse
                    .named(IsMongoUpCheck.class.getSimpleName())
                    .up()
                    .withData("mongoURI", "ds255728.mlab.com:55728/rso1920-messages")
                    .build();

        } catch (Exception e) {
            return HealthCheckResponse
                    .named(IsMongoUpCheck.class.getSimpleName())
                    .down()
                    .withData("mongoURI", "ds255728.mlab.com:55728/rso1920-messages")
                    .build();
        }
    }
}
