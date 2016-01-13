package solitaire;

import java.io.IOException;
import java.util.Scanner;
import java.util.Random;
import java.util.NoSuchElementException;

/**
 * This class implements a simplified version of Bruce Schneier's Solitaire Encryption algorithm.
 * 
 * @author RU NB CS112
 */
public class Solitaire {
	
	/**
	 * Circular linked list that is the deck of cards for encryption
	 */
	CardNode deckRear;
	
	/**
	 * Makes a shuffled deck of cards for encryption. The deck is stored in a circular
	 * linked list, whose last node is pointed to by the field deckRear
	 */
	public void makeDeck() {
		// start with an array of 1..28 for easy shuffling
		int[] cardValues = new int[28];
		// assign values from 1 to 28
		for (int i=0; i < cardValues.length; i++) {
			cardValues[i] = i+1;
		}
		
		// shuffle the cards
		Random randgen = new Random();
 	        for (int i = 0; i < cardValues.length; i++) {
	            int other = randgen.nextInt(28);
	            int temp = cardValues[i];
	            cardValues[i] = cardValues[other];
	            cardValues[other] = temp;
	        }
	     
	    // create a circular linked list from this deck and make deckRear point to its last node
	    CardNode cn = new CardNode();
	    cn.cardValue = cardValues[0];
	    cn.next = cn;
	    deckRear = cn;
	    for (int i=1; i < cardValues.length; i++) {
	    	cn = new CardNode();
	    	cn.cardValue = cardValues[i];
	    	cn.next = deckRear.next;
	    	deckRear.next = cn;
	    	deckRear = cn;
	    }
	}
	
	/**
	 * Makes a circular linked list deck out of values read from scanner.
	 */
	public void makeDeck(Scanner scanner) 
	throws IOException {
		CardNode cn = null;
		if (scanner.hasNextInt()) {
			cn = new CardNode();
		    cn.cardValue = scanner.nextInt();
		    cn.next = cn;
		    deckRear = cn;
		}
		while (scanner.hasNextInt()) {
			cn = new CardNode();
	    	cn.cardValue = scanner.nextInt();
	    	cn.next = deckRear.next;
	    	deckRear.next = cn;
	    	deckRear = cn;
		}
	}
	
	/**
	 * Implements Step 1 - Joker A - on the deck.
	 */
	void jokerA() {
		
		CardNode search = deckRear;
		while (search.next.cardValue != 27){
			search = search.next;
		}
		CardNode temp = search.next; //joker
		search.next = search.next.next;
		temp.next = search.next.next;
		search.next.next = temp;
		if (deckRear.cardValue == 27){
			deckRear = search.next;
			return;
		}
		if (deckRear.cardValue == search.next.cardValue)
			deckRear = temp;
	}
	
	/**
	 * Implements Step 2 - Joker B - on the deck.
	 */
	void jokerB() {
		CardNode search = deckRear;
		while (search.next.cardValue != 28){
			search = search.next;
		}
		CardNode temp = search.next;  //joker
		search.next = search.next.next;
		temp.next = search.next.next.next;
		search.next.next.next = temp;
		if (deckRear.cardValue == 28){
			deckRear = search.next;
			return;
		}
		if (deckRear.cardValue == search.next.cardValue){
			deckRear = search.next.next;
			return;
		}
		if (deckRear.cardValue == search.next.next.cardValue)
			deckRear = temp;
		
	}
	
	/**
	 * Implements Step 3 - Triple Cut - on the deck.
	 */
	void tripleCut() {
		CardNode search = deckRear;
		CardNode newRear = null;
		CardNode jokerOne = null;
		CardNode jokerTwo = null;
		while (search.next.cardValue != deckRear.cardValue){
			if (search.next.cardValue == 27 || search.next.cardValue == 28)
				if (jokerOne == null){
					jokerOne = search.next;
					newRear = search;
				}
				else
					jokerTwo = search.next;
			search = search.next;
		}
		if (jokerTwo == null)
			jokerTwo = deckRear;
		if (deckRear.next == jokerOne){
			deckRear = jokerTwo;
			return;
		}
		if (deckRear == jokerTwo){
			deckRear = newRear;
			return;
		}
		CardNode temp = jokerTwo.next;
		jokerTwo.next = deckRear.next;
		newRear.next = temp;
		deckRear.next = jokerOne;
		deckRear = newRear;
	}
	
