package lark.database.util;

import lark.database.mapper.MonitorJobMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DatastoreFactoryTest {

    @Test
    void testGetSession() {
        DatastoreFactory.init("jdbc:postgresql://127.0.0.1:5432/lark", "lark", "lark", "org.postgresql.Driver");
        try (SqlSession session = DatastoreFactory.openSession()) {
            Assertions.assertNotNull(session);
            Assertions.assertNotNull(session.getMapper(MonitorJobMapper.class).selectAll());
        }
    }

}
