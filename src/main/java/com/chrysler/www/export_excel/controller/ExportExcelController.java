package com.chrysler.www.export_excel.controller;

import com.chrysler.www.export_excel.dao.UserDao;
import com.chrysler.www.export_excel.entity.User;
import com.chrysler.www.export_excel.service.ExportExcelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangqiyang
 * @date 2020/4/7 - 13:56
 * @description
 */
@RestController
@RequestMapping("/export")
@Slf4j
public class ExportExcelController {
    @Autowired
    private ExportExcelService exportExcelService;
    @Autowired
    private UserDao userDao;
    @RequestMapping("/exportExcel")
    public ResponseEntity<byte[]> exportExcel(HttpServletResponse response) {
        long l = System.currentTimeMillis();
        try {
            String excelName="下载";
            User user = new User();
            long timeMillis = System.currentTimeMillis();
            List<User> users = userDao.queryAll(user);
            log.info("查询数据耗时:{}ms",System.currentTimeMillis() - timeMillis);
            return exportExcelService.exportExcel(users, response,excelName);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            long o = System.currentTimeMillis();
            log.info("下载总耗时:{}ms",o-l);
        }
        return null;
    }
}
