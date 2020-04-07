package com.chrysler.www.export_excel;

import com.chrysler.www.export_excel.dao.UserDao;
import com.chrysler.www.export_excel.entity.User;
import com.chrysler.www.export_excel.service.ExportExcelService;
import com.chrysler.www.export_excel.utils.UUIDUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ExportExcelApplicationTests {

    @Autowired
    private ExportExcelService exportExcelService;

    @Test
    void contextLoads() {
    }

}
