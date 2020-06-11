package com.ysdrzp.c_processdef;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;

/**
 * 管理流程定义、部署
 * @author ysdrzp
 */
public class TestProcessDef {

	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	/**
	 * 部署流程使用classpath
	 */
	@Test
	public void deployProcess01() {
		RepositoryService repositoryService = this.processEngine.getRepositoryService();
		Deployment deploy = repositoryService.createDeployment().name("请假流程001")
				.addClasspathResource("HelloWorld.bpmn")
				.addClasspathResource("HelloWorld.png").deploy();
		System.out.println("部署成功:流程部署ID：" + deploy.getId());
	}

	/**
	 * 部署流程使用zip 流程图的文件必须是xxxx.zip结尾
	 */
	@Test
	public void deployProcess02() {

		/**
		 * 如果不加'/'代表从当前包里面找文件，如果加'/'代表从classpath的根目录里面找文件
		 */
		InputStream inputStream = this.getClass().getResourceAsStream("/LeaveBill.zip");

		RepositoryService repositoryService = this.processEngine.getRepositoryService();

		Deployment deploy = repositoryService.createDeployment().name("报销流程001")
				.addZipInputStream(new ZipInputStream(inputStream))
				.deploy();
		// TODO: 2020/6/11 一直部署失败 
		System.out.println("部署成功,流程部署ID:" + deploy.getId());
	}

	/**
	 * 查询流程部署信息,查询表：act_re_deployment
	 */
	@Test
	public void queryProcessDeploy() {

		RepositoryService repositoryService = this.processEngine.getRepositoryService();
		// 创建部署信息的查询
		System.out.println("################################流程部署ID查询流程部署信息-返回单个对象################################");
		String deploymentId = "1";
		Deployment deploy = repositoryService.createDeploymentQuery()
				.deploymentId(deploymentId)
				.singleResult();
		System.out.println("部署ID:"+deploy.getId());
		System.out.println("部署名称:"+deploy.getName());
		System.out.println("部署时间:"+deploy.getDeploymentTime());

		System.out.println("################################流程部署ID查询流程部署信息-返回集合################################");
		List<Deployment> list = repositoryService.createDeploymentQuery()

		// 条件
		// .deploymentId(deploymentId) 				//根据部署ID去查询
		// .deploymentName(name)					//根据部署名称去查询
		// .deploymentTenantId(tenantId)			//根据tenantId去查询
		// .deploymentNameLike(nameLike)			//根据部署名称模糊查询
		// .deploymentTenantIdLike(tenantIdLike)	//根据tenantId模糊查询

		// 排序
		// .orderByDeploymentId().asc()  			//根据部署ID升序
		// .orderByDeploymenTime().desc() 			//根据部署时间降序
		// .orderByDeploymentName()					//根据部署名称升序

		// 结果集
		.list(); 									//查询返回list集合
		// .listPage(firstResult, maxResults) 		//分页查询返回list集合
		// .count();								//返回流程部署个数

		for (Deployment deployment : list) {
			System.out.println("部署ID:" + deployment.getId());
			System.out.println("部署名称:" + deployment.getName());
			System.out.println("部署时间:" + deployment.getDeploymentTime());
			System.out.println("########################");
		}
	}

