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
 * 
 * Created 02.10.2003, 17:16:13.
 * 
 * @author conni
 * @version $Id$
 */
public class DayTestCase extends TestCase
{

	public void testEquals() throws Exception
	{
		Day one = new Day(2000, 0, 1);
		Day otherEq = new Day(2000, 0, 1);
		assertEquals("Days must be equal", one, otherEq);

		Day otherNe = new Day(2000, 0, 2);
		assertFalse("Days must be not equal", one.equals(otherNe));
	}

	public void testBefore() throws Exception
	{
		Day one = new Day(2000, 0, 1);
		Day other = new Day(2000, 0, 2);
		assertTrue("Day must be before", one.before(other));
	}

	public void testBeforeOrEqual() throws Exception
	{
		Day one = new Day(2000, 0, 1);
		Day other = new Day(2000, 0, 2);
		assertTrue("Day must be before or equal", one.beforeOrEqual(other));
	}

	public void testCompareTo() throws Exception
	{
		Day one = new Day(2000, 0, 1);
		Day other = new Day(2000, 0, 2);
		assertEquals("Day must be before", -1, one.compareTo(other));
		assertEquals("Day must be after", 1, other.compareTo(one));
		assertEquals("Days must be equal", 0, one.compareTo(one));
		assertEquals("Days must be after", 0, other.compareTo(other));
	}

	/**
	 * Bug reported from Flo.
	 */
	public void testNewDay50th1st2004Fail() throws Exception
	{
		try
		{
			new Day(2004, 1, 50);
			fail("50.2.2004 should raise an error");
		}
		catch (IllegalArgumentException ex)
		{
			// all right
		}
	}
}