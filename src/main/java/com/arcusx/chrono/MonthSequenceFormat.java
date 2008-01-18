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
 * Formatting and parsing of month based timespan.
 * 
 * Created 05.10.2003, 16:58:48.
 * 
 * @author conni
 * @version $Id$
 */
public abstract class MonthSequenceFormat
{
	protected MonthSequenceFormat()
	{
	}

	public String format(MonthSequence months)
	{
		StringBuffer buf = new StringBuffer(10);
		format(months, buf);

		return buf.toString();
	}

	public abstract MonthSequence parse(String s) throws ParseException;

	public abstract void format(MonthSequence month, StringBuffer buf);
}
