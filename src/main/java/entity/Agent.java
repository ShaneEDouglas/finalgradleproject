/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Shane D
 */
public class Agent {
    int AgentID;
    String FirstName;
    String LastName;
    String ContactNumber;
    String Email;

    public Agent(int AgentID, String FirstName, String LastName, String ContactNUmber, String Email) {
        this.AgentID = AgentID;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.ContactNumber = ContactNUmber;
        this.Email = Email;
    }

    public int getAgentID() {
        return AgentID;
    }

    public void setAgentID(int AgentID) {
        this.AgentID = AgentID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String ContactNUmber) {
        this.ContactNumber = ContactNUmber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    @Override
    public String toString() {
        return "Agent{" + "AgentID=" + AgentID + ", FirstName=" + FirstName + ", LastName=" + LastName + ", ContactNUmber=" + ContactNumber + ", Email=" + Email + '}';
    }
    
    
           
}
