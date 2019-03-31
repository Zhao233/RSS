package com.example.demo.repository.info;

import com.example.demo.domain.info.WaiterDeliveryRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaiterDeliveryRecordDao extends JpaRepository<WaiterDeliveryRecord,Long> {
}

