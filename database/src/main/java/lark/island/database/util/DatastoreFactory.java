package lark.island.database.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.logging.log4j2.Log4j2Impl;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;

public class DatastoreFactory {
    private static SqlSessionFactory sqlSessionFactory;

    public static void init(String jdbcUrl, String username, String password, String driverClassName) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driverClassName);
        config.setAutoCommit(false);

        DataSource dataSource = new HikariDataSource(config);

        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("data", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.setLogImpl(Log4j2Impl.class);
        configuration.addMappers("lark.database.mapper");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    }

    public static SqlSession openSession() {
        return sqlSessionFactory.openSession();
    }

    private DatastoreFactory() {
        throw new IllegalStateException("Utility class");
    }
}
