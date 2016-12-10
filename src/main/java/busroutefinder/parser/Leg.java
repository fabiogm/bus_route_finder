package busroutefinder.parser;

import lombok.Data;

@Data
public class Leg {
    private final Integer source;
    private final Integer destination;
}
