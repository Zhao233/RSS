package com.example.demo.repository.info;

import com.example.demo.domain.info.CookerDeliveryRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CookerDeliveryRecordDao extends JpaRepository<CookerDeliveryRecord,Long> {
}

