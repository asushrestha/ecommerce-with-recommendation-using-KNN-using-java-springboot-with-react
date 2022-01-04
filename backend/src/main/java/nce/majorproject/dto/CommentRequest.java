package nce.majorproject.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CommentRequest {

    private Long id;


    private Long userId;

    @NotNull(message = "Product Id cannot be null")
    private Long productId;

    @Size(min=1,max = 755,message = "Invalid comment length")
    private String postComment;
}
