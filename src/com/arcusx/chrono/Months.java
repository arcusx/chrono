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
 * Limited month based timespan.
 * 
 * Created 02.10.2003, 16:47:07.
 * 
 * @author conni
 * @version $Id$
 */
public class Months implements Serializable, Collection
{

	private static final long serialVersionUID = 1L;

	private Month firstMonth;

	private int size;

	/**
	 * Get a months period.
	 * 
	 * @param firstMonth The first month in period. 
	 * @param lastMonth The last month in period, possibly null.
	 * @return A month period from firstMonth to lastMonth or an open period starting
	 * with firstMonth if lastMonth is null.
	 */
	public static Months valueOf(Month firstMonth, Month lastMonth)
	{
		if (lastMonth == null)
			return new OpenMonths(firstMonth);

		return new Months(firstMonth, lastMonth);
	}

	/**
	 * Create a new months period.
	 * 
	 * @param firstMonth First month in period.
	 * @param lastMonth Last month in period.
	 * @throws IllegalArgumentException if one is null or lastMonth is before firstMonth.
	 */
	public Months(Month firstMonth, Month lastMonth)
	{
		if (firstMonth == null)
			throw new IllegalArgumentException("Start month may not be null.");

		if (lastMonth != null && lastMonth.before(firstMonth))
			throw new IllegalArgumentException("First month (" + firstMonth + ") must be before last month ("
					+ lastMonth + ").");

		// FIXME broken with years <= 0
		if (firstMonth.getYearValue() <= 0)
			throw new UnsupportedOperationException("Calculating with years equal or less than zero is broken.");

		this.firstMonth = firstMonth;
		this.size = distanceBetween(firstMonth, lastMonth);
	}

	/**
	 * Create a new months period by start month and size.
	 * 
	 * @param firstMonth First month in period.
	 * @param size Count of months in period.
	 * @throws IllegalArgumentException if firstMonth is null or size < 0.
	 */
	public Months(Month firstMonth, int size)
	{
		if (firstMonth == null)
			throw new IllegalArgumentException("Start month may not be null.");

		// FIXME broken with years <= 0
		if (firstMonth.getYearValue() <= 0)
			throw new UnsupportedOperationException("Calculating with years equal or less than zero is broken.");

		if (size < 0)
			throw new IllegalArgumentException("Size must be 0 or greater.");

		this.firstMonth = firstMonth;
		this.size = size;
	}

	/**
	 * Create a months instance withount last month.
	 * 
	 * <b>For internal use only.</b>
	 * 
	 * @param firstMonth 
	 */
	Months(Month firstMonth)
	{
		if (firstMonth == null)
			throw new IllegalArgumentException("Start month may not be null.");

		// FIXME broken with years <= 0
		if (firstMonth.getYearValue() <= 0)
			throw new UnsupportedOperationException("Calculating with years equal or less than zero is broken.");

		this.firstMonth = firstMonth;
	}

	public boolean isOpen()
	{
		return false;
	}

	public Month getFirstMonth()
	{
		return this.firstMonth;
	}

	/**
	 * The last month or null if period has zero length or is open.
	 * 
	 * @return The last month or null.
	 */
	public Month getLastMonth()
	{
		return monthFor(this.firstMonth, this.size);
	}

	public boolean contains(Day day)
	{
		return day.afterOrEqual(this.firstMonth.getFirstDay()) && day.beforeOrEqual(getLastMonth().getLastDay());
	}

	public boolean contains(Month month)
	{
		return month.afterOrEqual(this.firstMonth) && month.beforeOrEqual(getLastMonth());
	}

	public boolean contains(Months months)
	{
		return !(months instanceof OpenMonths) && contains(months.getFirstMonth()) && contains(months.getLastMonth());
	}

	public Months limit(Months months)
	{
		return limit(months.getFirstMonth(), months.getLastMonth());
	}

