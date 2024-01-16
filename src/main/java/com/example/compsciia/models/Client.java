package com.example.compsciia.models;

import java.time.LocalDate;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Client {
    private int client_id;
    private int user_id;
    private String client_first_name;
    private String client_last_name;
    private String client_email;
    private String client_phone_number;
    private LocalDate client_date_of_birth;
    private String client_address;
    private String client_registered_id;
    public Client(){
        // Default constructor
    }

    public Client(int client_id, int user_id, String client_first_name, String client_last_name){
        this.client_id = client_id;
        this.user_id = user_id;
        this.client_first_name = client_first_name;
        this.client_last_name = client_last_name;
    }

    public Client(int client_id, int user_id, String client_first_name, String client_last_name, String client_email, String client_phone_number, LocalDate client_date_of_birth, String client_address, String client_registered_id){
        this.client_id = client_id;
        this.user_id = user_id;
        this.client_first_name = client_first_name;
        this.client_last_name = client_last_name;
        this.client_email = client_email;
        this.client_phone_number = client_phone_number;
        this.client_date_of_birth = client_date_of_birth;
        this.client_address = client_address;
        this.client_registered_id = client_registered_id;
    }
    public int getClientId(){
        return client_id;
    }
    public void setClientId(int client_id){
        this.client_id = client_id;
    }
    public int getUserId(){
        return user_id;
    }
    public void setUserId(int user_id){
        this.user_id = user_id;
    }
    public String getClientFirstName(){
        return client_first_name;
    }
    public void setClientFirstName(String client_first_name){
        this.client_first_name = client_first_name;
    }
    public String getClientLastName(){
        return client_last_name;
    }
    public void setClientLastName(String client_last_name){
        this.client_last_name = client_last_name;
    }
    public String getClientEmail(){
        return client_email;
    }
    public void setClientEmail(String client_email){
        this.client_email = client_email;
    }
    public String getClientPhoneNumber(){
        return client_phone_number;
    }
    public void setClientPhoneNumber(String client_phone_number){
        this.client_phone_number = client_phone_number;
    }
    public LocalDate getClientDateOfBirth(){
        return client_date_of_birth;
    }
    public void setClientDateOfBirth(LocalDate client_date_of_birth){
        this.client_date_of_birth = client_date_of_birth;
    }
    public String getClientAddress(){
        return client_address;
    }
    public void setClientAddress(String client_address){
        this.client_address = client_address;
    }
    public String getClientRegisteredId(){
        return client_registered_id;
    }
    public void setClientRegisteredId(String client_registered_id){
        this.client_registered_id = client_registered_id;
    }
    public static Client fromResultSet(ResultSet resultSet) throws SQLException {
        int client_id = resultSet.getInt("client_id");
        int user_id = resultSet.getInt("user_id");
        String client_first_name = resultSet.getString("client_first_name");
        String client_last_name = resultSet.getString("client_last_name");
        String client_email = resultSet.getString("client_email");
        String client_phone_number = resultSet.getString("client_phone_number");
        LocalDate client_date_of_birth = resultSet.getObject("client_date_of_birth", LocalDate.class);
        String client_address = resultSet.getString("client_address");
        String client_registered_id = resultSet.getString("client_registered_id");
        return new Client(client_id, user_id, client_first_name, client_last_name, client_email, client_phone_number, client_date_of_birth, client_address, client_registered_id);
    }

    @Override
    public String toString(){
        return "Client{" +
                "client_id=" + client_id +
                ", user_id=" + user_id +
                ", client_first_name='" + client_first_name + '\'' +
                ", client_last_name='" + client_last_name + '\'' +
                ", client_email='" + client_email + '\'' +
                ", client_phone_number='" + client_phone_number + '\'' +
                ", client_date_of_birth='" + client_date_of_birth + '\'' +
                ", client_address='" + client_address + '\'' +
                ", client_registered_id='" + client_registered_id + '\'' +
                '}';
    }
}
