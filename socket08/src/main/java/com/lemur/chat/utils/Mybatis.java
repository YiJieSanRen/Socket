package com.lemur.chat.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.lemur.chat.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

public class Mybatis {

    static final ThreadLocal<SqlSession> THREAD_LOCAL = new ThreadLocal<>();

    static SqlSessionFactoryBuilder sqlSessionFactoryBuilder;

    static Configuration configuration;

    static {
        sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        JdbcTransactionFactory jdbcTransactionFactory = new JdbcTransactionFactory();
        Properties properties;
        DataSource druidDataSource;
        try {
            properties = Resources.getResourceAsProperties("mysql.properties");
            druidDataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        Environment environment = new Environment("default", jdbcTransactionFactory, druidDataSource);
        configuration = new Configuration(environment);
        configuration.addMapper(UserMapper.class);
        configuration.setLogImpl(StdOutImpl.class);
    }

    public static SqlSession getSession() {
        if (!Objects.nonNull(THREAD_LOCAL.get())) {
            SqlSessionFactory build = sqlSessionFactoryBuilder.build(configuration);
            THREAD_LOCAL.set(build.openSession());
        }
        return THREAD_LOCAL.get();
    }
}
