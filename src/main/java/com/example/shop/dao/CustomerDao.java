package com.example.shop.dao;

import com.example.shop.customers.exception.CustomerException;
import com.example.shop.customers.model.Customer;
import com.example.shop.db.ConnectionFactory;
import org.glassfish.jersey.internal.inject.Custom;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao {


    public Connection getConnection()
    {
        ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
        return connectionFactory.getConnection();
    }

    public List<Customer> readAll() throws SQLException {
        String query = "SELECT * from customers";
        List<Customer> customerList = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
            preparedStatement = connection.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next())
        {
            long id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String email = resultSet.getString(3);
            String phoneNo = resultSet.getString(4);
            String address = resultSet.getString(5);

            customerList.add(new Customer(id,name,email,phoneNo,address));

        }


        if(customerList == null)
            throw new CustomerException("No Records available.");


        return customerList;

    }

    public Customer readById(long id) throws SQLException {
        String query = "SELECT * FROM customers WHERE customerid = ?";
        Connection connection = getConnection();
        Customer customer = null;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                long customerId = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);
                String phoneNo = resultSet.getString(4);
                String address = resultSet.getString(5);

                customer = new Customer(customerId,name,email,phoneNo,address);


            }

        if(customer == null)
            throw new CustomerException("Record not found for the given ID : "+id);


        return customer;
    }

    public Boolean isExist(Customer customer) throws SQLException {

        String query = "SELECT COUNT(*) FROM customers WHERE email = ? OR phoneNo = ?";
        Connection connection = getConnection();
        int rows =0;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,customer.getEmail());
            preparedStatement.setString(2,customer.getPhoneNo());


            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                rows = resultSet.getInt(1);
            }




        return rows != 0;


    }

    public Boolean isExist(long id) throws SQLException {

        String query = "SELECT COUNT(*) FROM customers WHERE customerid = ?";
        Connection connection = getConnection();
        int rows =0;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1,id);


            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                rows = resultSet.getInt(1);
            }

        return rows == 0;


    }


    public Customer create(Customer customer) throws SQLException {

        if(!isExist(customer))
        {
            String query = "INSERT INTO customers (name,email,phoneno,address) VALUES (?,?,?,?)";

            Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1,customer.getName());
                preparedStatement.setString(2,customer.getEmail());
                preparedStatement.setString(3,customer.getPhoneNo());
                preparedStatement.setString(4,customer.getAddress());
                preparedStatement.executeUpdate();

                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                while (resultSet.next())
                {
                    customer.setId(resultSet.getLong(1));
                }


            return customer;
        }

        else {

            // Throw Exception
            throw new CustomerException("Account Already Exists !");

        }

    }


    public int update(Customer customer) throws SQLException {

        if(!isExist(customer.getId())){

            String query = "UPDATE customers set name = ? , email = ? , phoneNo = ?,address = ? WHERE customerid = ? ";
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,customer.getName());
            preparedStatement.setString(2,customer.getEmail());
            preparedStatement.setString(3,customer.getPhoneNo());
            preparedStatement.setString(4,customer.getAddress());
            preparedStatement.setLong(5,customer.getId());

            int rows = preparedStatement.executeUpdate();

            return rows;


        }
        else {
            throw new CustomerException("Account Not Exists for the given id " +customer.getId() +" or Invalid");
        }
    }

    public Customer delete(long id) throws SQLException {
        if(!isExist(id)) {
            Customer customer = readById(id);
            String query = "DELETE FROM customers WHERE customerid = ?";
            Connection connection = getConnection();

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();

            return customer;
        }
        else {

            throw new CustomerException("Account Not Exists for the given id " +id);

        }


    }






}
