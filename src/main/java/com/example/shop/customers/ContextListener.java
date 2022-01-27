package com.example.shop.customers;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebListener
public class ContextListener implements ServletContextListener,ServletContextAttributeListener{

        @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("ServletContext Initialized.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        Connection connection = (Connection) servletContext.getAttribute("con");
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        servletContext.removeAttribute("con");


        System.out.println("ServletContext Destroyed.");
    }


    @Override
    public void attributeAdded(ServletContextAttributeEvent event) {
        System.out.println("ServletContextAttribute Added : "+event.getName() +"-->"+event.getValue());
    }

    @Override
    public void attributeRemoved(ServletContextAttributeEvent event) {
        System.out.println("ServletContextAttribute Removed : "+event.getName() +"-->"+event.getValue());

    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent event) {
        System.out.println("ServletContextAttribute Replaced : "+event.getName() +"-->"+event.getValue());

    }
}
