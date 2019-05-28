package com.example.demo.repository.user;

import com.example.demo.domain.foodInfo.Menu;
import com.example.demo.domain.user.Cooker;
import com.example.demo.domain.user.Waiter;
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

    /**=========================For Cooker==========================*/
    @Query(value = "SELECT cooker FROM Cooker cooker WHERE cooker.openID = ?1 AND cooker.enable = 1")
    Cooker getCookerByOpenID(String openid);

    @Query(value = "SELECT cooker FROM Cooker cooker WHERE cooker.loginID = ?1 AND cooker.enable = 1")
    Cooker getCookerByLoginID(String secret);

}

