package com.example.compsciia.views;

import com.example.compsciia.compsciia;
import com.example.compsciia.models.Client;
import com.example.compsciia.util.ClientService;
import com.example.compsciia.util.InvestmentService;
import com.example.compsciia.util.Validators;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class AddNewPane {

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
        Label titleLabel = new Label("Add New");
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

        // TabPane
        TabPane tabPane = new TabPane();
        tabPane.setPrefHeight(500.0);
        tabPane.setPrefWidth(750.0);
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

        // Client Entry Tab
        Tab clientEntryTab = new Tab("Client Entry");
        AnchorPane clientEntryAnchorPane = new AnchorPane();
        clientEntryAnchorPane.setPrefSize(750.0, 500.0);

        // Labels
        Label labelClientIDNumber = createLabel("ID Number", 30.0, 240.0);
        Label labelClientFirstName = createLabel("First Name", 30.0, 30.0);
        Label labelClientLastName = createLabel("Last Name", 30.0, 70.0);
        Label labelClientEmailAddress = createLabel("Email Address", 30.0, 110.0);
        Label labelClientAddress = createLabel("Address", 30.0, 280.0);
        Label labelClientPhoneNumber = createLabel("Phone Number", 30.0, 150.0);
        Label labelClientDateOfBirth = createLabel("Date of Birth", 30.0, 190.0);

        // Fields
        TextField textFieldClientIDNumber = createTextField(160.0, 240.0, 35.0);
        TextField textFieldClientFirstName = createTextField(160.0, 30.0, 35.0);
        TextField textFieldClientLastName = createTextField(160.0, 70.0, 35.0);
        TextField textFieldClientEmailAddress = createTextField(160.0, 110.0, 35.0);
        TextField textFieldClientAddress = createTextField(160.0, 280.0, 100.0);
        TextField textFieldClientPhoneNumber = createTextField(160.0, 150.0, 35.0);
        DatePicker fieldClientDateOfBirth = new DatePicker();
        fieldClientDateOfBirth.setLayoutX(160);
        fieldClientDateOfBirth.setLayoutY(190);
        fieldClientDateOfBirth.setPrefHeight(35);
        fieldClientDateOfBirth.setPrefWidth(250);
        fieldClientDateOfBirth.getStyleClass().add("textfield-design");
        fieldClientDateOfBirth.getStylesheets().add(compsciia.class.getResource("stylesheet.css").toExternalForm());

        // Buttons
        Button clientClearButton = createButton("Clear", 30.0, 390.0, 125.0);
        Button addNewClientButton = createButton("Add New Client", 220.0, 390.0, 125.0);

        clientClearButton.setOnAction(e -> {
            textFieldClientIDNumber.clear();
            textFieldClientFirstName.clear();
            textFieldClientLastName.clear();
            textFieldClientEmailAddress.clear();
            textFieldClientAddress.clear();
            textFieldClientPhoneNumber.clear();
            fieldClientDateOfBirth.setValue(null);
        });
        addNewClientButton.setOnAction(e -> {
            String clientRegisteredID = textFieldClientIDNumber.getText();
            String clientFirstName = textFieldClientFirstName.getText();
            String clientLastName = textFieldClientLastName.getText();
            String clientEmailAddress = textFieldClientEmailAddress.getText();
            String clientAddress = textFieldClientAddress.getText();
            String clientPhoneNumber = textFieldClientPhoneNumber.getText();
            LocalDate clientDateOfBirth = fieldClientDateOfBirth.getValue();
            isValidClient(userId, clientFirstName, clientLastName, clientEmailAddress, clientPhoneNumber, clientDateOfBirth, clientAddress, clientRegisteredID);
        });

        // Adding children to AnchorPane
        clientEntryAnchorPane.getChildren().addAll(
                labelClientIDNumber, labelClientFirstName, labelClientLastName, labelClientEmailAddress,
                labelClientAddress, labelClientPhoneNumber, labelClientDateOfBirth,
                textFieldClientIDNumber, textFieldClientFirstName, textFieldClientLastName,
                textFieldClientEmailAddress, textFieldClientAddress, textFieldClientPhoneNumber,
                fieldClientDateOfBirth, clientClearButton, addNewClientButton
        );

        // Setting content for the Client Entry Tab
        clientEntryTab.setContent(clientEntryAnchorPane);

        // Investment Entry Tab
        Tab investmentEntryTab = new Tab("Investment Entry");
        AnchorPane investmentEntryAnchorPane = new AnchorPane();
        investmentEntryAnchorPane.setPrefSize(750.0, 500.0);

        // Labels
        Label labelChooseClient = createLabel("Choose Client", 30.0, 30.0);
        Label labelInvestmentName = createLabel("Investment Name", 30.0, 70.0);
        Label labelInvestmentAmount = createLabel("Amount", 30.0, 110.0);
        Label labelInvestmentDate = createLabel("Date", 30.0, 150.0);
        Label labelInvestmentDescription = createLabel("Description", 30.0, 190.0);

        // ComboBox
        ComboBox<String> comboBoxChooseClient = new ComboBox<>();
        comboBoxChooseClient.setLayoutX(180);
        comboBoxChooseClient.setLayoutY(30);
        comboBoxChooseClient.setPrefHeight(35);
        comboBoxChooseClient.setPrefWidth(200);
        comboBoxChooseClient.getStyleClass().add("textfield-design");
        comboBoxChooseClient.getStylesheets().add(compsciia.class.getResource("stylesheet.css").toExternalForm());

        // Adding clients to ComboBox
        AtomicReference<ArrayList<Client>> clients = new AtomicReference<>(ClientService.getAllClientsFromDatabaseForUser(userId));
        Task<List<String>> getComboBoxClientListTask = new Task<List<String>>() {
            @Override
            protected List<String> call() throws Exception {
                ArrayList<String> clientNames = new ArrayList<>();
                for (Client client : clients.get()) {
                    clientNames.add(client.getClientFirstName() + " " + client.getClientLastName() + " (" + client.getClientId() + ")");
                }
                return clientNames;
            }
        };
        javafx.collections.ObservableList<String> clientNamesObservableList = FXCollections.observableArrayList();
        getComboBoxClientListTask.setOnSucceeded(e -> {
            clientNamesObservableList.setAll(getComboBoxClientListTask.getValue());
            comboBoxChooseClient.setItems(clientNamesObservableList);
        });
        new Thread(getComboBoxClientListTask).start();
//        List<String> clientNames = new ArrayList<>();
//        for (Client client : clients.get()) {
//            clientNames.add(client.getClientFirstName() + " " + client.getClientLastName() + " (" + client.getClientId() + ")");
//        }
//        ObservableList<String> clientNamesObservableList = FXCollections.observableArrayList(clientNames);
//        choiceBoxChooseClient.setItems(clientNamesObservableList);


        // TextFields
        TextField textFieldInvestmentName = createTextField(180.0, 70.0, 35.0);

        TextField textFieldInvestmentAmount = createTextField(180.0, 110.0, 35.0);
        // Set up the TextFormatter to accept only Double input
        Pattern validDoubleInput = Pattern.compile("-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?");
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (validDoubleInput.matcher(newText).matches()) {
                return change;
            } else {
                return null;
            }
        };
        StringConverter<Double> converter = new StringConverter<Double>() {
            @Override
            public Double fromString(String s) {
                if (s.isEmpty() || "-".equals(s) || ".".equals(s) || "-.".equals(s)) {
                    return 0.0 ;
                } else {
                    return Double.valueOf(s);
                }
            }
            @Override
            public String toString(Double d) {
                return d.toString();
            }
        };
        TextFormatter<Double> textFormatter = new TextFormatter<>(converter, 0.0, filter);
        textFieldInvestmentAmount.setTextFormatter(textFormatter);

        DatePicker fieldInvestmentDate = new DatePicker();
        fieldInvestmentDate.setLayoutX(180);
        fieldInvestmentDate.setLayoutY(150);
        fieldInvestmentDate.setPrefHeight(35);
        fieldInvestmentDate.setPrefWidth(250);
        fieldInvestmentDate.getStyleClass().add("textfield-design");
        fieldInvestmentDate.getStylesheets().add(compsciia.class.getResource("stylesheet.css").toExternalForm());

        TextField textFieldInvestmentDescription = createTextField(180.0, 190.0, 100.0);

        // Buttons
        Button investmentClearButton = createButton("Clear", 30.0, 300.0, 125.0);
        Button addNewInvestmentButton = createButton("Add New Investment", 220.0, 300.0, 150.0);

        investmentClearButton.setOnAction(e -> {
            textFieldInvestmentName.clear();
            textFieldInvestmentAmount.clear();
            fieldInvestmentDate.setValue(null);
            textFieldInvestmentDescription.clear();
        });

        addNewInvestmentButton.setOnAction(e -> {
            String investmentName = textFieldInvestmentName.getText();
            Double investmentAmount = Double.parseDouble(textFieldInvestmentAmount.getText());
            LocalDate investmentDate = fieldInvestmentDate.getValue();
            String investmentDescription = textFieldInvestmentDescription.getText();
            String clientName = comboBoxChooseClient.getValue();
            String[] clientNameArray = clientName.split(" ");
            String clientFirstName = clientNameArray[0];
            String clientLastName = clientNameArray[1];
            String clientIDString = clientNameArray[2];
            clientIDString = clientIDString.substring(1, clientIDString.length() - 1);
            int clientIDNumber = Integer.parseInt(clientIDString);
            Client client = ClientService.getClientFromDatabase(clientIDNumber);
            int clientId = client.getClientId();
            isValidInvestment(clientId, investmentName, investmentAmount, investmentDate, investmentDescription);
        });

        // Adding children to AnchorPane
        investmentEntryAnchorPane.getChildren().addAll(
                labelChooseClient, labelInvestmentName, labelInvestmentAmount, labelInvestmentDate, labelInvestmentDescription,
                comboBoxChooseClient, textFieldInvestmentName, textFieldInvestmentAmount, fieldInvestmentDate, textFieldInvestmentDescription,
                investmentClearButton, addNewInvestmentButton
        );

        // Setting content for the Investment Entry Tab
        investmentEntryTab.setContent(investmentEntryAnchorPane);

        // Adding tabs to the TabPane
        tabPane.getTabs().addAll(clientEntryTab, investmentEntryTab);

        // Adding TabPane to Center AnchorPane
        centerAnchorPane.getChildren().add(tabPane);

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
        label.setPrefWidth(150.0);
        label.setFont(new javafx.scene.text.Font(20.0));
        return label;
    }
    private static TextField createTextField(double layoutX, double layoutY, double prefHeight) {
        TextField textField = new TextField();
        textField.setLayoutX(layoutX);
        textField.setLayoutY(layoutY);
        textField.setPrefHeight(prefHeight);
        textField.setPrefWidth(250.0);
        textField.getStyleClass().add("textfield-design");
         textField.getStylesheets().add(compsciia.class.getResource("stylesheet.css").toExternalForm());
        return textField;
    }

    private static Button createButton(String text, double layoutX, double layoutY, double prefWidth) {
        Button button = new Button(text);
        button.setLayoutX(layoutX);
        button.setLayoutY(layoutY);
        button.setMnemonicParsing(false);
        button.setPrefHeight(35.0);
        button.setPrefWidth(prefWidth);
        button.getStyleClass().add("button-design");
         button.getStylesheets().add(compsciia.class.getResource("stylesheet.css").toExternalForm());
        return button;
    }

    private static void isValidClient(int user_id, String client_first_name, String client_last_name, String client_email, String client_phone_number, LocalDate client_date_of_birth, String client_address, String client_registered_id){
        if (!Validators.isValidFirstName(client_first_name)){
            Validators.showInvalidFirstNamePopup();
        } else if (!Validators.isValidLastName(client_last_name)){
            Validators.showInvalidLastNamePopup();
        } else if (!Validators.isValidEmail(client_email)){
            Validators.showInvalidEmailPopup();
        } else if (!Validators.isValidPhoneNumber(client_phone_number)){
            Validators.showInvalidPhoneNumberPopup();
        } else if (!Validators.isValidDateOfBirth(client_date_of_birth)){
            Validators.showInvalidDateOfBirthPopup();
        } else if (client_first_name == null || client_first_name.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a first name.");
            alert.showAndWait();
        }
        else if (client_last_name == null || client_last_name.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a last name.");
            alert.showAndWait();
        }
        else if (client_email == null || client_email.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter an email address.");
            alert.showAndWait();
        }
        else if (client_phone_number == null || client_phone_number.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a phone number.");
            alert.showAndWait();
        }
        else if (client_date_of_birth == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a date of birth.");
            alert.showAndWait();
        }
        else if (client_address == null || client_address.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter an address.");
            alert.showAndWait();
        }
        else if (client_registered_id == null || client_registered_id.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a registered ID.");
            alert.showAndWait();
        }
        else if (ClientService.checkIfClientExistsForUserInDatabase(user_id, client_first_name, client_last_name, client_email)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Client already exists in database.");
            alert.showAndWait();
        }
        else {
            ClientService.writeClientToDatabase(user_id, client_first_name, client_last_name, client_email, client_phone_number, client_date_of_birth, client_address, client_registered_id);
        }
    }

    private static void isValidInvestment(int client_id, String investment_name, Double investment_amount, LocalDate investment_date, String investment_description){
        if (investment_name == null || investment_name.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter an investment name.");
            alert.showAndWait();
        }
        else if (investment_amount == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter an investment amount.");
            alert.showAndWait();
        }
        else if (investment_date == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter an investment date.");
            alert.showAndWait();
        }
//        else if (investment_description == null || investment_description.isEmpty()){
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error");
//            alert.setHeaderText(null);
//            alert.setContentText("Please enter an investment description.");
//            alert.showAndWait();
//        }
        else {
            InvestmentService.writeInvestmentToDatabase(client_id, investment_name, investment_amount, investment_date, investment_description);
        }
    }
}
