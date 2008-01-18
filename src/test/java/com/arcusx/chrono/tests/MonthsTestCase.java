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

import java.util.*;

import junit.framework.*;

import com.arcusx.chrono.*;

/**
 * 
 * Created on 22.02.2005, 16:51:44.
 *
 * @author conni
 * @version $Id$
 */
public class MonthsTestCase extends TestCase
{
	public void testAddMonthInOrder() throws Exception
	{
		Months seq = new Months();
		seq.addMonth(new Month(2004, MonthOfYear.JANUARY));
		assertEquals((Object) new MonthSequence(new Month(2004, MonthOfYear.JANUARY), 1),
				seq.getMonthsParts().iterator().next());
		seq.addMonth(new Month(2004, MonthOfYear.FEBRUARY));
		assertEquals((Object) new MonthSequence(new Month(2004, MonthOfYear.JANUARY), 2),
				seq.getMonthsParts().iterator().next());
		seq.addMonth(new Month(2004, MonthOfYear.MARCH));
		assertEquals((Object) new MonthSequence(new Month(2004, MonthOfYear.JANUARY), 3),
				seq.getMonthsParts().iterator().next());
		seq.addMonth(new Month(2004, MonthOfYear.APRIL));
		assertEquals((Object) new MonthSequence(new Month(2004, MonthOfYear.JANUARY), 4),
				seq.getMonthsParts().iterator().next());
		seq.addMonth(new Month(2004, MonthOfYear.MAY));
		assertEquals((Object) new MonthSequence(new Month(2004, MonthOfYear.JANUARY), 5),
				seq.getMonthsParts().iterator().next());
		seq.addMonth(new Month(2004, MonthOfYear.JUNE));
		seq.addMonth(new Month(2004, MonthOfYear.JULY));
		seq.addMonth(new Month(2004, MonthOfYear.AUGUST));
		seq.addMonth(new Month(2004, MonthOfYear.SEPTEMBER));
		seq.addMonth(new Month(2004, MonthOfYear.OCTOBER));
		seq.addMonth(new Month(2004, MonthOfYear.NOVEMBER));
		seq.addMonth(new Month(2004, MonthOfYear.DECEMBER));
		assertEquals((Object) new MonthSequence(new Month(2004, MonthOfYear.JANUARY), 12),
				seq.getMonthsParts().iterator().next());
	}

	public void testAddMonthInReverseOrder() throws Exception
	{
		Months seq = new Months();
		seq.addMonth(new Month(2004, MonthOfYear.DECEMBER));
		assertEquals((Object) new MonthSequence(new Month(2004, MonthOfYear.DECEMBER), 1),
				seq.getMonthsParts().iterator().next());
		seq.addMonth(new Month(2004, MonthOfYear.NOVEMBER));
		assertEquals((Object) new MonthSequence(new Month(2004, MonthOfYear.NOVEMBER), 2),
				seq.getMonthsParts().iterator().next());
		seq.addMonth(new Month(2004, MonthOfYear.OCTOBER));
		assertEquals((Object) new MonthSequence(new Month(2004, MonthOfYear.OCTOBER), 3),
				seq.getMonthsParts().iterator().next());
	}

	public void testAddPartsInOrder() throws Exception
	{
		Months seq = new Months();
		seq.addMonths(new MonthSequence(new Month(2004, MonthOfYear.JANUARY), 1));
		assertEquals((Object) new MonthSequence(new Month(2004, MonthOfYear.JANUARY), 1),
				seq.getMonthsParts().iterator().next());
		seq.addMonths(new MonthSequence(new Month(2004, MonthOfYear.FEBRUARY), 1));
		assertEquals((Object) new MonthSequence(new Month(2004, MonthOfYear.JANUARY), 2),
				seq.getMonthsParts().iterator().next());
		seq.addMonths(new MonthSequence(new Month(2004, MonthOfYear.MARCH), 1));
		assertEquals((Object) new MonthSequence(new Month(2004, MonthOfYear.JANUARY), 3),
				seq.getMonthsParts().iterator().next());
	}

	public void testAddPartsWithSpace() throws Exception
	{
		Months seq = new Months();
		seq.addMonths(new MonthSequence(new Month(2004, MonthOfYear.JANUARY), 2));
		assertEquals((Object) new MonthSequence(new Month(2004, MonthOfYear.JANUARY), 2),
				seq.getMonthsParts().iterator().next());

		seq.addMonths(new MonthSequence(new Month(2004, MonthOfYear.APRIL), 2));
		Iterator iter = seq.getMonthsParts().iterator();
		assertEquals((Object) new MonthSequence(new Month(2004, MonthOfYear.JANUARY), 2), iter.next());
		assertEquals((Object) new MonthSequence(new Month(2004, MonthOfYear.APRIL), 2), iter.next());
		assertTrue(!iter.hasNext());

		seq.addMonth(new Month(2004, MonthOfYear.MARCH));
		iter = seq.getMonthsParts().iterator();
		assertEquals((Object) new MonthSequence(new Month(2004, MonthOfYear.JANUARY), 5), iter.next());
		assertTrue(!iter.hasNext());
	}

