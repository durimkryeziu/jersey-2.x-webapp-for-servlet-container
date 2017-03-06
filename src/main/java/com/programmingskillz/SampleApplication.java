package com.programmingskillz;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.jaxrs.xml.JacksonXMLProvider;
import com.programmingskillz.providers.SampleObjectMapperProvider;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.filter.UriConnegFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Durim Kryeziu
 */
@ApplicationPath("webapi")
public class SampleApplication extends ResourceConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SampleApplication.class);

    public SampleApplication() {
        setApplicationName("Jersey RESTful Webapp");
        packages(this.getClass().getPackage().getName()); // in this case i.e com.programmingskillz

        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());

        JacksonXMLProvider xmlProvider = new JacksonXMLProvider();

        xmlProvider.setMapper(xmlMapper);
        xmlProvider.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        Map<String, MediaType> mediaTypeMappings = new HashMap<>();
        mediaTypeMappings.put("xml", MediaType.APPLICATION_XML_TYPE);
        mediaTypeMappings.put("json", MediaType.APPLICATION_JSON_TYPE);

        UriConnegFilter uriConnegFilter = new UriConnegFilter(mediaTypeMappings, null);

        LOGGER.debug("Registering JAX-RS Components...");

        register(SampleObjectMapperProvider.class);
        register(xmlProvider);
        register(uriConnegFilter);
        register(LoggingFeature.class);

        property(ServerProperties.MONITORING_ENABLED, Boolean.TRUE);
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, Boolean.TRUE);
        property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_SERVER, "INFO");
        property(LoggingFeature.LOGGING_FEATURE_VERBOSITY_SERVER, LoggingFeature.Verbosity.HEADERS_ONLY);
    }
}