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

package com.arcusx.chrono;

import java.io.*;
import java.util.*;

/**
 * Unlimited month based timespan.
 * 
 * Created 02.10.2003, 16:47:07.
 * 
 * @author conni
 * @version $Id$
 */
public final class OpenMonthSequence extends MonthSequence implements Serializable
{

	private static final long serialVersionUID = 1L;

	/**
	 * Create a months instance withount last month.
	 * 
	 * @param firstMonth 
	 */
	public OpenMonthSequence(Month firstMonth)
	{
		super(firstMonth);
	}

	/**
	 * The last month or null if period has zero length or is open.
	 * 
	 * @return The last month or null.
	 */
	public Month getLastMonth()
	{
		return null;
	}

	public boolean isOpen()
	{
		return true;
	}

	public boolean contains(Day day)
	{
		return day.afterOrEqual(getFirstMonth().getFirstDay());
	}

	public boolean contains(Month month)
	{
		return month.afterOrEqual(getFirstMonth());
	}

	public boolean contains(MonthSequence months)
	{
		return months.getFirstMonth().afterOrEqual(getFirstMonth());
	}

	/**
	 * Limit the months period so it is between min and max.
	 * 
	 * @param min The minimum month, if null it means let period as is.
	 * @param max The maximum month the period may contain, if null it means let period as is.
	 * @return The new months period.
	 */
	public MonthSequence limitBy(Month min, Month max)
	{
		if (min != null && max != null && !min.beforeOrEqual(max))
			throw new IllegalArgumentException("Min may not be after max.");

		if (min == null && max == null)
			return this;

		Month newMin;
		if (min == null)
			newMin = getFirstMonth();
		else
			newMin = Month.maxOf(getFirstMonth(), min);

		Month newMax = null;
		if (max != null)
			newMax = max;

		return newMax == null ? new OpenMonthSequence(newMin) : new MonthSequence(newMin, newMax);
	}

	public boolean overlaps(MonthSequence otherMonths)
	{
		if (otherMonths.getLastMonth() != null)
		{
			if (otherMonths.getLastMonth().before(getFirstMonth()))
				return false;
		}

		return true;
	}

	public boolean equals(Object other)
	{
		if (other == null)
			return false;

		if (!OpenMonthSequence.class.equals(other.getClass()))
			return false;

		OpenMonthSequence otherMonths = (OpenMonthSequence) other;
		return otherMonths.getFirstMonth().equals(getFirstMonth());
	}

	public int hashCode()
	{
		return getFirstMonth().hashCode();
	}

	public String toString()
	{
		return "OpenMonths{" + getFirstMonth() + "}";
	}

	//
	// from Collection
	//

	public Iterator iterator()
	{
		return new Iter(getFirstMonth());
	}

	public boolean isEmpty()
	{
		return false;
	}

	public int size()
	{
		throw new UnsupportedOperationException("Open period has no size.");
	}

	public Object[] toArray()
	{
		throw new UnsupportedOperationException("Period is open.");
	}

	public Object[] toArray(Object[] array)
	{
		throw new UnsupportedOperationException("Period is open.");
	}

	private static final class Iter implements Iterator
	{

		private Calendar cal;

		private Iter(Month firstMonth)
		{
			this.cal = firstMonth.getFirstDay().toCalendar();
		}

		public boolean hasNext()
		{
			return true;
		}

		public Object next()
		{
			Month month = Month.valueOf(cal);
			cal.add(Calendar.MONTH, 1);

			return month;
		}

		public void remove()
		{
			throw new UnsupportedOperationException("Cannot remove Month from Months.");
		}
	}
}