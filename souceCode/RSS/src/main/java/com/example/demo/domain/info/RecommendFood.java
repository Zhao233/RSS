package com.example.demo.domain.info;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })

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

    /**
     * 0: 轮播
     * 1: 列表
     * */
    @Column(name = "type")
    private Integer type = 0;

    /**
     * 1: 生效
     * 0: 不生效
     * 默认生效
     * */
    @Column(name = "enable")
    private Integer enable = 1;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "update_time")
    private Timestamp updateTime;
}
