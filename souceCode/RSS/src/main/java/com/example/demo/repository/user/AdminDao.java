package com.example.demo.repository.user;

import com.example.demo.domain.user.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminDao extends JpaRepository<Admin,Long> {
    /**=========================For Cooker==========================*/
    @Query(value = "SELECT admin FROM Admin admin where name = ?1")
    Admin findAdminByName(String name);
}

