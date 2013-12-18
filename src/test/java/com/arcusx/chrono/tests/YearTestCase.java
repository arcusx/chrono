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

import junit.framework.TestCase;

import com.arcusx.chrono.Year;

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

	public void testLatestOfTwoYear()
	{
		// given
		Year aYear = new Year(2013);
		Year anotherYear = new Year(2014);

		// when
		Year latestYear = Year.latestOf(aYear, anotherYear);

		// then
		assertEquals(anotherYear, latestYear);
	}

	public void testLatestOfTwoYearWithNullIsOkay()
	{
		// given
		Year aYear = new Year(2013);
		Year anotherYear = new Year(2014);
		Year thirdYearNull = null;

		Year[] years = new Year[] {
				aYear,
				anotherYear,
				thirdYearNull};

		// when
		Year latestYear = Year.latestOf(years, true);

		// then
		assertEquals(anotherYear, latestYear);
	}

	public void testLatestOfWithNullThrowsException() throws Exception
	{
		// given
		Year aYear = new Year(2013);
		Year anotherYear = new Year(2014);
		Year thirdYearNull = null;

		// when
		try
		{
			Year.latestOf(aYear, anotherYear, thirdYearNull);

			// then
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// expected
		}
	}

}
