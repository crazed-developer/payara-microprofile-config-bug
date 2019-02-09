package soj.config;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ApplicationScoped
public class ApplicationScopedBean implements Serializable {

    @Inject
    @ConfigProperty(name = "test.configuration.property")
    private String injectedConfig;

    public String getInjectedConfig() {
        return injectedConfig;
    }

}
