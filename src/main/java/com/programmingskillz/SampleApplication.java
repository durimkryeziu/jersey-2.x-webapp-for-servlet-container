package com.programmingskillz;

import com.programmingskillz.providers.SampleObjectMapperProvider;
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

        LOGGER.debug("Registering JAX-RS Components...");
        register(SampleObjectMapperProvider.class);
        property(ServerProperties.MONITORING_ENABLED, Boolean.TRUE);
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, Boolean.TRUE);
    }
}