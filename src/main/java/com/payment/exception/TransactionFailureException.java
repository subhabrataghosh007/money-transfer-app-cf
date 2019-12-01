package com.payment.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.payment.dto.ErrorResponse;

@Provider
public class TransactionFailureException extends RuntimeException implements ExceptionMapper<TransactionFailureException> {

	private static final long serialVersionUID = 1L;


	public TransactionFailureException() {
		super();
	}

	public TransactionFailureException(String message) {
		super(message);
	}

	@Override
	public Response toResponse(TransactionFailureException exception) {

		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), Status.NOT_FOUND.getStatusCode());
		return Response.status(Status.NOT_FOUND).entity(errorResponse).build();
	}

}
