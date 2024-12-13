package lark.island.database.mapper;

import java.util.List;

import lark.island.database.model.MonitorJob;
import org.apache.ibatis.annotations.Param;

public interface MonitorJobMapper {
    MonitorJob selectByPrimaryKey(@Param("jobId") String jobId, @Param("countryRegionCode") String countryRegionCode);

    MonitorJob selectByPrimaryKeySkipLocked(@Param("jobId") String jobId, @Param("countryRegionCode") String countryRegionCode);

    List<MonitorJob> selectAll();

    int updateByPrimaryKey(MonitorJob row);
}