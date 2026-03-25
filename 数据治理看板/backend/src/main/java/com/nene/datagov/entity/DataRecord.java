package com.nene.datagov.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("data_record")
public class DataRecord {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("source_type")
    private String sourceType;

    @TableField("sample_date")
    private LocalDate sampleDate;

    @TableField("online_value")
    private BigDecimal onlineValue;

    @TableField("offline_value")
    private BigDecimal offlineValue;

    @TableField("`status`")
    private String status;
    private String note;

    @TableField("created_at")
    private LocalDateTime createdAt;
}
