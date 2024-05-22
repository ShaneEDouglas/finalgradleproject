/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Shane D
 */
public class Property {
    private int propertyId;
    private String address;
    private double price;
    private String propertyType;
    private String status;
    private int agentId;

    public Property(int propertyId, String address, double price, String propertyType, String status, int agentId) {
        this.propertyId = propertyId;
        this.address = address;
        this.price = price;
        this.propertyType = propertyType;
        this.status = status;
        this.agentId = agentId;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    @Override
    public String toString() {
        return "Property{" + "propertyId=" + propertyId + ", address=" + address + ", price=" + price + ", propertyType=" + propertyType + ", status=" + status + ", agentId=" + agentId + '}';
    }
    
    
}
