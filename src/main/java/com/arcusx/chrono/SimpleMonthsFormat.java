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

import java.util.Iterator;

/**
 * 
 * Created on 22.02.2005, 17:14:57.
 *
 * @author conni
 * @version $Id$
 */
public class SimpleMonthsFormat extends MonthsFormat
{
	public static final SimpleMonthsFormat INSTANCE = new SimpleMonthsFormat();

	public SimpleMonthsFormat()
	{
	}

	public Months parse(String s)
	{
		Months seq = new Months();
		int pos = 0;
		String[] parts = s.split(",");
		for (int i = 0; i < parts.length; ++i)
		{
			parts[i] = parts[i].trim();
			
			try
			{
				seq.addMonth(SimpleMonthFormat.INSTANCE.parse(parts[i]));
				pos += parts[i].length() + 1;
				continue;
			}
			catch (Exception ex)
			{
			}

			try
			{
				seq.addMonths(SimpleMonthSequenceFormat.INSTANCE.parse(parts[i]));
				pos += parts[i].length() + 1;
				continue;
			}
			catch (Exception ex)
			{
			}

			throw new IllegalArgumentException("Invalid month or months format.");
		}

		return seq;
	}

	public void format(Months seq, StringBuffer buf)
	{
		Iterator iter = seq.getMonthsParts().iterator();
		while (iter.hasNext())
		{
			Object curr = iter.next();
			if (curr instanceof Month)
				SimpleMonthFormat.INSTANCE.format((Month) curr, buf);
			else if ((curr instanceof MonthSequence) && ((MonthSequence) curr).size() == 1)
				SimpleMonthFormat.INSTANCE.format(((MonthSequence) curr).getFirstMonth(), buf);
			else
				SimpleMonthSequenceFormat.INSTANCE.format((MonthSequence) curr, buf);

			if (iter.hasNext())
				buf.append(", ");
		}
	}
}
