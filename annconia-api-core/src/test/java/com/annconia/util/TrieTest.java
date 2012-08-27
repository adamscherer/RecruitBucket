package com.annconia.util;

import org.apache.commons.collections.trie.PatriciaTrie;
import org.apache.commons.collections.trie.StringKeyAnalyzer;
import org.junit.Test;

public class TrieTest {
	
	
	@Test
	public void stripTagsNull() {
		PatriciaTrie<String, String> trie = new PatriciaTrie<String, String>(new StringKeyAnalyzer());
		trie.put("Sioux Falls".toUpperCase(), "Sioux Falls");
		trie.put("St. Louis".toUpperCase(), "St. Louis");
		trie.put("Chicago".toUpperCase(), "Chicago");
		trie.put("New York", "New York");
		trie.put("Los Angeles", "Los Angeles");
		trie.put("Washington", "Washington");
		
		System.out.println(trie.getPrefixedBy("S"));
		System.out.println(trie.getPrefixedBy("S"));
	}
}
