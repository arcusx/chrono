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
	public static final SimpleMonthFormat INSTANCE = new SimpleMonthFormat();
	
	public SimpleMonthFormat()
	{
	}

	public Month parse(String s) throws ParseException
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
			month = Integer.parseInt(s.substring(slashPos + 1));
		}
		catch (NumberFormatException ex)
		{
			throw new ParseException("Invalid format. Expected yyyy/mm; is " + s, 0);
		}

		return new Month(year, month);
	}

	public void format(Month month, StringBuffer buf)
	{
		buf.append(month.getYearValue()).append("/").append(month.getMonthValue() + 1);
	}
}