	/**
	 * Implements Step 4 - Count Cut - on the deck.
	 */
	void countCut() {		
		int count = deckRear.cardValue;
		if (count == 28 || count == 27)
			return;
		CardNode search = deckRear;
		CardNode cutNode = null;
		while(search.next != deckRear){
			search = search.next;
			count--;
			if (count == 0)
				cutNode = search;
		}
		search.next = deckRear.next;
		CardNode newFirst = cutNode.next;
		deckRear.next = newFirst;
		cutNode.next = deckRear;
	}
	
	/**
	 * Gets a key. Calls the four steps - Joker A, Joker B, Triple Cut, Count Cut, then
	 * counts down based on the value of the first card and extracts the next card value 
	 * as key. But if that value is 27 or 28, repeats the whole process (Joker A through Count Cut)
	 * on the latest (current) deck, until a value less than or equal to 26 is found, which is then returned.
	 * 
	 * @return Key between 1 and 26
	 */
	int getKey() {
		int key = 28;
		while(key > 26){
			jokerA();
			jokerB();
			tripleCut();
			countCut();
			int count = deckRear.next.cardValue;
			if (count == 28)
				count = 27;
			CardNode search = deckRear;
			for (int i = 0; i < count; i++)
				search = search.next;
			key = search.next.cardValue;
		}
	    return key;
	}
	
	/**
	 * Utility method that prints a circular linked list, given its rear pointer
	 * 
	 * @param rear Rear pointer
	 */
	private static void printList(CardNode rear) {
		if (rear == null) { 
			return;
		}
		System.out.print(rear.next.cardValue);
		CardNode ptr = rear.next;
		do {
			ptr = ptr.next;
			System.out.print("," + ptr.cardValue);
		} while (ptr != rear);
		System.out.println("\n");
	}

	/**
	 * Encrypts a message, ignores all characters except upper case letters
	 * 
	 * @param message Message to be encrypted
	 * @return Encrypted message, a sequence of upper case letters only
	 */
	public String encrypt(String message) {	
		String newMessage = "";
		for (int i = 0; i < message.length(); i++)
			if (Character.isLetter(message.charAt(i)))
				newMessage += Character.toUpperCase(message.charAt(i));
		int[] keystream = new int[newMessage.length()];
		for (int i = 0; i < keystream.length; i++)
			keystream[i] = getKey();
		
		int[] encryptMessage = new int[newMessage.length()];
		for (int i = 0; i < encryptMessage.length; i++){
			encryptMessage[i] = keystream[i] + ((int)newMessage.charAt(i) - 64);
			if (encryptMessage[i] > 26)
				encryptMessage[i] -= 26;
		}
		String enMessage = "";
		for (int i = 0; i < encryptMessage.length; i++)
			enMessage += (char)(encryptMessage[i] + 64);
	    return enMessage;
	}
	
	/**
	 * Decrypts a message, which consists of upper case letters only
	 * 
	 * @param message Message to be decrypted
	 * @return Decrypted message, a sequence of upper case letters only
	 */
	public String decrypt(String message) {	
		int[] keystream = new int[message.length()];
		for (int i = 0; i < keystream.length; i++)
			keystream[i] = getKey();
		
		int[] decryptMessage = new int[message.length()];
		for (int i = 0; i < decryptMessage.length; i++){
			
			decryptMessage[i] = ((int)message.charAt(i) - 64) - keystream[i];
			if (((int)message.charAt(i) - 64) <= keystream[i])
				decryptMessage[i] += 26;
		}
		String deMessage = "";
		for (int i = 0; i < decryptMessage.length; i++)
			deMessage += (char)(decryptMessage[i] + 64);
	    return deMessage;
	}
}
