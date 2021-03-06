package com.example.demo.domain.info;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })

@Entity
@Component
@Table(name = "frequently_used_food")
@Data
public class FrequentlyUsedFood {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "user_id")
    private long userID;

    @Column(name = "foods_id")
    private String foodsId;

    @Column(name = "styles_id")
    private String stylesId;

    /**
     * 菜品的数量
     * */
    @Column(name = "nums")
    private String nums;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "update_time")
    private Timestamp updateTime;
}
