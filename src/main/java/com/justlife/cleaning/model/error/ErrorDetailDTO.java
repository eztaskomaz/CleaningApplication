package com.justlife.cleaning.model.error;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

public class ErrorDetailDTO implements Serializable {

    private static final long serialVersionUID = 5232341957428189351L;

    private String key;
    private String message;
    private String errorCode;
    private String[] args = ArrayUtils.EMPTY_STRING_ARRAY;

    public ErrorDetailDTO() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("key", key)
                .append("message", message)
                .append("errorCode", errorCode)
                .append("args", args)
                .toString();
    }
}