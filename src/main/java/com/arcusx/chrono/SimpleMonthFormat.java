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

import java.text.*;

/**
 * Simple implementation of formatting parsing of
 * months.
 * 
 * Created 05.10.2003, 16:58:48.
 * 
 * @author conni
 * @version $Id$
 */
public class SimpleMonthFormat extends MonthFormat
{
	private static final DateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy/MM");

	public static final SimpleMonthFormat INSTANCE = new SimpleMonthFormat(DEFAULT_DATE_FORMAT);

	private DateFormat dateFormat;

	public SimpleMonthFormat(String pattern)
	{
		this(new SimpleDateFormat(pattern));
	}

	private SimpleMonthFormat(DateFormat dateFormat)
	{
		this.dateFormat = dateFormat;
	}

	public Month parse(String s) throws ParseException
	{
		// FIXME this is for compatibility, check it (care for MonthSequenceFormat!)
		if (this.dateFormat == DEFAULT_DATE_FORMAT)
		{
			int slashPos = s.indexOf('/');
			if (slashPos == -1)
				throw new ParseException("Invalid format. Expected yyyy/mm; is " + s, 0);

			int year = -1;
			try
			{
				year = Integer.parseInt(s.substring(0, slashPos));
			}
			catch (NumberFormatException ex)
			{
				throw new ParseException("Invalid format. Expected yyyy/mm; is " + s, 0);
			}

			int month = -1;
			try
			{
				month = Integer.parseInt(s.substring(slashPos + 1)) - 1;
			}
			catch (NumberFormatException ex)
			{
				throw new ParseException("Invalid format. Expected yyyy/mm; is " + s, 0);
			}

			return new Month(year, month);
		}
		else
			return Month.valueOf(this.dateFormat.parse(s));
	}

	public void format(Month month, StringBuffer buf)
	{
		// FIXME this is for compatibility, check it (care for MonthSequenceFormat!)
		if (this.dateFormat == DEFAULT_DATE_FORMAT)
		{
			buf.append(month.getYearValue()).append("/").append(month.getMonthValue() + 1);
		}
		else
			buf.append(this.dateFormat.format(month.getFirstDay().toDate()));
	}
}