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
 * A days based timespan.
 * 
 * Created 02.10.2003, 17:01:04.
 * 
 * @author conni
 * @version $Id$
 */
public class DaySequence implements Serializable, Collection
{

	private static final long serialVersionUID = 1L;

	private Day firstDay;

	private Day lastDay;

	public DaySequence(Day firstDay, Day lastDay)
	{
		if (firstDay == null)
			throw new IllegalArgumentException("First day may not be null.");
		if (lastDay == null)
			throw new IllegalArgumentException("Last day may not be null.");

		// swap if necessary
		if (firstDay.after(lastDay))
		{
			Day tmp = lastDay;
			lastDay = firstDay;
			firstDay = tmp;
		}

		this.firstDay = firstDay;
		this.lastDay = lastDay;
	}

	public Day getFirstDay()
	{
		return this.firstDay;
	}

	public Day getLastDay()
	{
		return this.lastDay;
	}

	/**
	 * Test if a day is contained within these days
	 * including first and last day.
	 * 
	 * @return True if day is in.
	 */
	public boolean contain(Day day)
	{
		return this.firstDay.beforeOrEqual(day) && this.lastDay.afterOrEqual(day);
	}

	public boolean overlaps(DaySequence otherDays)
	{
		if (otherDays.firstDay.before(this.firstDay) && otherDays.lastDay.before(this.firstDay))
			return false;

		if (otherDays.firstDay.after(otherDays.lastDay))
			return false;

		return true;
	}

	//
	// from Object
	//

	public boolean equals(Object other)
	{
		if (other == null)
			return false;

		if (!DaySequence.class.equals(other.getClass()))
			return false;

		DaySequence otherDays = (DaySequence) other;

		return this.firstDay.equals(otherDays.firstDay) && this.lastDay.equals(otherDays.lastDay);
	}

	public int hashCode()
	{
		return this.firstDay.hashCode() ^ this.lastDay.hashCode();
	}

	//
	// from Collection
	//

	/**
	 * Overrides @see java.util.Collection#add(java.lang.Object).
	 * 
	 * @throws UnsupportedOperationException if invoked.
	 */
	public boolean add(Object arg0)
	{
		throw new UnsupportedOperationException("Days are immutable.");
	}

	/**
	 * Overrides @see java.util.Collection#addAll(java.util.Collection).
	 * 
	 * @throws UnsupportedOperationException if invoked.
	 */
	public boolean addAll(Collection arg0)
	{
		throw new UnsupportedOperationException("Days are immutable.");
	}

	/**
	 * Overrides @see java.util.Collection#clear().
	 * 
	 * @throws UnsupportedOperationException if invoked.
	 */
	public void clear()
	{
		throw new UnsupportedOperationException("Days are immutable.");
	}

	/**
	 * Overrides @see java.util.Collection#contains(java.lang.Object).
	 */
	public boolean contains(Object item)
	{
		if (!(item instanceof Day))
			return false;

		return contain((Day) item);
	}

	/**
	 * Overrides @see java.util.Collection#containsAll(java.util.Collection).
	 */
	public boolean containsAll(Collection coll)
	{
		boolean containsAll = true;
		Iterator iter = coll.iterator();
		while (containsAll && iter.hasNext())
		{
			containsAll = contains(iter.next());
		}

		return containsAll;
	}

	/**
	 * Overrides @see java.util.Collection#isEmpty().
	 */
	public boolean isEmpty()
	{
		return size() == 0;
	}

	/**
	 * Overrides @see java.util.Collection#remove(java.lang.Object).
	 * 
	 * @throws UnsupportedOperationException if invoked.
	 */
	public boolean remove(Object arg0)
	{
		throw new UnsupportedOperationException("Days are immutable.");
	}

	/**
	 * Overrides @see java.util.Collection#removeAll(java.util.Collection).
	 * 
	 * @throws UnsupportedOperationException if invoked.
	 */
	public boolean removeAll(Collection arg0)
	{
		throw new UnsupportedOperationException("Days are immutable.");
	}

	/**
	 * Overrides @see java.util.Collection#retainAll(java.util.Collection).
	 * 
	 * @throws UnsupportedOperationException if invoked.
	 */
	public boolean retainAll(Collection arg0)
	{
		throw new UnsupportedOperationException("Days are immutable.");
	}

	/**
	 * Overrides @see java.util.Collection#toArray().
	 */
	public Object[] toArray()
	{
		return toArray(new Day[size()]);
	}

	/**
	 * Overrides @see java.util.Collection#toArray(java.lang.Object[]).
	 */
	public Object[] toArray(Object[] days)
	{
		Iterator iter = iterator();
		for (int i = 0; iter.hasNext(); ++i)
		{
			Day curr = (Day) iter.next();
			days[i] = curr;
		}

		return days;
	}

	public int size()
	{
		// FIXME could this be done for efficiently?
		Iterator iter = iterator();
		int i = 0;
		while (iter.hasNext())
		{
			++i;
			iter.next();
		}

		return i;
	}

	public Iterator iterator()
	{
		return new Iter(this.firstDay, this.lastDay);
	}

	private static final class Iter implements Iterator
	{

		private Calendar cal;

		private Day lastDay;

		private Iter(Day firstDay, Day lastDay)
		{
			this.cal = firstDay.toCalendar();
			this.lastDay = lastDay;
		}

		public boolean hasNext()
		{
			return Day.valueOf(cal).beforeOrEqual(this.lastDay);
		}

		public Object next()
		{
			Day day = Day.valueOf(cal);
			cal.add(Calendar.DATE, 1);

			return day;
		}

		public void remove()
		{
			throw new UnsupportedOperationException("Cannot remove Day from Days.");
		}
	}
}