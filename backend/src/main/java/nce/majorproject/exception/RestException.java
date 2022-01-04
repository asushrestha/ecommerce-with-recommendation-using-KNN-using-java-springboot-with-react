package nce.majorproject.exception;

import lombok.Getter;

@Getter
public class RestException extends RuntimeException{

    private String message;

    public RestException(String message) {
        super(message);
        this.message = message;
    }
}
