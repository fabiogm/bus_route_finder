package busroutefinder.configuration;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class ConfigurationManager {

    private String routeFilename;

    public ConfigurationManager() {
        routeFilename = System.getProperty("routeFile");
        log.info("Loading route file: " + routeFilename);
    }

    public String getRouteFile() {
        return routeFilename;
    }

}
