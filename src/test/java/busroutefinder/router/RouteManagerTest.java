package busroutefinder.router;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RouteManagerTest {
    @Mock
    BufferedReader bufferedReader;

    private final String line1 = "2";
    private final String line2 = "0 1 2 3";
    private final String line3 = "1 2 6 7";

    @Test
    public void shouldReadFromStream() throws IOException {
        when(bufferedReader.readLine()).thenReturn(line1, line2, line3);

        RouteManager routeManager = new RouteManager();
        routeManager.loadRoutes(bufferedReader);

        assertTrue(routeManager.areConnected(1, 3));
        assertFalse(routeManager.areConnected(2, 5));
    }

}