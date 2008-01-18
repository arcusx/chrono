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
 * Day formatting and parsing.
 * 
 * Created 05.10.2003, 16:58:48.
 * 
 * @author conni
 * @version $Id$
 */
public abstract class DayFormat
{

	protected DayFormat()
	{
	}

	public abstract Day parse(String s) throws ParseException;

	public String format(Day day)
	{
		StringBuffer buf = new StringBuffer(10);
		format(day, buf);

		return buf.toString();
	}

	public abstract void format(Day day, StringBuffer buf);
}