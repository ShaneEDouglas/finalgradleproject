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
import java.util.List;
import models.ClientDAO;
import models.PropertyDAO;
import models.TransactionDAO;

/**
 *
 * @author Shane D
 */
@WebServlet(name = "TransactionServlet", urlPatterns = {"/api/transactions"})
public class TransactionServlet extends HttpServlet {
    private final TransactionDAO transactionDAO = new TransactionDAO();
    private final Gson gson = new Gson();

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String id = req.getParameter("id");
        if (id == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"Transaction ID is required for deletion\"}");
            return;
        }

        try {
            int transactionId = Integer.parseInt(id);
            Transaction transactionToDelete = transactionDAO.get(transactionId).orElse(null);

            if (transactionToDelete == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error\": \"Client not found\"}");
                return;
            }

            transactionDAO.delete(transactionToDelete);
            resp.getWriter().write("{\"message\": \"Transaction deleted successfully\"}");
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"Invalid Transaction ID format\"}");
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
            resp.getWriter().write("{\"error\": \"Transaction ID is required\"}");
        }
        
        try {
            int transactionID = Integer.parseInt(id);
            Transaction transactionToUpdate = gson.fromJson(req.getReader(), Transaction.class);
            transactionToUpdate.setPropertyId(transactionID);
            transactionDAO.update(transactionToUpdate);
         
         resp.getWriter().write(gson.toJson(transactionToUpdate));
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
            Transaction newTransaction = gson.fromJson(req.getReader(), Transaction.class);

            transactionDAO.insert(newTransaction);

            resp.getWriter().write(gson.toJson(newTransaction));
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
            int transactionID = Integer.parseInt(id);
            Transaction transaction = transactionDAO.get(transactionID).orElse(null);

            if (transaction != null) {
                 resp.getWriter().write(gson.toJson(transaction));
             } else {
                 resp.getWriter().write("{}"); //no client found
             }
           } catch(NumberFormatException e){
               resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
               resp.getWriter().write("{'error': 'Invalid transaction ID'");
              
           }
       } else {
           // list the clients if no specfc id is given
           List<Transaction> transactions = transactionDAO.getAll();
           resp.getWriter().write(gson.toJson(transactions));
       }
    }
    
    
    
    
}
