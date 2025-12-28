module org.budgettracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;      // İleride lazım olacak
    requires org.xerial.sqlitejdbc; // İleride lazım olacak

    opens org.budgettracker to javafx.fxml;
    opens org.budgettracker.controller to javafx.fxml;
    exports org.budgettracker;
}