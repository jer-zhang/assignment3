/* WORD LADDER AdjacencyList.java
 * EE422C Project 3 submission by
 * Jerry Zhang
 * jz9954
 * 15465
 * Slip days used: 0
 * Git URL: https://github.com/jer-zhang/assignment3.git
 * Spring 2018
 */

package assignment3;

import java.util.*;

public class AdjacencyList {
	
	private final static int WORD_SIZE = 5;
	public Map<String, LinkedList<String>> al = new HashMap<String, LinkedList<String>>();
	
	/**
	 * This is the default Adjacency List constructor
	 */
	public AdjacencyList() {
	}
	
	/**
	 * This Adjacency List constructor takes a dictionary and constructs an adjacency list out of all words
	 * @param dict Set of Strings that form dictionary
	 */
	public AdjacencyList(Set<String> dict) {
		for (String s1 : dict) {
			LinkedList<String> ll = new LinkedList<String>();
			al.put(s1, ll);
			for (String s2 : dict) {
				int charsEqual = 0;
				for (int i = 0; i < WORD_SIZE; i++) {
					if (s1.charAt(i) == s2.charAt(i)) {
						charsEqual++;
					}
				}
				if (charsEqual == WORD_SIZE-1) {
					ll.add(s2);
				}
			}
		}
	}
	
	/**
	 * This accessor returns the linked list connected to the s key
	 * @param s Input string
	 * @return Linked list of words adjacent to the input string
	 */
	public LinkedList<String> getLL(String s) {
		return al.get(s);
	}
}
