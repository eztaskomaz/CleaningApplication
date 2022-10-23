package com.justlife.cleaning.common.exception;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public abstract class BaseCleaningAppException extends RuntimeException {

    private final String key;
    private final String[] args;

    public BaseCleaningAppException(String key) {
        this.key = key;
        this.args = ArrayUtils.EMPTY_STRING_ARRAY;
    }

    public BaseCleaningAppException(String key, String... args) {
        this.key = key;
        this.args = args;
    }

    public String getKey() {
        return key;
    }

    public String[] getArgs() {
        return args;
    }

    @Override
    public String getMessage() {
        return "Business exception occured " + key + " " + StringUtils.join(args);
    }
}
