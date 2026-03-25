package com.nene.datagov.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SourceDeviationVO {
    private String sourceType;
    private BigDecimal avgDeviation;
}
