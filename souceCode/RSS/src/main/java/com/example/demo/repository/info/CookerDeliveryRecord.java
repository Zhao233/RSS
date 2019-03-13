package com.example.demo.repository.info;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CookerDeliveryRecord extends JpaRepository<CookerDeliveryRecord,Long> {
}

