package busroutefinder.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class GraphTest {

    private Graph sut;

    @Before
    public void setUp() {
        sut = new Graph();

        sut.addEdge(new Edge(1, 2));
        sut.addEdge(new Edge(1, 3));
        sut.addEdge(new Edge(1, 4));
        sut.addEdge(new Edge(3, 4));
        sut.addEdge(new Edge(3, 5));
        sut.addEdge(new Edge(5, 6));
        sut.addEdge(new Edge(5, 7));

    }

    @Test
    public void shouldFindAllPathsBetweenTwoNodes() {
        List<List<Integer>> actualPaths = new ArrayList<>();

        sut.enumeratePaths(1, 5, actualPaths);

        assertThat(actualPaths, containsInAnyOrder(newArrayList(1, 3, 5), newArrayList(1, 3, 4, 5)));
    }
}