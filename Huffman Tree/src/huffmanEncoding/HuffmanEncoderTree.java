package huffmanEncoding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.PriorityQueue;

public class HuffmanEncoderTree {
	
	private HashMap<Character, Integer> freqTable;	
	private FrequencyTreeNode root;
	private HashMap<Character, String> huffmanCodes;
	
	public HuffmanEncoderTree(String file) throws FileNotFoundException{
		freqTable = new HashMap<Character, Integer>();
		File f = new File(file);
		Scanner s = new Scanner(f);
		while(s.hasNextLine()){
			String line = s.nextLine();
			for (int i = 0; i < line.length(); i++){
				if (freqTable.get(line.charAt(i)) != null)
					freqTable.put(line.charAt(i), freqTable.get(line.charAt(i)) + 1);
				else
					freqTable.put(line.charAt(i), 1);
			}
		}
		s.close();
		
		PriorityQueue<FrequencyTreeNode> queue = new PriorityQueue<FrequencyTreeNode>(freqTable.size());
		Iterator<Map.Entry<Character, Integer>> it = freqTable.entrySet().iterator();
		while (it.hasNext()){
			Map.Entry<Character, Integer> entry = it.next();
			queue.offer(new FrequencyTreeNode(entry.getKey(), entry.getValue()));
		}
		
		while(queue.size() >= 2){
			FrequencyTreeNode one = queue.poll();
			FrequencyTreeNode two = queue.poll();
			
			FrequencyTreeNode newNode = new FrequencyTreeNode('*', one.frequency + two.frequency);
			newNode.left = two;
			newNode.right = one;
			queue.offer(newNode);
		}
		
		root = queue.poll();
		
		huffmanCodes = new HashMap<Character, String>();
		makeHuffmanCode(root, "");
		
		PrintWriter encodeScheme = new PrintWriter(f.getParentFile() + "/encoded scheme.txt");
		
		Iterator<Map.Entry<Character, String>> it2 = huffmanCodes.entrySet().iterator();
		while (it2.hasNext()){
			Map.Entry<Character, String> entry = it2.next();
			encodeScheme.println(entry.getKey() + "" + entry.getValue());
		}
		encodeScheme.flush();
		
		PrintWriter encode = new PrintWriter(f.getParentFile() + "/encoded message.txt");
		Scanner s2 = new Scanner(f);
		while(s2.hasNextLine()){
			String line = s2.nextLine();
			
			for (int i = 0; i < line.length(); i++){
				encode.print(huffmanCodes.get(line.charAt(i)));
			}
			encode.println();
		}
		s2.close();
		encode.flush();
	}
	
	public void makeHuffmanCode(FrequencyTreeNode currNode, String currCode){
		if (currNode.letter != '*'){
			huffmanCodes.put(currNode.letter, currCode);
			return;
		}
		makeHuffmanCode(currNode.left, currCode + "0");
		makeHuffmanCode(currNode.right, currCode + "1");
	}
	
	public String returnFreqTable(){
		Iterator<Map.Entry<Character, Integer>> it = freqTable.entrySet().iterator();
		String freqTableString = "";
		while (it.hasNext()){
			Map.Entry<Character, Integer> entry = it.next();
			freqTableString += entry.getKey() + " " + entry.getValue() + "\n";
		}
		return freqTableString;
	}
	
	public String returnCodeTable(){
		Iterator<Map.Entry<Character, String>> it = huffmanCodes.entrySet().iterator();
		String freqTableString = "";
		while (it.hasNext()){
			Map.Entry<Character, String> entry = it.next();
			freqTableString += entry.getKey() + "" + entry.getValue() + "\n";
		}
		return freqTableString;
	}
	
	
}
