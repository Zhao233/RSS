package com.example.demo.repository.info;

import com.example.demo.domain.info.AppInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppInfoDao extends JpaRepository<AppInfo, Long> {
}
