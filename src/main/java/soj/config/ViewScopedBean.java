package soj.config;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class ViewScopedBean implements Serializable {

    @Inject
    @ConfigProperty(name = "test.configuration.property")
    private String injectedConfig;

    public String getInjectedConfig() {
        return injectedConfig;
    }

}
