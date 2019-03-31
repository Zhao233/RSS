package com.example.demo.repository.info;

import com.example.demo.domain.info.PreferenceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferenceRecordDao extends JpaRepository<PreferenceRecord,Long> {
}

