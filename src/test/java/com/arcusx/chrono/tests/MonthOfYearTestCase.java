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

import com.arcusx.chrono.MonthOfYear;

/**
 * 
 * Created on 02.06.2004, 20:44:22.
 *
 * @author floyd
 * @version $Id$
 */
public class MonthOfYearTestCase extends TestCase
{
	/**
	 * This has been reported by Flo.
	 */
	public void testNegativeAdd() throws Exception
	{
		MonthOfYear.valueOf("DEC").add(-12);

	}
}