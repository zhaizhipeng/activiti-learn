package com.ysdrzp.d_processInstance;

import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.List;

/**
 * 流程实例、任务的执行
 * @author ysdrzp
 */
public class TestProcessInstance {

	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	@Test
	public void deployProcess() {
		RepositoryService repositoryService = this.processEngine.getRepositoryService();
		Deployment deploy = repositoryService.createDeployment().name("请假流程001")
				.addClasspathResource("HelloWorld.bpmn")
				.addClasspathResource("HelloWorld.png")
				.deploy();
		System.out.println("部署成功:流程部署ID：" + deploy.getId());
	}

	/**
	 * 启动流程
	 */
	@Test
	public void startProcess() {
		RuntimeService runtimeService = this.processEngine.getRuntimeService();

		/**
		 * 根据流程定义ID启动流程
		 */
		//runtimeService.startProcessInstanceById(processDefinitionId)

		/**
		 * 参数1：流程定义ID 参数2：Map<String,Object> 流程变量
		 */
//		runtimeService.startProcessInstanceById(processDefinitionId, variables);

		/**
		 * 参数1：流程定义ID 参数2：String 业务ID 把业务ID和流程执行实例进行绑定
		 */
//		runtimeService.startProcessInstanceById(processDefinitionId, businessKey);

		/**
		 * 参数1：流程定义ID 参数2：String 业务ID 把业务ID和流程执行实例进行绑定 参数3：Map<String,Object> 流程变量
		 */
//		runtimeService.startProcessInstanceById(processDefinitionId, businessKey, variables)

//		runtimeService.startProcessInstanceByKey(processDefinitionKey)//根据流程定义的key启动

		/**
		 * 参数1：流程定义的Key 参数2：Map<String,Object> 流程变量
		 */
//		runtimeService.startProcessInstanceByKey(processDefinitionKey, variables)

		/**
		 * 参数1：流程定义Key 参数2：String 业务ID 把业务ID和流程执行实例进行绑定
		 */
//		runtimeService.startProcessInstanceByKey(processDefinitionId, businessKey);

		/**
		 * 参数1：流程定义Key 参数2：String 业务ID 把业务ID和流程执行实例进行绑定 参数3：Map<String,Object> 流程变量
		 */
//		runtimeService.startProcessInstanceByKey(processDefinitionId, businessKey, variables)

		// 实例开发中使用的
		// runtimeService.startProcessInstanceByKey(processDefinitionId, businessKey,
		// variables)
		// runtimeService.startProcessInstanceByKey(processDefinitionId, businessKey);

		String processDefinitionKey = "HelloWorld";
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);
		System.out.println("流程启动成功:" + processInstance.getId() + "   " + processInstance.getProcessDefinitionId() + "  "
				+ processInstance.getProcessInstanceId());

	}

	/**
	 * 查询我的个人任务act_ru_task
	 */
	@Test
	public void queryMyTask() {
		TaskService taskService = this.processEngine.getTaskService();
		String assignee = "李四";
		List<Task> list = taskService.createTaskQuery()
				// 条件
				.taskAssignee(assignee)// 根据任务办理人查询任务
//		.deploymentId(deploymentId)//根据部署ID查询 where id=id
//		.deploymentIdIn(deploymentIds)//根据部署ID集合查询   where id in (1,2,3,4)
//		.executionId(executionId)//根据执行实例ID
//		.processDefinitionId(processDefinitionId)//根据流程定义ID
//		.processDefinitionKey(processDefinitionKey)//根据流程定义的key
//		.processDefinitionKeyIn(processDefinitionKeys)
//		.processDefinitionKeyLike(processDefinitionKeyLike)
//		.processDefinitionName(processDefinitionName)
//		.processDefinitionNameLike(processDefinitionNameLike)
//		.processInstanceBusinessKey(processInstanceBusinessKey)
				// 排序
				.orderByTaskCreateTime().desc()
				// 结果集
				.list();
//		.listPage(firstResult, maxResults)
//		.count();
//		.singleResult()

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

	/**
	 * 办理任务
	 */
	@Test
	public void completeTask() {
		TaskService taskService = this.processEngine.getTaskService();
		String taskId = "20002";
		// 根据任务ID去完成任务
		taskService.complete(taskId);
		// 根据任务ID去完成任务并指定流程变量
//		taskService.complete(taskId, variables);
		System.out.println("任务完成");
	}

	/**
	 * 判断流程是否结束 作用：更新业务表里面的状态
	 */
	@Test
	public void isComplete() {
		// 已知流程实例ID
		RuntimeService runtimeService = this.processEngine.getRuntimeService();
		String processInstanceId = "2501";
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		if (null != processInstance) {
			System.out.println("流程未结束");
		} else {
			System.out.println("流程已结束");
		}

		// 已知任务ID 5002 [了解]
		// 根据任务ID查询任务实例对象
		/*
		 * TaskService taskService = this.processEngine.getTaskService(); String
		 * taskId="5002"; Task task =
		 * taskService.createTaskQuery().taskId(taskId).singleResult();
		 * //从任务实例里面取出流程实例ID String processInstanceId2 = task.getProcessInstanceId();
		 * //使用流程实例ID去流程实例表里面查询有没有数据 ProcessInstanceQuery processInstance2 =
		 * runtimeService.createProcessInstanceQuery().processInstanceId(
		 * processInstanceId2); if (null != processInstance2) {
		 * System.out.println("流程未结束"); } else { System.out.println("流程已结束"); }
		 */

	}

	/**
	 * 7，查询当前的流程实例 act_ru_execution
	 * 
	 */
	@Test
	public void queryProcessInstance() {
		RuntimeService runtimeService = this.processEngine.getRuntimeService();
		List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().list();
		if (null != list && list.size() > 0) {
			for (ProcessInstance pi : list) {
				System.out.println("执行实例ID:" + pi.getId());
				System.out.println("流程定义ID:" + pi.getProcessDefinitionId());
				System.out.println("流程实例ID:" + pi.getProcessInstanceId());
				System.out.println("########################");
			}
		}
	}

	/**
	 * 7，附加功能，查询历史任务（后面讲）act_hi_taskinst
	 */
	@Test
	public void queryHistoryTask() {
		HistoryService historyService = this.processEngine.getHistoryService();
		List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().list();
		if (null != list && list.size() > 0) {
			for (HistoricTaskInstance hi : list) {
				System.out.println("任务ID:" + hi.getId());
				System.out.println("任务办理人:" + hi.getAssignee());
				System.out.println("执行实例ID:" + hi.getExecutionId());
				System.out.println("任务名称:" + hi.getName());
				System.out.println("流程定义ID:" + hi.getProcessDefinitionId());
				System.out.println("流程实例ID:" + hi.getProcessInstanceId());
				System.out.println("任务创建时间:" + hi.getCreateTime());
				System.out.println("任务结束时间:" + hi.getEndTime());
				System.out.println("任务持续时间:" + hi.getDurationInMillis());
				System.out.println("####################");
			}
		}
	}

	/**
	 * 8，附加功能，查询历史流程实例（后面讲）
	 */
	@Test
	public void queryHistoryProcessInstance() {
		HistoryService historyService = this.processEngine.getHistoryService();
		List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery().list();
		if(null!=list&&list.size()>0) {
			for (HistoricProcessInstance hi : list) {
				System.out.println("执行实例ID:" + hi.getId());
				System.out.println("流程定义ID:" + hi.getProcessDefinitionId());
				System.out.println("流程启动时间:" + hi.getStartTime());
				System.out.println("########################");
			}
		}
	}

}
