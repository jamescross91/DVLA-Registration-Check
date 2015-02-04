package api;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class RegCheckApplication extends Application {
    @Override
    public synchronized Restlet createInboundRoot() {
        Router router = new Router(getContext());

        router.attach("/register", RegisterResource.class);
        router.attach("/login", LoginResource.class);

        //Business
        router.attach("/check", RegCheckResource.class);

        return router;
    }
}
