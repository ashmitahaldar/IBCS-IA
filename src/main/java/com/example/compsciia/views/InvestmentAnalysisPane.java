package com.example.compsciia.views;

import com.example.compsciia.compsciia;
import com.example.compsciia.models.Client;
import com.example.compsciia.models.Investment;
import com.example.compsciia.util.ClientService;
import com.example.compsciia.util.InvestmentService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.decampo.xirr.Transaction;
import org.decampo.xirr.Xirr;

public class InvestmentAnalysisPane {
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
        Label titleLabel = new Label("Investment Analysis");
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

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        ObservableList<XYChart.Series<String, Number>> investmentsGraphSeries = FXCollections.observableArrayList();
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis, investmentsGraphSeries);
        lineChart.setLayoutX(15.0);
        lineChart.setLayoutY(50.0);
        lineChart.setPrefHeight(380.0);
        lineChart.setPrefWidth(450.0);
        lineChart.getXAxis().setAnimated(false);

        Label chooseClientLabel = new Label("Choose Client:");
        chooseClientLabel.setLayoutX(15.0);
        chooseClientLabel.setLayoutY(10.0);
        chooseClientLabel.setPrefHeight(40.0);
        chooseClientLabel.setPrefWidth(125.0);
        chooseClientLabel.setFont(new Font(20.0));

        AtomicReference<ArrayList<Client>> clients = new AtomicReference<ArrayList<Client>>
                (ClientService.getAllClientsFromDatabaseForUser(userId));
        AtomicReference<ArrayList<Investment>> investments = new AtomicReference<>();
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setLayoutX(150.0);
        comboBox.setLayoutY(10.0);
        comboBox.setPrefHeight(40.0);
        comboBox.setPrefWidth(180.0);
        comboBox.getStyleClass().add("textfield-design");
        comboBox.getStylesheets().add(compsciia.class.getResource("stylesheet.css").toExternalForm());
        Task<List<String>> getComboBoxClientListTask = new Task<List<String>>() {
            @Override
            protected List<String> call() throws Exception {
                ArrayList<String> clientNames = new ArrayList<>();
                for (Client client : clients.get()) {
                    clientNames.add(client.getClientFirstName() + " " + client.getClientLastName()
                            + " (" + client.getClientId() + ")");
                }
                return clientNames;
            }
        };
        javafx.collections.ObservableList<String> clientNamesObservableList = FXCollections.observableArrayList();
        getComboBoxClientListTask.setOnSucceeded(e -> {
            clientNamesObservableList.setAll(getComboBoxClientListTask.getValue());
            comboBox.setItems(clientNamesObservableList);
        });
        new Thread(getComboBoxClientListTask).start();
        comboBox.setOnAction(e -> {
            int comboBoxSelectedClientId = Integer.parseInt(comboBox.getSelectionModel().getSelectedItem().split("\\(")[1].split("\\)")[0]);
            investments.set(InvestmentService.getAllInvestmentsFromDatabaseForClient(comboBoxSelectedClientId));
            System.out.println("Selected Client ID: " + comboBoxSelectedClientId);
            System.out.println(investments.get());
        });

        ToggleGroup displayByToggleGroup = new ToggleGroup();

        RadioButton cumulativeRadioButton = new RadioButton("Cumulative");
        cumulativeRadioButton.setLayoutX(480.0);
        cumulativeRadioButton.setLayoutY(95.0);
        cumulativeRadioButton.setMnemonicParsing(false);
        cumulativeRadioButton.setPrefHeight(30.0);
        cumulativeRadioButton.setPrefWidth(142.0);
        cumulativeRadioButton.setFont(new Font(20.0));

        RadioButton dateRadioButton = new RadioButton("Date");
        dateRadioButton.setLayoutX(480.0);
        dateRadioButton.setLayoutY(130.0);
        dateRadioButton.setMnemonicParsing(false);
        dateRadioButton.setFont(new Font(20.0));

        cumulativeRadioButton.setToggleGroup(displayByToggleGroup);
        dateRadioButton.setToggleGroup(displayByToggleGroup);
        displayByToggleGroup.selectToggle(cumulativeRadioButton);

        Label displayByLabel = new Label("Display by:");
        displayByLabel.setLayoutX(480.0);
        displayByLabel.setLayoutY(55.0);
        displayByLabel.setPrefHeight(40.0);
        displayByLabel.setPrefWidth(125.0);
        displayByLabel.setFont(new Font(20.0));

        DatePicker fromDatePicker = new DatePicker();
        fromDatePicker.setLayoutX(550.0);
        fromDatePicker.setLayoutY(165.0);
        fromDatePicker.setPrefHeight(40.0);
        fromDatePicker.setPrefWidth(180.0);
        fromDatePicker.setDisable(true); // disabled since cumulative is selected by default in the beginning

        DatePicker toDatePicker = new DatePicker();
        toDatePicker.setLayoutX(550.0);
        toDatePicker.setLayoutY(210.0);
        toDatePicker.setPrefHeight(40.0);
        toDatePicker.setPrefWidth(180.0);
        toDatePicker.setDisable(true); // disabled since cumulative is selected by default in the beginning

        Label fromLabel = new Label("From:");
        fromLabel.setLayoutX(480.0);
        fromLabel.setLayoutY(165.0);
        fromLabel.setPrefHeight(40.0);
        fromLabel.setPrefWidth(60.0);
        fromLabel.setFont(new Font(20.0));

        Label toLabel = new Label("To:");
        toLabel.setLayoutX(480.0);
        toLabel.setLayoutY(210.0);
        toLabel.setPrefHeight(40.0);
        toLabel.setPrefWidth(60.0);
        toLabel.setFont(new Font(20.0));

        cumulativeRadioButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                fromDatePicker.setDisable(true);
                toDatePicker.setDisable(true);
            } else {
                fromDatePicker.setDisable(false);
                toDatePicker.setDisable(false);
            }
        });

        Label xirrLabel = new Label("XIRR");
        xirrLabel.setLayoutX(480.0);
        xirrLabel.setLayoutY(310.0);
        xirrLabel.setPrefHeight(40.0);
        xirrLabel.setPrefWidth(60.0);
        xirrLabel.setFont(new Font(20.0));

        TextField xirrTextField = new TextField();
        xirrTextField.setLayoutX(550.0);
        xirrTextField.setLayoutY(310.0);
        xirrTextField.setMaxWidth(180.0);
        xirrTextField.setPrefHeight(40.0);
        xirrTextField.getStyleClass().add("textfield-design");
        xirrTextField.setDisable(true);

        Button loadGraphButton = new Button("Load Graph");
        loadGraphButton.setLayoutX(550.0);
        loadGraphButton.setLayoutY(260.0);
        loadGraphButton.setMnemonicParsing(false);
        loadGraphButton.setPrefHeight(40.0);
        loadGraphButton.setPrefWidth(120.0);
        loadGraphButton.getStyleClass().add("button-design");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        loadGraphButton.setOnAction(e -> {
            if (comboBox.getSelectionModel().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("No Client Selected");
                alert.setHeaderText(null);
                alert.setContentText("Please select a client to load the graph.");
                alert.showAndWait();
            } else if (dateRadioButton.isSelected() && ((fromDatePicker.getValue() == null)
                    || (toDatePicker.getValue() == null)
                    || fromDatePicker.getValue().isAfter(toDatePicker.getValue()))){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Date Range");
                alert.setHeaderText(null);
                alert.setContentText("Please select a valid date range to load the graph.");
                alert.showAndWait();
            } else {
                if (cumulativeRadioButton.isSelected()) {
                    Task<XYChart.Series<String, Number>> getCumulativeInvestmentsGraphSeriesTask = new Task<XYChart.Series<String, Number>>() {
                        @Override
                        protected XYChart.Series<String, Number> call() throws Exception {
                            XYChart.Series<String, Number> series = new XYChart.Series<>();
                            series.setName("Cumulative");
                            ArrayList<Investment> retrievedInvestments = investments.get();
                            Comparator<Investment> investmentComparatorByDate = (i1, i2) -> {
                                if (i1.getInvestmentDate().isBefore(i2.getInvestmentDate())) {
                                    return -1;
                                } else if (i1.getInvestmentDate().isAfter(i2.getInvestmentDate())) {
                                    return 1;
                                } else {
                                    return 0;
                                }
                            };
                            retrievedInvestments.sort(investmentComparatorByDate);
                            for (Investment investment : retrievedInvestments) {
                                series.getData().add(new XYChart.Data<>(investment
                                        .getInvestmentDate().format(formatter), investment.getInvestmentAmount()));
                            }
                            return series;
                        }
                    };
                    getCumulativeInvestmentsGraphSeriesTask.setOnSucceeded(e1 -> {
                        investmentsGraphSeries.setAll(getCumulativeInvestmentsGraphSeriesTask.getValue());
                    });
                    new Thread(getCumulativeInvestmentsGraphSeriesTask).start();
                } else {
                    Task<XYChart.Series<String, Number>> getDatewiseInvestmentsGraphSeriesTask = new Task<XYChart.Series<String, Number>>() {
                        @Override
                        protected XYChart.Series<String, Number> call() throws Exception {
                            XYChart.Series<String, Number> series = new XYChart.Series<>();
                            series.setName("Date");
                            ArrayList<Investment> retrievedInvestments = investments.get();
                            Comparator<Investment> investmentComparatorByDate = (i1, i2) -> {
                                if (i1.getInvestmentDate().isBefore(i2.getInvestmentDate())) {
                                    return -1;
                                } else if (i1.getInvestmentDate().isAfter(i2.getInvestmentDate())) {
                                    return 1;
                                } else {
                                    return 0;
                                }
                            };
                            retrievedInvestments.sort(investmentComparatorByDate);
                            for (Investment investment : retrievedInvestments) {
                                LocalDate fromDate = fromDatePicker.getValue();
                                LocalDate toDate = toDatePicker.getValue();
                                if (investment.getInvestmentDate().isAfter(fromDate) && investment.getInvestmentDate()
                                        .isBefore(toDate)){
                                    series.getData().add(new XYChart.Data<>(investment
                                            .getInvestmentDate().format(formatter), investment.getInvestmentAmount()));
                                }
                            }
                            return series;
                        }
                    };
                    getDatewiseInvestmentsGraphSeriesTask.setOnSucceeded(e1 -> {
                        investmentsGraphSeries.setAll(getDatewiseInvestmentsGraphSeriesTask.getValue());
                    });
                    new Thread(getDatewiseInvestmentsGraphSeriesTask).start();
                }
            }
        });

        Button calculateXirrButton = new Button("Calculate XIRR");
