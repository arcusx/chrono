/**
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
 * Created on 12.06.2004, 16:07:51.
 *
 * @author conni
 * @version $Id$
 */
public class SimpleMonthFormatTestCase extends TestCase
{

	public void testParseWithZero() throws Exception
	{
		Month month = SimpleMonthFormat.INSTANCE.parse("2003/03");
		assertEquals(new Month(2003, 2), month);
	}

	public void testParseNoZero() throws Exception
	{
		Month month = SimpleMonthFormat.INSTANCE.parse("2003/3");
		assertEquals(new Month(2003, 2), month);
	}

	public void testFormat() throws Exception
	{
		Month month = new Month(2003, 2);
		String monthStr = SimpleMonthFormat.INSTANCE.format(month);
		assertEquals("2003/3", monthStr);
	}
}