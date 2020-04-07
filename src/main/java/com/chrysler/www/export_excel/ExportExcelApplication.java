package com.chrysler.www.export_excel;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhangqiyang
 */
@SpringBootApplication
@MapperScan({"com.chrysler.www.export_excel.dao"})
public class ExportExcelApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExportExcelApplication.class, args);
    }

}
