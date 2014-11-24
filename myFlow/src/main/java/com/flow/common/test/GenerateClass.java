package com.flow.common.test;

import util.mysql.MySQL;

public class GenerateClass {
	public static void main(String[] args) {
		String path = "E:/java/git/myFlow/myFlow/src/main/java/com/flow/business/entity/";
//		MySQL.batchToClassByHibernate("flow_1", path);
		MySQL.tableToClassByHibernate("flow_1", "MENU", path);
	}
}
