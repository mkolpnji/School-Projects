package project_20_3_simple;

import java.util.*;

public class CalculatorRunner
{	
	public static void main(String[] args)
	{
		List<Test> tests = new LinkedList<Test>(); // null for expected result means exception
		
		//testStrings.add("5 * ( 5 )");
		//expectedResults.add(25.0);
		
		// Set 1: simple order of operations
		tests.add(new Test("5 + 2 * 3", 11.0));
		tests.add(new Test("5 + 4 * 3", 17.0));
		tests.add(new Test("-1.2 / -1.2 + 4", 5.0));
		tests.add(new Test("2 ^ 3", 8.0));
		tests.add(new Test("2 ^ 3.1", 8.574187700290345));
		tests.add(new Test("22 / 7", 3.142857142857143));		
		
		// Set 2: divide by 0
		tests.add(new Test("5 / 0", Double.POSITIVE_INFINITY));
		
		// Set 3: invalid input
		tests.add(new Test("5/0", null));
		tests.add(new Test("22 / ", null));
		
		// Set 4: parentheses
		tests.add(new Test("( 5 + 4 ) * 3", 27.0));
		tests.add(new Test("2 + 6 * ( 4 - 2 )", 14.0));
		tests.add(new Test("( 36 - 6 ) / ( 12 + 3 )", 2.0));
		tests.add(new Test("2 * ( 13 - ( 1 + 6 ) )", 12.0));
		tests.add(new Test("15 / ( ( ( 4 + 1 ) * 3 ) - 15 )", Double.POSITIVE_INFINITY));
		
		System.out.println("Start of Tests");
		for(Test test : tests)
		{
			Double desiredResult = test.result;
			Double actualResult;

			try
			{
				actualResult = Calculator.calculate(test.input);
			}
			catch (IllegalArgumentException e)
			{
				actualResult = null;
			}
			
			if ((desiredResult == null && actualResult != null) || (desiredResult != null && !desiredResult.equals(actualResult)))
			{
				System.out.println("Failed test: " + test);
				System.out.println("Expt result: " + desiredResult);
				System.out.println("Actu result: " + actualResult);
				System.out.println("");
			}
			else
			{
				System.out.println("Passed test: " + test);
			}
		}
		System.out.println("End of Tests");
	}

}