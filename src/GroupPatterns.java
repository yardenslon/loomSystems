import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;

public class GroupPatterns {
	public static Map<ArrayList<Integer>, ArrayList<Integer>> map;
	public static ArrayList<String[]> lst;
	
	
	/* Reads the input file line by line. Each line is saved as an array of words.
	 * Returns an ArrayList of lines.
	 */
	public static ArrayList<String[]> readFileToArray(String fname){
		BufferedReader reader;
		ArrayList<String[]> lst = new ArrayList<>();
		try {
			reader = new BufferedReader(new FileReader(fname));
			String line = reader.readLine();
			while (line != null) {
				String[] splitLine = line.split(" ");
				lst.add(splitLine);
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lst;
	}
	
	
	/* Puts into the map: 
	 * key: {i, hashVal} where i is the index of the changing word
	 * value: array of index of lines
	 */
	public static void putInMap(int hashVal, int i, int idx) {
		ArrayList<Integer> key = new ArrayList<>();
		key.add(i);
		key.add(hashVal);
		
		if(map.containsKey(key)) {
			map.get(key).add(idx);
		} else {
			ArrayList<Integer> arr = new ArrayList<>();
			arr.add(idx);
			map.put(key, arr);
		}
	}
	
	
	/* Calculate the hash for the current line.
	 * For the line calculate the hashCode of all the words in the line except for the "changed" word
	 */
	public static void calculateHashLine(int idx) {
		String tmp = null;
		int retVal;
		String[] line = lst.get(idx);
		//The first two words in each line are time and date, hence should not be considered in the hash calculation
		for(int i=2; i<line.length; i++) {
			tmp = line[i];
			// Ignore the i word
			line[i] = "";
			retVal = Arrays.deepHashCode(Arrays.copyOfRange(line, 2, line.length));
			line[i] = tmp;
			//Enter the calculated hash into the map
			putInMap(retVal, i, idx);
		}
	}
	
	
	/* Iterate over all lines in the file and calculate the hash for each line */
	public static void calculateHashFile() {
		
		for(int i=0; i<lst.size(); i++) {
			calculateHashLine(i);
		}
	}
	
	/*Iterate over the map, and for every entry that has more than one value print the values*/
	public static void printToFile() {
		
		try {
			FileWriter fileWriter = new FileWriter(new File("output.txt"));
	        PrintWriter printWriter = new PrintWriter(fileWriter);
	        
			// Iterate over the map
	        for (Map.Entry<ArrayList<Integer>, ArrayList<Integer>> entry : map.entrySet()) {
			    ArrayList<Integer> key = entry.getKey();
			    ArrayList<Integer> value = entry.getValue();
			    
			    // There is a group of similar sentences
			    if(value.size() > 1) {
			    	StringBuilder s = new StringBuilder("The changing word was: ");
			    	for(int i=0; i<value.size(); i++) {
			    		String[] line = lst.get(value.get(i));
			    		printWriter.print(String.join(" ", line));
			    		printWriter.print("\n");
			    		s.append(lst.get(value.get(i))[key.get(0)]);
			    		if(i != value.size() - 1) {
			    			s.append(", ");
			    		}
			    	}
			    	printWriter.print(s.toString());
			    	printWriter.print("\n\n");
			    }	
		    }
			printWriter.close();
		} catch (IOException e) {
            e.printStackTrace();
        }
		
	}
		
	public static void main(String[] args) {
		//accept file name through command line args 
        String fname =args[0]; 
		
		map = new HashMap<>();
		lst = readFileToArray(fname);
		calculateHashFile();
		printToFile();
	}

}
