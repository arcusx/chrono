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
 * http://www.arcusx.com
 * mailto:info@arcusx.com
 */

package com.arcusx.chrono.tests;

import com.arcusx.chrono.*;

import junit.framework.*;

/**
 * 
 * 
 * Created on 25.02.2004
 * 
 * @author conni
 * @version $Id$
 * 
 */
public class MonthTestCase extends TestCase
{
	public void testCompareTo() throws Exception
	{
		Month one = new Month(2000, 0);
		Month other = new Month(2000, 1);
		assertEquals("Month must be before", -1, one.compareTo(other) );
		assertEquals("Month must be after", 1, other.compareTo(one) );
		assertEquals("Months must be equal", 0, one.compareTo(one) );
		assertEquals("Months must be after", 0, other.compareTo(other) );

	}
}
