package com.app.exc_handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import com.app.dto.ErrorResponse;
import com.app.dto.ResponseMessage;

@ControllerAdvice 
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	//catch all -- equivalent
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception e) {
		System.out.println("in handle any exc");
		return new ResponseEntity<>(new ErrorResponse("Server side error !!!!",e.getMessage()),
				 HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<ResponseMessage> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e)
	{
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ResponseMessage("Please add file less than 11 MB"));
	}
	
}
