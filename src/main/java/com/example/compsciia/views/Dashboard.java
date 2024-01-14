package com.example.compsciia.views;

import com.example.compsciia.compsciia;
import com.example.compsciia.models.User;
import com.example.compsciia.util.SceneSwitcher;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Optional;

public class Dashboard {
    public static Scene createScene(Stage stage, loginPage loginPage, User user){
        BorderPane root = new BorderPane();
        root.setPrefSize(1000, 600);

        // Center AnchorPane
        AnchorPane centerPane = new AnchorPane();
        centerPane.setPrefSize(750, 600);
        centerPane.getStylesheets().add(compsciia.class.getResource("stylesheet.css").toExternalForm());

        // Left VBox
        VBox leftVBox = new VBox();
        leftVBox.setPrefSize(250, 600);
        leftVBox.getStyleClass().add("login-bg");

        // First AnchorPane in left VBox
        AnchorPane firstAnchorPane = new AnchorPane();
        Label label = new Label("Clientify");
        label.setFont(new Font(30));
        AnchorPane.setLeftAnchor(label, 70.0);
        AnchorPane.setTopAnchor(label, 7.0);
        firstAnchorPane.getChildren().add(label);

        // Second AnchorPane in left VBox
        AnchorPane secondAnchorPane = new AnchorPane();
        secondAnchorPane.setPrefSize(250, 200);
        Circle circle = new Circle(63, Color.DODGERBLUE);
        circle.setCenterX(10);
        circle.setCenterY(0);
        circle.setLayoutX(115);
        circle.setLayoutY(63);
        Image im = new Image("https://media.istockphoto.com/id/1180184210/photo/sky-cloud-pink-love-sweet-love-color-tone-for-wedding-card-background.jpg?s=612x612&w=0&k=20&c=nm7d0aCwz2CuT-ETUFsI5S08eCnLZQsLhR4pAYJdbP0=",false);
        circle.setFill(new ImagePattern(im));
        Label welcomeLabel = new Label("Welcome!");
        welcomeLabel.setFont(new Font(20));
        welcomeLabel.setPrefSize(100, 25);
        welcomeLabel.setLayoutX(80);
        welcomeLabel.setLayoutY(135);
        Label userLabel = new Label(user.getEmail());
        userLabel.setFont(new Font(20));
        userLabel.setPrefSize(200, 25);
        userLabel.setLayoutX(25);
        userLabel.setLayoutY(160);
        userLabel.setAlignment(javafx.geometry.Pos.CENTER);
        secondAnchorPane.getChildren().addAll(circle, welcomeLabel, userLabel);

        // Third AnchorPane in left VBox
        AnchorPane thirdAnchorPane = new AnchorPane();
        thirdAnchorPane.setPrefSize(250, 225);
        Button dashboardButton = createNavbarButton("Dashboard", "fas-home");
        dashboardButton.setPrefSize(200, 50);
        dashboardButton.setLayoutX(25);
        dashboardButton.setLayoutY(0);
        dashboardButton.setOnAction(e -> {
            try {
                new SceneSwitcher(centerPane, "dashboard.fxml");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        Button addNewButton = createNavbarButton("Add New", "far-plus-square");
        addNewButton.setPrefSize(200, 50);
        addNewButton.setLayoutX(25);
        addNewButton.setLayoutY(55);
        addNewButton.setOnAction(e -> {
            try {
                new SceneSwitcher(centerPane, "addNew.fxml");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        Button clientRecordsButton = createNavbarButton("Client Records", "fas-users");
        clientRecordsButton.setPrefSize(200, 50);
        clientRecordsButton.setLayoutX(25);
        clientRecordsButton.setLayoutY(110);
        clientRecordsButton.setOnAction(e -> {
            try {
                new SceneSwitcher(centerPane, "clientRecords.fxml");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        Button investmentAnalysisButton = createNavbarButton("Investment Analysis", "fas-chart-line");
        investmentAnalysisButton.setPrefSize(200, 50);
        investmentAnalysisButton.setLayoutX(25);
        investmentAnalysisButton.setLayoutY(165);
        investmentAnalysisButton.setOnAction(e -> {
            try {
                new SceneSwitcher(centerPane, "investmentAnalysis.fxml");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        thirdAnchorPane.getChildren().addAll(dashboardButton, addNewButton, clientRecordsButton, investmentAnalysisButton);

        // Fourth AnchorPane in left VBox
        AnchorPane fourthAnchorPane = new AnchorPane();
        fourthAnchorPane.setPrefSize(250, 125);
        Button profileButton = createNavbarButton("Profile", null);
        profileButton.setPrefSize(200, 35);
        profileButton.setLayoutX(25);
        profileButton.setLayoutY(10);
        profileButton.setOnAction(e -> {
            try {
                new SceneSwitcher(centerPane, "profile.fxml");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        Button logOutButton = createNavbarButton("Log Out", null);
        logOutButton.setPrefSize(200, 35);
        logOutButton.setLayoutX(25);
        logOutButton.setLayoutY(50);
        logOutButton.setOnAction(e -> logOut(stage, loginPage));
        ProgressBar progressBar = new ProgressBar(0.0);
        progressBar.setPrefSize(200, 20);
        progressBar.setLayoutX(25);
        progressBar.setLayoutY(95);
        fourthAnchorPane.getChildren().addAll(profileButton, logOutButton, progressBar);

        leftVBox.getChildren().addAll(firstAnchorPane, secondAnchorPane, thirdAnchorPane, fourthAnchorPane);

        root.setCenter(centerPane);
        root.setLeft(leftVBox);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(compsciia.class.getResource("stylesheet.css").toExternalForm());
        return scene;
    }
    private static Button createNavbarButton(String text, String iconLiteral) {
        Button button = new Button(text);
        button.getStyleClass().add("navbar-button");

        if (iconLiteral != null) {
            FontIcon icon = new FontIcon(iconLiteral);
            button.setGraphic(icon);
            icon.setIconSize(12);
        }

        return button;
    }

    private static void logOut(Stage stage, loginPage loginPage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Log Out");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to log out?");
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.isPresent() && buttonType.get().equals(ButtonType.OK)) {
            stage.setScene(loginPage.createScene(stage, new signUpPage()));
        }
    }
}
