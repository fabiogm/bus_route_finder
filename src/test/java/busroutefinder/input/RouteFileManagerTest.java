package busroutefinder.input;

import busroutefinder.configuration.ConfigurationManager;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RouteFileManagerTest {

    @Mock
    ConfigurationManager configurationManager;

    @Test
    public void shouldLoadFilePresentInConfiguration() throws IOException {
        File tempFile = File.createTempFile("test", "file");
        tempFile.deleteOnExit();
        FileWriter fileWriter = new FileWriter(tempFile);
        String fileText = "ThisShouldWork";
        fileWriter.write(fileText);
        fileWriter.close();
        when(configurationManager.getRouteFile()).thenReturn(tempFile.getPath());

        RouteFileManager routeFileManager = new RouteFileManager(configurationManager);

        assertThat(routeFileManager.getFilename(), equalTo(tempFile.getPath()));
        assertThat(routeFileManager.getRouteFileContent().readLine(), equalTo(fileText));
    }
}