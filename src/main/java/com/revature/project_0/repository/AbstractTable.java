package com.revature.project_0.repository;

import java.util.TreeMap;

public abstract class AbstractTable<K extends Comparable<K>, Model > {
	private TreeMap<K, Model> table = new TreeMap<>();
	
	boolean addRecord(K key, Model record) {
		if (table.containsKey(key))
			return false;
		table.put(key, record);
		return true;
	}
	
	boolean deleteRecord(K key) {
		return table.remove(key) != null;
	}
	
	Model selectRecord(K k) {
		return table.get(k);
	}
}
