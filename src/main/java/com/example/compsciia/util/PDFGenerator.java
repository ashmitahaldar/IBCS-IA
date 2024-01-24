package com.example.compsciia.util;

import com.example.compsciia.models.Client;
import com.example.compsciia.models.Investment;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PDFGenerator {
    private static final String FILE_NAME = "itext.pdf";
    public static void generateTestPDF(){
        Document document = new Document();

        try {

            PdfWriter.getInstance(document, new FileOutputStream(new File("itext.pdf")));

            //open
            document.open();

            Paragraph p = new Paragraph();
            p.add("This is my paragraph 1");
            p.setAlignment(Element.ALIGN_CENTER);

            document.add(p);

            Paragraph p2 = new Paragraph();
            p2.add("This is my paragraph 2"); //no alignment

            document.add(p2);

            Font f = new Font();
            f.setStyle(Font.BOLD);
            f.setSize(8);

            document.add(new Paragraph("This is my paragraph 3", f));

            //close
            document.close();

            System.out.println("Done!");

        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }

    }

    public static void printSingleClientData(Client client, String filePath){
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            Font f = new Font();
            f.setStyle(Font.BOLD);
            f.setSize(30);

            Paragraph p = new Paragraph("Client Data", f);
            p.setAlignment(Element.ALIGN_CENTER);

            document.add(p);
            document.add(new Paragraph("\n"));

            PdfPTable table = new PdfPTable(6);

            PdfPCell cell;

            cell = new PdfPCell(new Phrase("Client Data"));
            cell.setColspan(6);
            table.addCell(cell);

            table.addCell("First Name");
            table.addCell("Last Name");
            table.addCell("Email");
            table.addCell("Phone Number");
            table.addCell("Address");
            table.addCell("Registered ID");

            table.addCell(client.getClientFirstName());
            table.addCell(client.getClientLastName());
            table.addCell(client.getClientEmail());
            table.addCell(client.getClientPhoneNumber());
            table.addCell(client.getClientAddress());
            table.addCell(client.getClientRegisteredId());

            document.add(table);
            document.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Client Data Exported");
            alert.setContentText("Client data has been exported to " + filePath);
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printAllClientData(ArrayList<Client> clients, String filePath){
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            Font f = new Font();
            f.setStyle(Font.BOLD);
            f.setSize(30);

            Paragraph p = new Paragraph("Client Data", f);
            p.setAlignment(Element.ALIGN_CENTER);

            document.add(p);
            document.add(new Paragraph("\n"));

            PdfPTable table = generateClientDataTable(clients);

            document.add(table);
            document.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Client Data Exported");
            alert.setContentText("Client data has been exported to " + filePath);
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void printAllInvestmentData(ArrayList<Investment> investments, String filePath){
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            Font f = new Font();
            f.setStyle(Font.BOLD);
            f.setSize(30);

            Paragraph p = new Paragraph("Investment Data", f);
            p.setAlignment(Element.ALIGN_CENTER);

            document.add(p);
            document.add(new Paragraph("\n"));

            PdfPTable table = generateInvestmentDataTable(investments);

            document.add(table);
            document.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Investment Data Exported");
            alert.setContentText("Investment data has been exported to " + filePath);
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static PdfPTable generateClientDataTable(ArrayList<Client> clients){
        PdfPTable table = new PdfPTable(6);

        PdfPCell cell;

        cell = new PdfPCell(new Phrase("Client Data"));
        cell.setColspan(6);
        table.addCell(cell);

        table.addCell("First Name");
        table.addCell("Last Name");
        table.addCell("Email");
        table.addCell("Phone Number");
        table.addCell("Address");
        table.addCell("Registered ID");

        for (Client client : clients){
            table.addCell(client.getClientFirstName());
            table.addCell(client.getClientLastName());
            table.addCell(client.getClientEmail());
            table.addCell(client.getClientPhoneNumber());
            table.addCell(client.getClientAddress());
            table.addCell(client.getClientRegisteredId());
        }

        return table;
    }

    public static PdfPTable generateInvestmentDataTable(ArrayList<Investment> investments){
        PdfPTable table = new PdfPTable(5);

        PdfPCell cell;

        cell = new PdfPCell(new Phrase("Investment Data"));
        cell.setColspan(6);
        table.addCell(cell);

        table.addCell("Investment ID");
        table.addCell("Name");
        table.addCell("Amount");
        table.addCell("Date");
        table.addCell("Description");

        for (Investment investment : investments){
            Integer id = investment.getInvestmentId();
            table.addCell(id.toString());
            table.addCell(investment.getInvestmentName());
            table.addCell(investment.getInvestmentAmount().toString());
            table.addCell(investment.getInvestmentDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            table.addCell(investment.getInvestmentDescription());
        }

        return table;
    }
}

