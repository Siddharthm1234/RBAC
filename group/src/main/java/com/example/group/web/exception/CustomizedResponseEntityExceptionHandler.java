package com.example.group.web.exception;

import com.example.group.constant.Constants;
import com.example.group.web.dto.ResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.List;

/**
 * [Group Service] controller advice allows exception handling techniques across the whole application
 *
 * @author Siddharth Mehta
 */
@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GroupNotFoundException.class)
    public final ResponseEntity<ResponseDto> groupNotFoundException(Exception groupNotFoundException, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), Constants.GROUP_NOT_FOUND, "Group Not Found!", groupNotFoundException.getMessage());

        return new ResponseEntity<>(new ResponseDto(null, exceptionResponse), HttpStatus.OK);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public final ResponseEntity<ResponseDto> roleNotFoundException(Exception roleNotFoundException, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), Constants.ROLE_NOT_FOUND, "Role Not Found!", roleNotFoundException.getMessage());

        return new ResponseEntity<>(new ResponseDto(null, exceptionResponse), HttpStatus.OK);
    }

    @ExceptionHandler(GroupRoleNotFoundException.class)
    public final ResponseEntity<ResponseDto> groupRoleNotFoundException(Exception groupRoleNotFoundException, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), Constants.GROUP_ROLE_NOT_FOUND, "Group-Role Mapping Not Found!", groupRoleNotFoundException.getMessage());

        return new ResponseEntity<>(new ResponseDto(null, exceptionResponse), HttpStatus.OK);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        StringBuilder errorMessages = new StringBuilder();
        for (FieldError fieldError : fieldErrors) {
            errorMessages.append(fieldError.getDefaultMessage() + " ");
        }
        String errorMessage = errorMessages.substring(0, errorMessages.length() - 1);


        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), Constants.GROUP_SERVICE_INVALID_METHOD_ARGUMENT, "Validation Failed!", errorMessage);

        return new ResponseEntity<>(new ResponseDto(null, exceptionResponse), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GroupNameNotUniqueException.class)
    public ResponseEntity<ResponseDto> groupNameNotUniqueException(GroupNameNotUniqueException groupNameNotUniqueException, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), Constants.GROUP_NAME_DATA_INTEGRITY_VIOLATION, "Group Already Exists!", groupNameNotUniqueException.getMessage());

        return new ResponseEntity<>(new ResponseDto(null, exceptionResponse), HttpStatus.OK);

    }

    @ExceptionHandler(GroupRoleNotUniqueException.class)
    public ResponseEntity<ResponseDto> groupRoleNotUniqueException(GroupRoleNotUniqueException groupRoleNotUniqueException, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), Constants.GROUP_ROLE_DATA_INTEGRITY_VIOLATION, "Group-Role Mapping Already Exists!", groupRoleNotUniqueException.getMessage());

        return new ResponseEntity<>(new ResponseDto(null, exceptionResponse), HttpStatus.OK);
    }

    @ExceptionHandler(RoleServiceDownException.class)
    public final ResponseEntity<ResponseDto> roleServiceDownException(Exception roleServiceDownException, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), Constants.ROLE_SERVICE_DOWN, "Cannot Connect To Role Service!", roleServiceDownException.getMessage());

        return new ResponseEntity<>(new ResponseDto(null, exceptionResponse), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
