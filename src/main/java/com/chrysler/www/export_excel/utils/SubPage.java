package com.chrysler.www.export_excel.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangqiyang
 * @date 2020/4/7 - 11:22
 * @description  分页工具类
 */
public class SubPage {
    public static List resultPage(List tables,Integer pageNo,Integer pageSize){
        if(pageNo==0 && pageSize==0){
            return tables;
        }
        List pageList = new ArrayList<>();
        if(tables!=null && tables.size()>0){
            int currIdx=(pageNo>1?(pageNo-1)*pageSize:0);
            for (int i = 0; i < pageSize && i < tables.size() - currIdx; i++) {
                Object o=tables.get(currIdx+i);
                pageList.add(o);
            }
        }
        return pageList;
    }
}
