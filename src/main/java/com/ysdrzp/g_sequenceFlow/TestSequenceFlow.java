package com.ysdrzp.g_sequenceFlow;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

/**
 * 连线
 * @author ysdrzp
 */
public class TestSequenceFlow {

	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	@Test
	public void deployProcess() {

		RepositoryService repositoryService = this.processEngine.getRepositoryService();
		Deployment deploy = repositoryService.createDeployment().name("报销流程")
				.addClasspathResource("SequenceFlowBPMN.bpmn")
				.addClasspathResource("SequenceFlowBPMN.png")
				.deploy();
		System.out.println("部署成功:流程部署ID：" + deploy.getId());
	}

	@Test
	public void startProcess() {

		RuntimeService runtimeService = this.processEngine.getRuntimeService();
		String processDefinitionKey = "SequenceFlowBPMN";
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);
		System.out.println("=================================流程启动成功=================================");
		System.out.println("流程执行ID:" + processInstance.getId());
		System.out.println("流程定义ID:" + processInstance.getProcessDefinitionId());
		System.out.println("流程实例ID:" + processInstance.getProcessInstanceId());
	}

	@Test
	public void queryMyTask() {

		TaskService taskService = this.processEngine.getTaskService();

		String assignee = "李四";
		List<Task> list = taskService.createTaskQuery()
				.taskAssignee(assignee)
				.orderByTaskCreateTime().desc()
				.list();

		if (null != list && list.size() > 0) {
			for (Task task : list) {
				System.out.println("任务ID:" + task.getId());
				System.out.println("任务办理人:" + task.getAssignee());
				System.out.println("执行实例ID:" + task.getExecutionId());
				System.out.println("任务名称:" + task.getName());
				System.out.println("流程定义ID:" + task.getProcessDefinitionId());
				System.out.println("流程实例ID:" + task.getProcessInstanceId());
				System.out.println("任务创建时间:" + task.getCreateTime());
				System.out.println("####################");
			}
		}
	}

	@Test
	public void completeTask() {

		TaskService taskService = this.processEngine.getTaskService();

		String taskId = "7503";
		taskService.complete(taskId);
		System.out.println("任务完成");
	}

	@Test
	public void completeTask2() {
		TaskService taskService = this.processEngine.getTaskService();
		/**
		 * 任务ID
		 */
		String taskId = "5002";

		/**
		 * 使用流程变量控制流程走向
		 */
		Map<String, Object> variables = new HashMap<>();
		variables.put("outcome", "重要");
		taskService.complete(taskId, variables);
		System.out.println("任务完成");
	}

}
