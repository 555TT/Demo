package com.demo.excel.entity;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (VulFpHome)实体类
 *
 * @author makejava
 * @since 2024-08-16 15:41:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VulFpHome {


    @ExcelProperty("id")
    private Long id;

    @ExcelProperty("位置")
    private String location;

    @ExcelProperty("匹配内容")
    private String matchContent;

    @ExcelProperty("匹配类型")
    private String matchType;

    @ExcelProperty("产品名")
    private String product;
    /**
     * 是否手动录入1是0否
     */
    @ExcelProperty("手动录入")
    private Integer handled;

    @ExcelProperty("更新时间")
    private Date updateTime;

    @ExcelProperty("创建时间")
    private Date createTime;

    @ExcelProperty("更新人")
    private String uploader;

    @ExcelProperty("描述")
    private String description;
}
