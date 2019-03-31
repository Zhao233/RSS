package com.example.demo.repository.info;

import com.example.demo.domain.info.DiscountRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRecordDao extends JpaRepository<DiscountRecord,Long> {

}

