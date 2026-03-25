package com.nene.datagov.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nene.datagov.entity.DataRecord;
import com.nene.datagov.mapper.DataRecordMapper;
import com.nene.datagov.service.DataRecordService;
import org.springframework.stereotype.Service;

@Service
public class DataRecordServiceImpl extends ServiceImpl<DataRecordMapper, DataRecord> implements DataRecordService {
}
