package com.example.demo.repository.user;


import com.example.demo.domain.user.CookerRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CookerRoleDao extends JpaRepository<CookerRole,Long> {

    @Query(value = "SELECT role.name FROM CookerRole role where role.id = ?1")
    String getRoleNameByID(long id);
}
