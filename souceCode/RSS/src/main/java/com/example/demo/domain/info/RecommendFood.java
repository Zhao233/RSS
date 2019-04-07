package com.example.demo.domain.info;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Component
@Table(name = "recommend_food")
@Data
public class RecommendFood {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "food_id")
    private long foodID;

    @Column(name = "type")
    private int type;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "update_time")
    private Timestamp updateTime;
}
