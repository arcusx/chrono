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
 * Month based timespan.
 * 
 * Created 02.10.2003, 16:47:07.
 * 
 * @author conni
 * @version $Id$
 */
public class Months implements Serializable, Collection
{

	private static final long serialVersionUID = 1L;

	private static final Integer ZERO = new Integer(0);

	private static final Integer ONE = new Integer(1);

	private Month firstMonth;

	private Month lastMonth; // initialized on demand

	private Integer count;

	public Months(Month firstMonth, Month lastMonth)
	{
		if (firstMonth == null)
			throw new IllegalArgumentException("Start month may not be null.");

		if (lastMonth != null && lastMonth.before(firstMonth))
			throw new IllegalArgumentException("First month (" + firstMonth + ") may not be before last month ("
					+ lastMonth + ").");

		this.firstMonth = firstMonth;
		this.count = distanceBetween(firstMonth, lastMonth);
	}

	public Months(Month firstMonth, int count)
	{
		if (firstMonth == null)
			throw new IllegalArgumentException("Start month may not be null.");

		// FIXME broken with years <= 0
		if (firstMonth.getYearValue() <= 0)
			throw new UnsupportedOperationException("Calculating with years equal or less than zero is broken.");

		if (count < 0)
			throw new IllegalArgumentException("Count must be 0 or greater.");

		this.firstMonth = firstMonth;
		this.count = new Integer(count);
	}

	public Months(Month firstMonth)
	{
		if (firstMonth == null)
			throw new IllegalArgumentException("Start month may not be null.");

		this.firstMonth = firstMonth;
	}

	public boolean isOpen()
	{
		return this.count == null;
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
		if (!isOpen() && this.count.intValue() > 0)
			this.lastMonth = monthFor(this.firstMonth, this.count.intValue());

		return this.lastMonth;
	}

	public boolean contains(Day day)
	{
		if (!isOpen())
			return day.afterOrEqual(this.firstMonth.getFirstDay()) && day.beforeOrEqual(getLastMonth().getLastDay());
		else
			return day.afterOrEqual(this.firstMonth.getFirstDay());
	}

	public boolean contains(Month month)
	{
		if (!isOpen())
			return month.afterOrEqual(this.firstMonth) && month.beforeOrEqual(getLastMonth());
		else
			return month.afterOrEqual(this.firstMonth);
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
		if (min != null && max != null && !min.beforeOrEqual(max))
			throw new IllegalArgumentException("Min may not be after max.");

		if (isOpen())
			return limitOpen(min, max);

		return limitNotOpen(min, max);
	}

	private Months limitNotOpen(Month min, Month max)
	{
		if (min != null && max != null && !min.beforeOrEqual(max))
			throw new IllegalArgumentException("Min may not be before max.");

		// check if min is given and keep it if it is set after firstMonth
		Month newFirstMonth = null;
		if (min == null)
			newFirstMonth = this.firstMonth;
		else
			newFirstMonth = min.after(this.firstMonth) ? min : this.firstMonth;

		// check if max is given and keep it if it is set before lastMonth
		Month newLastMonth = null;
		if (max == null)
			newLastMonth = getLastMonth();
		else
			newLastMonth = max.before(getLastMonth()) ? max : getLastMonth();

		// create new months period if firstMonth and lastMonth changed
		if (this.firstMonth.equals(newFirstMonth) && this.lastMonth.equals(newLastMonth))
			return this;

		return new Months(newFirstMonth, newLastMonth);
	}

	private Months limitOpen(Month min, Month max)
	{
		if (min == null && max == null)
			return this;

		if (max != null)
			return new Months(min != null ? Month.max(min, this.firstMonth) : this.firstMonth, max);

		return new Months(Month.max(min, this.firstMonth));
	}

	public boolean overlaps(Months otherMonths)
	{
		checkNotIsOpen(); // FIXME implement for open Months

		if (otherMonths.firstMonth.before(this.firstMonth) && otherMonths.lastMonth.before(this.firstMonth))
			return false;

		if (otherMonths.firstMonth.after(otherMonths.lastMonth))
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

		if (!isOpen() && !otherMonths.isOpen())
			return this.firstMonth.equals(otherMonths.firstMonth) && getLastMonth().equals(otherMonths.getLastMonth());

		return otherMonths.isOpen() && this.firstMonth.equals(otherMonths.firstMonth);
	}

	public int hashCode()
	{
		if (!isOpen())
			return this.firstMonth.hashCode() ^ this.lastMonth.hashCode();

		return this.firstMonth.hashCode();
	}

	public String toString()
	{
		return "Months{" + this.firstMonth + "-" + this.lastMonth + "}";
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
		// cannot be empty
		return false;
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
		checkNotIsOpen();

		return count.intValue();
	}

	public Object[] toArray()
	{
		checkNotIsOpen();

		return toArray(new Object[size()]);
	}

	public Object[] toArray(Object[] array)
	{
		checkNotIsOpen();

		Iterator iter = iterator();
		for (int i = 0; iter.hasNext(); ++i)
		{
			array[i] = iter.next();
		}

		return array;
	}

	private void checkNotIsOpen()
	{
		if (isOpen())
			throw new UnsupportedOperationException("Months has open end.");
	}

	private static Month monthFor(Month firstMonth, int count)
	{
		Calendar cal = new GregorianCalendar();
		cal.setTime(firstMonth.getFirstDay().toDate());
		cal.add(Calendar.MONTH, count - 1);

		return Month.valueOf(cal.getTime());
	}

	private static Integer distanceBetween(Month one, Month other)
	{
		if (other == null)
			return null;

		if (one.equals(other))
			return ONE;

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

		return new Integer(count);
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