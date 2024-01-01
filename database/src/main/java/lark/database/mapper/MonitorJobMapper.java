package lark.database.mapper;

import java.util.List;
import lark.database.model.MonitorJob;

public interface MonitorJobMapper {
    MonitorJob selectByPrimaryKey(String jobId);

    MonitorJob selectByPrimaryKeySkipLocked(String jobId);

    List<MonitorJob> selectAll();

    int updateByPrimaryKey(MonitorJob row);
}