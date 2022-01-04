package nce.majorproject.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Response {

    private Long id;
    private String status;
}
