package com.example.shop.customers.controller;

import com.example.shop.customers.helper.Status;
import com.example.shop.customers.model.Customer;
import com.example.shop.dao.CustomerDao;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Path("/customers")
public class CustomerController {

    private CustomerDao customerDao;
    @Context
    UriInfo uriInfo;

    public CustomerController() {

        customerDao = new CustomerDao();

    }

    @GET
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response get() throws SQLException {

        List<Customer> customerList = customerDao.readAll();
        return Response.ok().entity(customerList).build();

    }

    /*
        customers/1
    */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") long id) throws SQLException {
        Customer customer = customerDao.readById(id);
        return Response.ok().entity(customer).build();

    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(Customer customer) throws SQLException {
        Customer result = customerDao.create(customer);

       String newId = String.valueOf(result.getId());

       URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();

        return Response.status(Response.Status.CREATED)
                .entity(result)
                .location(uri)
                .build();

    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") long id) throws SQLException {

        Customer customer = customerDao.delete(id);
        return Response.ok().entity(customer).build();

    }



    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") long id ,Customer customer) throws SQLException {
        customer.setId(id);
        int result = customerDao.update(customer);
        Status status = new Status();
        status.setMessage(result+" Record Updated Successfully.");
        return Response.ok().entity(status).build();



    }





}
