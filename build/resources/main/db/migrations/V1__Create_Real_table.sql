/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  Shane D
 * Created: May 7, 2024
 */

CREATE TABLE Agents (
    AgentID INT PRIMARY KEY,
    FirstName VARCHAR(50),
    LastName VARCHAR(50),
    ContactNumber VARCHAR(20),
    EMAIL VARCHAR(50)
);

CREATE TABLE Properties (
    PropertyID INT PRIMARY KEY,
    Address VARCHAR(255),
    Price DECIMAL(10, 2),
    PropertyType VARCHAR(50),
    Status VARCHAR(50),
    AgentID INT,
    
    FOREIGN KEY (AgentID) REFERENCES Agents(AgentID)
);

CREATE TABLE Clients (
    ClientID INT PRIMARY KEY,
    FirstName VARCHAR(50),
    LastName VARCHAR(50),
    ContactNumber VARCHAR(15),
    Email VARCHAR(100)
);

CREATE TABLE Transactions (
    TransactionID INT PRIMARY KEY,
    PropertyID INT,
    ClientID INT,
    TransactionDate DATE,
    SalePrice DECIMAL(10, 2),
    
    FOREIGN KEY (PropertyID) REFERENCES Properties(PropertyID),
    FOREIGN KEY (ClientID) REFERENCES Clients(ClientID)
);

CREATE TABLE Viewings (
    ViewingID INT PRIMARY KEY,
    PropertyID INT,
    ClientID INT,
    ViewingDate DATE,  -- Changed from DATETIME to TIMESTAMP
    AgentID INT,
    
    FOREIGN KEY (PropertyID) REFERENCES Properties(PropertyID),
    FOREIGN KEY (ClientID) REFERENCES Clients(ClientID),
    FOREIGN KEY (AgentID) REFERENCES Agents(AgentID)
);




