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
import java.util.List;
import models.ClientDAO;

/**
 *
 * @author Shane D
 */
@WebServlet(name = "ClientServlet", urlPatterns = {"/api/clients"})
public class ClientServlet extends HttpServlet {
   private final ClientDAO clientDAO = new ClientDAO();
   private final Gson gson = new Gson();

     @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       resp.setContentType("aplication/json");
       resp.setCharacterEncoding("UTF-8");
       
       String id = req.getParameter("id");
       
       if (id != null){
           try {
            int clientID = Integer.parseInt(id);
            Client client = clientDAO.get(clientID).orElse(null);

            if (client != null) {
                 resp.getWriter().write(gson.toJson(client));
             } else {
                 resp.getWriter().write("{}"); //no client found
             }
           } catch(NumberFormatException e){
               resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
               resp.getWriter().write("{'error': 'Invalid client ID'");
              
           }
       } else {
           // list the clients if no specfc id is given
           List<Client> clients = clientDAO.getAll();
           resp.getWriter().write(gson.toJson(clients));
       }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("aplication/json");
        resp.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");
        
        if (id == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"Client ID is required\"}");
        }
        
        try {
            int clientID = Integer.parseInt(id);
            Client clientToUpdate = gson.fromJson(req.getReader(), Client.class);
            clientToUpdate.setClientId(clientID);
            clientDAO.update(clientToUpdate);
         
         resp.getWriter().write(gson.toJson(clientToUpdate));
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
            Client newClient = gson.fromJson(req.getReader(), Client.class);

            clientDAO.insert(newClient);

            resp.getWriter().write(gson.toJson(newClient));
            resp.setStatus(HttpServletResponse.SC_CREATED);
        }     
        catch(Exception e) {
          resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
          resp.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String id = req.getParameter("id");
        if (id == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"Client ID is required for deletion\"}");
            return;
        }

        try {
            int clientId = Integer.parseInt(id);
            Client clientToDelete = clientDAO.get(clientId).orElse(null);

            if (clientToDelete == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error\": \"Client not found\"}");
                return;
            }

            clientDAO.delete(clientToDelete);
            resp.getWriter().write("{\"message\": \"Client deleted successfully\"}");
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"Invalid agent ID format\"}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
   
   
}
