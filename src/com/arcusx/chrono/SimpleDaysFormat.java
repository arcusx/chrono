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
 * This a very simple day based timespan format.
 * 
 * Created 30.05.2003, 13:15:23.
 * 
 * @author conni
 * @version $Id$
 */
public class SimpleDaysFormat extends DaysFormat
{

	public static final SimpleDaysFormat INSTANCE = new SimpleDaysFormat();

	public SimpleDaysFormat()
	{
	}

	public Days parse(String s) throws ParseException
	{
		String[] parts = s.split("-");
		if (parts.length != 6)
			throw new ParseException("Not of the form yyyy-mm-dd-yyyy-mm-dd", -1);
		Day start = SimpleDayFormat.INSTANCE.parse(parts[0]+"-"+parts[1]+"-"+parts[2]);
		Day end = SimpleDayFormat.INSTANCE.parse(parts[3]+"-"+parts[4]+"-"+parts[5]);

		return new Days(start, end);
	}

	public void format(Days days, StringBuffer buf)
	{
		// format first day into buffer
		SimpleDayFormat.INSTANCE.format(days.getFirstDay(), buf);

		// if is "real" period (longer than one day),
		// so format last month into, too
		if (!days.getFirstDay().equals(days.getLastDay()))
		{
			buf.append('-');
			SimpleDayFormat.INSTANCE.format(days.getLastDay(), buf);
		}
	}
}