package com.example.compsciia.models;

import java.time.LocalDate;

public class Investment {
    private int investment_id;
    private int client_id;
    private String investment_name;
    private String investment_amount;
    private LocalDate investment_date;
    private String investment_description;

    public Investment(){
        // Default constructor
    }
    public Investment(int investment_id, int client_id, String investment_name, String investment_amount, LocalDate investment_date){
        this.investment_id = investment_id;
        this.client_id = client_id;
        this.investment_name = investment_name;
        this.investment_amount = investment_amount;
        this.investment_date = investment_date;
    }
    public Investment(int investment_id, int client_id, String investment_name, String investment_amount, LocalDate investment_date, String investment_description){
        this.investment_id = investment_id;
        this.client_id = client_id;
        this.investment_name = investment_name;
        this.investment_amount = investment_amount;
        this.investment_date = investment_date;
        this.investment_description = investment_description;
    }
    public int getInvestmentId(){
        return investment_id;
    }
    public void setInvestmentId(int investment_id){
        this.investment_id = investment_id;
    }
    public int getClientId(){
        return client_id;
    }
    public void setClientId(int client_id){
        this.client_id = client_id;
    }
    public String getInvestmentName(){
        return investment_name;
    }
    public void setInvestmentName(String investment_name){
        this.investment_name = investment_name;
    }
    public String getInvestmentAmount(){
        return investment_amount;
    }
    public void setInvestmentAmount(String investment_amount){
        this.investment_amount = investment_amount;
    }
    public LocalDate getInvestmentDate(){
        return investment_date;
    }
    public void setInvestmentDate(LocalDate investment_date){
        this.investment_date = investment_date;
    }
    public String getInvestmentDescription(){
        return investment_description;
    }
    public void setInvestmentDescription(String investment_description){
        this.investment_description = investment_description;
    }
    public static Investment fromResultSet(java.sql.ResultSet rs) throws java.sql.SQLException {
        int investment_id = rs.getInt("investment_id");
        int client_id = rs.getInt("client_id");
        String investment_name = rs.getString("investment_name");
        String investment_amount = rs.getString("investment_amount");
        LocalDate investment_date = rs.getObject("investment_date", LocalDate.class);
        String investment_description = rs.getString("investment_description");
        return new Investment(investment_id, client_id, investment_name, investment_amount, investment_date, investment_description);
    }

    @Override
    public String toString(){
        return "Investment{" +
                "investment_id=" + investment_id +
                ", client_id=" + client_id +
                ", investment_name='" + investment_name + '\'' +
                ", investment_amount='" + investment_amount + '\'' +
                ", investment_date='" + investment_date + '\'' +
                ", investment_description='" + investment_description + '\'' +
                '}';
    }
}
