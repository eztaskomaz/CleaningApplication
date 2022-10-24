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
    private static final String ERROR_CODE = ".errorCode";

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

    @ExceptionHandler(CleaningAppBusinessException.class)
    public ResponseEntity<ErrorDTO> handleTrendyolBusinessException(CleaningAppBusinessException exception) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setException("CleaningAppBusinessException");
        errorDTO.addError(getErrorDetailDTO(exception));
        LOGGER.error("CleaningAppBusinessException Caused By:{}", errorDTO.toString());
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CleaningAppDomainNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleTrendyolDomainNotFoundException(CleaningAppDomainNotFoundException exception) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setException("CleaningAppDomainNotFoundException");
        errorDTO.addError(getErrorDetailDTO(exception));
        LOGGER.error("CleaningAppDomainNotFoundException Caused By:{}", errorDTO.toString());
        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
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

    private ErrorDetailDTO getErrorDetailDTO(BaseCleaningAppException exception) {
        ErrorDetailDTO errorDetailDTO = new ErrorDetailDTO();
        errorDetailDTO.setKey(exception.getKey());
        errorDetailDTO.setMessage(getMessage(exception.getKey(), exception.getArgs(), exception.getMessage()));
        errorDetailDTO.setErrorCode(getErrorCode(exception.getKey()));
        errorDetailDTO.setArgs(exception.getArgs());
        return errorDetailDTO;
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

    private String getErrorCode(String key) {
        return Optional.of(getMessage(() -> messageSource.getMessage(key + ERROR_CODE, new Object[]{}, EN)))
                .filter(StringUtils::isNotBlank)
                .orElse(StringUtils.EMPTY);
    }
}
