package com.chrysler.www.export_excel.service;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.chrysler.www.export_excel.ExcelConstant.ExcelConstant;
import com.chrysler.www.export_excel.dao.UserDao;
import com.chrysler.www.export_excel.entity.User;
import com.chrysler.www.export_excel.utils.SubPage;
import com.chrysler.www.export_excel.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

/**
 * @author zhangqiyang
 * @date 2020/4/7 - 10:31
 * @description
 */
@Service
public class ExportExcelImpl implements ExportExcelService{//InitializingBean
    @Autowired
    private UserDao userDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    /**
     * 导出用户数据
     *
     * @param user     用户信息
     * @param response 相应
     * @return
     */
    @Override
    public ResponseEntity<byte[]> exportExcel(List<User> user, HttpServletResponse response) throws Exception{
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            // 设置EXCEL名称
            String fileName = new String(("绑定关系").getBytes(), "UTF-8");
            // 设置SHEET名称
            Sheet sheet = new Sheet(1, 0);
            sheet.setSheetName("sheet1");
            // 设置标题
            Table table = new Table(1);
            List<List<String>> titles = new ArrayList<List<String>>();
            titles.add(Collections.singletonList("编号"));
            titles.add(Collections.singletonList("姓名"));
            titles.add(Collections.singletonList("年龄"));
            titles.add(Collections.singletonList("电话"));
            titles.add(Collections.singletonList("地址"));
            titles.add(Collections.singletonList("邮箱"));
            table.setHead(titles);
            // 查询总数并 【封装相关变量 这块直接拷贝就行 不要改动】
            Integer totalRowCount = user.size();
            Integer pageSize = ExcelConstant.PER_WRITE_ROW_COUNT;
            Integer writeCount = totalRowCount % pageSize == 0 ? (totalRowCount / pageSize) : (totalRowCount / pageSize + 1);
            // 写数据 这个i的最大值直接拷贝就行了 不要改
            for (int i = 0; i < writeCount; i++) {
                List<List<String>> dataList = new ArrayList<>();
                // 此处查询并封装数据即可 currentPage, pageSize这个变量封装好的 不要改动
//                PageHelper.startPage(i + 1, pageSize);
//                List<SysSystemVO> sysSystemVOList = this.sysSystemReadMapper.selectSysSystemVOList(sysSystemVO);
                List<User> list = SubPage.resultPage(user, i + 1, pageSize);
                if (!CollectionUtils.isEmpty(list)) {
                    list.forEach(u -> {
                        dataList.add(Arrays.asList(
                                u.getUid(),
                                u.getAddress(),
                                u.getAge(),
                                u.getPhone(),
                                u.getEmail(),
                                u.getPhone()
                        ));
                    });
                }
                writer.write0(dataList, sheet, table);
            }
            // 下载EXCEL
            response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName).getBytes("gb2312"), "ISO-8859-1") + ".xlsx");
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding("utf-8");
            writer.finish();
            out.flush();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        HttpHeaders httpHeaders = new HttpHeaders();
        return new ResponseEntity<byte[]>(bos.toByteArray(),httpHeaders, HttpStatus.OK);
    }

    @Override
    public List<User> insertUser() {
        User user = new User();
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 993; i++) {
            user.setUid(UUIDUtil.getUUID());
            user.setAddress(i+"");
            user.setEmail(i+"");
            user.setAge(i+"");
            user.setPhone(i+"");
            user.setEmail(i+"");
            user.setUserName(i+"");
            users.add(user);
        }
        for (int i = 0; i < 10; i++) {
            users.addAll(users);
        }
        return users;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public long batchInsert() {
        //初始化100多万条数据
        List<User> data = insertUser();
        Long startTime = System.currentTimeMillis();
        String addSql = "insert into tet.user (UID, USER_NAME, PHONE, ADDRESS,EMAIL,AGE) VALUES(?,?,?,?,?,?)";
        jdbcTemplate.batchUpdate(addSql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, data.get(i).getUid());
                ps.setString(2, data.get(i).getUserName());
                ps.setString(3, data.get(i).getPhone());
                ps.setString(4, data.get(i).getAddress());
                ps.setString(5, data.get(i).getEmail());
                ps.setString(6, data.get(i).getAge());
            }
            @Override
            public int getBatchSize() {
                return data.size();
            }
        });
        Long endTime = System.currentTimeMillis();
        System.out.println("插入"+data.size()+"条数据用时：" + (endTime - startTime) + "毫秒");
        System.out.println("插入"+data.size()+"条数据用时：" + (endTime - startTime) / 1000 + "秒");
        return data.size();
    }

//    @Override
//    public void afterPropertiesSet() throws Exception {
//        batchInsert();
//    }
}
