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

import com.arcusx.chrono.GregorianUtils;

/**
 *
 * @author conni
 * @version $Id$
 */
public class GregorianUtilsTestCase extends TestCase
{
	public void testCheckValidGregorianDateOk()
	{
		GregorianUtils.checkValidGregorianDate(1977, 1, 1);
	}

	public void testCheckValidGregorianDateMin()
	{
		GregorianUtils.checkValidGregorianDate(GregorianUtils.MIN_GREGORIAN_YEAR,
				GregorianUtils.MIN_GREGORIAN_MOTNH1,
				GregorianUtils.MIN_GREGORIAN_DAY);
	}

	public void testCheckValidGregorianDateYearBeforeMin()
	{
		try
		{
			GregorianUtils.checkValidGregorianDate(GregorianUtils.MIN_GREGORIAN_YEAR - 1,
					GregorianUtils.MIN_GREGORIAN_MOTNH1,
					GregorianUtils.MIN_GREGORIAN_DAY);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	public void testCheckValidGregorianDateMonthBeforeMin()
	{
		try
		{
			GregorianUtils.checkValidGregorianDate(GregorianUtils.MIN_GREGORIAN_YEAR,
					GregorianUtils.MIN_GREGORIAN_MOTNH1 - 1,
					GregorianUtils.MIN_GREGORIAN_DAY);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	public void testCheckValidGregorianDateDayBeforeMin()
	{
		try
		{
			GregorianUtils.checkValidGregorianDate(GregorianUtils.MIN_GREGORIAN_YEAR - 1,
					GregorianUtils.MIN_GREGORIAN_MOTNH1,
					GregorianUtils.MIN_GREGORIAN_DAY - 1);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}
}
