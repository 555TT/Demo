package com.demo.excel.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.demo.excel.dao.ExcelDao;
import com.demo.excel.entity.VulFpHome;
import com.demo.excel.service.ExcelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author: 小手WA凉
 * @create: 2024-08-16
 */

@Service
@Slf4j
public class ExcelServiceImpl implements ExcelService {

    @Resource
    private ExcelDao excelDao;

//    private static final String PATH="C:\\Users\\DELL\\Desktop\\";

    @Override
    public void download(HttpServletResponse response) {
        List<VulFpHome> vulFpHomes =excelDao.list();
        String fileName="指纹_"+System.currentTimeMillis()+".xlsx";
        fileName=new String(fileName.getBytes(), StandardCharsets.ISO_8859_1);
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        try {
            EasyExcel.write(response.getOutputStream(), VulFpHome.class).sheet("指纹").doWrite(vulFpHomes);
        } catch (IOException e) {
            log.info("文件下载异常：{}",e.getMessage());
        }
    }
}
