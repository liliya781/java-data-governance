package com.nene.datagov.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class DataRecordUpsertRequest {
    @NotBlank
    private String sourceType;

    @NotNull
    private LocalDate sampleDate;

    @NotNull
    private BigDecimal onlineValue;

    @NotNull
    private BigDecimal offlineValue;

    @NotBlank
    private String status;

    private String note;
}
