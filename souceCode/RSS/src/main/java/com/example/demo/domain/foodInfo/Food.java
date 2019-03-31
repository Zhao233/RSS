package com.example.demo.domain.foodInfo;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Component
@Table(name = "food")
@Data
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "menu_id")
    private long menuId;

    @Column(name = "styles_id")
    private String stylesId;

    @Column(name = "role")
    private int role;

    @Column(name = "pic_url")
    private String picUrl;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "update_time")
    private Timestamp updateTime;
}
