package com.luv2code.springsecurity.demo.config;

import java.beans.PropertyVetoException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages="com.luv2code.springsecurity.demo")
@PropertySource("classpath:persistence-mysql.properties")
public class DemoAppConfig {

	@Autowired
	private Environment env;
	
	private Logger logger = Logger.getLogger(getClass().getName());

	@Bean
	public ViewResolver viewResolver() {
		
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		
		viewResolver.setPrefix("/WEB-INF/view/");
		viewResolver.setSuffix(".jsp");
		
		return viewResolver;
	}
	
	@Bean
	public DataSource secureDataSourse() {
		
		ComboPooledDataSource secureDataSourse =
				new ComboPooledDataSource();
		
		try {
			secureDataSourse.setDriverClass(env.getProperty("jdbc.driver"));
		} catch (PropertyVetoException exc) {
			throw new RuntimeException(exc);
		}
		
		
		logger.info(">>> jbds.url=" + env.getProperty("jdbc.url"));
		logger.info(">>> jbds.user=" + env.getProperty("jdbc.user"));
		
		secureDataSourse.setJdbcUrl(env.getProperty("jdbc.url"));
		secureDataSourse.setUser(env.getProperty("jdbc.user"));
		secureDataSourse.setPassword(env.getProperty("jdbc.password"));
		
		secureDataSourse.setInitialPoolSize(
				getIntProperty("connection.pool.initialPoolSize"));
		secureDataSourse.setMinPoolSize(
				getIntProperty("connection.pool.minPoolSize"));
		secureDataSourse.setMaxPoolSize(
				getIntProperty("connection.pool.maxPoolSize"));
		secureDataSourse.setMaxIdleTime(
				getIntProperty("connection.pool.maxIdleTime"));
		
		return secureDataSourse;
	}
	
	private int getIntProperty (String propName) {
		
		String propVal = env.getProperty(propName);
		
		int intPropVal = Integer.parseInt(propVal);
		
		return intPropVal;
	}
	
}



































