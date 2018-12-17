package com.revature.project_0.repository;

import java.util.TreeMap;

abstract class AbstractTable<K extends Comparable<K>, M > {
	private TreeMap<K, M> table = new TreeMap<>();
	
	boolean addRecord(K key, M record) {
		if (table.containsKey(key))
			return false;
		table.put(key, record);
		return true;
	}
	
	boolean deleteRecord(K key) {
		return table.remove(key) != null;
	}
	
	M selectRecord(K key) {
		return table.get(key);
	}

	TreeMap<K, M> getTable() {
		return table;
	}
}