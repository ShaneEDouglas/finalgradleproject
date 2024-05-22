/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.io.IOException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import com.google.gson.Gson;
import entity.Client;
import entity.Property;
import java.util.List;
import models.ClientDAO;
import models.PropertyDAO;

/**
 *
 * @author Shane D
 */
@WebServlet(name= "PropertyServlet", urlPatterns = {"/api/properties"})
public class PropertyServlet extends HttpServlet {
    private final PropertyDAO propertyDAO = new PropertyDAO();
    private final Gson gson = new Gson();

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String id = req.getParameter("id");
        if (id == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"Property ID is required for deletion\"}");
            return;
        }

        try {
            int propertyId = Integer.parseInt(id);
            Property propertyToDelete = propertyDAO.get(propertyId).orElse(null);

            if (propertyToDelete == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error\": \"Client not found\"}");
                return;
            }

            propertyDAO.delete(propertyToDelete);
            resp.getWriter().write("{\"message\": \"Property deleted successfully\"}");
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"Invalid property ID format\"}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("aplication/json");
        resp.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");
        
        if (id == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"Property ID is required\"}");
        }
        
        try {
            int propertyID = Integer.parseInt(id);
            Property propertyToUpdate = gson.fromJson(req.getReader(), Property.class);
            propertyToUpdate.setPropertyId(propertyID);
            propertyDAO.update(propertyToUpdate);
         
         resp.getWriter().write(gson.toJson(propertyToUpdate));
        } catch(Exception e){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("aplication/json");
        resp.setCharacterEncoding("UTF-8");
           
        try {
            Property newProperty = gson.fromJson(req.getReader(), Property.class);

            propertyDAO.insert(newProperty);

            resp.getWriter().write(gson.toJson(newProperty));
            resp.setStatus(HttpServletResponse.SC_CREATED);
        }     
        catch(Exception e) {
          resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
          resp.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("aplication/json");
       resp.setCharacterEncoding("UTF-8");
       
       String id = req.getParameter("id");
       
       if (id != null){
           try {
            int propertyID = Integer.parseInt(id);
            Property property = propertyDAO.get(propertyID).orElse(null);

            if (property != null) {
                 resp.getWriter().write(gson.toJson(property));
             } else {
                 resp.getWriter().write("{}"); //no client found
             }
           } catch(NumberFormatException e){
               resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
               resp.getWriter().write("{'error': 'Invalid property ID'");
              
           }
       } else {
           // list the clients if no specfc id is given
           List<Property> properties = propertyDAO.getAll();
           resp.getWriter().write(gson.toJson(properties));
       }
    }
    
    
}
