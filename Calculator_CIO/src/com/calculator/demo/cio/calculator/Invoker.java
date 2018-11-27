/**
 * 
 */
package com.calculator.demo.cio.calculator;

import com.calculator.demo.cio.intrfc.Command;

/**
 * @author Admin
 *
 */
public class Invoker {

	public void compute(Command command) {
		command.calculate();
	}

}
