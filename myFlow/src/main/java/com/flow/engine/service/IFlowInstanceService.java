package com.flow.engine.service;

import java.util.List;

import com.flow.engine.entity.Business;
import com.flow.engine.entity.Condition;
import com.flow.engine.entity.Flow;
import com.flow.engine.entity.FlowInstance;
import com.flow.engine.entity.FlowInstanceAssist;
import com.flow.engine.entity.Link;
import com.flow.engine.entity.LinkHasCondition;
import com.flow.engine.entity.Process;
import com.flow.engine.entity.ProcessOperator;
import com.flow.engine.entity.User;

/**
 * 2014-5-1 确定初步接口定义
 * @author Administrator
 *
 */
public interface IFlowInstanceService {
	
	/**
	 * 创建一个流程实例，绑定其环节,必须绑定到少1个环节
	 * 1、取第一个link和第二个link保存流程实例，并返回其ID；
	 * 2、批量保存流程实例ID与流程ID与环节ID的对应关系,并返回其主键集合;
	 * 3、将第一个环节的操作情况保存进流程状态表，记录操作人，操作结果，操作时间等；
	 * 4、如果创建了子流程实例，则将你流程的下一个环节设定为0，并更新其父流程的所有状态信息为不显示，表示其开启子流程；
	 * @param links
	 * @param business
	 * @param user
	 * @param parentInstance
	 * @return
	 */
	List<FlowInstanceAssist> createFlowInstance(Flow flow, List<Link> links, Business business, User user, FlowInstance parentInstance);
	
	/**
	 * 为流程实例的每一个环节绑定其条件，可绑定一个或多个，也可不绑定传null
	 * 1、在返回的主键集合中循环绑定其每一个环节的条件关系；
	 * @param assist 流程实例和环节绑定的主键
	 * @param conditions
	 * @return
	 */
	boolean setFlowInstanceLinkCondition(FlowInstanceAssist assist, List<Condition> conditions);
	
	/**
	 * 驱动流程，依据条件
	 * @param operator 传入流程实例ID、用户ID、条件表达式集合
	 * @return
	 */
	boolean driveFlowInstance(ProcessOperator operator);
	
	/**
	 * 启动一条默认流程或者公用流程，或者已经自定义好的流程
	 * @param flow
	 * @param business
	 * @param user
	 * @return
	 */
	boolean startFlowInstance(Flow flow, Business business, User user);
	
	/**
	 * 终止流程
	 * 1、将流程实例中的下一个环节直接设定为-1，表示流程终止
	 * 2、将该流程实例的状态中插入一条表示该流程结束的状态信息
	 * @param instance 传入流程实例ID
	 * @param user
	 * @return
	 */
	boolean finishFlowInstance(FlowInstance instance, User user);
	
	/**
	 * 修改流程实例
	 * 1、不允许修改已经操作过的环节，及其环节对应的条件；
	 * 2、不允许修改绑定的业务和绑定的申请人；
	 * 3、默认流程不允许修改，自定义的公用流程只有特定人或者岗位或者部门可以修改；
	 * @param instance
	 * @param links
	 * @return
	 */
	List<FlowInstanceAssist> updateFlowInstance(FlowInstance instance, List<FlowInstanceAssist> links, User user);
	
	/**
	 * 修改流程实例中环节绑定条件的关系
	 * 1、多用于修改已经绑定的条件
	 * 2、集合中有ID的则修改原来的记录，没ID的则新增
	 * @param conditions 
	 * @return
	 */
	boolean updateFlowInstanceLinkCondition(List<LinkHasCondition> conditions);
	
	/**
	 * 我待办的信息
	 * 1、通过当前用户信息找出对应的全部流程实例，将其对应的最后一条状态信息的集合返回
	 * @param instance
	 * @return
	 */
	List<Process> myToDo(User user);
	
	/**
	 * 流经过我的流程
	 * 1、通过当前用户信息找出对应的全部流程实例，将其对应的最后一条状态信息的集合返回
	 * @param user
	 * @return
	 */
	List<Process> passMe(User user);
	
	/**
	 * 我创建的流程
	 * 1、通过当前用户信息找出对应的全部流程实例，将其对应的最后一条状态信息的集合返回
	 * @param user
	 * @return
	 */
	List<Process> iCreate(User user);
	
	/**
	 * 抄送我的
	 * 1、查看当前环节对应的审批人信息，只要审批通过则抄送相应人
	 * 2、通过当前用户信息找出对应的全部流程实例，将其对应的最后一条状态信息的集合返回
	 * @param user
	 * @return
	 */
	List<Process> ccMe(User user);
	
	/**
	 * 通过流程实例获取其对应的全部流程状态
	 * 1、若有子流程则将其按顺序放在主流程操作状态的后面
	 * @param instance
	 * @return
	 */
	List<Process> getProcesses(FlowInstance instance);

}
