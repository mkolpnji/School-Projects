package project_20_3_simple;

import java.util.HashMap;
import java.util.Stack;

public class Calculator {

	private static Stack<Double> numbers;
	private static Stack<String> operators;
	private static HashMap<String, Integer> operatorPriority;
	
	
	
	public static Double calculate(String input){
		if (operatorPriority == null){
			operatorPriority = new HashMap<String,Integer>();
			operatorPriority.put("+", 1);
			operatorPriority.put("-", 1);
			operatorPriority.put("*", 2);
			operatorPriority.put("/", 2);
			operatorPriority.put("^", 3);
			operatorPriority.put("(", 0);
		}
		
		numbers = new Stack<Double>();
		operators = new Stack<String>();
		
		int currentIndex = 0;
		while (currentIndex < input.length()){
			int endIndex;
			for (endIndex = currentIndex; endIndex < input.length() && input.charAt(endIndex) != ' '; endIndex++){
			}
			String character = input.substring(currentIndex, endIndex);
			
			try {
				double number = Double.parseDouble(character);
				numbers.push(number);
			}
			catch(NumberFormatException e){
				if (character.length() > 1)
					return null;
				else if (character.equals(")")){
					while (!operators.peek().equals("(")){
						operateTop();
						if (numbers.peek() == Double.POSITIVE_INFINITY)
							return numbers.peek();
					}
					operators.pop();
				}
				else if(character.equals("(")){
					operators.push(character);
				}
				else {
					while(!operators.empty() && operatorPriority.get(character) < operatorPriority.get(operators.peek())){
						operateTop();
						if (numbers.peek() == Double.POSITIVE_INFINITY)
							return numbers.peek();
					}
				
					operators.push(character);
				}
			}
			currentIndex = endIndex + 1;
		}
		
		if (operators.size() != numbers.size() - 1)
			return null;
		
		while(!operators.empty()){
			operateTop();
			if (numbers.peek() == Double.POSITIVE_INFINITY)
				return numbers.peek();
		}
		return numbers.peek();
	}

	private static void operateTop(){
		double numTwo = numbers.pop();
		double numOne = numbers.pop();
		String operator = operators.pop();
		
		switch(operator){
			case "+":
				numbers.push(numOne + numTwo);
				break;
			case "-":
				numbers.push(numOne - numTwo);
				break;
			case "*":
				numbers.push(numOne * numTwo);
				break;
			case "/":
				if (numTwo != 0)
					numbers.push(numOne / numTwo);
				else
					numbers.push(Double.POSITIVE_INFINITY);
				break;
			case "^":
				numbers.push(Math.pow(numOne, numTwo));
				break;
		}
	}
}
