package com.flow.common.test;

import util.mysql.MySQL;

public class generateClass {
	public static void main(String[] args) {
		String path = "E:/Workspaces/MyEclipse Professional/myFlow_1/src/org/flow/com/business/entity/";
//		MySQL.batchToClassByHibernate("flow_1", path);
		MySQL.tableToClassByHibernate("flow_1", "LINK_has_CONDITION", path);
	}
}
