package org.budgettracker.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.budgettracker.dao.TransactionDAO;
import org.budgettracker.model.Transaction;

import java.time.LocalDate;

public class AddTransactionController {

    @FXML private ComboBox<String> typeBox;
    @FXML private TextField categoryField;
    @FXML private TextField amountField;
    @FXML private DatePicker datePicker;
    @FXML private TextArea descArea;

    @FXML
    public void initialize() {

        typeBox.getItems().addAll("GELİR", "GİDER");
    }

    @FXML
    private void handleSave() {

        String type = typeBox.getValue();
        String category = categoryField.getText();
        String amountStr = amountField.getText();
        LocalDate date = datePicker.getValue();
        String desc = descArea.getText();


        if (type == null || category.isEmpty() || amountStr.isEmpty() || date == null) {
            System.out.println("Lütfen tüm alanları doldurun!");
            return;
        }

        try {
            double amount = Double.parseDouble(amountStr);


            Transaction newTransaction = new Transaction(0, type, category, amount, date, desc);
            new TransactionDAO().save(newTransaction);


            Stage stage = (Stage) typeBox.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            System.out.println("Lütfen tutar kısmına sayı girin!");
        }
    }
}