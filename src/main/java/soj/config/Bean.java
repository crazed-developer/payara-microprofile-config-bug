package soj.config;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class Bean {

    @Inject
    @ConfigProperty(name = "config.property")
    private String injectedConfig;

    public String getInjectedConfig() {
        return injectedConfig;
    }
}
