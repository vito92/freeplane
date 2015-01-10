package org.freeplane.core.ui.menubuilders;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

public class EntryTest {

	@Test
	public void equalEntriesWithChild() {
		Entry firstStructureWithEntry = new Entry();
		final Entry firstEntry = new Entry();
		firstStructureWithEntry.addChild(firstEntry);

		Entry otherStructureWithEntry = new Entry();
		final Entry otherEntry = new Entry();
		otherStructureWithEntry.addChild(otherEntry);
		
		assertThat(firstStructureWithEntry, equalTo(otherStructureWithEntry));
	}

	@Test
	public void equalEntriesWithDifferentBuilders() {
		Entry firstStructureWithEntry = new Entry();
		firstStructureWithEntry.setBuilders(Arrays.asList("builder"));

		Entry otherStructureWithEntry = new Entry();
		assertThat(firstStructureWithEntry, CoreMatchers.not(otherStructureWithEntry));
	}

	@Test
	public void knowsParent() {
		Entry structureWithEntry = new Entry();
		final Entry child = new Entry();
		structureWithEntry.addChild(child);
		
		assertThat(child.getParent(), equalTo(structureWithEntry));
	}


	@Test
	public void rootEntryPathIsSlashifiedName() {
		Entry entry = new Entry();
		entry.setName("name");
		assertThat(entry.getPath(), equalTo("/name"));
	}


	@Test
	public void childEntryPathIsSlashifiedNameAfteParentName() {
		Entry entry = new Entry();
		entry.setName("parent");
		Entry child = new Entry();
		child.setName("child");
		entry.addChild(child);
		assertThat(child.getPath(), equalTo("/parent/child"));
	}

	@Test
	public void test() {
		Entry firstStructureWithEntry = new Entry();
		final Entry firstEntry = new Entry();
		firstStructureWithEntry.addChild(firstEntry);
		Entry child = null;
		for(Entry childInLoop : firstStructureWithEntry.children())
			child = childInLoop;
		assertThat(firstEntry, equalTo(child));
	}
}