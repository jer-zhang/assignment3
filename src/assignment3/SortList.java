/* WORD LADDER SortList.java
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

public class SortList {
	private ArrayList<String> strings = new ArrayList<String>();
	private ArrayList<Integer> sharedLetters = new ArrayList<Integer>();
	private ArrayList<Integer> numAdjacent = new ArrayList<Integer>();
	
	/**
	 * This is the default constructor of SortList
	 */
	public SortList() {
	}
	
	/**
	 * This method adds elements to each of the ArrayLists in the SortList
	 * @param s String to be added
	 * @param shared Number of shared letters to be added
	 * @param adjacent Numbers of adjacent words to be added
	 */
	public void add(String s, int shared, int adjacent) {
		strings.add(s);
		sharedLetters.add(shared);
		numAdjacent.add(adjacent);
	}
	
	/**
	 * This method sorts all elements in the SortList, first by number of
	 * shared letters, then by number of words adjacent
	 */
	public void sort() {
		for (int i = 1; i < sharedLetters.size(); i++) {
			int j = i-1;
			int curKey = sharedLetters.get(i);
			String curString = strings.get(i);
			while ((j >= 0) && (curKey > sharedLetters.get(j))) {
				sharedLetters.set(j+1, sharedLetters.get(j));
				strings.set(j+1, strings.get(j));
				j--;
			}
			sharedLetters.set(j+1, curKey);
			strings.set(j+1, curString);
		}
		sortAdjacent();
	}
	
	/**
	 * This method takes a SortList, sorted by number of shared letters, then
	 * sorts it by number of words adjacent
	 */
	private void sortAdjacent() {
		if (sharedLetters.size() == 0) {
			return;
		}
		
		int numShared = sharedLetters.get(0);
		
		// Iterates over entire list
		for (int i = 0; i < strings.size(); i++) {
			int numSame = 0;
			
			// Checks to see how many words have the same number of shared letters
			while ((i + numSame < sharedLetters.size()) && (sharedLetters.get(i + numSame) == numShared)) {
				numSame++;
			}
			numSame--;
			
			// If more than one word has the same number of shared letters, this will
			// sort them based off of the number of adjacent words
			if (numSame > 1) {
				int upper = i + numSame;
				for (i++; i < upper; i++) {
					int j = i-1;
					int curKey = numAdjacent.get(i);
					String curString = strings.get(i);
					int curShared = sharedLetters.get(i);
					while ((j >= 0) && (curKey > numAdjacent.get(j))) {
						numAdjacent.set(j+1, numAdjacent.get(j));
						strings.set(j+1, strings.get(j));
						sharedLetters.set(j+1, sharedLetters.get(j));
						j--;
					}
					numAdjacent.set(j+1, curKey);
					strings.set(j+1, curString);
					sharedLetters.set(j+1, curShared);
				}
			}
		}
	}
	
	/**
	 * This method returns the sorted list of strings to traverse
	 * @return ArrayList of strings
	 */
	public ArrayList<String> getStrings() {
		return strings;
	}
}
