/**
 *
 * This software is written by arcus(x) GmbH and subject 
 * to a contract between arcus(x) and its customer.
 *
 * This software stays property of arcus(x) unless differing
 * arrangements between arcus(x) and its customer apply.
 *
 * arcus(x) GmbH
 * Bergiusstrasse 27
 * D-22765 Hamburg, Germany
 *
 * Tel.: +49 (0)40.333 102 92 
 * Fax.: +49 (0)40.333 102 93 
 * http://www.arcusx.com
 * mailto:info@arcusx.com
 *
 */

package com.arcusx.chrono.tests;

import com.arcusx.chrono.*;

import junit.framework.*;

/**
 *
 * @author david
 * @version $Id$
 */
public class YearTestCase extends TestCase
{

	public void testLeapYear() throws Exception
	{
		Year aYear = new Year(2004);
		assertTrue(aYear.isLeapYear(aYear.getYearValue()));
	}

	public void testLeapYearSomeAmount() throws Exception
	{
		Year aYear = new Year(2004);
		Year anotherYear = new Year(2009);

		int i = aYear.amountOfLeapYears(aYear.getYearValue(), anotherYear.getYearValue());
		assertTrue(i == 2);
	}

	public void testLeapYearNoneAmount() throws Exception
	{
		Year aYear = new Year(2005);
		Year anotherYear = new Year(2006);

		int i = aYear.amountOfLeapYears(aYear.getYearValue(), anotherYear.getYearValue());
		assertTrue(i == 0);
	}

}
