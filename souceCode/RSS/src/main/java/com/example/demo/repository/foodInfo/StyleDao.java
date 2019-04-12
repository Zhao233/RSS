package com.example.demo.repository.foodInfo;

import com.example.demo.domain.foodInfo.Style;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StyleDao extends JpaRepository<Style,Long> {
    @Query(value = "SELECT style.name FROM Style style where style.id = ?1")
    String getStyleNameByStyleID(long id);

}
