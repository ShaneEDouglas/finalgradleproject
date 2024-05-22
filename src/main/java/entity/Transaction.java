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
public class Transaction {
    private int transactionId;
    private int propertyId;
    private int clientId;
    private Date transactionDate;
    private double salePrice;

    public Transaction(int transactionId, int propertyId, int clientId, Date transactionDate, double salePrice) {
        this.transactionId = transactionId;
        this.propertyId = propertyId;
        this.clientId = clientId;
        this.transactionDate = transactionDate;
        this.salePrice = salePrice;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
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

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    @Override
    public String toString() {
        return "Transaction{" + "transactionId=" + transactionId + ", propertyId=" + propertyId + ", clientId=" + clientId + ", transactionDate=" + transactionDate + ", salePrice=" + salePrice + '}';
    }
    
    
}