	/**
	 * 查询流程定义信息,查询表：act_re_procdef
	 */
	@Test
	public void queryProcDef() {

		RepositoryService repositoryService = this.processEngine.getRepositoryService();

		System.out.println("################################流程定义ID查询流程定义信息-返回单个对象################################");

		String processDefinitionId = "HelloWorld:1:4";

		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
				.processDefinitionId(processDefinitionId)
				.singleResult();

		System.out.println("流程定义ID:" + processDefinition.getId());
		System.out.println("流程部署ID:" + processDefinition.getDeploymentId());
		System.out.println("流程定义KEY:" + processDefinition.getKey());
		System.out.println("流程定义的名称:" + processDefinition.getName());
		System.out.println("流程定义的bpmn文件名:" + processDefinition.getResourceName());
		System.out.println("流程图片名:" + processDefinition.getDiagramResourceName());
		System.out.println("流程定义的版本号:" + processDefinition.getVersion());
		System.out.println("##################");

		System.out.println("################################流程定义ID查询流程定义信息-返回集合################################");

		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()

		// 条件
//		.deploymentId(deploymentId) 											//根据部署ID查询
//		.deploymentIds(deploymentIds) 											//根据部署ID的集合查询Set<String> deploymentIds
//		.processDefinitionId(processDefinitionId)								//根据流程定义ID
//		.processDefinitionIds(processDefinitionIds)								//根据流程定义的IDS查询
//		.processDefinitionKey(processDefinitionKey)								//根据流程定义的的key查询
//		.processDefinitionKeyLike(processDefinitionKeyLike)						//根据流程定义的的key模糊查询
//		.processDefinitionName(processDefinitionName)							//根据流程定义的名称查询
//		.processDefinitionNameLike(processDefinitionNameLike)					//根据流程定义的名称模糊查询
//		.processDefinitionResourceName(resourceName)							//根据流程图的BPMN文件名查询
//		.processDefinitionResourceNameLike(resourceNameLike)					//根据流程图的BPMN文件名模糊查询
//		.processDefinitionVersion(processDefinitionVersion)						//根据流程定义的版本查询
//		.processDefinitionVersionGreaterThan(processDefinitionVersion)			//version>num
//		.processDefinitionVersionGreaterThanOrEquals(processDefinitionVersion)	//version>=num
//		.processDefinitionVersionLowerThan(processDefinitionVersion)			//version<num
//		.processDefinitionVersionLowerThanOrEquals(processDefinitionVersion)	//version<=num

		// 排序
//		.orderByDeploymentId()
//		.orderByProcessDefinitionId()
//		.orderByProcessDefinitionKey()
//		.orderByProcessDefinitionName()
//		.orderByProcessDefinitionVersion()

		// 结果集
		.list();
//		.listPage(firstResult, maxResults)
//		.count()

		if (null != list && list.size() > 0) {
			for (ProcessDefinition processDefinition1 : list) {
				System.out.println("流程定义ID:" + processDefinition1.getId());
				System.out.println("流程部署ID:" + processDefinition1.getDeploymentId());
				System.out.println("流程定义KEY:" + processDefinition1.getKey());
				System.out.println("流程定义的名称:" + processDefinition1.getName());
				System.out.println("流程定义的bpmn文件名:" + processDefinition1.getResourceName());
				System.out.println("流程图片名:" + processDefinition1.getDiagramResourceName());
				System.out.println("流程定义的版本号:" + processDefinition1.getVersion());
				System.out.println("##################");
			}
		}
	}

