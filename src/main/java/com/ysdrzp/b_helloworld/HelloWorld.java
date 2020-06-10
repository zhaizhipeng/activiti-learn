package com.ysdrzp.b_helloworld;

import java.util.List;

import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.task.Task;
import org.junit.Test;

/**
 * 入门工作流 - 模拟请假的流程
 */
public class HelloWorld {

	/**
	 *
	 * 		//查询历史记录的服务器act_hi_actinst, act_hi_attachment, act_hi_comment, act_hi_detail, act_hi_identitylink, act_hi_procinst, act_hi_taskinst, act_hi_varinst
	 * 		HistoryService historyService = processEngine.getHistoryService();
	 * 		//页面表单的服务器[了解]
	 * 		FormService formService = processEngine.getFormService();
	 * 		//对工作流的用户管理的表的操作act_id_group, act_id_info, act_id_membership, act_id_user
	 * 		IdentityService identityService = processEngine.getIdentityService();
	 * 		//管理器
	 * 		ManagementService managementService = processEngine.getManagementService();
	 */

	/**
	 * 0、得到流程引擎
	 */
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	/**
	 * 1、流程部署
	 */
	@Test
	public void deployProcess() {
		/**
		 * 流程图的部署、修改、删除
		 * 涉及到的表：
		 * 		act_ge_bytearray,通用的流程定义和流程资源
		 * 		act_ge_property,系统相关属性
		 * 		act_re_deployment,流程部署信息
		 * 		act_re_procdef,流程定义信息
		 * 		act_re_model,模型信息
		 */
		RepositoryService repositoryService = this.processEngine.getRepositoryService();
		Deployment deploy = repositoryService.createDeployment().name("请假流程001")
				.addClasspathResource("HelloWorld.bpmn")
				.addClasspathResource("HelloWorld.png")
				.deploy();

		System.out.println("部署成功:流程部署ID："+deploy.getId());
	}

	/**
	 * 2、启动流程
	 */
	@Test
	public void startProcess() {
		/**
		 * 流程的运行
		 * 涉及的表：
		 * 		act_ru_execution,运行时流程执行实例
		 * 		act_ru_identitylink,当前节点参与者的信息,任务参与者数据
		 * 		act_ru_task,运行时任务数据信息
		 * 		act_ru_variable,运行时流程变量数据信息
		 * 		act_ru_event_subscr,
		 * 		act_ru_job,
		 */
		RuntimeService runtimeService = this.processEngine.getRuntimeService();

		/**
		 * 流程ID - HelloWorld:1:4。【由“流程编号：流程版本号：自增长ID”组成】
		 * 根据流程定义ID,启动任务
		 */
		//String processDefinitionId="HelloWorld:1:4";
		//runtimeService.startProcessInstanceById(processDefinitionId);

		/**
		 * 流程编号：HelloWorld
		 */
		String processDefinitionKey="HelloWorld";
		runtimeService.startProcessInstanceByKey(processDefinitionKey);
		System.out.println("流程启动成功");
	}
	
	/**
	 * 3、查询任务
	 */
	@Test
	public void findTask() {

		System.out.println("################################运行任务实例################################");

		TaskService taskService = this.processEngine.getTaskService();
		String assignee = "王五";
		List<Task> list = taskService.createTaskQuery().taskAssignee(assignee).list();
		if(null!=list&&list.size()>0) {
			for (Task task : list) {
				System.out.println("任务ID:"+task.getId());
				System.out.println("流程实例ID:"+task.getProcessInstanceId());
				System.out.println("执行实例ID:"+task.getExecutionId());
				System.out.println("流程定义ID:"+task.getProcessDefinitionId());
				System.out.println("任务名称:"+task.getName());
				System.out.println("任务办理人:"+task.getAssignee());
				System.out.println("################################");
			}
		}
	}
	
	/**
	 * 4、办理任务
	 */
	@Test
	public void completeTask() {
		TaskService taskService = this.processEngine.getTaskService();
		String taskId="7502";
		taskService.complete(taskId);
		System.out.println("任务完成");
	}

	/**
	 * 5、历史任务查询
	 * 涉及的表：
	 * 		act_hi_taskinst,历史任务实例信息
	 * 		act_hi_procinst,历史流程实例信息
	 * 		act_hi_actinst,历史节点信息
	 * 		act_hi_identitylink,历史流程人员表
	 * 		act_hi_varinst,历史变量信息
	 */
	@Test
	public void queryTaskHistory(){

		HistoryService historyService = this.processEngine.getHistoryService();

		/**
		 * 流程实例ID - 2501
		 */
		String processInstanceId = "2501";

		System.out.println("################################历史任务实例################################");

		/**
		 * 历史任务实例查询
		 */
		List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery()
				.processInstanceId(processInstanceId)
				.list();

		if (null != historicTaskInstances && historicTaskInstances.size() > 0){
			for (HistoricTaskInstance taskInstance : historicTaskInstances){
				System.out.println("任务ID:" + taskInstance.getId());
				System.out.println("任务名称:" + taskInstance.getName());
				System.out.println("执行实例ID:" + taskInstance.getExecutionId());
				System.out.println("流程实例ID:" + taskInstance.getProcessInstanceId());
				System.out.println("流程定义ID:" + taskInstance.getProcessDefinitionId());
				System.out.println("任务办理人:" + taskInstance.getAssignee());
				System.out.println("申请时间:" + taskInstance.getStartTime());
				System.out.println("审批时间:" + taskInstance.getEndTime());
				System.out.println("################################");
			}
		}

		System.out.println("################################历史流程实例################################");

		/**
		 * 历史流程实例查询
		 */
		List<HistoricProcessInstance> historicProcessInstances = historyService.createHistoricProcessInstanceQuery()
				.processInstanceId(processInstanceId)
				.list();

		if (null != historicProcessInstances && historicProcessInstances.size() > 0){
			for (HistoricProcessInstance historicProcessInstance : historicProcessInstances){
				System.out.println("执行实例ID:" + historicProcessInstance.getId());
				System.out.println("流程实例ID:" + processInstanceId);
				System.out.println("流程定义ID:" + historicProcessInstance.getProcessDefinitionId());
				System.out.println("流程定义Key:" + historicProcessInstance.getProcessDefinitionKey());
				System.out.println("流程定Name:" + historicProcessInstance.getProcessDefinitionName());
				System.out.println("流程部署ID:" + historicProcessInstance.getDeploymentId());
				System.out.println("流程开始时间:" + historicProcessInstance.getStartTime());
				System.out.println("审批完成时间:" + historicProcessInstance.getEndTime());
				System.out.println("################################");
			}
		}

	}
	
}
