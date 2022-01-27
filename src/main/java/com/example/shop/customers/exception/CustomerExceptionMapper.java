package com.example.shop.customers.exception;

import com.example.shop.customers.helper.Status;
import org.omg.CORBA.UserException;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CustomerExceptionMapper implements ExceptionMapper<CustomerException> {

    @Override
    public Response toResponse(CustomerException e) {
        Status status = new Status();
        status.setMessage(e.getMessage());
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(status)
                .type(MediaType.APPLICATION_JSON)
                .build();

    }
}
