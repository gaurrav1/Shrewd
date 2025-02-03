//package com.shrewd.config.hibernate;
//
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class MasterDatabaseConfig {
//
//    @Primary
//    @Bean(name = "masterDataSource")
//    public DataSource masterDataSource() {
//        return DataSourceBuilder.create()
//                .url("jdbc:mysql://localhost:3306/shrewd")
//                .username("gaurav")
//                .password("NGaurav@113")
//                .driverClassName("com.mysql.cj.jdbc.Driver")
//                .build();
//    }
//}
