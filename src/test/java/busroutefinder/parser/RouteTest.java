package busroutefinder.parser;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class RouteTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldFailIfLessThanTwoStationsSupplied() throws IOException {
        expectedException.expect(IOException.class);
        expectedException.expectMessage("Routes must have at least two constituting stations.");
        new Route(0, newArrayList(4));
    }

    @Test
    public void shouldCorrectlyParseLegs() throws IOException {
        Route route = new Route(0, newArrayList(4, 6, 7));

        Leg leg = route.getNextLeg().get();
        assertThat(leg.getSource(), equalTo(4));
        assertThat(leg.getDestination(), equalTo(6));

        leg = route.getNextLeg().get();
        assertThat(leg.getSource(), equalTo(6));
        assertThat(leg.getDestination(), equalTo(7));

        assertFalse(route.hasNextLeg());
    }
}
