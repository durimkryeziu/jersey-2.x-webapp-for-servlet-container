package com.programmingskillz;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.jaxrs.xml.JacksonXMLProvider;
import com.programmingskillz.providers.SampleObjectMapperProvider;
import io.swagger.jaxrs.config.BeanConfig;
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

        String[] packages = {this.getClass().getPackage().getName(), "io.swagger.jaxrs.listing"};

        packages(packages);

        LOGGER.debug("Registering JAX-RS Components...");

        register(SampleObjectMapperProvider.class);
        register(jacksonXMLProvider());
        register(uriConnegFilter());
        register(LoggingFeature.class);

        setUpSwagger();

        property(ServerProperties.MONITORING_ENABLED, Boolean.TRUE);
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, Boolean.TRUE);
        property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_SERVER, "INFO");
        property(LoggingFeature.LOGGING_FEATURE_VERBOSITY_SERVER, LoggingFeature.Verbosity.HEADERS_ONLY);
    }

    private JacksonXMLProvider jacksonXMLProvider() {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());

        JacksonXMLProvider xmlProvider = new JacksonXMLProvider();

        xmlProvider.setMapper(xmlMapper);
        xmlProvider.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return xmlProvider;
    }

    private UriConnegFilter uriConnegFilter() {
        Map<String, MediaType> mediaTypeMappings = new HashMap<>();
        mediaTypeMappings.put("xml", MediaType.APPLICATION_XML_TYPE);
        mediaTypeMappings.put("json", MediaType.APPLICATION_JSON_TYPE);

        return new UriConnegFilter(mediaTypeMappings, null);
    }

    private void setUpSwagger() {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setTitle("Jersey RESTful Webapp");
        beanConfig.setDescription("Sample Jersey 2.x Web Application that can be deployed in a Servlet Container");
        beanConfig.setLicense("Unlicense");
        beanConfig.setLicenseUrl("http://unlicense.org");
        beanConfig.setVersion("1.0.0");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:9999");
        beanConfig.setBasePath("webapi");
        beanConfig.setResourcePackage("com.programmingskillz.resource");
        beanConfig.setScan(true);
        beanConfig.setPrettyPrint(true);
    }
}