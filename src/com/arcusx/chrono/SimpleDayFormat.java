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
 * day.
 * 
 * Created 05.10.2003, 16:58:48.
 * 
 * @author conni
 * @version $Id$
 */
public class SimpleDayFormat extends DayFormat
{

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	public static final SimpleDayFormat INSTANCE = new SimpleDayFormat();

	public SimpleDayFormat()
	{
	}

	public Day parse(String s) throws ParseException
	{
		return Day.valueOf(DATE_FORMAT.parse(s));
	}

	public void format(Day day, StringBuffer buf)
	{
		buf.append(DATE_FORMAT.format(day.toDate()));
	}
}