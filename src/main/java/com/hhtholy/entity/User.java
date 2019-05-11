package com.hhtholy.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author hht
 * @create 2019-04-16 15:08
 * 用户实体
 */
@Entity
@Table(name = "user")
@Data
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    private String password;
    private String name;
    private String salt;
    private String anonymousName;

    private String role;
}
