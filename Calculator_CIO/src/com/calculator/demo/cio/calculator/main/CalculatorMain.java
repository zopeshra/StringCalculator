package com.calculator.demo.cio.calculator.main;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.logging.Logger;

import com.calculator.demo.cio.calculator.Calculator;
import com.calculator.demo.cio.calculator.Invoker;
import com.calculator.demo.cio.impl.ArithmaticCalculatorCommand;

/**
 * @author Shraddha Zope-Ladhe
 *
 */

public class CalculatorMain {
	public static Logger logger = Logger.getLogger("CalculatorMain.class");

	private final String delimiter = ",|\n";

	public static void main(String[] args) {
		new CalculatorMain().start();
	}

	/*
	 * This is the starting point of the program. It receives input from the command
	 * line and process them further and sends to calculate function. At the end
	 * this method displays the calculated result.
	 */
	public void start() {
		Scanner scanner = new Scanner(System.in);
		logger.info("\n\t Please input expression to calculate::");

		String line = scanner.nextLine();
		List<String> postfixString = CalculatorMain.infixToPostfixConvert(line);
		Calculator calculator = Calculator.getInstance();
		calculator.setCurrent(new BigDecimal(0));

		calculate(calculator, postfixString);

		logger.info("Result is " + calculator.getCurrent());
	}

	/*
	 * This method keeps a stack to process postfix version of the input and execute
	 * the right command implementation. Currently this method supports for
	 * arithmetic command calculations only.
	 * 
	 * @param Cal
	 * 
	 * @param postFix
	 */
	public void calculate(Calculator cal, List<String> postFix) {

		Stack<BigDecimal> stack = new Stack<BigDecimal>();

		for (int i = 0; i < postFix.size(); i++) {

			String next = postFix.get(i);

			if (next.equals("+") || next.equals("-") || next.equals("*") || next.equals("/")) {
				ArithmaticCalculatorCommand cmd = new ArithmaticCalculatorCommand(next.charAt(0), stack.pop(),
						stack.pop(), cal);
				Invoker invoker = new Invoker();
				invoker.compute(cmd);
				stack.push(cal.getCurrent());
			} else if (false) {

			} else {
				stack.push(new BigDecimal(next.trim()));
			}
		}
	}

	/*
	 * This method convert the infix into postfix in order to proceed in the
	 * calculation.
	 * 
	 * @param input
	 */
	public static List<String> infixToPostfixConvert(String input) {

		int priority = 0;
		String postfixBuffer = "";
		Stack<Character> stack = new Stack<Character>();
		List<String> postfixArray = new ArrayList<String>();

		for (int i = 0; i < input.length(); i++) {
			char ch = input.charAt(i);
			if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {

				if (postfixBuffer.length() > 0) {
					postfixArray.add(postfixBuffer);
				}
				postfixBuffer = "";
				// check the precedence
				if (stack.size() <= 0)
					stack.push(ch);
				else {
					Character chTop = (Character) stack.peek();
					if (chTop == '*' || chTop == '/')
						priority = 1;
					else
						priority = 0;
					if (priority == 1) {
						if (ch == '+' || ch == '-') {
							postfixArray.add(String.valueOf(stack.pop()));
							i--;
						} else { // Same
							postfixArray.add(String.valueOf(stack.pop()));
							i--;
						}
					} else {
						if (ch == '+' || ch == '-') {
							postfixArray.add(String.valueOf(stack.pop()));
							stack.push(ch);
						} else
							stack.push(ch);
					}
				}
			} else {
				postfixBuffer += ch;
			}
		}
		postfixArray.add(postfixBuffer);
		int len = stack.size();
		for (int j = 0; j < len; j++)
			postfixArray.add(stack.pop().toString());

		return postfixArray;
	}
	
	public int calculate(String string) throws Exception {

		String[] inputNumbers = string.split(delimiter);
		if (isEmpty(string))
			return 0;
		if (string.length() == 1) {
			return stringToInt(string);
		} else
			return getSum(inputNumbers);
	}

	private int getSum(String[] inputNumbers) throws Exception {
		isNegative(inputNumbers);

		return calculateValues(inputNumbers);
	}

	private int calculateValues(String[] numbers) {
		int sum = 0;
		for (String i : numbers) {
			if (stringToInt(i) > 1000)
				continue;
			sum += stringToInt(i);
		}
		return sum;

	}

	private void isNegative(String[] inputNumbers) throws Exception {
		for (String i : inputNumbers) {
			if (stringToInt(i) < 0) {
				throw new Exception("Negative Input");
			}

		}

	}

	private boolean isEmpty(String input) {
		return input.isEmpty();
	}

	private int stringToInt(String string) {
		return Integer.parseInt(string);
	}

}
