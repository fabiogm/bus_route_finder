package busroutefinder.controller;

import busroutefinder.model.Trip;
import busroutefinder.route.RoutePlanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api")
public class ApiController {

    RoutePlanner routePlanner;

    @Autowired
    public ApiController(RoutePlanner routePlanner) {
        this.routePlanner = routePlanner;
    }

    @RequestMapping(value = "/direct", method = GET)
    public Trip direct(@RequestParam(value="dep_sid") Integer departureId,
                       @RequestParam(value="arr_sid") Integer arrivalId) {
        return Trip.builder()
                .departureStationId(departureId)
                .arrivalStationId(arrivalId)
                .hasDirectConnection(routePlanner.hasDirectBusRouteTo(departureId, arrivalId))
                .build();
    }

}
