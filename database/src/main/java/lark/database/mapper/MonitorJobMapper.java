package lark.database.mapper;

import java.util.List;
import lark.database.model.MonitorJob;

public interface MonitorJobMapper {
    int deleteByPrimaryKey(String jobId);

    int insert(MonitorJob row);

    MonitorJob selectByPrimaryKey(String jobId);

    List<MonitorJob> selectAll();

    int updateByPrimaryKey(MonitorJob row);
}