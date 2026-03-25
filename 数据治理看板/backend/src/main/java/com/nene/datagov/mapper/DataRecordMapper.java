package com.nene.datagov.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nene.datagov.entity.DataRecord;
import com.nene.datagov.vo.MonthTrendVO;
import com.nene.datagov.vo.SourceCountVO;
import com.nene.datagov.vo.SourceDeviationVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DataRecordMapper extends BaseMapper<DataRecord> {

    @Select("""
        SELECT source_type AS name, COUNT(*) AS "value"
        FROM data_record
        GROUP BY source_type
        ORDER BY 2 DESC
    """)
    List<SourceCountVO> countBySource();

    @Select("""
        SELECT source_type AS sourceType,
               ROUND(AVG(ABS(online_value - offline_value)), 2) AS avgDeviation
        FROM data_record
        GROUP BY source_type
        ORDER BY avgDeviation DESC
    """)
    List<SourceDeviationVO> averageDeviationBySource();

    @Select("""
        SELECT FORMATDATETIME(sample_date, 'yyyy-MM') AS "month", COUNT(*) AS total
        FROM data_record
        GROUP BY FORMATDATETIME(sample_date, 'yyyy-MM')
        ORDER BY 1
    """)
    List<MonthTrendVO> monthTrend();
}
