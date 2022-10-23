package com.justlife.cleaning.common.exception;

import java.util.function.Supplier;

public class CleaningAppDomainNotFoundException extends BaseCleaningAppException implements Supplier<CleaningAppDomainNotFoundException> {

    private static final long serialVersionUID = -2637102363408773760L;

    public CleaningAppDomainNotFoundException(String key) {
        super(key);
    }

    public CleaningAppDomainNotFoundException(String key, String... args) {
        super(key, args);
    }

    @Override
    public CleaningAppDomainNotFoundException get() {
        return new CleaningAppDomainNotFoundException(getKey(), getArgs());
    }

}
