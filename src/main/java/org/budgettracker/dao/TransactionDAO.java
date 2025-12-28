package org.budgettracker.dao;

import org.budgettracker.model.Transaction;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {


    public void save(Transaction t) {
        String sql = "INSERT INTO transactions(type, category, amount, date, description) VALUES(?,?,?,?,?)";

        try (Connection conn = DBConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, t.type());
            pstmt.setString(2, t.category());
            pstmt.setDouble(3, t.amount());
            pstmt.setString(4, t.date().toString()); // Tarihi String olarak saklıyoruz
            pstmt.setString(5, t.description());

            pstmt.executeUpdate();
            System.out.println("Veritabanına kaydedildi: " + t.category());

        } catch (SQLException e) {
            System.out.println("Kayıt hatası: " + e.getMessage());
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM transactions WHERE id = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Kayıt silindi: ID " + id);

        } catch (SQLException e) {
            System.out.println("Silme hatası: " + e.getMessage());
        }
    }


    public List<Transaction> findAll() {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions";

        try (Connection conn = DBConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Transaction t = new Transaction(
                        rs.getInt("id"),
                        rs.getString("type"),
                        rs.getString("category"),
                        rs.getDouble("amount"),
                        LocalDate.parse(rs.getString("date")), // String'i Tarihe çevir
                        rs.getString("description")
                );
                list.add(t);
            }
        } catch (SQLException e) {
            System.out.println("Listeleme hatası: " + e.getMessage());
        }
        return list;

    }
}