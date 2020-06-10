package com.ysdrzp.a_init;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.junit.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * 初始化工作流表
 * @author 翟志鹏
 */
public class InitTable {

	/**
	 * 方式一
	 */
	@Test
	public void initTables() {
		/**
		 * 1、创建数据源
		 */
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/activiti-learn");
		dataSource.setUsername("root");
		dataSource.setPassword("root");

		/**
		 * 2、创建流程引擎的配置
		 */
		ProcessEngineConfiguration configuration = ProcessEngineConfiguration
				.createStandaloneProcessEngineConfiguration();

		/**
		 * 3、绑定数据源
		 */
		configuration.setDataSource(dataSource);

		/**
		 * 指定策略
		 * ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE：如果数据库里面没有表，也不会创建
		 * ProcessEngineConfiguration.DB_SCHEMA_UPDATE_CREATE_DROP： 创建表，使用完之后删除
		 * ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE： 如果数据库里面没有表，就创建
		 * drop-create： 如果数据库里面有表，那么先删除再创建
		 */

		/**
		 * 4、配置表的初始化的方式
		 */
		configuration.setDatabaseSchemaUpdate("drop-create");

		/**
		 * 5、得到流程引擎【核心】
		 */
		ProcessEngine processEngine = configuration.buildProcessEngine();
		System.out.println(processEngine);
	}

	/**
	 * 方式二
	 */
	@Test
	public void intiTables2() {
		/**
		 * 根据配置初始化
		 */
		ProcessEngineConfiguration configuration = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("/activiti.cfg.xml");

		/**
		 * 得到流程引擎
		 */
		ProcessEngine processEngine = configuration.buildProcessEngine();
		System.out.println(processEngine);
	}

	/**
	 * 方式三：原理还是加载配置文件
	 */
	@Test
	public void intiTables3() {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		System.out.println(processEngine);
	}

}
