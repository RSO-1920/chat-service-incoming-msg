package si.fri.rso.api.v1;

import com.kumuluz.ee.discovery.annotations.RegisterService;
import com.kumuluz.ee.health.HealthRegistry;
import com.kumuluz.ee.health.enums.HealthCheckType;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import si.fri.rso.api.v1.controllers.IncomingMsgController;
import si.fri.rso.api.v1.health.IsMongoUpCheck;
import si.fri.rso.api.v1.health.IsMqttUpCheck;
import si.fri.rso.mongo.MongoConnection;
import si.fri.rso.services.MessageListenerTest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/v1")
@OpenAPIDefinition(info = @Info(title = "Incoming message REST API and mqtt message sender", version = "v1", contact = @Contact(), license = @License(),
        description = "Incoming Message api for communication with mqtt broker"), servers = @Server(url ="http://localhost:8090/v1"))
@RegisterService(value = "rso1920-incoming-msg")
public class IncomingMsgApi extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> resources = new HashSet<Class<?>>();
        resources.add(IncomingMsgController.class);
        resources.add(MessageListenerTest.class);
        // resources.add(MongoConnection.class);
        resources.add(LogContextInterceptor.class);
        HealthRegistry.getInstance().register(IsMongoUpCheck.class.getSimpleName(), new IsMongoUpCheck(), HealthCheckType.LIVENESS);
        HealthRegistry.getInstance().register(IsMqttUpCheck.class.getSimpleName(), new IsMqttUpCheck(), HealthCheckType.LIVENESS);
        return resources;
    }
}
