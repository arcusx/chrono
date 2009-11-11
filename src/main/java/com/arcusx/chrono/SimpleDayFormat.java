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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

/**
 * Simple implementation of formatting parsing of
 * day.
 * 
 * Created 05.10.2003, 16:58:48.
 * 
 * @author conni
 * @version $Id$
 */
public final class SimpleDayFormat extends DayFormat
{
	public static final SimpleDayFormat INSTANCE = new SimpleDayFormat("yyyy-MM-dd");

	private Pattern regex;
	private DateFormat dateFormat;

	public SimpleDayFormat(String pattern)
	{
		this(pattern, null);
	}

	SimpleDayFormat(String dateFormatPattern, Pattern regexPattern)
	{
		this.dateFormat = new SimpleDateFormat(dateFormatPattern);
		this.regex = regexPattern;
	}

	public Day parse(String s) throws ParseException
	{
		if (s == null)
			throw new ParseException("Cannot parse date " + s + ".", 0);

		if (regex != null)
		{
			if (!this.regex.matcher(s).matches())
			{
				throw new ParseException("Unparsable date '" + s + "'.", 0);
			}
		}

		return Day.valueOf(dateFormat.parse(s));
	}

	public void format(Day day, StringBuffer buf)
	{
		buf.append(dateFormat.format(day.toDate()));
	}
}