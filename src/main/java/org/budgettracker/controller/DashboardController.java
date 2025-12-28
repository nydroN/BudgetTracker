package org.budgettracker.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.budgettracker.dao.TransactionDAO;
import org.budgettracker.dao.DBConnection;
import org.budgettracker.model.Transaction;
import java.io.IOException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;

public class DashboardController {

    @FXML private TableView<Transaction> transactionTable;
    @FXML private Button addButton;
    @FXML private Button deleteButton;

    private final TransactionDAO transactionDAO = new TransactionDAO();

    @FXML
    public void initialize() {
        DBConnection.createNewTable();


        setupTableColumns();
        loadData();

        addButton.setOnAction(event -> {
            showAddTransactionDialog();
            loadData();
        });
        deleteButton.setOnAction(event -> handleDelete());
    }

    private void setupTableColumns() {

        TableColumn<Transaction, String> dateCol = new TableColumn<>("Tarih");
        dateCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().date().toString()));


        TableColumn<Transaction, String> typeCol = new TableColumn<>("Tür");
        typeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().type()));


        TableColumn<Transaction, String> catCol = new TableColumn<>("Kategori");
        catCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().category()));


        TableColumn<Transaction, Double> amountCol = new TableColumn<>("Tutar");
        amountCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().amount()));


        TableColumn<Transaction, String> descCol = new TableColumn<>("Açıklama");
        descCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().description()));


        transactionTable.getColumns().setAll(dateCol, typeCol, catCol, amountCol, descCol);
    }

    private void loadData() {

        ObservableList<Transaction> data = FXCollections.observableArrayList(transactionDAO.findAll());
        transactionTable.setItems(data);
    }

    private void showAddTransactionDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddTransaction.fxml"));
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Yeni Ekle");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setScene(new Scene(loader.load()));
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void handleDelete() {

        Transaction selectedTransaction = transactionTable.getSelectionModel().getSelectedItem();

        if (selectedTransaction == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Uyarı");
            alert.setHeaderText(null);
            alert.setContentText("Lütfen silmek için tablodan bir satır seçin!");
            alert.showAndWait();
            return;
        }


        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Silme Onayı");
        confirm.setHeaderText(null);
        confirm.setContentText("Bu kaydı silmek istediğine emin misin?");

        if (confirm.showAndWait().get() == ButtonType.OK) {

            transactionDAO.delete(selectedTransaction.id());


            loadData();
        }
    }
}