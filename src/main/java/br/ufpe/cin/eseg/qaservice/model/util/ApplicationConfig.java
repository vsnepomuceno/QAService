package br.ufpe.cin.eseg.qaservice.model.util;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("resources")
public class ApplicationConfig extends ResourceConfig {

	 public ApplicationConfig() {
	        packages("br.ufpe.cin.eseg.qaservice.model");
	    }
}
