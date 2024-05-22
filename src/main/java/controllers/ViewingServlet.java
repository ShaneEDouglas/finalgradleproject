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
import entity.Transaction;
import entity.Viewing;
import java.util.List;
import models.ClientDAO;
import models.PropertyDAO;
import models.TransactionDAO;
import models.ViewingDAO;

/**
 *
 * @author Shane D
 */
@WebServlet(name = "ViewingServlet", urlPatterns = {"/api/viewings"})
public class ViewingServlet extends HttpServlet {
    private final ViewingDAO viewingDAO = new ViewingDAO();
    private final Gson gson = new Gson();

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String id = req.getParameter("id");
        if (id == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"Viewing ID is required for deletion\"}");
            return;
        }

        try {
            int viewingId = Integer.parseInt(id);
            Viewing viewingToDelete = viewingDAO.get(viewingId).orElse(null);

            if (viewingToDelete == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error\": \"Viewing not found\"}");
                return;
            }

            viewingDAO.delete(viewingToDelete);
            resp.getWriter().write("{\"message\": \"Viewing deleted successfully\"}");
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"Invalid viewing ID format\"}");
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
            resp.getWriter().write("{\"error\": \"Viewing ID is required\"}");
        }
        
        try {
            int viewingID = Integer.parseInt(id);
            Viewing viewingToUpdate = gson.fromJson(req.getReader(), Viewing.class);
            viewingToUpdate.setPropertyId(viewingID);
            viewingDAO.update(viewingToUpdate);
         
         resp.getWriter().write(gson.toJson(viewingToUpdate));
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
            Viewing newViewing = gson.fromJson(req.getReader(), Viewing.class);

            viewingDAO.insert(newViewing);

            resp.getWriter().write(gson.toJson(newViewing));
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
            int viewingID = Integer.parseInt(id);
            Viewing viewing = viewingDAO.get(viewingID).orElse(null);

            if (viewing != null) {
                 resp.getWriter().write(gson.toJson(viewing));
             } else {
                 resp.getWriter().write("{}"); //no client found
             }
           } catch(NumberFormatException e){
               resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
               resp.getWriter().write("{'error': 'Invalid viewing ID'");
              
           }
       } else {
           // list the clients if no specfc id is given
           List<Viewing> viewings = viewingDAO.getAll();
           resp.getWriter().write(gson.toJson(viewings));
       }
    }
    
    
}
