package busroutefinder.input;

import busroutefinder.configuration.ConfigurationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Component
public class RouteFileManager {

    private BufferedReader routeFileBuffer;
    private String filename;

    @Autowired
    public RouteFileManager(ConfigurationManager configurationManager) throws IOException {
        filename = configurationManager.getRouteFile();
        routeFileBuffer = new BufferedReader(new FileReader(filename));
    }

    public BufferedReader getRouteFileContent() {
        return routeFileBuffer;
    }

    public String getFilename() {
        return filename;
    }
}
