package io.isotope.enigma.engine.config;

import io.isotope.enigma.engine.config.properties.EnigmaProperties;
import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
@Conditional(OnLocalProfile.class)
public class DebugServerConfiguration {

    @Bean
    public ServletWebServerFactory servletContainer(EnigmaProperties enigmaProperties) {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(getHttpConnector(enigmaProperties));
        return tomcat;
    }

    private Connector getHttpConnector(EnigmaProperties enigmaProperties) {
        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setScheme("http");
        connector.setPort(enigmaProperties.getHttpPort());
        connector.setSecure(false);
        return connector;
    }
}
