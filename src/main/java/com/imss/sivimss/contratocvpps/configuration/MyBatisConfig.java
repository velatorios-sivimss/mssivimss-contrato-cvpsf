package com.imss.sivimss.contratocvpps.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.imss.sivimss.contratocvpps.configuration.Mapper.Consultas;
import com.imss.sivimss.contratocvpps.configuration.Mapper.PlanSFPAMapper;

@Service
public class MyBatisConfig {

	@Value("${spring.datasource.driverClassName}")
	private String driver;

	@Value("${spring.datasource.url}")
	private String url;

	@Value("${spring.datasource.username}")
	private String userName;

	@Value("${spring.datasource.password}")
	private String password;

	@Value("${enviroment}")
	private String enviroment;

	public SqlSessionFactory buildqlSessionFactory() {
		DataSource dataSource = new PooledDataSource(driver, url, userName, password);

		Environment environment = new Environment(enviroment, new JdbcTransactionFactory(), dataSource);

		Configuration configuration = new Configuration(environment);
		configuration.addMapper(Consultas.class);
		configuration.addMapper(PlanSFPAMapper.class);

		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();

		return builder.build(configuration);
	}
}
