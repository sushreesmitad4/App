/**
 * 
 */
package com.tarang.ewallet.walletui.validator;

import org.junit.Assert;
import org.junit.Test;

import com.tarang.ewallet.walletui.validator.common.Common;
import com.tarang.ewallet.walletui.validator.common.CommonValidator;


/**
 * @author  : prasadj
 * @date    : Oct 22, 2012
 * @time    : 8:46:20 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class CustomerRegistrationValidatorTest {

	@Test
	public void cutomerRegistrationDateValidTest1() {
		String value = "10/12/2012";
		boolean valid = CommonValidator.dateValidation("dd/MM/yyyy", value);
		Assert.assertEquals(true, valid);
	}

	@Test
	public void cutomerRegistrationDateNotValidTest1() {
		String value = "10/13/2012";
		boolean valid = CommonValidator.dateValidation("dd/MM/yyyy", value);
		Assert.assertEquals(false, valid);
	}

	@Test
	public void realNumberValidatorTest() {
		String[] values = {"0.01", "1.01", "100000000000.0", "1000000000", "1.1", "1.10",
				"1000d5", "d5", "a1.1", "1.0a", "1a.0", "1.a1"};
		Boolean[] rsts = {true, true, true, true, true, true,
				false, false, false, false, false, false }; 
		for(int i = 0; i < values.length; i++){
			boolean arst = CommonValidator.expressionPattern(Common.REAL_NUM_EXP, values[i]);
			Assert.assertEquals(rsts[i], arst);
		}
	}

}
