package com.ysdrzp.i_parallelGateWay;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import java.util.List;

/**
 * 并行网关
 * if else if  else if  else
 * @author ysdrzp
 *
 */
public class TestParallelGateWay {

	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	@Test
	public void deployProcess() {
		RepositoryService repositoryService = this.processEngine.getRepositoryService();
		Deployment deploy = repositoryService.createDeployment().name("购物流程")
				.addClasspathResource("ParallelGateWay.bpmn")
				.addClasspathResource("ParallelGateWay.png")
				.deploy();
		System.out.println("部署成功:流程部署ID：" + deploy.getId());
	}

	@Test
	public void startProcess() {
		RuntimeService runtimeService = this.processEngine.getRuntimeService();
		String processDefinitionKey = "myProcess";
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);
		System.out.println("=================================流程启动成功=================================");
		System.out.println("流程执行ID:" + processInstance.getId());
		System.out.println("流程定义ID:" + processInstance.getProcessDefinitionId());
		System.out.println("流程实例ID:" + processInstance.getProcessInstanceId());
	}

	@Test
	public void queryMyTask() {
		TaskService taskService = this.processEngine.getTaskService();
		String assignee = "商家";
		//String assignee = "买家";
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
		String taskId = "7502";
		taskService.complete(taskId);
		System.out.println("任务完成");
	}
}
