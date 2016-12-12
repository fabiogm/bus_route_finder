package busroutefinder.controller;

import busroutefinder.router.RouteManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ApiControllerTest {

    @Mock
    RouteManager routeManager;

    private ApiController apiController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        apiController = new ApiController(routeManager);
        mockMvc = MockMvcBuilders.standaloneSetup(apiController).build();
    }

    @Test
    public void shouldInformWhenThereIsRoute() throws Exception {
        doReturn(true).when(routeManager).areConnected(anyInt(), anyInt());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/direct")
                .param("dep_sid", "1")
                .param("arr_sid", "7");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.direct_bus_route", equalTo(true)));
    }

    @Test
    public void shouldInformWhenThereIsNoRoute() throws Exception {
        doReturn(false).when(routeManager).areConnected(anyInt(), anyInt());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/direct")
                .param("dep_sid", "1")
                .param("arr_sid", "7");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.direct_bus_route", equalTo(false)));
    }

    @Test
    public void shouldGiveClientErrorWhenArrivalStationMissing() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/direct")
                .param("dep_sid", "1");

        mockMvc.perform(request)
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldGiveClientErrorWhenDepartureStationMissing() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/direct")
                .param("arr_sid", "7");

        mockMvc.perform(request)
                .andExpect(status().is4xxClientError());
    }

}