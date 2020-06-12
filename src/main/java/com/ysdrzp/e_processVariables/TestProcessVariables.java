package com.ysdrzp.e_processVariables;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

/**
 * 流程变量测试
 * 涉及的表 - act_ru_variable、act_hi_varinst
 * @author ysdrzp
 *
 */
public  class TestProcessVariables {

	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	/**
	 * 部署流程使用classpath
	 */
	@Test
	public void deployProcess() {
		RepositoryService repositoryService = this.processEngine.getRepositoryService();
		Deployment deploy = repositoryService.createDeployment().name("请假流程001")
				.addClasspathResource("HelloWorld.bpmn")
				.addClasspathResource("HelloWorld.png").deploy();
		System.out.println("部署成功:流程部署ID：" + deploy.getId());
	}

	/**
	 * 启动流程
	 */
	@Test
	public void startProcess() {

		RuntimeService runtimeService = this.processEngine.getRuntimeService();

		/**
		 * 流程定义Key
		 */
		String processDefinitionKey = "HelloWorld";

		/**
		 * 流程变量对象
		 */
		Map<String,Object> variables=new HashMap<>();
		variables.put("请假天数", 5);
		variables.put("请假原因", "约会");
		variables.put("请假时间", new Date());

		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);
		System.out.println("=================================流程启动成功=================================");
		System.out.println("流程执行ID:" + processInstance.getId());
		System.out.println("流程定义ID:" + processInstance.getProcessDefinitionId());
		System.out.println("流程实例ID:" + processInstance.getProcessInstanceId());
	}
	
	/**
	 * 设置流程变量1
	 */
	@Test
	public void setVariables() {

		RuntimeService runtimeService = this.processEngine.getRuntimeService();
		String executionId="7501";

		Map<String,Object> variables=new HashMap<>();
		variables.put("请假天数", 6);
		variables.put("请假原因", "约会妹子");
		variables.put("请假时间", new Date());
		variables.put("用户对象", new User(1,"小明"));

		runtimeService.setVariables(executionId, variables);
		System.out.println("流程变量设置成功");
	}

	/**
	 * 设置流程变量2
	 */
	@Test
	public void setVariables2() {

		/**
		 * 流程执行ID
		 */
		TaskService taskService = this.processEngine.getTaskService();
		
		String taskId="7507";
		Map<String,Object> variables=new HashMap<>();
		variables.put("任务ID设置", 9);

		taskService.setVariables(taskId, variables);
		System.out.println("流程变量设置成功");
	}
	
	/**
	 * 获取流程变量
	 */
	@Test
	public void getVariables() {

		RuntimeService runtimeService = this.processEngine.getRuntimeService();

		/**
		 * 流程执行ID
		 */
		String executionId = "7501";

		Integer days=(Integer) runtimeService.getVariable(executionId, "请假天数");
		Date date=(Date) runtimeService.getVariable(executionId, "请假时间");
		User user=(User) runtimeService.getVariable(executionId, "用户对象");

		System.out.println("请假天数：" + days);
		System.out.println("请假时间：" + date.toLocaleString());
		System.out.println("用户对象：" + user);
	}
	
	/**
	 * 查询历史的流程变量
	 */
	@Test
	public void getHistoryVariables() {

		HistoryService historyService = this.processEngine.getHistoryService();

		/**
		 * 流程实例ID
		 */
		String processInstanceId = "7501";
		List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery()
				.processInstanceId(processInstanceId)
				.list();
		
		for (HistoricVariableInstance historicVariableInstance : list) {
			System.out.println("ID：" + historicVariableInstance.getId());
			System.out.println("变量值：" + historicVariableInstance.getValue());
			System.out.println("变量名:" + historicVariableInstance.getVariableName());
			System.out.println("变量类型：" + historicVariableInstance.getVariableTypeName());
			System.out.println("流程实例ID：" + historicVariableInstance.getProcessInstanceId());
			System.out.println("#####################");
		}
	}

}
