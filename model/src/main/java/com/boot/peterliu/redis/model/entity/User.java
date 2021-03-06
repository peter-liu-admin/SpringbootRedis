package com.boot.peterliu.redis.model.entity;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

@Data
@ToString
public class User implements Serializable{
    private Integer id;

    @NotBlank(message = "姓名不能为空!")
    private String name;

    @NotBlank(message = "邮箱不能为空！")
    private String email;
}