//        calculateXirrButton.setLayoutX(480.0);
        calculateXirrButton.setLayoutX(550.0);
        calculateXirrButton.setLayoutY(360.0);
        calculateXirrButton.setMnemonicParsing(false);
        calculateXirrButton.setPrefHeight(40.0);
        calculateXirrButton.setPrefWidth(120.0);
        calculateXirrButton.getStyleClass().add("button-design");
        calculateXirrButton.setOnAction(e -> {
            if (comboBox.getSelectionModel().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("No Client Selected");
                alert.setHeaderText(null);
                alert.setContentText("Please select a client to calculate the XIRR value.");
                alert.showAndWait();
            } else if (dateRadioButton.isSelected() && ((fromDatePicker.getValue() == null) || (toDatePicker.getValue() == null))){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Date Range");
                alert.setHeaderText(null);
                alert.setContentText("Please select a valid date range to calculate the XIRR value.");
                alert.showAndWait();
            } else {
                Double xirrRate = 0.0;
                if (cumulativeRadioButton.isSelected()) {
                    ArrayList<Transaction> transactions = new ArrayList<>();
                    for (Investment investment : investments.get()) {
                        Double amount = investment.getInvestmentAmount();
                        String date = investment.getInvestmentDate().format(formatter);
                        transactions.add(new Transaction(amount, date));
                    }
                    xirrRate = new Xirr(transactions).xirr();
                    xirrTextField.setText(xirrRate.toString());
                } else {
                    ArrayList<Transaction> transactions = new ArrayList<>();
                    for (Investment investment : investments.get()) {
                        Double amount = investment.getInvestmentAmount();
                        String date = investment.getInvestmentDate().format(formatter);
                        LocalDate fromDate = fromDatePicker.getValue();
                        LocalDate toDate = toDatePicker.getValue();
                        if (investment.getInvestmentDate().isAfter(fromDate) && investment.getInvestmentDate().isBefore(toDate)) {
                            transactions.add(new Transaction(amount, date));
                        }
                    }
                    xirrRate = new Xirr(transactions).xirr();
                }
                xirrTextField.setText(xirrRate.toString());
            }
        });

