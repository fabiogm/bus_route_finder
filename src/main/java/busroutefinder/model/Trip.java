package busroutefinder.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Trip {
    @JsonProperty("dep_sid")
    private Integer departureStationId;
    @JsonProperty("arr_sid")
    private Integer arrivalStationId;
    @JsonProperty("direct_bus_route")
    private Boolean hasDirectConnection;
}
