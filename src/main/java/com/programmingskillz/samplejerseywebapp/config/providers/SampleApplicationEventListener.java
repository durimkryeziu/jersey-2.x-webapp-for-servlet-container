package com.programmingskillz.samplejerseywebapp.config.providers;

import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ext.Provider;

import static org.glassfish.jersey.server.monitoring.ApplicationEvent.Type.INITIALIZATION_FINISHED;

/**
 * @author Durim Kryeziu
 */
@Provider
public class SampleApplicationEventListener implements ApplicationEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(SampleApplicationEventListener.class);

    @Override
    public synchronized void onEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent.getType().equals(INITIALIZATION_FINISHED)) {

            String applicationName = applicationEvent.getResourceConfig().getApplicationName();

            LOGGER.info("Application '{}' was initialized.", applicationName);
        }
    }

    @Override
    public synchronized RequestEventListener onRequest(RequestEvent requestEvent) {
        return null;
    }
}