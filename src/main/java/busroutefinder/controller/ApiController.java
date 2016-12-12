package busroutefinder.controller;

import busroutefinder.model.Trip;
import busroutefinder.parser.Route;
import busroutefinder.router.RouteManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api")
public class ApiController {

    RouteManager routeManager;

    @Autowired
    public ApiController(RouteManager routeManager) {
        this.routeManager = routeManager;
    }

    @RequestMapping(value = "/direct", method = GET)
    public Trip direct(@RequestParam(value="dep_sid") Integer departureId,
                       @RequestParam(value="arr_sid") Integer arrivalId) {
        return Trip.builder()
                .departureStationId(departureId)
                .arrivalStationId(arrivalId)
                .hasDirectConnection(routeManager.areConnected(departureId, arrivalId))
                .build();
    }

}
