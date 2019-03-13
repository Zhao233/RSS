package com.example.demo.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Admin extends JpaRepository<Admin,Long> {
}

