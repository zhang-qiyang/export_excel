package com.chrysler.www.export_excel.service;

import com.chrysler.www.export_excel.entity.User;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author zhangqiyang
 * @date 2020/4/7 - 10:30
 * @description
 */
public interface ExportExcelService {
    /**导出用户数据
     * @param user 用户信息
     * @param response 相应
     * @return
     * @throws Exception 异常
     */
    ResponseEntity<byte[]> exportExcel(List<User> user, HttpServletResponse response) throws Exception;

    long batchInsert();

    List<User> insertUser();
}
