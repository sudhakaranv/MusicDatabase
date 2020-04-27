package com.lexisnexis.assessment.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Sudhakaran Vasudevan
 * 
 * Global exception handler to handle exceptions thrown from controllers
 *
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.lexisnexis.assessment.controller")
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * @param ex
	 * @param request
	 * @return ErrorReponse 
	 * 
	 * This method is to handle exception when the resource is not found
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {

		String errorMessage = ex.getLocalizedMessage();

		log.error(errorMessage, ex);

		List<String> errorDesc = new ArrayList<>();
		errorDesc.add(errorMessage);

		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Resource Not Found", errorDesc,
				request.getDescription(false), new Date());

		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
	}

	/**
	 * This method is to handle exception when required arguments are missing in
	 * request body
	 * 
	 * Overridden to change response format.
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String errorMessage = ex.getLocalizedMessage();
		log.error(errorMessage, ex);

		List<String> errorDesc = ex.getBindingResult().getAllErrors().stream().map(e -> e.getDefaultMessage())
				.collect(Collectors.toList());

		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Bad Request", errorDesc,
				request.getDescription(false), new Date());

		return new ResponseEntity<Object>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	/**
	 * @param ex
	 * @param request
	 * @return ErrorResponse
	 * 
	 * This method is to handle all other internal exceptions
	 */
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorResponse> handleAllOtherExceptions(Exception ex, WebRequest request) {

		String errorMessage = ex.getLocalizedMessage();

		log.error(errorMessage, ex);

		List<String> errorDesc = new ArrayList<>();
		errorDesc.add(ex.getLocalizedMessage());

		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				"Internal Server Error", errorDesc, request.getDescription(false), new Date());

		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
