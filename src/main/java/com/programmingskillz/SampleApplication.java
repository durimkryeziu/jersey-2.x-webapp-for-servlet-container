package com.programmingskillz;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ApplicationPath;

/**
 * @author Durim Kryeziu
 */
@ApplicationPath("webapi")
public class SampleApplication extends ResourceConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SampleApplication.class);

    public SampleApplication() {
        setApplicationName("Jersey RESTful Webapp");
        packages(this.getClass().getPackage().getName()); // in this case i.e com.programmingskillz

        JacksonJsonProvider jsonProvider = new JacksonJsonProvider()
                .configure(SerializationFeature.INDENT_OUTPUT, Boolean.TRUE);

        LOGGER.debug("Registering JAX-RS Components...");
        register(jsonProvider);
        property(ServerProperties.MONITORING_ENABLED, Boolean.TRUE);
    }
}