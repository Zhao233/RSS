package com.example.demo.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })

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

    /**
     * 厨师角色的区分字段（
     * 例如：1，为凉菜师
     *       2，为糕点师
     * */
    @Column(name = "role_id")
    private Integer roleId;

    /**
     * 厨师角色的名称
     * */
    @Column(name = "role_name")
    private String roleName;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "update_time")
    private Timestamp updateTime;


}
