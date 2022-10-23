package com.justlife.cleaning.common.exception;

import com.justlife.cleaning.model.error.ErrorDTO;
import com.justlife.cleaning.model.error.ErrorDetailDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;
import java.util.Optional;
import java.util.function.Supplier;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);
    private static final Locale EN = new Locale("en");

    private MessageSource messageSource;

    public GlobalControllerExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setException("MethodArgumentNotValidException");
        prepareBindingResult(exception.getBindingResult(), errorDTO);
        LOGGER.error("Field validation failed. Caused by: {}", errorDTO);
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    private void prepareBindingResult(BindingResult bindingResult, ErrorDTO errorDTO) {
        bindingResult.getAllErrors().forEach(i -> {
            ErrorDetailDTO errorDetailDTO = new ErrorDetailDTO();
            errorDetailDTO.setMessage(getMessage(i.getDefaultMessage(), i.getArguments(), StringUtils.EMPTY));
            errorDetailDTO.setKey(i.getDefaultMessage());
            errorDTO.addError(errorDetailDTO);
        });
    }

    private String getMessage(String key, Object[] args, String defaultMessage) {
        return Optional.of(getMessage(() -> messageSource.getMessage(key, args, EN)))
                .filter(StringUtils::isNotBlank)
                .orElse(defaultMessage);
    }

    private String getMessage(Supplier<String> supplier) {
        String message = StringUtils.EMPTY;
        try {
            message = supplier.get();
        } catch (Exception exception) {
            LOGGER.warn("MessageResource Not found", exception);
        }
        return message;
    }
}
