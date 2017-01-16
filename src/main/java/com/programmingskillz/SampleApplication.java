package com.programmingskillz;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import javax.ws.rs.ApplicationPath;

/**
 * @author Durim Kryeziu
 */
@ApplicationPath("webapi")
public class SampleApplication extends ResourceConfig {

    public SampleApplication() {
        setApplicationName("Jersey RESTful Web App");
        packages(this.getClass().getPackage().getName()); // in this case i.e com.programmingskillz

        property(ServerProperties.MONITORING_ENABLED, Boolean.TRUE);
    }
}