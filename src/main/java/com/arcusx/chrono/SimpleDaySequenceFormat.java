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


/**
 * This a very simple day based timespan format.
 * 
 * Created 30.05.2003, 13:15:23.
 * 
 * @author conni
 * @version $Id$
 */
public class SimpleDaySequenceFormat extends DaySequenceFormat
{

	public static final SimpleDaySequenceFormat INSTANCE = new SimpleDaySequenceFormat();

	public SimpleDaySequenceFormat()
	{
	}

	public DaySequence parse(String s)
	{
		String[] parts = s.split("-");
		if (parts.length != 6)
			throw new IllegalArgumentException("Not of the form yyyy-mm-dd-yyyy-mm-dd");
		Day start = SimpleDayFormat.INSTANCE.parse(parts[0] + "-" + parts[1] + "-" + parts[2]);
		Day end = SimpleDayFormat.INSTANCE.parse(parts[3] + "-" + parts[4] + "-" + parts[5]);

		return new DaySequence(start, end);
	}

	public void format(DaySequence days, StringBuffer buf)
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