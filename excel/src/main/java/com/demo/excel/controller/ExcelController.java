package com.demo.excel.controller;

import com.demo.excel.service.ExcelService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: 小手WA凉
 * @create: 2024-08-16
 */
@RestController
@RequestMapping("/excel")
public class ExcelController {

    @Resource
    private ExcelService excelService;

    @GetMapping("/download")
    public String download(HttpServletResponse response){
        excelService.download(response);
        return "下载成功";
    }
}
