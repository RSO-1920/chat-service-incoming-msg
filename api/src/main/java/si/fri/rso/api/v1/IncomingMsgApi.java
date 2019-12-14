package si.fri.rso.api.v1;

import si.fri.rso.api.v1.controllers.IncomingMsgController;
import si.fri.rso.services.MessageListenerTest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/v1")
public class IncomingMsgApi extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> resources = new HashSet<Class<?>>();
        resources.add(IncomingMsgController.class);
        resources.add(MessageListenerTest.class);
        return resources;
    }
}
