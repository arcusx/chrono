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

	private Month firstMonth;
	private Month lastMonth;

	public Months(Month firstMonth, Month lastMonth)
	{
		// swap if necessary
		if (firstMonth.after(lastMonth))
		{
			Month tmp = lastMonth;
			lastMonth = firstMonth;
			firstMonth = tmp;
		}

		this.firstMonth = firstMonth;
		this.lastMonth = lastMonth;
	}

	public Months(Month firstMonth, int count)
	{
		if (count <= 0)
			throw new IllegalArgumentException("Count must be 1 or more.");

		this.firstMonth = firstMonth;

		Calendar cal = firstMonth.getFirstDay().toCalendar();
		cal.add(Calendar.MONTH, count - 1);
		this.lastMonth = Month.valueOf(cal);
	}

	public Month getFirstMonth()
	{
		return this.firstMonth;
	}

	public Month getLastMonth()
	{
		return this.lastMonth;
	}

	public boolean contains(Day day)
	{
		return day.afterOrEqual(this.firstMonth.getFirstDay()) && day.beforeOrEqual(this.lastMonth.getLastDay());
	}

	public boolean contains(Month month)
	{
		return month.afterOrEqual(this.firstMonth) && month.beforeOrEqual(this.lastMonth);
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
		// check if min is given and keep it if it is set after firstMonth
		Month newFirstMonth = null;
		if (min == null)
			newFirstMonth = this.firstMonth;
		else
			newFirstMonth = min.after(this.firstMonth) ? min : this.firstMonth;

		// check if max is given and keep it if it is set before lastMonth
		Month newLastMonth = null;
		if (max == null)
			newLastMonth = this.lastMonth;
		else
			newLastMonth = max.before(this.lastMonth) ? max : this.lastMonth;

		// create new months period if firstMonth and lastMonth changed
		if (this.firstMonth.equals(newFirstMonth) && this.lastMonth.equals(newLastMonth))
			return this;
		else
			return new Months(newFirstMonth, newLastMonth);
	}

	public boolean overlaps(Months otherMonths)
	{
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

		return this.firstMonth.equals(otherMonths.firstMonth) && this.lastMonth.equals(otherMonths.lastMonth);
	}

	public int hashCode()
	{
		return this.firstMonth.hashCode() ^ this.lastMonth.hashCode();
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
		return new Iter(this.firstMonth, this.lastMonth);
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
		// FIXME this could be done more efficiently
		int i = 0;
		Iterator iter = iterator();
		while (iter.hasNext())
		{
			++i;
			iter.next();
		}
		return i;
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
			return Month.valueOf(cal).beforeOrEqual(this.lastMonth);
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
