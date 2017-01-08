package com.programmingskillz;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

/**
 * @author Durim Kryeziu
 */
@ApplicationPath("webapi")
public class SampleApplication extends ResourceConfig {

    public SampleApplication() {
        setApplicationName("Sample Jersey Web Application");
        packages("com.programmingskillz");
    }
}