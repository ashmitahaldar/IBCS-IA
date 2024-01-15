module com.example.compsciia {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.desktop;
    requires javafx.swing;

    opens com.example.compsciia to javafx.fxml;
    exports com.example.compsciia;
    exports com.example.compsciia.models;
    opens com.example.compsciia.models to javafx.fxml;
    exports com.example.compsciia.controllers;
    opens com.example.compsciia.controllers to javafx.fxml;
    exports com.example.compsciia.views;
    opens com.example.compsciia.views to javafx.fxml;
    exports com.example.compsciia.util;
    opens com.example.compsciia.util to javafx.fxml;
}