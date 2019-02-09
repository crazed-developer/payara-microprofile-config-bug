package soj.config;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@RequestScoped
public class RequestScopedBean implements Serializable {

    @Inject
    @ConfigProperty(name = "test.configuration.property")
    private String injectedConfig;

    public String getInjectedConfig() {
        return injectedConfig;
    }

}
