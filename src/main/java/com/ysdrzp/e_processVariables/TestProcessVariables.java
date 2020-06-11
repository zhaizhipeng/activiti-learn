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
 * 相关表 - act_ru_variable、act_hi_varinst
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
		// 得到流程部署的service
		RepositoryService repositoryService = this.processEngine.getRepositoryService();
		Deployment deploy = repositoryService.createDeployment().name("请假流程001").addClasspathResource("HelloWorld.bpmn")
				.addClasspathResource("HelloWorld.png").deploy();
		System.out.println("部署成功:流程部署ID：" + deploy.getId());
	}

	/**
	 * 启动流程
	 */
	@Test
	public void startProcess() {
		RuntimeService runtimeService = this.processEngine.getRuntimeService();
		String processDefinitionKey = "HelloWorld";
//		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);
		//创建流程变量对象
		Map<String,Object> variables=new HashMap<>();
		variables.put("请假天数", 5);//int
		variables.put("请假原因", "约会");
		variables.put("请假时间", new Date());
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);
		System.out.println("流程启动成功:" + processInstance.getId() + "   " + processInstance.getProcessDefinitionId() + "  "
				+ processInstance.getProcessInstanceId());

	}
	
	/**
	 * 设置流程变量1
	 */
	@Test
	public void setVariables() {
		RuntimeService runtimeService = this.processEngine.getRuntimeService();
		String executionId="2501";
		//runtimeService.setVariable(executionId, "请假人", "小明");
		Map<String,Object> variables=new HashMap<>();
		variables.put("请假天数", 6);//int
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
		TaskService taskService = this.processEngine.getTaskService();
		
		String taskId="2507";
		//runtimeService.setVariable(executionId, "请假人", "小明");
		Map<String,Object> variables=new HashMap<>();
		variables.put("任务ID设置的", 9);//int
//		taskService.setVariable(taskId, variableName, value);
		taskService.setVariables(taskId, variables);
		System.out.println("流程变量设置成功");
	}
	
	
	/**
	 * 获取流程变量
	 */
	@Test
	public void getVariables() {
		RuntimeService runtimeService = this.processEngine.getRuntimeService();
		String executionId="2501";
		Integer days=(Integer) runtimeService.getVariable(executionId, "请假天数");
		Date date=(Date) runtimeService.getVariable(executionId, "请假时间");
		User user=(User) runtimeService.getVariable(executionId, "用户对象");
		System.out.println(days);
		System.out.println(date.toLocaleString());
		System.out.println(user.getId()+"  "+user.getName());
	}
	
	/**
	 * 7：查询历史的流程变量
	 */
	@Test
	public void getHistoryVariables() {
		HistoryService historyService = this.processEngine.getHistoryService();
		
		/*HistoricVariableInstance singleResult = historyService.createHistoricVariableInstanceQuery().id("2503").singleResult();;
		System.out.println(singleResult.getId());
		System.out.println(singleResult.getValue());
		System.out.println(singleResult.getVariableName());
		System.out.println(singleResult.getVariableTypeName());*/
		String processInstanceId="2501";
		List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).list();
		
		for (HistoricVariableInstance hvs : list) {
			System.out.println("ID"+hvs.getId());
			System.out.println("变量值"+hvs.getValue());
			System.out.println("变量名"+hvs.getVariableName());
			System.out.println("变量类型"+hvs.getVariableTypeName());
			System.out.println("#####################");
		}
	}

	/**
	 * 设置流程变量2
	 */
	@Test
	public void setVariables3() {
		RuntimeService runtimeService = this.processEngine.getRuntimeService();
		String executionId="2501";
		//runtimeService.setVariable(executionId, "请假人", "小明");
		Map<String,Object> variables=new HashMap<>();
		variables.put("测试", "约会妹子");
		runtimeService.setVariablesLocal(executionId, variables);
		System.out.println("流程变量设置成功");
	}
	
	@Test
	public void setVariables4() {
		TaskService taskService = this.processEngine.getTaskService();
		String taskId="2507";
		//runtimeService.setVariable(executionId, "请假人", "小明");
		Map<String,Object> variables=new HashMap<>();
		variables.put("测试2", 9);//int
		taskService.setVariablesLocal(taskId, variables);
		System.out.println("流程变量设置成功");
	}

}
