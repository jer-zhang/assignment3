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
	
	/* // Random object to generate random ints
	static Random rand = new Random();
	*/ // Random object to generate random ints
	
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
			
			/* // Generates random words from dictionary
			ArrayList<String> words = new ArrayList<String>();
			ArrayList<String> listDict = new ArrayList<String>(dict);
			words.add(listDict.get(rand.nextInt(listDict.size())));
			words.add(listDict.get(rand.nextInt(listDict.size())));
			*/ // Generates random words from dictionary
			
			ArrayList<String> ladder = getWordLadderDFS(words.get(0), words.get(1));
			printLadder(ladder);
			

			ArrayList<String> ladder2 = getWordLadderBFS(words.get(0), words.get(1));
			printLadder(ladder2);
			
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
			words.add(word.toLowerCase());
			words.add(keyboard.next().toLowerCase());
		}
		return words;
	}
	
	/**
	 * This method does a DFS to find a word ladder between start and end, if
	 * start and end appear in the dictionary. Otherwise, it will return a two
	 * element word ladder containing just start and end
	 * @param start Starting word
	 * @param end Ending word
	 * @return Word ladder
	 */
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		
		// Returned list should be ordered start to end.  Include start and end.
		// If ladder is empty, return list with just start and end.
		start = start.toUpperCase();
		end = end.toUpperCase();
		
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
			sortList.add(s, lettersShared(s, end), al.getLL(s).size());
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
	
	/**
	 * This method does a BFS to find a word ladder between start and end, if
	 * start and end appear in the dictionary. Otherwise, it will return a two
	 * element word ladder containing just start and end
	 * @param start Starting word
	 * @param end Ending word
	 * @return Word ladder
	 */
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
		start = start.toUpperCase();
		end = end.toUpperCase();
    	
		ArrayList<String> tempLadder = new ArrayList<String>();
		
		if (dict.contains(start) && dict.contains(end)) {
			Queue<ArrayList<String>> q = new LinkedList<ArrayList<String>>();
			Set<String> visited = new HashSet<String>();
			
			tempLadder.add(start);
			q.add(tempLadder);
			visited.add(start);
			
			// Performs the BFS
			while (!q.isEmpty()) {
				if (q.peek().get(q.peek().size() - 1).equals(end)) {
					tempLadder = q.peek();
					break;
				} else {
					for (String s : al.getLL(q.peek().get(q.peek().size()-1))) {
						if (!visited.contains(s)) {
							visited.add(s);
							tempLadder = new ArrayList<String>(q.peek());
							tempLadder.add(s);
							q.add(tempLadder);
						}
					}
					q.remove();
				}
			}
			
			// If the queue is empty, there is no word ladder
			if (q.isEmpty()) {
				tempLadder = new ArrayList<String>();
				tempLadder.add(start);
				tempLadder.add(end);
			}
			
		} else {
			tempLadder.add(start);
			tempLadder.add(end);
		}
		
		return tempLadder;
	}
    
	/**
	 * This method prints the word ladder to the console
	 * @param ladder The ladder to be printed
	 */
	public static void printLadder(ArrayList<String> ladder) {
		if (ladder.size() != 2) {
			System.out.println("A " + ladder.size() + "-rung word ladder exists between " + ladder.get(0).toLowerCase() + " and " + ladder.get(ladder.size()-1).toLowerCase() + ".");
			for (String s : ladder) {
				System.out.println(s.toLowerCase());
			}
		} else {
			System.out.println("No word ladder can be found between " + ladder.get(0).toLowerCase()+ " and " + ladder.get(ladder.size()-1).toLowerCase() + ".");
		}
	
	}

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
