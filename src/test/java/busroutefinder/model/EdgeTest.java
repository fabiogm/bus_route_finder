package busroutefinder.model;

import com.google.common.collect.Lists;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class EdgeTest {

    @Test
    public void shouldCorrectlyParseListOfVerticesIntoEdges() {
        ArrayList<Integer> vertices = Lists.newArrayList(3, 7, 8, 12);
        ArrayList<Edge> expectedEdges = Lists.newArrayList(new Edge(3, 7), new Edge(7, 8), new Edge(8, 12));

        Assert.assertThat(Edge.edgesFromVertices(vertices), Matchers.equalTo(expectedEdges));

    }

}