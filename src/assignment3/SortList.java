package assignment3;

import java.util.*;

public class SortList {
	private ArrayList<String> strings = new ArrayList<String>();
	private ArrayList<Integer> keys = new ArrayList<Integer>();
	
	public SortList() {
	}
	
	public void add(String s, int i) {
		strings.add(s);
		keys.add(i);
	}
	
	public void sort() {
		for (int i = 1; i < keys.size(); i++) {
			int j = i-1;
			int curKey = keys.get(i);
			String curString = strings.get(i);
			while ((j >= 0) && (curKey > keys.get(j))) {
				keys.set(j+1, keys.get(j));
				strings.set(j+1, strings.get(j));
				j--;
			}
			keys.set(j+1, curKey);
			strings.set(j+1, curString);
		}
	}
	
	public ArrayList<String> getStrings() {
		return strings;
	}
}