	public void testContains() throws Exception
	{
		Months seq = new Months();
		seq.addMonths(new MonthSequence(new Month(2004, MonthOfYear.JANUARY), 2));
		assertTrue(seq.contains(new Month(2004, MonthOfYear.JANUARY)));
		assertTrue(seq.contains(new Month(2004, MonthOfYear.FEBRUARY)));
		assertFalse(seq.contains(new Month(2003, MonthOfYear.DECEMBER)));
		assertFalse(seq.contains(new Month(2004, MonthOfYear.MARCH)));

		seq.addMonths(new MonthSequence(new Month(2004, MonthOfYear.APRIL), 2));
		assertTrue(seq.contains(new Month(2004, MonthOfYear.JANUARY)));
		assertTrue(seq.contains(new Month(2004, MonthOfYear.FEBRUARY)));
		assertFalse(seq.contains(new Month(2003, MonthOfYear.DECEMBER)));
		assertFalse(seq.contains(new Month(2004, MonthOfYear.MARCH)));
		assertTrue(seq.contains(new Month(2004, MonthOfYear.APRIL)));
		assertTrue(seq.contains(new Month(2004, MonthOfYear.MAY)));
		assertFalse(seq.contains(new Month(2004, MonthOfYear.JUNE)));

		seq.addMonth(new Month(2004, MonthOfYear.MARCH));
		assertTrue(seq.contains(new Month(2004, MonthOfYear.JANUARY)));
		assertTrue(seq.contains(new Month(2004, MonthOfYear.FEBRUARY)));
		assertFalse(seq.contains(new Month(2003, MonthOfYear.DECEMBER)));
		assertTrue(seq.contains(new Month(2004, MonthOfYear.MARCH)));
		assertTrue(seq.contains(new Month(2004, MonthOfYear.APRIL)));
		assertTrue(seq.contains(new Month(2004, MonthOfYear.MAY)));
		assertFalse(seq.contains(new Month(2004, MonthOfYear.JUNE)));
	}
	
	public void testContinous() throws Exception
	{
		Months seq = new Months();
		seq.addMonth(new Month(2005, MonthOfYear.JANUARY));
		seq.addMonth(new Month(2005, MonthOfYear.FEBRUARY));
		seq.addMonth(new Month(2005, MonthOfYear.MARCH));
		seq.addMonth(new Month(2005, MonthOfYear.APRIL));
		assertTrue(seq.isContinous());
	}

	public void testNotContinous() throws Exception
	{
		Months seq = new Months();
		seq.addMonth(new Month(2005, MonthOfYear.JANUARY));
		seq.addMonth(new Month(2005, MonthOfYear.FEBRUARY));
		seq.addMonth(new Month(2005, MonthOfYear.APRIL));
		assertFalse(seq.isContinous());
	}

	public void testContinousReverseOrder() throws Exception
	{
		Months seq = new Months();
		seq.addMonth(new Month(2005, MonthOfYear.APRIL));
		seq.addMonth(new Month(2005, MonthOfYear.MARCH));
		seq.addMonth(new Month(2005, MonthOfYear.FEBRUARY));
		seq.addMonth(new Month(2005, MonthOfYear.JANUARY));
		assertTrue(seq.isContinous());
	}

	public void testNotContinousReverseOrder() throws Exception
	{
		Months seq = new Months();
		seq.addMonth(new Month(2005, MonthOfYear.APRIL));
		seq.addMonth(new Month(2005, MonthOfYear.FEBRUARY));
		seq.addMonth(new Month(2005, MonthOfYear.JANUARY));
		assertFalse(seq.isContinous());
	}

	public void testMonthsContinous() throws Exception
	{
		Months seq = new Months();
		seq.addMonths(new MonthSequence(new Month(2005, MonthOfYear.JANUARY),5));
		seq.addMonths(new MonthSequence(new Month(2005, MonthOfYear.JUNE),4));
		assertTrue(seq.isContinous());
	}

	public void testMonthsNotContinous() throws Exception
	{
		Months seq = new Months();
		seq.addMonths(new MonthSequence(new Month(2005, MonthOfYear.JANUARY),5));
		seq.addMonths(new MonthSequence(new Month(2005, MonthOfYear.JULY),4));
		assertFalse(seq.isContinous());
	}
}
