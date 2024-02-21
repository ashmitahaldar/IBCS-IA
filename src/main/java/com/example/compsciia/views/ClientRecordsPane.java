package com.example.compsciia.views;

import com.example.compsciia.compsciia;
import com.example.compsciia.models.Client;
import com.example.compsciia.models.Investment;
import com.example.compsciia.models.User;
import com.example.compsciia.util.ClientService;
import com.example.compsciia.util.InvestmentService;
import com.example.compsciia.util.PDFGenerator;
import com.example.compsciia.util.Validators;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class ClientRecordsPane {
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
        Label titleLabel = new Label("Client Records");
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
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // Client Entry Tab
        Tab clientEntryTab = new Tab("Client Records");
        AnchorPane clientEntryAnchorPane = new AnchorPane();
        clientEntryAnchorPane.setPrefSize(750.0, 500.0);

        // Labels
        Label labelClientID = createLabel("ID: ", 15.0, 0);
        Label labelClientFirstName = createLabel("First Name", 15.0, 30.0);
        Label labelClientLastName = createLabel("Last Name", 15.0, 70.0);
        Label labelClientEmailAddress = createLabel("Email Address", 15.0, 110.0);
        Label labelClientPhoneNumber = createLabel("Phone Number", 15.0, 150.0);
        Label labelClientDateOfBirth = createLabel("Date of Birth", 15.0, 190.0);
        Label labelClientRegisteredID = createLabel("ID Number", 15.0, 240.0);
        Label labelClientAddress = createLabel("Address", 15.0, 280.0);

        // TextFields
        TextField textFieldClientFirstName = createTextField(145.0, 30.0, 35.0);
        TextField textFieldClientLastName = createTextField(145.0, 70.0, 35.0);
        TextField textFieldClientEmailAddress = createTextField(145.0, 110.0, 35.0);
        TextField textFieldClientPhoneNumber = createTextField(145.0, 150.0, 35.0);
        DatePicker fieldClientDateOfBirth = new DatePicker();
        fieldClientDateOfBirth.setLayoutX(145.0);
        fieldClientDateOfBirth.setLayoutY(190);
        fieldClientDateOfBirth.setPrefHeight(35);
        fieldClientDateOfBirth.setPrefWidth(200);
        fieldClientDateOfBirth.getStyleClass().add("textfield-design");
        fieldClientDateOfBirth.getStylesheets().add(compsciia.class.getResource("stylesheet.css").toExternalForm());
        TextField textFieldClientRegisteredID = createTextField(145.0, 240.0, 35.0);
        TextField textFieldClientAddress = createTextField(145.0, 280.0, 100.0);

        // Clients Table
        AtomicReference<ArrayList<Client>> clients = new AtomicReference<>(ClientService.getAllClientsFromDatabaseForUser(userId));
        TableView clientsTable = createClientTableView(clients.get());
        clientsTable.setLayoutX(360);
        clientsTable.setLayoutY(10);
        clientsTable.setPrefSize(380, 370);
//        clientsTable.getStyleClass().add("tableview-design");
        clientsTable.getStylesheets().add(compsciia.class.getResource("stylesheet.css").toExternalForm());
        clientsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelectedClient, newSelectedClient) -> {
            if (newSelectedClient != null) {
                System.out.println("Selected Client: " + newSelectedClient);
                labelClientID.setText("ID: " + ((Client)newSelectedClient).getClientId());
                textFieldClientFirstName.setText(((Client)newSelectedClient).getClientFirstName());
                textFieldClientLastName.setText(((Client)newSelectedClient).getClientLastName());
                textFieldClientEmailAddress.setText(((Client)newSelectedClient).getClientEmail());
                textFieldClientPhoneNumber.setText(((Client)newSelectedClient).getClientPhoneNumber());
                fieldClientDateOfBirth.setValue(((Client)newSelectedClient).getClientDateOfBirth());
                textFieldClientRegisteredID.setText(((Client)newSelectedClient).getClientRegisteredId());
                textFieldClientAddress.setText(((Client)newSelectedClient).getClientAddress());
            } else {
                System.out.println("No client selected.");
            }
        });

        // Buttons
        Button clientDeleteButton = createButton("Delete", 15.0, 390.0, 100.0);
        Button clientUpdateButton = createButton("Update", 120.0, 390.0, 100.0);
        Button clientClearButton = createButton("Clear", 225.0, 390.0, 120.0);
        Button clientPrintButton = createButton("Print Client Data", 370.0, 390.0, 150.0);
        clientPrintButton.disableProperty().bind(clientsTable.getSelectionModel().selectedItemProperty().isNull());
        Button clientPrintAllButton = createButton("Print All Client Data", 535.0, 390.0, 150.0);

        clientDeleteButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Client");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this client?");
            Optional<ButtonType> buttonType = alert.showAndWait();
            if(buttonType.isPresent() && buttonType.get().equals(ButtonType.OK)) {
                Client selectedClient = (Client) clientsTable.getSelectionModel().getSelectedItem();
                ClientService.deleteClientFromDatabase(selectedClient.getClientId());
                clients.set(ClientService.getAllClientsFromDatabaseForUser(userId));
                clientsTable.setItems(FXCollections.observableArrayList(clients.get()));
            }
        });

        clientUpdateButton.setOnAction(e -> {
            Client selectedClient = (Client) clientsTable.getSelectionModel().getSelectedItem();
            isValidClientUpdate(selectedClient.getClientId(), textFieldClientFirstName.getText(), textFieldClientLastName.getText(), textFieldClientEmailAddress.getText(), textFieldClientPhoneNumber.getText(), fieldClientDateOfBirth.getValue(), textFieldClientAddress.getText(), textFieldClientRegisteredID.getText());
            clients.set(ClientService.getAllClientsFromDatabaseForUser(userId));
            clientsTable.setItems(FXCollections.observableArrayList(clients.get()));
        });

        clientClearButton.setOnAction(e -> {
            labelClientID.setText("ID: ");
            textFieldClientFirstName.clear();
            textFieldClientLastName.clear();
            textFieldClientEmailAddress.clear();
            textFieldClientPhoneNumber.clear();
            fieldClientDateOfBirth.setValue(null);
            textFieldClientRegisteredID.clear();
            textFieldClientAddress.clear();
        });

        clientPrintButton.setOnAction(e -> {
            if (clientsTable.getSelectionModel().getSelectedItem() != null) {
                Client selectedClient = (Client) clientsTable.getSelectionModel().getSelectedItem();
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save PDF");
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
                fileChooser.setInitialFileName("Client Record - " + selectedClient.getClientFirstName() + " " + selectedClient.getClientLastName() + ".pdf");
                String filePath = fileChooser.showSaveDialog(clientPrintButton.getScene().getWindow()).getAbsolutePath();
                PDFGenerator.printSingleClientData(selectedClient, filePath);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please select a client.");
                alert.showAndWait();
            }
        });

        clientPrintAllButton.setOnAction(e ->{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save PDF");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            fileChooser.setInitialFileName("Client Records.pdf");
            String filePath = fileChooser.showSaveDialog(clientPrintAllButton.getScene().getWindow()).getAbsolutePath();
            PDFGenerator.printAllClientData(clients.get(), filePath);
        });

        // Adding children to AnchorPane
        clientEntryAnchorPane.getChildren().addAll(
                labelClientRegisteredID, labelClientFirstName, labelClientLastName, labelClientEmailAddress,
                labelClientAddress, labelClientPhoneNumber, labelClientDateOfBirth, labelClientID,
                textFieldClientRegisteredID, textFieldClientFirstName, textFieldClientLastName,
                textFieldClientEmailAddress, textFieldClientAddress, textFieldClientPhoneNumber,
                fieldClientDateOfBirth, clientUpdateButton, clientDeleteButton,
                clientClearButton, clientPrintButton, clientPrintAllButton, clientsTable
        );

        // Setting content for the Client Entry Tab
        clientEntryTab.setContent(clientEntryAnchorPane);

        // Investment Entry Tab
        Tab investmentEntryTab = new Tab("Investment Records");
        AnchorPane investmentEntryAnchorPane = new AnchorPane();
        investmentEntryAnchorPane.setPrefSize(750.0, 500.0);

        // Labels
        Label labelChooseClient = createLabel("Choose Client", 5.0, 30.0);
        Label labelInvestmentID = createLabel("ID: ", 5, 70);
        Label labelInvestmentName = createLabel("Investment Name", 5.0, 110.0);
        Label labelInvestmentAmount = createLabel("Amount", 5.0, 150.0);
        Label labelInvestmentDate = createLabel("Date", 5.0, 190.0);
        Label labelInvestmentDescription = createLabel("Description", 5.0, 230.0);

        // ComboBox
        ComboBox<String> comboBoxChooseClient = new ComboBox<>();
        comboBoxChooseClient.setLayoutX(155);
        comboBoxChooseClient.setLayoutY(30);
        comboBoxChooseClient.setPrefHeight(35);
        comboBoxChooseClient.setPrefWidth(200);
        comboBoxChooseClient.getStyleClass().add("textfield-design");
        comboBoxChooseClient.getStylesheets().add(compsciia.class.getResource("stylesheet.css").toExternalForm());
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
//        comboBoxChooseClient.setItems(clientNamesObservableList);

        // TextFields
        TextField textFieldInvestmentName = createTextField(155.0, 110.0, 35.0);

        TextField textFieldInvestmentAmount = createTextField(155.0, 150.0, 35.0);
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
        fieldInvestmentDate.setLayoutX(155);
        fieldInvestmentDate.setLayoutY(190);
        fieldInvestmentDate.setPrefHeight(35);
        fieldInvestmentDate.setPrefWidth(200);
        fieldInvestmentDate.getStyleClass().add("textfield-design");
        fieldInvestmentDate.getStylesheets().add(compsciia.class.getResource("stylesheet.css").toExternalForm());

        TextField textFieldInvestmentDescription = createTextField(155.0, 230.0, 100.0);

