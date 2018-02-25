package assignment3;

import java.util.*;

public class SortList {
	private ArrayList<String> strings = new ArrayList<String>();
	private ArrayList<Integer> sharedLetters = new ArrayList<Integer>();
	
	public SortList() {
	}
	
	public void add(String s, int i) {
		strings.add(s);
		sharedLetters.add(i);
	}
	
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
	}
	
	public ArrayList<String> getStrings() {
		return strings;
	}
}
