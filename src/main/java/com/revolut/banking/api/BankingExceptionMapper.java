package com.revolut.banking.api;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import com.revolut.banking.exception.TransferException;

/**
 * Class responsible for handling exceptions.
 * It will return an INTERNAL_SERVER_ERROR as status code.
 * 
 * @author ilincamarin
 * 
 */
public class BankingExceptionMapper implements ExceptionMapper<TransferException> {

	public Response toResponse(TransferException ex) {

		return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
				.entity(ex.getMessage())
				.type(MediaType.TEXT_PLAIN).
				build();
	}

}

