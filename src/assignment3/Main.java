/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Git URL:
 * Fall 2017
 */


package assignment3;
import java.util.*;
import java.io.*;

public class Main {
	
	// static variables and constants only here.
	private static AdjacencyList al;
	private static Set<String> dict;
	private final static int WORD_SIZE = 5;
	
	public static void main(String[] args) throws Exception {
		
		Scanner kb;	// input Scanner for commands
		PrintStream ps;	// output file, for student testing and grading only
		// If arguments are specified, read/write from/to files instead of Std IO.
		if (args.length != 0) {
			kb = new Scanner(new File(args[0]));
			ps = new PrintStream(new File(args[1]));
			System.setOut(ps);			// redirect output to ps
		} else {
			kb = new Scanner(System.in);// default input from Stdin
			ps = System.out;			// default output to Stdout
		}
		initialize();
				
		while (true) {
			ArrayList<String> words = parse(kb);
			if (words.isEmpty()) {
				return;
			}
			
			ArrayList<String> ladder = getWordLadderDFS(words.get(0), words.get(1));
			if (ladder.size() != 2) {
				System.out.println("A " + ladder.size() + "-rung word ladder exists between " + words.get(0).toLowerCase() + " and " + words.get(1).toLowerCase() + ".");
			} else {
				System.out.println("No word ladder can be found between " + words.get(0).toLowerCase() + " and " + words.get(1).toLowerCase() + ".");
			}
			printLadder(ladder);
		}
		
	}
	
	/**
	 * This method initializes the dictionary and adjacency list
	 */
	public static void initialize() {
		dict = makeDictionary();
		al = new AdjacencyList(dict);
	}
	
	/**
	 * @param keyboard Scanner connected to System.in
	 * @return ArrayList of Strings containing start word and end word. 
	 * If command is /quit, return empty ArrayList. 
	 */
	public static ArrayList<String> parse(Scanner keyboard) {
		ArrayList<String> words = new ArrayList<>();
		System.out.println("Enter starting and ending words: ");
		String word = keyboard.next();
		if (word != "/quit") {
			words.add(word.toUpperCase());
			words.add(keyboard.next().toUpperCase());
		}
		return words;
	}
	
	/**
	 * This method does a DFS to find a word ladder between start and end, if
	 * start and end appear in the dictionary. Otherwise, it will return a two
	 * element word ladder containing just start and end
	 * @param start Starting string
	 * @param end Ending string
	 * @return Word ladder
	 */
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		
		// Returned list should be ordered start to end.  Include start and end.
		// If ladder is empty, return list with just start and end.
		// TODO some code
		// TODO more code
		
		ArrayList<String> ladder = new ArrayList<String>();
		if (dict.contains(start) && dict.contains(end)) {
			Set<String> visited = new HashSet<String>();
			ladder.add(start);
			visited.add(start);
			if (!DFS(start, end, ladder, visited)) {
				ladder.add(end);
			}
		} else {
			ladder.add(start);
			ladder.add(end);
		}
		return ladder;
	}
	
	/**
	 * This recursive method will do a DFS to find a word ladder between cur and end
	 * @param cur Current string
	 * @param end End string
	 * @param ladder Word ladder
	 * @param visited Set of words visited
	 * @return True if word ladder was found, false otherwise
	 */
	private static boolean DFS(String cur, String end, ArrayList<String> ladder, Set<String> visited) {
		if (cur.equals(end)) {
			return true;
		}
		visited.add(cur);
		
		// Takes adjacency list and sorts by letters matching end string
		SortList sortList = new SortList();
		for (String s : al.getLL(cur)) {
			sortList.add(s, lettersShared(s, end));
		}
		sortList.sort();
		ArrayList<String> sortedWords = sortList.getStrings();
		
		for (String s : sortedWords) {
			if (!visited.contains(s)) {
				ladder.add(s);
				if (DFS(s, end, ladder, visited)) {
					return true;
				}
				ladder.remove(ladder.size()-1);
			}
		}
		return false;
	}
	
	/**
	 * This method compares two strings of the same size and returns the number shared letters in the same index
	 * @param s1 First string
	 * @param s2 Second string
	 * @return Number of letters shared
	 */
	private static int lettersShared(String s1, String s2) {
		int lettersShared = 0;
		for (int i = 0; i < WORD_SIZE; i++) {
			if (s1.charAt(i) == s2.charAt(i)) {
				lettersShared++;
			}
		}
		return lettersShared;
	}
	
    public static ArrayList<String> getWordLadderBFS(String start, String end) {

		ArrayList<String> ladder = new ArrayList<String>();
		Queue<String> bfs = new LinkedList<String>();
		Set<String> visited = new HashSet<String>();
		
		
    	
		
		return null; // replace this line later with real return
	}
    
	/**
	 * This method prints the word ladder to the console
	 * @param ladder
	 */
	public static void printLadder(ArrayList<String> ladder) {
		for (String s : ladder) {
			System.out.println(s.toLowerCase());
		}
	}
	// TODO
	// Other private static methods here

	/* Do not modify makeDictionary */
	public static Set<String>  makeDictionary () {
		Set<String> words = new HashSet<String>();
		Scanner infile = null;
		try {
			infile = new Scanner (new File("five_letter_words.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Dictionary File not Found!");
			e.printStackTrace();
			System.exit(1);
		}
		while (infile.hasNext()) {
			words.add(infile.next().toUpperCase());
		}
		return words;
	}
}