	/**
	 * 查询流程图,根据流程定义ID
	 */
	@Test
	public void viewProcessImg() {

		RepositoryService repositoryService = this.processEngine.getRepositoryService();

		/**
		 * 流程定义ID
		 */
		String processDefinitionId = "HelloWorld:1:4";
		InputStream inputStream = repositoryService.getProcessDiagram(processDefinitionId);

		File file = new File("d:/HelloWorld.png");
		try {
			BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
			int len = 0;
			byte[] b = new byte[1024];
			while ((len = inputStream.read(b)) != -1) {
				outputStream.write(b, 0, len);
				outputStream.flush();
			}
			outputStream.close();
			inputStream.close();
			System.out.println("查询成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询流程图,根据流程部署ID
	 */
	@Test
	public void viewProcessImg2() {

		RepositoryService repositoryService = this.processEngine.getRepositoryService();

		/**
		 * 流程部署ID
		 */
		String deploymentId = "1";

		/**
		 * 根据流程部署ID查询流程定义对象
		 */
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
				.deploymentId(deploymentId).singleResult();

		/**
		 * 从流程定义对象里面查询出流程定义ID
		 */
		String processDefinitionId = processDefinition.getId();
		System.out.println("流程定义ID:" + processDefinitionId);

		InputStream inputStream = repositoryService.getProcessDiagram(processDefinitionId);

		File file = new File("d:/" + processDefinition.getDiagramResourceName());
		try {
			BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
			int len = 0;
			byte[] b = new byte[1024];
			while ((len = inputStream.read(b)) != -1) {
				outputStream.write(b, 0, len);
				outputStream.flush();
			}
			outputStream.close();
			inputStream.close();
			System.out.println("查询成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询最新的流程定义
	 */
	@Test
	public void queryNewProcessDef() {

		/**
		 * 查询所有的流程定义根据版本号升序
		 */
		RepositoryService repositoryService = this.processEngine.getRepositoryService();

		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
				.orderByProcessDefinitionVersion()
				.asc()
				.list();

		Map<String, ProcessDefinition> map = new HashMap<>();
		if (null != list && list.size() > 0) {
			for (ProcessDefinition pd : list) {
				// 流程定义相同，覆盖流程定义信息
				map.put(pd.getKey(), pd);
			}
		}

		System.out.println("map:" + map.toString());

		System.out.println("################################查询所有的流程定义根据版本号升序################################");

		Collection<ProcessDefinition> values = map.values();
		for (ProcessDefinition processDefinition : values) {
			System.out.println("流程定义ID:" + processDefinition.getId());
			System.out.println("流程部署ID:" + processDefinition.getDeploymentId());
			System.out.println("流程定义KEY:" + processDefinition.getKey());
			System.out.println("流程定义的名称:" + processDefinition.getName());
			System.out.println("流程定义的bpmn文件名:" + processDefinition.getResourceName());
			System.out.println("流程图片名:" + processDefinition.getDiagramResourceName());
			System.out.println("流程定义的版本号:" + processDefinition.getVersion());
			System.out.println("##################");
		}
	}

	/**
	 * 启动流程：流程部署多次，以最新版本的流程定义版本启动流程
	 */
	@Test
	public void startProcess() {
		RuntimeService runtimeService = this.processEngine.getRuntimeService();
		String processDefinitionKey = "HelloWorld";
		runtimeService.startProcessInstanceByKey(processDefinitionKey);
		System.out.println("流程启动成功");
	}

	/**
	 * 删除流程定义
	 */
	@Test
	public void deleteProcessDef() {

		RepositoryService repositoryService = this.processEngine.getRepositoryService();
		String deploymentId = "12501";
		/**
		 * 根据流程部署id删除流程定义，如果当前id的流程正在执行，那么会报错
		 */
		// repositoryService.deleteDeployment(deploymentId);

		/**
		 * 根据流程部署id删除删除流程定义,act_re_procdef,流程部署信息也会删除,act_re_deployment
		 * 如果当前id的流程正在执行,会把正在执行的流程数据删除,act_ru_*和act_hi_*表里面的数据
		 */
		repositoryService.deleteDeployment(deploymentId, true);
		// repositoryService.deleteDeploymentCascade(deploymentId);==repositoryService.deleteDeployment(deploymentId, true);
		System.out.println("删除成功");
	}

	/**
	 * 附加功能：删除流程定义（删除key相同的所有不同版本的流程定义）
	 */
	@Test
	public void deleteAllSameVersion() {

		RepositoryService repositoryService = this.processEngine.getRepositoryService();

		/**
		 * 根据流程定义的key查询流程集合
		 */
		String processDefinitionKey = "HelloWorld";

		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
				.processDefinitionKey(processDefinitionKey)
				.list();

		if (null != list && list.size() > 0) {
			for (ProcessDefinition pd : list) {
				repositoryService.deleteDeployment(pd.getDeploymentId(), true);
			}
		}
	}

}
