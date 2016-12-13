package busroutefinder.route;

import busroutefinder.input.RouteFileManager;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.IOException;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RoutePlannerTest {
    @Mock
    BufferedReader bufferedReader;

    @Mock
    RouteFileManager routeFileManager;

    @Mock
    RouteManager routeManager;

    private final String line1 = "1";
    private final String line2 = "0 1 2 3";

    @Test
    public void shouldDetectDirectPathFromSourceToDestination() throws IOException {
        when(bufferedReader.readLine()).thenReturn(line1, line2);
        when(routeFileManager.getRouteFileContent()).thenReturn(bufferedReader);
        when(routeManager.getRoute(anyString())).thenReturn(Sets.newHashSet(1));

        RoutePlanner routePlanner = new RoutePlanner(routeFileManager, routeManager);

        assertTrue(routePlanner.hasDirectBusRouteTo(1, 3));
        assertFalse(routePlanner.hasDirectBusRouteTo(2, 5));
    }

}