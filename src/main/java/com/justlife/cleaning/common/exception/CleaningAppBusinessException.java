package com.justlife.cleaning.common.exception;

import java.util.function.Supplier;

public class CleaningAppBusinessException extends BaseCleaningAppException implements Supplier<CleaningAppBusinessException> {

    public CleaningAppBusinessException(String key) {
        super(key);
    }

    public CleaningAppBusinessException(String key, String... args) {
        super(key, args);
    }

    @Override
    public CleaningAppBusinessException get() {
        return new CleaningAppBusinessException(getKey(), getArgs());
    }

}
