package com.ysdrzp.b_helloworld;

import java.util.List;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
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
	 * 得到流程引擎
	 */
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	@Test
	public void deployProcess() {
		/**
		 * 流程图的部署、修改、删除
		 * 涉及到的表：
		 * 		act_ge_bytearray,
		 * 		act_re_deployment,
		 * 		act_re_model,
		 * 		act_re_procdef
		 */
		RepositoryService repositoryService = this.processEngine.getRepositoryService();
		Deployment deploy = repositoryService.createDeployment().name("请假流程001")
				.addClasspathResource("HelloWorld.bpmn.xml.xml")
				.addClasspathResource("HelloWorld.png")
				.deploy();

		System.out.println("部署成功:流程部署ID："+deploy.getId());
	}

	/**
	 * 启动流程
	 */
	@Test
	public void startProcess() {
		/**
		 * 流程的运行
		 * 涉及的表：
		 * 		act_ru_event_subscr,
		 * 		act_ru_execution,
		 * 		act_ru_identitylink,
		 * 		act_ru_job,
		 * 		act_ru_task,
		 * 		act_ru_variable
		 */
		RuntimeService runtimeService = this.processEngine.getRuntimeService();
		String processDefinitionId="HelloActiviti:1:4";
//		runtimeService.startProcessInstanceById(processDefinitionId);

		String processDefinitionKey="HelloActiviti";
		runtimeService.startProcessInstanceByKey(processDefinitionKey);
		System.out.println("流程启动成功");
	}
	
	/**
	 * 查询任务
	 */
	@Test
	public void findTask() {
		TaskService taskService = this.processEngine.getTaskService();
		String assignee="王五";
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
	 * 办理任务
	 */
	@Test
	public void completeTask() {
		TaskService taskService = this.processEngine.getTaskService();
		String taskId="7502";
		taskService.complete(taskId);
		System.out.println("任务完成");
	}

	/**
	 * 历史任务查询
	 */
	@Test
	public void queryTaskHistory(){

	}
	
}
