package com.lexisnexis.assessment.exception;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Sudhakaran Vasudevan
 * 
 *  Custom Error Response model
 *
 */
@AllArgsConstructor
@Getter
public class ErrorResponse {

	private int code;
	private String message;
	private List<String> details;
	private String path;
	private Date timestamp;

}
