package busroutefinder.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.function.Predicate;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GraphTest {

    @Mock
    Predicate<Integer> stoppagePredicate;

    private Graph sut;

    @Before
    public void setUp() {
        sut = new Graph();

        sut.addEdge(new Edge(1, 2));
        sut.addEdge(new Edge(1, 3));
        sut.addEdge(new Edge(1, 4));
        sut.addEdge(new Edge(3, 5));
    }

    @Test
    public void shouldVisitEachNodeOnlyOneTime() {
        doReturn(false).when(stoppagePredicate).test(anyInt());

        sut.breadthFirstSearch(1, stoppagePredicate);

        verify(stoppagePredicate, times(1)).test(1);
        verify(stoppagePredicate, times(1)).test(2);
        verify(stoppagePredicate, times(1)).test(3);
        verify(stoppagePredicate, times(1)).test(4);
    }

    @Test
    public void shouldSearchBreadthFirst() {
        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
        doReturn(false).when(stoppagePredicate).test(captor.capture());

        sut.breadthFirstSearch(1, stoppagePredicate);

        assertThat(captor.getAllValues(), equalTo(newArrayList(1, 2, 3, 4, 5)));
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
    public void shouldFindConnectedNodes() {
        assertTrue(sut.areConnected(3, 5));
    }

    @Test
    public void shouldNotFindConnectedNodes() {
        assertTrue(sut.areConnected(2, 5));
    }
}