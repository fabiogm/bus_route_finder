package busroutefinder.parser;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class RouteDataFileParserTest {
    File tempFile;

    @Before
    public void setUp() throws IOException {
        tempFile = File.createTempFile("route", ".txt");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {
            bw.write("2\n");
            bw.write("0 1 2 3\n");
            bw.write("1 1 2 4 6\n");
        }

        tempFile.deleteOnExit();
    }

    @Test
    public void shouldReadNumberOfRoutesInFile() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(tempFile));
        RouteDataFileParser sut = new RouteDataFileParser(br);
        assertThat(sut.getNumberOfRoutes(), equalTo(2L));
    }

    @Test
    public void shouldCorrectlyReadLines() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(tempFile));
        RouteDataFileParser sut = new RouteDataFileParser(br);

        assertTrue(sut.hasNext());
        Route route = sut.getNextRoute().get();
        assertThat(route.getId(),  equalTo(0));

        assertTrue(sut.hasNext());
        route = sut.getNextRoute().get();
        assertThat(route.getId(), equalTo(1));

        assertFalse(sut.hasNext());
    }

}
