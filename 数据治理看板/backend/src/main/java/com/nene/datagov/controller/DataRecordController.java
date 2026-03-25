package com.nene.datagov.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nene.datagov.common.ApiResponse;
import com.nene.datagov.dto.DataRecordUpsertRequest;
import com.nene.datagov.entity.DataRecord;
import com.nene.datagov.mapper.DataRecordMapper;
import com.nene.datagov.service.DataRecordService;
import com.nene.datagov.vo.MonthTrendVO;
import com.nene.datagov.vo.SourceCountVO;
import com.nene.datagov.vo.SourceDeviationVO;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class DataRecordController {
    private final DataRecordService dataRecordService;
    private final DataRecordMapper dataRecordMapper;

    public DataRecordController(DataRecordService dataRecordService, DataRecordMapper dataRecordMapper) {
        this.dataRecordService = dataRecordService;
        this.dataRecordMapper = dataRecordMapper;
    }

    @GetMapping("/records")
    public ApiResponse<List<DataRecord>> listRecords(
            @RequestParam(required = false) String sourceType,
            @RequestParam(required = false) String status
    ) {
        LambdaQueryWrapper<DataRecord> qw = new LambdaQueryWrapper<>();
        qw.eq(sourceType != null && !sourceType.isBlank(), DataRecord::getSourceType, sourceType)
          .eq(status != null && !status.isBlank(), DataRecord::getStatus, status)
          .orderByDesc(DataRecord::getSampleDate);
        return ApiResponse.ok(dataRecordService.list(qw));
    }

    @PostMapping("/records")
    public ApiResponse<DataRecord> createRecord(@Valid @RequestBody DataRecordUpsertRequest request) {
        DataRecord record = new DataRecord();
        BeanUtils.copyProperties(request, record);
        record.setCreatedAt(LocalDateTime.now());
        dataRecordService.save(record);
        return ApiResponse.ok(record);
    }

    @PutMapping("/records/{id}")
    public ApiResponse<DataRecord> updateRecord(@PathVariable Long id, @Valid @RequestBody DataRecordUpsertRequest request) {
        DataRecord record = dataRecordService.getById(id);
        if (record == null) {
            return ApiResponse.fail("record not found");
        }
        BeanUtils.copyProperties(request, record);
        dataRecordService.updateById(record);
        return ApiResponse.ok(record);
    }

    @DeleteMapping("/records/{id}")
    public ApiResponse<Boolean> deleteRecord(@PathVariable Long id) {
        return ApiResponse.ok(dataRecordService.removeById(id));
    }

    @GetMapping("/stats/source")
    public ApiResponse<List<SourceCountVO>> sourceStats() {
        return ApiResponse.ok(dataRecordMapper.countBySource());
    }

    @GetMapping("/stats/deviation")
    public ApiResponse<List<SourceDeviationVO>> deviationStats() {
        return ApiResponse.ok(dataRecordMapper.averageDeviationBySource());
    }

    @GetMapping("/stats/month-trend")
    public ApiResponse<List<MonthTrendVO>> monthTrend() {
        return ApiResponse.ok(dataRecordMapper.monthTrend());
    }
}
