package com.justlife.cleaning.model.error;

import java.util.ArrayList;
import java.util.List;

public class ErrorDTO {

    private long timestamp = System.currentTimeMillis();
    private String exception;
    private List<ErrorDetailDTO> errors = new ArrayList<>();

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public List<ErrorDetailDTO> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorDetailDTO> errors) {
        this.errors = errors;
    }

    public void addError(ErrorDetailDTO errorDetailDTO) {
        errors.add(errorDetailDTO);
    }
}
