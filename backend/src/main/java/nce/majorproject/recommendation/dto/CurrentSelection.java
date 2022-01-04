package nce.majorproject.recommendation.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CurrentSelection {

    @NotNull(message = "select one product id to trigger this initiation")
    private Long currentProductId;

    @NotNull(message = "select view or aded to cart or checkout")
    private String typeOfInput;
}
