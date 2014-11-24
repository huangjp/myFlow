package com.flow.common.test;

import java.util.List;

import cock.util.FileUtil;

import com.flow.common.util.debug.DebugUtil;

public class GenerateProperties {
	public static void main(String[] args) {
		String entityPath = "E:\\Workspaces\\MyEclipse Professional\\myFlow_1\\src\\org\\flow\\com\\engine\\entity\\";
		String controlPath = "E:\\Workspaces\\MyEclipse Professional\\myFlow_1\\src\\org\\flow\\com\\engine\\web\\";
		String path = "src/org/flow/com/config/flow";//文件生成路径
		List<String> entities = FileUtil.scanFilePaths(entityPath);
		List<String> controllers = FileUtil.scanFilePaths(controlPath);
		StringBuilder sb = new StringBuilder();
		for (String con : controllers) {
			if(con.contains(".java")) {
				for(String en : entities) {
					if(en.contains(".java")) {
						String s = con.split("src")[1];
						String control = s.substring(1, s.indexOf(".")).replace("\\", ".");
						String classCon = control.substring(control.lastIndexOf("."));
						String entity = en.substring(en.lastIndexOf("\\") + 1, en.lastIndexOf("."));
						if(classCon.contains(entity.replace("entity", ""))) sb.append(entity + "=" + control);
					}
				}
			}
		}
		DebugUtil.generateProperties(path, sb.toString());
	}
}
