package lark.database.util;

import lark.database.mapper.MonitorJobMapper;
import lark.database.model.MonitorJob;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Statement;

class DatastoreFactoryTest {

    @BeforeAll
    static void init() {
        DatastoreFactory.init("jdbc:h2:mem:test", "sa", "sa", "org.h2.Driver");
        try (SqlSession session = DatastoreFactory.openSession()) {
            Statement stmt = session.getConnection().createStatement();
            stmt.execute("DROP TABLE IF EXISTS monitor_job");
            stmt.execute("CREATE TABLE monitor_job" +
                    "(" +
                    "    job_id CHARACTER VARYING," +
                    "    country_region_code CHARACTER VARYING," +
                    "    cron_json CHARACTER VARYING," +
                    "    once_per_duration CHARACTER VARYING," +
                    "    hold_start_time TIMESTAMP WITH TIME ZONE," +
                    "    hold_end_time TIMESTAMP WITH TIME ZONE," +
                    "    last_trig_time TIMESTAMP WITH TIME ZONE" +
                    ")");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetSession() {
        try (SqlSession session = DatastoreFactory.openSession()) {
            Assertions.assertNotNull(session);
            MonitorJobMapper mapper = session.getMapper(MonitorJobMapper.class);
            Assertions.assertNotNull(mapper.selectAll());
            Assertions.assertNull(mapper.selectByPrimaryKey("NoData", "NoData"));
            Assertions.assertNull(mapper.selectByPrimaryKeySkipLocked("NoData", "NoData"));
            Assertions.assertEquals(0, mapper.updateByPrimaryKey(MonitorJob.builder().jobId("NoData").build()));
        }
    }

}
