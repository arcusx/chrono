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

import java.util.*;

/**
 * 
 * Created on 22.02.2005, 16:33:23.
 *
 * @author conni
 * @version $Id$
 */
public class MonthSequence implements Sequence
{
	private List parts = new ArrayList();

	public MonthSequence()
	{
	}

	public void addMonth(Month month)
	{
		addMonths(new Months(month, 1));
	}

	public void addMonths(Months months)
	{
		if (months.size() == 0)
			return;

		this.parts.add(months);

		// sort by months start
		Collections.sort(this.parts, new Comparator()
		{
			public int compare(Object o1, Object o2)
			{
				return ((Months) o1).getFirstMonth().compareTo(((Months) o2).getFirstMonth());
			}
		});

		// compact
		for (int i = 0; i < this.parts.size() - 1;)
		{
			Months curr = (Months) this.parts.get(i);
			Months next = (Months) this.parts.get(i + 1);
			if (overlapOrMeet(curr, next))
			{
				curr = new Months(curr.getFirstMonth(), Month.maxOf(curr.getLastMonth(), next.getLastMonth()));
				this.parts.set(i, curr);
				this.parts.remove(i + 1);
				continue;
			}

			++i;
		}
	}

	public void addMonthSequence(MonthSequence seq)
	{
		for (Iterator iter = seq.getMonthsParts().iterator(); iter.hasNext();)
		{
			Object curr = iter.next();
			if (curr instanceof Month)
				addMonth((Month) curr);
			else
				addMonths((Months) curr);
		}
	}

	public Collection getMonths()
	{
		List months = new ArrayList();
		for (int i = 0; i < this.parts.size(); ++i)
		{
			Months curr = (Months) this.parts.get(i);
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
		return this.parts.isEmpty() ? null : ((Months) this.parts.get(0)).getFirstMonth();
	}

	public Month getLastMonth()
	{
		return this.parts.isEmpty() ? null : ((Months) this.parts.get(this.parts.size() - 1)).getLastMonth();
	}

	public Collection getMonthsParts()
	{
		return Collections.unmodifiableCollection(this.parts);
	}

	private boolean overlapOrMeet(Months earlier, Months later)
	{
		if (later.getFirstMonth().beforeOrEqual(earlier.getLastMonth())
				|| later.getFirstMonth().add(-1).equals(earlier.getLastMonth()))
			return true;

		return false;
	}

	public String toString()
	{
		return "MonthSequence{parts=" + parts + "}";
	}
	
	public boolean contains( Month month )
	{
		for(int i=0; i<this.parts.size(); ++i)
		{
			if( ((Months)parts.get(i)).contains(month) )
				return true;
		}
		
		return false;
	}
}
