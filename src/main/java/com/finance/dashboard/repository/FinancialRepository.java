package com.finance.dashboard.repository;

import com.finance.dashboard.model.FinancialRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface FinancialRepository extends JpaRepository<FinancialRecord, Long>{

}
