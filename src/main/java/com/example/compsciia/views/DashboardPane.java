package com.example.compsciia.views;

import com.example.compsciia.compsciia;
import com.example.compsciia.models.Client;
import com.example.compsciia.models.User;
import com.example.compsciia.util.ClientService;
import com.example.compsciia.util.InvestmentService;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class DashboardPane {
    public static AnchorPane createPane(Integer userId){
        AnchorPane root = new AnchorPane();
        root.setLayoutX(10.0);
        root.setLayoutY(10.0);
        root.setPrefHeight(600.0);
        root.setPrefWidth(750.0);
        root.getStylesheets().add(compsciia.class.getResource("stylesheet.css").toExternalForm());

        // BorderPane
        BorderPane borderPane = new BorderPane();
        borderPane.setPrefHeight(600.0);
        borderPane.setPrefWidth(750.0);

        // Top AnchorPane
        AnchorPane topAnchorPane = new AnchorPane();
        topAnchorPane.setPrefHeight(100.0);
        topAnchorPane.setPrefWidth(750.0);
        topAnchorPane.getStyleClass().add("app-bg");
        topAnchorPane.getStylesheets().add(compsciia.class.getResource("stylesheet.css").toExternalForm());

        // Label in Top AnchorPane
        Label titleLabel = new Label("Dashboard");
        titleLabel.setLayoutX(20.0);
        titleLabel.setLayoutY(25.0);
        titleLabel.setPrefHeight(40.0);
        titleLabel.setPrefWidth(500.0);
        titleLabel.setFont(new javafx.scene.text.Font(40.0));
        topAnchorPane.getChildren().add(titleLabel);

        // Center AnchorPane
        AnchorPane centerAnchorPane = new AnchorPane();
        centerAnchorPane.setPrefHeight(500.0);
        centerAnchorPane.setPrefWidth(750.0);

        VBox mainVBox = new VBox();

        // AnchorPane 1
        AnchorPane anchorPane1 = new AnchorPane();

        // Pane 1
        Pane totalClientsPane = new Pane();
        totalClientsPane.getStyleClass().add("pane-design");
        totalClientsPane.getStylesheets().add(compsciia.class.getResource("stylesheet.css").toExternalForm());
        totalClientsPane.setPrefSize(300, 150);
        totalClientsPane.setLayoutX(50.0);
        totalClientsPane.setLayoutY(10.0);

        // Labels for Pane 1
        Label labelTotalClients = new Label("Total Clients");
        labelTotalClients.setFont(new javafx.scene.text.Font(30.0));
        Label labelTotalClientsValue = new Label("");
        labelTotalClientsValue.setFont(new javafx.scene.text.Font(40.0));

        // Set value of labelTotalClientsValue in a Task thread
        Task<Integer> totalClientsValueTask = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                return ClientService.getNumberOfClientsFromDatabaseForUser(userId);
            }
        };
        totalClientsValueTask.setOnSucceeded(e -> {
            labelTotalClientsValue.setText(totalClientsValueTask.getValue().toString());
        });
        new Thread(totalClientsValueTask).start();

        // Set positions for Labels in Pane 1
        labelTotalClients.setLayoutX(60.0);
        labelTotalClients.setLayoutY(10.0);
        labelTotalClients.setAlignment(javafx.geometry.Pos.CENTER);
        labelTotalClientsValue.setPrefSize(200.0, 50.0);
        labelTotalClientsValue.setLayoutX(50.0);
        labelTotalClientsValue.setLayoutY(60.0);
        labelTotalClientsValue.setAlignment(javafx.geometry.Pos.CENTER);

        // Add Labels to Pane 1
        totalClientsPane.getChildren().addAll(labelTotalClients, labelTotalClientsValue);

        // Pane 2
        Pane totalInvestmentsPane = new Pane();
        totalInvestmentsPane.setLayoutX(400.0);
        totalInvestmentsPane.getStyleClass().add("pane-design");
        totalInvestmentsPane.getStylesheets().add(compsciia.class.getResource("stylesheet.css").toExternalForm());
        totalInvestmentsPane.setPrefSize(300, 150);
        totalInvestmentsPane.setLayoutX(400.0);
        totalInvestmentsPane.setLayoutY(10.0);

        // Labels for Pane 2
        Label labelTotalInvestments = new Label("Total Investments");
        labelTotalInvestments.setFont(new javafx.scene.text.Font(30.0));
        Label labelTotalInvestmentsValue = new Label("");
        labelTotalInvestmentsValue.setFont(new javafx.scene.text.Font(40.0));

        // Set value of labelTotalInvestmentsValue in a Task thread
        Task<Integer> totalInvestmentsValueTask = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                return InvestmentService.getNumberOfInvestmentsFromDatabaseForUser(userId);
            }
        };
        totalInvestmentsValueTask.setOnSucceeded(e -> {
            labelTotalInvestmentsValue.setText(totalInvestmentsValueTask.getValue().toString());
        });
        new Thread(totalInvestmentsValueTask).start();

        // Set positions for Labels in Pane 2
        labelTotalInvestments.setLayoutX(40.0);
        labelTotalInvestments.setLayoutY(10.0);
        labelTotalInvestments.setAlignment(javafx.geometry.Pos.CENTER);
        labelTotalInvestmentsValue.setPrefSize(200.0, 50.0);
        labelTotalInvestmentsValue.setLayoutX(50.0);
        labelTotalInvestmentsValue.setLayoutY(60.0);
        labelTotalInvestmentsValue.setAlignment(javafx.geometry.Pos.CENTER);

        // Add Labels to Pane 2
        totalInvestmentsPane.getChildren().addAll(labelTotalInvestments, labelTotalInvestmentsValue);

        // Add Panes to AnchorPane 1
        anchorPane1.getChildren().addAll(totalClientsPane, totalInvestmentsPane);

        // AnchorPane 2
        AnchorPane anchorPane2 = new AnchorPane();

        // ListView
        ObservableList<String> clientNamesInListView = FXCollections.observableArrayList();
        Task<ArrayList<String>> clientNamesTask = new Task<ArrayList<String>>() {
            @Override
            protected ArrayList<String> call() throws Exception {
                return ClientService.getAllClientNamesFromDatabaseForUser(userId);
            }
        };
        clientNamesTask.setOnSucceeded(e -> {
            clientNamesInListView.addAll(clientNamesTask.getValue());
        });
        new Thread(clientNamesTask).start();
        ListView<String> listView = new ListView<>(clientNamesInListView);
        listView.setLayoutX(10.0);
        listView.setLayoutY(40.0);
        listView.setPrefHeight(295.0);
        listView.setPrefWidth(300.0);
        listView.getStyleClass().add("list-view-design");
        listView.getStylesheets().add(compsciia.class.getResource("stylesheet.css").toExternalForm());

        // BarChart
        ObservableList<XYChart.Series<String, Number>> dashboardInvestmentsClientGraphSeries
                = FXCollections.observableArrayList();
        BarChart<String, Number> barChart = new BarChart<>(new CategoryAxis(), new NumberAxis());
        barChart.setLayoutX(320.0);
        barChart.setLayoutY(40.0);
        barChart.setPrefHeight(295.0);
        barChart.setPrefWidth(420.0);
        barChart.getXAxis().setAnimated(false);
        barChart.setStyle("-fx-bar-fill: #FFA670;");
        Task<XYChart.Series<String, Number>> dashboardInvestmentsClientGraphTask = new Task<XYChart.Series<String, Number>>() {
            @Override
            protected XYChart.Series<String, Number> call() throws Exception {
                XYChart.Series<String, Number> series = new XYChart.Series<>();
                series.setName("Number of Investments");
                ArrayList<Integer> clientIDs = ClientService.getAllClientIDsFromDatabaseForUser(userId);
                for (Integer clientID : clientIDs) {
                    Client client = ClientService.getClientFromDatabase(clientID);
                    String clientName = client.getClientFirstName() + " " + client.getClientLastName();
                    Integer numberOfInvestments = InvestmentService.getNumberOfInvestmentsFromDatabaseForClient(clientID);
                    series.getData().add(new XYChart.Data<>(clientName, numberOfInvestments));
                }
                return series;
            }
        };
        dashboardInvestmentsClientGraphTask.setOnSucceeded(e -> {
            dashboardInvestmentsClientGraphSeries.add(dashboardInvestmentsClientGraphTask.getValue());
            Platform.runLater(() -> {
                barChart.getData().addAll(dashboardInvestmentsClientGraphSeries);
            });
        });
        new Thread(dashboardInvestmentsClientGraphTask).start();

        // Labels
        Label labelRegisteredClients = new Label("Registered Clients:");
        labelRegisteredClients.setFont(new javafx.scene.text.Font(25.0));

        Label labelInvestmentEntries = new Label("Number Of Investment Entries By Client:");
        labelInvestmentEntries.setFont(new javafx.scene.text.Font(25.0));

        // Set positions for Labels
        labelRegisteredClients.setLayoutX(10.0);
        labelRegisteredClients.setLayoutY(10.0);
        labelInvestmentEntries.setLayoutX(320.0);
        labelInvestmentEntries.setLayoutY(10.0);

        // Add components to AnchorPane 2
        anchorPane2.getChildren().addAll(listView, barChart, labelRegisteredClients, labelInvestmentEntries);

        // Add AnchorPanes to VBox
        mainVBox.getChildren().addAll(anchorPane1, anchorPane2);

        centerAnchorPane.getChildren().addAll(mainVBox);

        // Adding Top and Center AnchorPanes to BorderPane
        borderPane.setTop(topAnchorPane);
        borderPane.setCenter(centerAnchorPane);

        // Adding BorderPane to AnchorPane
        root.getChildren().add(borderPane);

        return root;
    }
}
