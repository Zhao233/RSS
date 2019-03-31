package com.example.demo.repository.info;

import com.example.demo.domain.info.NonPaymentRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NonPaymentRecordDao extends JpaRepository<NonPaymentRecord,Long> {
}