//        Button generateReportButton = new Button("Generate Report");
//        generateReportButton.setLayoutX(610.0);
//        generateReportButton.setLayoutY(360.0);
//        generateReportButton.setMnemonicParsing(false);
//        generateReportButton.setPrefHeight(40.0);
//        generateReportButton.setPrefWidth(120.0);
//        generateReportButton.getStyleClass().add("button-design");
//        generateReportButton.disableProperty().bind(comboBox.getSelectionModel().selectedItemProperty().isNull());

//        generateReportButton.setOnAction(e -> {
//            if (comboBox.getSelectionModel().isEmpty()){
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("No Client Selected");
//                alert.setHeaderText(null);
//                alert.setContentText("Please select a client to generate the report.");
//                alert.showAndWait();
//            } else if (dateRadioButton.isSelected() && ((fromDatePicker.getValue() == null) || (toDatePicker.getValue() == null))){
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Invalid Date Range");
//                alert.setHeaderText(null);
//                alert.setContentText("Please select a valid date range to generate the report.");
//                alert.showAndWait();
//            } else {
//                // TODO: Generate Report
//            }
//        });

        centerAnchorPane.getChildren().addAll(lineChart, chooseClientLabel, comboBox,
                cumulativeRadioButton, dateRadioButton, displayByLabel,
                fromLabel, fromDatePicker, toLabel, toDatePicker, loadGraphButton,
                xirrLabel, xirrTextField, calculateXirrButton); //generateReportButton

        // Adding Top and Center AnchorPanes to BorderPane
        borderPane.setTop(topAnchorPane);
        borderPane.setCenter(centerAnchorPane);

        // Adding BorderPane to AnchorPane
        root.getChildren().add(borderPane);

        return root;
    }

    private static Label createLabel(String text, double layoutX, double layoutY) {
        Label label = new Label(text);
        label.setLayoutX(layoutX);
        label.setLayoutY(layoutY);
        label.setPrefHeight(40.0);
        label.setPrefWidth(125.0);
        label.setFont(new javafx.scene.text.Font(20.0));
        return label;
    }
}
