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

package com.arcusx.chrono;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Months  is an possibly uncontinuos collection of months,
 * technically {@see Month} and {@see MonthSequence} objects.
 * 
 * Created on 22.02.2005, 16:33:23.
 *
 * @author conni
 * @version $Id$
 */
public class Months implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private List parts = new ArrayList();

	/**
	 * Create a new empty month sequence.
	 */
	public Months()
	{
	}

	public void addMonth(Month month)
	{
		addMonths(new MonthSequence(month, 1));
	}

	public void addMonths(MonthSequence months)
	{
		if (months.size() == 0)
			return;

		this.parts.add(months);

		// sort by months start
		Collections.sort(this.parts, new Comparator()
		{
			public int compare(Object o1, Object o2)
			{
				return ((MonthSequence) o1).getFirstMonth().compareTo(((MonthSequence) o2).getFirstMonth());
			}
		});

		// compact
		for (int i = 0; i < this.parts.size() - 1;)
		{
			MonthSequence curr = (MonthSequence) this.parts.get(i);
			MonthSequence next = (MonthSequence) this.parts.get(i + 1);
			if (overlapOrMeet(curr, next))
			{
				curr = new MonthSequence(curr.getFirstMonth(), Month.maxOf(curr.getLastMonth(), next.getLastMonth()));
				this.parts.set(i, curr);
				this.parts.remove(i + 1);
				continue;
			}

			++i;
		}
	}

	public void addMonthSequence(Months seq)
	{
		for (Iterator iter = seq.getMonthsParts().iterator(); iter.hasNext();)
		{
			Object curr = iter.next();
			if (curr instanceof Month)
				addMonth((Month) curr);
			else
				addMonths((MonthSequence) curr);
		}
	}

	/**
	 * Get collection of {@see Month Month objects}.
	 * 
	 * @return The collection.
	 */
	public Collection getMonths()
	{
		List months = new ArrayList();
		for (int i = 0; i < this.parts.size(); ++i)
		{
			MonthSequence curr = (MonthSequence) this.parts.get(i);
			months.addAll(curr);
		}

		return months;
	}

	public boolean isEmpty()
	{
		return this.parts.isEmpty();
	}

	public Month getFirstMonth()
	{
		return this.parts.isEmpty() ? null : ((MonthSequence) this.parts.get(0)).getFirstMonth();
	}

	public Month getLastMonth()
	{
		return this.parts.isEmpty() ? null : ((MonthSequence) this.parts.get(this.parts.size() - 1)).getLastMonth();
	}

	/**
	 * Get the months parts of the sequence.
	 * 
	 * @return Collection of Month and Months objects.
	 */
	public Collection getMonthsParts()
	{
		return Collections.unmodifiableCollection(this.parts);
	}

	/**
	 * Test if the month sequence is continuos.
	 * 
	 * @return True if it is, else false.
	 */
	public boolean isContinous()
	{
		boolean continous = true;
		MonthSequence lastPart = null;
		for (int i = 0; continous && i < this.parts.size(); i++)
		{
			Object currPartObj = this.parts.get(i);

			// convert Month to Months for simplicity
			if (currPartObj instanceof Month)
				currPartObj = new MonthSequence((Month) currPartObj, 1);

			MonthSequence currPart = (MonthSequence) currPartObj;
			if (lastPart != null)
			{
				if (!lastPart.getLastMonth().add(1).equals(currPart.getFirstMonth()))
					continous = false;
			}
			lastPart = currPart;
		}
		return continous;
	}

	/**
	 * Test if to months parts meet or overlap. Helper for
	 * compaction.
	 * 
	 * @param earlier The earlier part.
	 * @param later The later part.
	 * @return True if they meet or overlap, else false.
	 */
	private boolean overlapOrMeet(MonthSequence earlier, MonthSequence later)
	{
		if (later.getFirstMonth().beforeOrEqual(earlier.getLastMonth()) || later.getFirstMonth().add(-1).equals(earlier.getLastMonth()))
			return true;

		return false;
	}

	public String toString()
	{
		return "MonthSequence{parts=" + parts + "}";
	}

	/**
	 * Test if the sequence contains a month.
	 * 
	 * @param month Month to test for.
	 * @return True if contains, else false.
	 */
	public boolean contains(Month month)
	{
		for (int i = 0; i < this.parts.size(); ++i)
		{
			if (((MonthSequence) parts.get(i)).contains(month))
				return true;
		}

		return false;
	}
}
