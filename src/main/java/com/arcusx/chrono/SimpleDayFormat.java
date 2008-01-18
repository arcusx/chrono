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
	public static final SimpleDayFormat INSTANCE = new SimpleDayFormat("yyyy-MM-dd");

	private DateFormat dateFormat;

	public SimpleDayFormat(String pattern)
	{
		this.dateFormat = new SimpleDateFormat(pattern);
	}

	public Day parse(String s) throws ParseException
	{
		return Day.valueOf(dateFormat.parse(s));
	}

	public void format(Day day, StringBuffer buf)
	{
		buf.append(dateFormat.format(day.toDate()));
	}
}