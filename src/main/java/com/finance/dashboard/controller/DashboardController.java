package com.finance.dashboard.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finance.dashboard.model.FinancialRecord;
import com.finance.dashboard.model.User;
import com.finance.dashboard.repository.UserRepository;
import com.finance.dashboard.service.FinancialService;
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
	private final FinancialService financialService;
	private final UserRepository userRepository;
	
	public DashboardController(FinancialService financialService, UserRepository userRepository) {
		this.financialService = financialService;
		this.userRepository = userRepository;
	}
	
	@GetMapping("/summary")
	public Map<String, Object> getSummary() {
		Map<String, Object> summary = new HashMap<>();
		summary.put("totalIncome", financialService.getTotalIncome());
		summary.put("totalExpense", financialService.getTotalExpense());
		summary.put("netBalance", financialService.getNetBalance());
		return summary;
	}
	
	@GetMapping("/records")
	public List<FinancialRecord> getAllRecords() {
		return financialService.getAllRecords();
	}
	
	@PutMapping("/records/{id}")
	public FinancialRecord updateRecord(@PathVariable Long id, @RequestBody FinancialRecord recordDetails) {
		return financialService.updateRecord(id,  recordDetails);
	}
	
	@DeleteMapping("/records/{id}")
	public String deleteRecord(@PathVariable Long id, @RequestParam Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		financialService.deleteRecord(id,  user);
		return "Record deleted successfully!";
	}
}
