package org.budgettracker.model;

import java.time.LocalDate;


public record Transaction(
        int id,
        String type,
        String category,
        double amount,
        LocalDate date,
        String description
) {}