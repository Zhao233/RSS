package com.example.demo.model.admin;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class Food_All {
    public Food_All(long id, String picUrl, String name, String menuName, List<String> styles, String role, int enable, Timestamp createTime, Timestamp updateTime) {
        this.id = id;
        this.picUrl = picUrl;
        this.name = name;
        this.menuName = menuName;
        this.styles = styles;
        this.role = role;
        this.enable = enable;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    long id;
    String picUrl;
    String name;
    String menuName;
    List<String> styles;
    String role;
    int enable;
    Timestamp createTime;
    Timestamp updateTime;
}
