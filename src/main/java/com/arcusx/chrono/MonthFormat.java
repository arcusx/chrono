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

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.NoSuchElementException;

/**
 * Month formatting and parsing.
 * 
 * Created 05.10.2003, 16:58:48.
 * 
 * @author conni
 * @version $Id$
 */
public abstract class MonthFormat
{
	/**
	 * Get a day format for a given locale.
	 * 
	 * @param locale The locale, not null.
	 * @return Day format, never null.
	 * @throws NoSuchElementException if no instance for locale is known.
	 */
	public static MonthFormat newInstanceFor(Locale locale)
	{
		SimpleMonthFormatData formatData = SimpleMonthFormatData.instanceFor(locale);
		if (formatData == null)
			throw new NoSuchElementException("No day format for " + locale + " known.");

		return new SimpleMonthFormat(new SimpleDateFormat(formatData.getDateFormatPattern()), formatData
				.getStringPattern());
	}

	protected MonthFormat()
	{
	}

	public abstract Month parse(String s);

	public String format(Month month)
	{
		StringBuffer buf = new StringBuffer(10);
		format(month, buf);

		return buf.toString();
	}

	public abstract void format(Month month, StringBuffer buf);
}
