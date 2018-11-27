package com.cio.demo.calculator.test.main;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.calculator.demo.cio.calculator.main.CalculatorMain;

import static org.testng.Assert.assertEquals;


/**
 * @author Shraddha Zope-Ladhe
 *
 */
@Test
public class CalculatorMainTest {

	private CalculatorMain calcMain;

	@BeforeTest
	public void init() {
		calcMain = new CalculatorMain();
	}

	public void emptyStringReturnsZero() throws Exception {
		assertEquals(calcMain.calculate(""), 0);
	}

	public void singleValueIsReplied() throws Exception {
		assertEquals(calcMain.calculate("1"), 1);
	}

	public void twoNumbersCommaDelimitedReturnSum() throws Exception
	{
		assertEquals(calcMain.calculate("1,2"), 3);
	}
	
	public void twoNumbersNewLineDelimitedReturnSum() throws Exception
	{
		assertEquals(calcMain.calculate("1\n2"), 3);
	}
	
	public void threeNumbersReturnSum() throws Exception
	{
		assertEquals(calcMain.calculate("1,2,3"),6);
	}
	
	@Test(expectedExceptions=Exception.class)
	public void negativeInputReturnsException() throws Exception
	{
		calcMain.calculate("-1");
	}
	
	public void ignoresNumbersGreaterThan1000() throws Exception{
		assertEquals(calcMain.calculate("10,10,1001"), 20);
	}
}
