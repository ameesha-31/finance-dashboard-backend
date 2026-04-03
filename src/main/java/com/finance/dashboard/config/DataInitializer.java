package com.finance.dashboard.config;

import com.finance.dashboard.model.FinancialRecord;
import com.finance.dashboard.model.Role;
import com.finance.dashboard.model.TransactionType;
import com.finance.dashboard.model.User;
import com.finance.dashboard.repository.FinancialRepository;
import com.finance.dashboard.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepo, FinancialRepository financeRepo) {
        return args -> {
            
            if (userRepo.findByUsername("ameesha_admin").isEmpty()) {
                
                User admin = new User();
                admin.setUsername("ameesha_admin");
                admin.setRole(Role.ADMIN);
                userRepo.save(admin);

                FinancialRecord income = new FinancialRecord();
                income.setAmount(7000.0);
                income.setType(TransactionType.INCOME);
                income.setCategory("Freelance");
                income.setDate(LocalDate.now());
                income.setCreatedBy(admin);
                financeRepo.save(income);

                FinancialRecord expense = new FinancialRecord();
                expense.setAmount(500.0);
                expense.setType(TransactionType.EXPENSE);
                expense.setCategory("Food");
                expense.setDate(LocalDate.now());
                expense.setCreatedBy(admin);
                financeRepo.save(expense);
                
                System.out.println(">>> Sample Data Loaded Successfully!");
            }
        };
    }
}