package edu.cornell.mannlib.vitro.webapp.controller.individual;

import edu.cornell.mannlib.vitro.webapp.config.ConfigurationProperties;
import edu.cornell.mannlib.vitro.webapp.controller.VitroRequest;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Map;

public class GesahIndividualResponseBuilderExtension implements IndividualResponseBuilder.ExtendedResponse {
    @WebListener
    public static class Setup implements ServletContextListener {
        @Override
        public void contextInitialized(ServletContextEvent sce) {
            IndividualResponseBuilder.registerExtendedResponse(new GesahIndividualResponseBuilderExtension());
        }

        @Override
        public void contextDestroyed(ServletContextEvent sce) {

        }
    }

    @Override
    public void addOptions(VitroRequest vreq, Map<String, Object> body) {
        ConfigurationProperties props = ConfigurationProperties.getBean(vreq);

        String iiifUrl = props.getProperty("iiif.url.base", "http://gesah01.develop.labs.tib.eu:8080/gesah-iiif");
        body.put("iiifUrl", iiifUrl);
    }
}
