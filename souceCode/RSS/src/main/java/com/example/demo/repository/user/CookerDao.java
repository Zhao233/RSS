package com.example.demo.repository.user;

import com.example.demo.domain.user.Cooker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CookerDao extends JpaRepository<Cooker,Long> {
//    @Query(value = "SELECT login_id FROM cooker where login_id = ?1")
//    List<String> getLoginID(String LoginID);
}

