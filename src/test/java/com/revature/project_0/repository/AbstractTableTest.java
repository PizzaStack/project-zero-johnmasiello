package com.revature.project_0.repository;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AbstractTableTest {
	private AbstractTable table;
	@Before
	public void createMockTable() {
		table = new MockAbstractTable();
	}
	
	@Test
	public void addThreeMappings() {
		assertTrue(table.addRecord(3, "Cherry"));
		assertTrue(table.addRecord(2, "Banana"));
		assertTrue(table.addRecord(1, "Apple"));
	}
	
	@Test
	public void addDuplicateKeyFails() {
		table.addRecord(1, "apple");
		assertFalse(table.addRecord(1, "gord"));
	}
	
	@Test
	public void removeNonExistantKeyFails() {
		table.addRecord(1, "apple");
		assertFalse(table.deleteRecord(2));
	}
	
	@Test
	public void removeExistingKeyPasses() {
		table.addRecord(1, "apple");
		assertTrue(table.deleteRecord(1));
	}
	
	@Test
	public void selectKeyGetsEqualValue() {
		table.addRecord(1, "apple");
		assertEquals("apple", table.selectRecord(1));
	}
	
	@Test
	public void selectMissingKeyReturnsNull() {
		assertEquals(null, table.selectRecord(1));
	}
	
	@Test
	public void testGenerateKey() {
		Long firstKey = (Long) table.generateNextPrimaryKey();
		assertEquals(null, firstKey);
		assertTrue(table.addRecord(1, "Apple"));
		Long nextKey = (Long) table.generateNextPrimaryKey();
		assertEquals(null, nextKey);
	}
}
