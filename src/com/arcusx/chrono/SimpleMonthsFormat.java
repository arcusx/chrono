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
 * This a very simple month based timespan format.
 * 
 * Created 30.05.2003, 13:15:23.
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

	public String format(Months period)
	{
		StringBuffer buf = new StringBuffer();

		format(period, buf);

		return buf.toString();
	}

	/**
	 * <b>FIXME Not implemented, yet.</b>
	 */
	public Months parse(String s) throws ParseException
	{
		String[] parts = s.split("-");
		if( parts.length != 2 )
			throw new ParseException("Not of the form yyyy/mm-yyyy/mm",-1);
		Month start = SimpleMonthFormat.INSTANCE.parse(parts[0]);
		Month end = SimpleMonthFormat.INSTANCE.parse(parts[1]);
		return Months.valueOf(start,end);
	}

	public void format(Months period, StringBuffer buf)
	{
		SimpleMonthFormat monthFormat = new SimpleMonthFormat();

		Months months = (Months) period;

		// format first month into buffer
		monthFormat.format(months.getFirstMonth(), buf);

		// if is "real" period (longer than one month),
		// so format last month into, too
		if (!months.getFirstMonth().equals(months.getLastMonth()))
		{
			buf.append('-');
			monthFormat.format(months.getLastMonth(), buf);
		}
	}
}