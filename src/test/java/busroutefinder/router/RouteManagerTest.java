package busroutefinder.router;

import busroutefinder.input.RouteFileManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.IOException;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RouteManagerTest {
    @Mock
    BufferedReader bufferedReader;

    @Mock
    RouteFileManager routeFileManager;

    private final String line1 = "2";
    private final String line2 = "0 1 2 3";
    private final String line3 = "1 2 6 7";

    @Test
    public void shouldDetectDirectPathFromSourceToDestination() throws IOException {
        when(bufferedReader.readLine()).thenReturn(line1, line2, line3);
        when(routeFileManager.getRouteFileContent()).thenReturn(bufferedReader);

        RouteManager routeManager = new RouteManager(routeFileManager);

        assertTrue(routeManager.areConnected(1, 3));
        assertFalse(routeManager.areConnected(2, 5));
    }

}