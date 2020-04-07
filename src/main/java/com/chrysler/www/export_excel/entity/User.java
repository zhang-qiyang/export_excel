package com.chrysler.www.export_excel.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangqiyang
 * @date 2020/4/7 - 10:32
 * @description
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1601183967356245161L;
    private String uid;
    private String userName;
    private String age;
    private String phone;
    private String address;
    private String email;
}
