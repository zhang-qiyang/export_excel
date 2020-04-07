package com.chrysler.www.export_excel.utils;

import java.util.UUID;

/**
 * @author zhangqiyang
 * @date 2020/4/7 - 15:47
 * @description
 */
public class UUIDUtil {
    public static String getUUID(){
       return UUID.randomUUID().toString().replace("-","");
    }
}
