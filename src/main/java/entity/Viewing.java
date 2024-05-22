/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;


import java.sql.Date;

/**
 *
 * @author Shane D
 */
public class Viewing {
    private int viewingId;
    private int propertyId;
    private int clientId;
    private Date viewingDate;
    private int agentId;

    public Viewing(int viewingId, int propertyId, int clientId, Date viewingDate, int agentId) {
        this.viewingId = viewingId;
        this.propertyId = propertyId;
        this.clientId = clientId;
        this.viewingDate = viewingDate;
        this.agentId = agentId;
    }

    public int getViewingId() {
        return viewingId;
    }

    public void setViewingId(int viewingId) {
        this.viewingId = viewingId;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public Date getViewingDate() {
        return viewingDate;
    }

    public void setViewingDate(Date viewingDate) {
        this.viewingDate = viewingDate;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    @Override
    public String toString() {
        return "Viewing{" + "viewingId=" + viewingId + ", propertyId=" + propertyId + ", clientId=" + clientId + ", viewingDate=" + viewingDate + ", agentId=" + agentId + '}';
    }
    
    
}
