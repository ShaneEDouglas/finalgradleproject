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
import entity.Agent;
import java.util.List;
import models.AgentDAO;


/**
 *
 * @author Shane D
 */
@WebServlet(name = "AgentServlet", urlPatterns = {"/api/agents"})
public class AgentServlet extends HttpServlet {
   private final AgentDAO agentDAO = new AgentDAO();
   private final Gson gson = new Gson();
   
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String id = request.getParameter("id");
        
        if (id != null){
            try {
                int agentID = Integer.parseInt(id);               
                Agent agent = agentDAO.get(agentID).orElse(null);
                
                if (agent != null) {
                    response.getWriter().write(gson.toJson(agent));
                } else {
                    response.getWriter().write("{}"); //no agent found
                }
            }
            catch(NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{'error': 'Invalid agent ID'");
                
            }
        } else {
            //Get all the listed agents if no id is provided
            List<Agent> agents = agentDAO.getAll();
            response.getWriter().write(gson.toJson(agents));
            
        }
   }
   
   @Override
   protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("aplication/json");
      response.setCharacterEncoding("UTF-8");
      
      String id = request.getParameter("id");
      
      if (id == null) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write("{\"error\": \"Agent ID is required\"}");
      }
      
      try {
         int agentId = Integer.parseInt(id);
         Agent agentToUpdate = gson.fromJson(request.getReader(), Agent.class);
         agentToUpdate.setAgentID(agentId);
         agentDAO.update(agentToUpdate);
         
         response.getWriter().write(gson.toJson(agentToUpdate));
      } catch (NumberFormatException e) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write("{\"error\": \"Invalid agent ID format\"}");
      } catch (Exception e) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
     }
      
   }
   
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("aplication/json");
      response.setCharacterEncoding("UTF-8");
           
      try {
          Agent newAgent = gson.fromJson(request.getReader(), Agent.class);
          
          agentDAO.insert(newAgent);
          
          response.getWriter().write(gson.toJson(newAgent));
          response.setStatus(HttpServletResponse.SC_CREATED);
      }     
      catch(Exception e) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
      }
   }
   
   @Override
   protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    String id = request.getParameter("id");
    if (id == null) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write("{\"error\": \"Agent ID is required for deletion\"}");
        return;
    }

    try {
        int agentId = Integer.parseInt(id);
        Agent agentToDelete = agentDAO.get(agentId).orElse(null);

        if (agentToDelete == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("{\"error\": \"Agent not found\"}");
            return;
        }

        agentDAO.delete(agentToDelete);
        response.getWriter().write("{\"message\": \"Agent deleted successfully\"}");
    } catch (NumberFormatException e) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write("{\"error\": \"Invalid agent ID format\"}");
    } catch (Exception e) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
    }
   }
}
