package com.example.compsciia.models;

import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private Image profileImage;

    public User() {
        // Default constructor
    }

    public User(int id, String username, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public User(int id, String username, String email, String password, String firstName, String lastName, String phoneNumber, LocalDate dateOfBirth) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
    }

    public User(int id, String username, String email, String password, String firstName, String lastName, String phoneNumber, LocalDate dateOfBirth, Image profileImage) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.profileImage = profileImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }

    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public void setProfileImage(Image profileImage) {
        this.profileImage = profileImage;
    }
    public Image getProfileImage() {
        return profileImage;
    }

    public static User fromResultSet(ResultSet resultSet) throws SQLException, IOException {
        int id = resultSet.getInt("id");
        String username = resultSet.getString("username");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        String phoneNumber = resultSet.getString("phone_number");
        LocalDate dateOfBirth = resultSet.getObject("date_of_birth", LocalDate.class);
        byte[] imgBytes = resultSet.getBytes("profile_image");
        if (imgBytes != null){
            InputStream in = new ByteArrayInputStream(imgBytes);
            BufferedImage tempProfileImage = ImageIO.read(in);
            Image profileImage = SwingFXUtils.toFXImage(tempProfileImage, null);
            return new User(id, username, email, password, firstName, lastName, phoneNumber, dateOfBirth, profileImage);
        } else {
            return new User(id, username, email, password, firstName, lastName, phoneNumber, dateOfBirth);
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}