//        // Investments table
        AtomicReference<ArrayList<Investment>> investments = new AtomicReference<>();
        TableView investmentsTable = createInvestmentTableView();
        investmentsTable.setLayoutX(360);
        investmentsTable.setLayoutY(10);
        investmentsTable.setPrefSize(380, 370);
        investmentsTable.getStylesheets().add(compsciia.class.getResource("stylesheet.css").toExternalForm());
        comboBoxChooseClient.setOnAction(e -> {
            int comboBoxSelectedClientId = Integer.parseInt(comboBoxChooseClient.getSelectionModel().getSelectedItem().split("\\(")[1].split("\\)")[0]);
            investments.set(InvestmentService.getAllInvestmentsFromDatabaseForClient(comboBoxSelectedClientId));
            System.out.println("Selected Client ID: " + comboBoxSelectedClientId);
            setInvestmentTableViewItems(investmentsTable, investments.get());
        });
        investmentsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelectedInvestment, newSelectedInvestment) -> {
            if (newSelectedInvestment != null) {
                System.out.println("Selected Investment: " + newSelectedInvestment);
                int selectedInvestmentClientId = ((Investment)newSelectedInvestment).getClientId();
                labelInvestmentID.setText("ID: " + ((Investment)newSelectedInvestment).getInvestmentId());
                textFieldInvestmentName.setText(((Investment)newSelectedInvestment).getInvestmentName());
                textFieldInvestmentAmount.setText(Double.toString(((Investment)newSelectedInvestment).getInvestmentAmount()));
                fieldInvestmentDate.setValue(((Investment)newSelectedInvestment).getInvestmentDate());
                textFieldInvestmentDescription.setText(((Investment)newSelectedInvestment).getInvestmentDescription());
            } else {
                System.out.println("No client selected.");
            }
        });

        // Buttons
        Button investmentDeleteButton = createButton("Delete", 15.0, 390.0, 100.0);
        Button investmentUpdateButton = createButton("Update", 120.0, 390.0, 100.0);
        Button investmentClearButton = createButton("Clear", 225.0, 390.0, 120.0);
        Button investmentPrintAllButton = createButton("Print All Investment Data", 370.0, 390.0, 150.0);
        investmentPrintAllButton.disableProperty().bind(comboBoxChooseClient.valueProperty().isNull());

        investmentDeleteButton.setOnAction(e ->{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Investment");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this investment?");
            Optional<ButtonType> buttonType = alert.showAndWait();
            if(buttonType.isPresent() && buttonType.get().equals(ButtonType.OK)){
                Investment selectedInvestment = (Investment) investmentsTable.getSelectionModel().getSelectedItem();
                InvestmentService.deleteInvestmentFromDatabase(selectedInvestment.getInvestmentId());
                int comboBoxSelectedClientId = Integer.parseInt(comboBoxChooseClient.getSelectionModel().getSelectedItem().split("\\(")[1].split("\\)")[0]);
                investments.set(InvestmentService.getAllInvestmentsFromDatabaseForClient(comboBoxSelectedClientId));
                setInvestmentTableViewItems(investmentsTable, investments.get());
            }
        });

        investmentClearButton.setOnAction(e -> {
            labelInvestmentID.setText("ID: ");
            textFieldInvestmentName.clear();
            textFieldInvestmentAmount.clear();
            fieldInvestmentDate.setValue(null);
            textFieldInvestmentDescription.clear();
        });

        investmentUpdateButton.setOnAction(e -> {
            Investment selectedInvestment = (Investment) investmentsTable.getSelectionModel().getSelectedItem();
            isValidInvestmentUpdate(selectedInvestment.getInvestmentId(), textFieldInvestmentName.getText(), Double.parseDouble(textFieldInvestmentAmount.getText()), fieldInvestmentDate.getValue(), textFieldInvestmentDescription.getText());
            int comboBoxSelectedClientId = Integer.parseInt(comboBoxChooseClient.getSelectionModel().getSelectedItem().split("\\(")[1].split("\\)")[0]);
            investments.set(InvestmentService.getAllInvestmentsFromDatabaseForClient(comboBoxSelectedClientId));
            setInvestmentTableViewItems(investmentsTable, investments.get());
        });

        investmentPrintAllButton.setOnAction(e -> {
            Client selectedClient = clients.get().get(comboBoxChooseClient.getSelectionModel().getSelectedIndex());
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save PDF");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            fileChooser.setInitialFileName("Investment Records - " + selectedClient.getClientFirstName() + " " + selectedClient.getClientLastName() + ".pdf");
            String filePath = fileChooser.showSaveDialog(investmentPrintAllButton.getScene().getWindow()).getAbsolutePath();
            PDFGenerator.printAllInvestmentData(investments.get(), filePath);
        });

        // Adding children to AnchorPane
        investmentEntryAnchorPane.getChildren().addAll(
                labelChooseClient, labelInvestmentID,labelInvestmentName, labelInvestmentAmount, labelInvestmentDate, labelInvestmentDescription,
                comboBoxChooseClient, textFieldInvestmentName, textFieldInvestmentAmount, fieldInvestmentDate, textFieldInvestmentDescription,
                investmentClearButton, investmentDeleteButton, investmentPrintAllButton, investmentUpdateButton,
                investmentsTable
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
        textField.setPrefWidth(200.0);
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

    private static TableView createClientTableView(ArrayList<Client> clients){
        TableView clientsTable = new TableView();
        TableColumn<Client, Integer> clientIdColumn = new TableColumn<>("Client ID");
        clientIdColumn.setCellValueFactory(new PropertyValueFactory<>("clientId"));

        TableColumn<Client, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("clientFirstName"));

        TableColumn<Client, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("clientLastName"));

        TableColumn<Client, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("clientEmail"));

        TableColumn<Client, String> phoneNumberColumn = new TableColumn<>("Phone Number");
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("clientPhoneNumber"));

        TableColumn<Client, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("clientAddress"));

        TableColumn<Client, String> registeredIdColumn = new TableColumn<>("Registered ID");
        registeredIdColumn.setCellValueFactory(new PropertyValueFactory<>("clientRegisteredId"));

        // Step 4: Add Columns to TableView
        clientsTable.getColumns().addAll(clientIdColumn, firstNameColumn, lastNameColumn, emailColumn, phoneNumberColumn, addressColumn, registeredIdColumn);

        ObservableList<Client> clientsObservableList = FXCollections.observableArrayList(clients);

        clientsTable.setItems(clientsObservableList);

        return clientsTable;
    }

    private static TableView createInvestmentTableView(){
        TableView investmentsTable = new TableView();
        TableColumn<Investment, Integer> investmentIdColumn = new TableColumn<>("Investment ID");
        investmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("investmentId"));

        TableColumn<Investment, String> investmentNameColumn = new TableColumn<>("Name");
        investmentNameColumn.setCellValueFactory(new PropertyValueFactory<>("investmentName"));

        TableColumn<Investment, Double> investmentAmountColumn = new TableColumn<>("Amount");
        investmentAmountColumn.setCellValueFactory(new PropertyValueFactory<>("investmentAmount"));

        TableColumn<Investment, LocalDate> investmentDateColumn = new TableColumn<>("Date");
        investmentDateColumn.setCellValueFactory(new PropertyValueFactory<>("investmentDate"));

        TableColumn<Investment, String> investmentDescriptionColumn = new TableColumn<>("Description");
        investmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("investmentDescription"));

        investmentsTable.getColumns().addAll(investmentIdColumn, investmentNameColumn, investmentAmountColumn, investmentDateColumn, investmentDescriptionColumn);

        // Remember to use setInvestmentTableViewItems() to set values for TableView

        return investmentsTable;
    }

    private static void setInvestmentTableViewItems(TableView investmentsTable, ArrayList<Investment> investments){
        ObservableList<Investment> investmentsObservableList = FXCollections.observableArrayList(investments);
        investmentsTable.setItems(investmentsObservableList);
    }

    private static void isValidClientUpdate(int client_id, String client_first_name, String client_last_name, String client_email, String client_phone_number, LocalDate client_date_of_birth, String client_address, String client_registered_id){
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
        else {
            ClientService.updateClientInDatabase(client_id, client_first_name, client_last_name, client_email, client_phone_number, client_date_of_birth, client_address, client_registered_id);
        }
    }

    private static void isValidInvestmentUpdate(int investment_id, String investment_name, double investment_amount, LocalDate investment_date, String investment_description){
        if (investment_name == null || investment_name.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter an investment name.");
            alert.showAndWait();
        } else if (investment_amount == 0.0){
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
            InvestmentService.updateInvestmentInDatabase(investment_id, investment_name, investment_amount, investment_date, investment_description);
        }
    }
}
