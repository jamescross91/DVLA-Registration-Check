import api.RegCheckApplication;
import lombok.SneakyThrows;
import nl.flotsam.xeger.Xeger;
import org.restlet.Component;
import org.restlet.data.Parameter;
import org.restlet.data.Protocol;
import org.restlet.util.Series;

public class Bootstrap {
    @SneakyThrows
    public static void main(String args[]) {
        //Create a new Component.
//        Component component = new Component();
//
//        //Create a new HTTPS server listening on port 2709.
//        org.restlet.Server server = component.getServers().add(Protocol.HTTP, 4243);
//        Series<Parameter> params = server.getContext().getParameters();
//
//        component.getContext().getParameters().add("maxThreads", "1024");
//        component.getContext().getParameters().add("minThreads", "128");
//        component.getContext().getParameters().add("lowThreads", "900");
//        component.getContext().getParameters().add("maxQueued", "-1");
//        component.getContext().getParameters().add("acceptQueueSize", "10000");
//
//        //component.getDefaultHost().attach("/web", new WebApplication());
//        component.getDefaultHost().attach("/api", new RegCheckApplication());
//        //Start the component.
//        component.start();
    }
}
