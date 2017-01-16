package com.programmingskillz.providers;

import com.programmingskillz.repository.DataSource;
import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;

import javax.ws.rs.ext.Provider;

import static org.glassfish.jersey.server.monitoring.ApplicationEvent.Type.INITIALIZATION_FINISHED;

/**
 * @author Durim Kryeziu
 */
@Provider
public class SampleApplicationEventListener implements ApplicationEventListener {

    @Override
    public synchronized void onEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent.getType().equals(INITIALIZATION_FINISHED)) {

            String applicationName = applicationEvent.getResourceConfig().getApplicationName();
            System.out.println("Initializing '" + applicationName + "'...");

            DataSource.init();

            System.out.println("Application '" + applicationName + "' was initialized.");
        }
    }

    @Override
    public synchronized RequestEventListener onRequest(RequestEvent requestEvent) {
        return null;
    }
}