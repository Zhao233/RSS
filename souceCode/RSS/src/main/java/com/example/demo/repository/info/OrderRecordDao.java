package com.example.demo.repository.info;

import com.example.demo.domain.info.OrderRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRecordDao extends JpaRepository<OrderRecord,Long> {
}

