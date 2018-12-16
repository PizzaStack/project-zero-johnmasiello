package com.revature.project_0.repository;

import java.util.TreeSet;

public abstract class AbstractTable<Model extends Comparable<Model>> {
	private TreeSet<Model> table = new TreeSet<>();
	
	boolean addNewRecord(Model record) {
		return table.add(record);
	}
	
	boolean deleteRecord(Model record) {
		return table.remove(record);
	}
}
