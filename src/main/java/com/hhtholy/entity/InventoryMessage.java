package com.hhtholy.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

/**
 * @author hht
 * @create 2019-05-22 16:56
 *   库存消息
 */
@Entity
@Table(name="inventory")
@Data
public class InventoryMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "message")
    private String message;

    @Column(name = "read")
    private Integer read; //消息的读取状态 1表示已经读取  2表示未读取
}
