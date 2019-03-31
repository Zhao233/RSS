package com.example.demo.domain.user;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Component
@Table(name = "cooker_role")
@Data
public class CookerRole {
    /**
     * 数据库id
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "role_id")
    private int roleId;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "update_time")
    private Timestamp updateTime;


}
