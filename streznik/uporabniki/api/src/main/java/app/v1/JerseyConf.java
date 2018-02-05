package app.v1;

import app.v1.sources.UporabnikSource;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.enterprise.context.ApplicationScoped;

//@ApplicationScoped
public class JerseyConf extends ResourceConfig {

	public JerseyConf() {
		register(UporabnikSource.class);
		register(MultiPartFeature.class);
		
		
	}


}
