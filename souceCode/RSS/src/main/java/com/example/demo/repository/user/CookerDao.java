package com.example.demo.repository.user;

import com.example.demo.domain.foodInfo.Menu;
import com.example.demo.domain.user.Cooker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CookerDao extends JpaRepository<Cooker,Long> {

    @Query(value = "SELECT cooker FROM Cooker cooker where cooker.name like %?1%")
    Page<Cooker> getAll(String search, Pageable pageable);
}

