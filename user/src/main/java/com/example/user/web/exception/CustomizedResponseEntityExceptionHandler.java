package com.example.user.web.exception;

import com.example.user.constant.Constants;
import com.example.user.web.dto.ResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.List;

/**
 * [User Service] controller advice allows exception handling techniques across the whole application
 *
 * @author Siddharth Mehta
 */
@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<ResponseDto> userNotFoundException(Exception userNotFoundException, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), Constants.USER_NOT_FOUND, "User Not Found!", userNotFoundException.getMessage());

        return new ResponseEntity<>(new ResponseDto(null, exceptionResponse), HttpStatus.OK);
    }

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

    @ExceptionHandler(UserGroupNotFoundException.class)
    public final ResponseEntity<ResponseDto> userGroupNotFoundException(Exception userGroupNotFoundException, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), Constants.USER_GROUP_NOT_FOUND, "User-Group Mapping Not Found!", userGroupNotFoundException.getMessage());

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

        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), Constants.USER_SERVICE_INVALID_METHOD_ARGUMENT, "Validation Failed!", errorMessage);


        return new ResponseEntity<>(new ResponseDto(null, exceptionResponse), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNameNotUniqueException.class)
    public ResponseEntity<ResponseDto> userNameNotUniqueException(UserNameNotUniqueException userNameNotUniqueException, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), Constants.USER_NAME_DATA_INTEGRITY_VIOLATION, "User Already Exists!", userNameNotUniqueException.getMessage());

        return new ResponseEntity<>(new ResponseDto(null, exceptionResponse), HttpStatus.OK);
    }

    @ExceptionHandler(UserGroupNotUniqueException.class)
    public ResponseEntity<ResponseDto> userGroupNotUniqueException(UserGroupNotUniqueException userGroupNotUniqueException, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), Constants.USER_GROUP_DATA_INTEGRITY_VIOLATION, "User-Group Mapping Already Exist!", userGroupNotUniqueException.getMessage());

        return new ResponseEntity<>(new ResponseDto(null, exceptionResponse), HttpStatus.OK);
    }

    @ExceptionHandler(GroupServiceDownException.class)
    public final ResponseEntity<ResponseDto> groupServiceDownException(Exception groupServiceDownException, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), Constants.GROUP_SERVICE_DOWN, "Cannot Connect To Group Service!", groupServiceDownException.getMessage());

        return new ResponseEntity<>(new ResponseDto(null, exceptionResponse), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
