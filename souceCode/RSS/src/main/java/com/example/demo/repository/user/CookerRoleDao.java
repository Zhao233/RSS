package com.example.demo.repository.user;


import com.example.demo.domain.user.CookerRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CookerRoleDao extends JpaRepository<CookerRole,Long> {
}
