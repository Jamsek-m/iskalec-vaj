package app.v1;

import app.v1.sources.UporabnikSource;
import com.kumuluz.ee.cors.annotations.CrossOrigin;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@ApplicationPath("v1")
@CrossOrigin(supportedMethods = "GET POST PUT DELETE", allowOrigin = "localhost:4200")
public class App extends Application {

}
