package io.swagger.steps.helper;

import io.cucumber.core.internal.com.fasterxml.jackson.annotation.JsonProperty;

public class CustomError {
    @JsonProperty
    private String message;

    @JsonProperty
    private String timestamp;

    @JsonProperty
    private int status;

    @JsonProperty
    private String error;

    @JsonProperty
    private String path;

    public String getMessage() {
        return message;
    }
}