package com.finance.dashboard.service;

import com.finance.dashboard.model.*;
import com.finance.dashboard.repository.FinancialRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FinancialService {
    private final FinancialRepository financialRepository;

    public FinancialService(FinancialRepository financialRepository) {
        this.financialRepository = financialRepository;
    }

   
    public FinancialRecord addRecord(FinancialRecord record, User user) {
        if (user.getRole() == Role.VIEWER) {
            throw new RuntimeException("Access Denied: Viewers cannot create records!");
        }
        record.setCreatedBy(user);
        return financialRepository.save(record);
    }
    
    public FinancialRecord updateRecord(Long id, FinancialRecord updatedDetails) {
        return financialRepository.findById(id).map(record -> {
            record.setAmount(updatedDetails.getAmount());
            record.setType(updatedDetails.getType());
            record.setCategory(updatedDetails.getCategory());
            record.setDescription(updatedDetails.getDescription());
            return financialRepository.save(record);
        }).orElseThrow(() -> new RuntimeException("Record not found with id " + id));
    }
    
    public void deleteRecord(Long id, User user) {
    	if(user.getRole() == Role.VIEWER) {
    		throw new RuntimeException("Access Denied: Viewers cannot delete records.");
    	}
    	financialRepository.deleteById(id);
    }
    public List<FinancialRecord> getAllRecords() {
        return financialRepository.findAll();
    }

    
    public double getTotalIncome() {
        return financialRepository.findAll().stream()
                .filter(r -> r.getType() == TransactionType.INCOME)
                .mapToDouble(FinancialRecord::getAmount)
                .sum();
    }

    public double getTotalExpense() {
        return financialRepository.findAll().stream()
                .filter(r -> r.getType() == TransactionType.EXPENSE)
                .mapToDouble(FinancialRecord::getAmount)
                .sum();
    }

    public double getNetBalance() {
        return getTotalIncome() - getTotalExpense();
    }
}