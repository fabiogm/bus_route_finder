package busroutefinder.model;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.Predicate;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GraphTest {

    @Mock
    Predicate<Integer> stoppagePredicate;

    private Graph sut;

    private Stack<String> stack;

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

        stack = new Stack<>();
    }

    @Test
    public void shouldVisitEachNodeOnlyOneTime() {
        doReturn(false).when(stoppagePredicate).test(anyInt());

        sut.breadthFirstSearch(1, stoppagePredicate);

        verify(stoppagePredicate, times(1)).test(1);
        verify(stoppagePredicate, times(1)).test(2);
        verify(stoppagePredicate, times(1)).test(3);
        verify(stoppagePredicate, times(1)).test(4);
        verify(stoppagePredicate, times(1)).test(5);
        verify(stoppagePredicate, times(1)).test(6);
        verify(stoppagePredicate, times(1)).test(7);
    }

    @Test
    public void shouldSearchBreadthFirst() {
        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
        doReturn(false).when(stoppagePredicate).test(captor.capture());

        sut.breadthFirstSearch(1, stoppagePredicate);

        assertThat(captor.getAllValues(), equalTo(newArrayList(1, 2, 3, 4, 5, 6, 7)));
    }

    @Test
    public void shouldStopEarlyBreadthFirstSearch() {
        ArrayList<Integer> actualInvocations = new ArrayList<>();
        when(stoppagePredicate.test(anyInt())).thenAnswer(invocation -> {
            int firstMockArg = (Integer) invocation.getArguments()[0];
            actualInvocations.add(firstMockArg);
            return firstMockArg == 3;
        });

        assertTrue(sut.breadthFirstSearch(1, stoppagePredicate));

        assertThat(actualInvocations, equalTo(newArrayList(1, 2, 3)));
    }

    @Test
    public void shouldFindAllPathsBetweenTwoNodes() {
        List<List<Integer>> expectedPaths = Lists.newArrayList();
        List<List<Integer>> actualPaths = new ArrayList<>();

        sut.enumeratePaths(1, 5, actualPaths);

        assertThat(actualPaths, containsInAnyOrder(newArrayList(1, 3, 5), newArrayList(1, 3, 4, 5)));
    }
}