	/**
	 * Limit the months period so it is between min and max.
	 * 
	 * @param min The minimum month, if null it means let period as is.
	 * @param max The maximum month the period may contain, if null it means let period as is.
	 * @return The new months period.
	 */
	public Months limit(Month min, Month max)
	{
		if (min == null && max == null)
			return this;

		if (min != null && max != null && !min.beforeOrEqual(max))
			throw new IllegalArgumentException("Min may not be after max.");

		if (min != null && min.after(getLastMonth()))
			return new Months(min, 0);

		if (max != null && max.before(this.firstMonth))
			return new Months(min, 0);

		// check if min is given and keep it if it is set after firstMonth
		Month newFirstMonth = null;
		if (min == null)
			newFirstMonth = this.firstMonth;
		else
			newFirstMonth = min;

		// check if max is given and keep it if it is set before lastMonth
		Month newLastMonth = null;
		if (max == null)
			newLastMonth = getLastMonth();
		else
			newLastMonth = max.before(getLastMonth()) ? max : getLastMonth();

		// create new months period if firstMonth and lastMonth changed
		if (this.firstMonth.equals(newFirstMonth) && getLastMonth().equals(newLastMonth))
			return this;

		return new Months(newFirstMonth, newLastMonth);
	}

	public boolean overlaps(Months otherMonths)
	{
		if (otherMonths.firstMonth.before(this.firstMonth) && otherMonths.getLastMonth().before(this.firstMonth))
			return false;

		if (otherMonths.firstMonth.after(otherMonths.getLastMonth()))
			return false;

		return true;
	}

	public boolean equals(Object other)
	{
		if (other == null)
			return false;

		if (!Months.class.equals(other.getClass()))
			return false;

		Months otherMonths = (Months) other;

		if (size() == 0 && otherMonths.size() == 0)
			return this.firstMonth.equals(otherMonths.firstMonth);

		return this.firstMonth.equals(otherMonths.firstMonth) && this.size == otherMonths.size;
	}

	public int hashCode()
	{
		return this.firstMonth.hashCode() ^ size;
	}

	public String toString()
	{
		return "Months{" + this.firstMonth + "-" + getLastMonth() + "}";
	}

	//
	// from Collection
	//

	public Iterator iterator()
	{
		return new Iter(this.firstMonth, getLastMonth());
	}

	public boolean add(Object o)
	{
		throw new UnsupportedOperationException("Immutable.");
	}

	public boolean addAll(Collection c)
	{
		throw new UnsupportedOperationException("Immutable.");
	}

	public void clear()
	{
		throw new UnsupportedOperationException("Immutable.");
	}

	public boolean contains(Object o)
	{
		if (o instanceof Month)
			return contains((Month) o);
		if (o instanceof Months)
			return contains((Months) o);

		return false;
	}

	public boolean containsAll(Collection c)
	{
		Iterator iter = c.iterator();
		while (iter.hasNext())
		{
			if (!contains(iter.next()))
				return false;
		}

		return true;
	}

	public boolean isEmpty()
	{
		return size() == 0;
	}

	public boolean remove(Object o)
	{
		throw new UnsupportedOperationException("Immutable.");
	}

	public boolean removeAll(Collection c)
	{
		throw new UnsupportedOperationException("Immutable.");
	}

	public boolean retainAll(Collection c)
	{
		throw new UnsupportedOperationException("Immutable.");
	}

	public int size()
	{
		return this.size;
	}

	public Object[] toArray()
	{
		return toArray(new Object[size()]);
	}

	public Object[] toArray(Object[] array)
	{
		Iterator iter = iterator();
		for (int i = 0; iter.hasNext(); ++i)
		{
			array[i] = iter.next();
		}

		return array;
	}

	private static Month monthFor(Month firstMonth, int count)
	{
		if (count == 0)
			return null;

		Calendar cal = new GregorianCalendar();
		cal.setTime(firstMonth.getFirstDay().toDate());
		cal.add(Calendar.MONTH, count - 1);

		return Month.valueOf(cal.getTime());
	}

	private static int distanceBetween(Month one, Month other)
	{
		if (one.equals(other))
			return 1;

		if (!one.before(other))
			throw new IllegalArgumentException("First month must be before or equal to last month.");

		Calendar cal = new GregorianCalendar();
		cal.setTime(one.getFirstDay().toDate());
		Date otherDate = other.getFirstDay().toDate();
		int count = 1;
		while (!cal.getTime().equals(otherDate))
		{
			cal.add(Calendar.MONTH, 1);
			count++;
		}

		return count;
	}

	private static final class Iter implements Iterator
	{

		private Calendar cal;

		private Month lastMonth;

		private Iter(Month firstMonth, Month lastMonth)
		{
			this.cal = firstMonth.getFirstDay().toCalendar();
			this.lastMonth = lastMonth;
		}

		public boolean hasNext()
		{
			return this.lastMonth == null || Month.valueOf(cal).beforeOrEqual(this.lastMonth);
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