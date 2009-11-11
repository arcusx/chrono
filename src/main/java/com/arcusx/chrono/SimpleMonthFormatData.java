/**
 *
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
 * Fax.: +49 (0)40.333 102 93 
 * http://www.arcusx.com
 * mailto:info@arcusx.com
 *
 */

package com.arcusx.chrono;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

/**
 *
 * @author conni
 * @version $Id$
 */
final class SimpleMonthFormatData implements Serializable
{
	private static final long serialVersionUID = 1L;

	private static Map<Locale, SimpleMonthFormatData> instanceMap = new HashMap<Locale, SimpleMonthFormatData>();
	static
	{
		try
		{
			register(Locale.GERMAN, "[0-9][0-9]/[0-9][0-9][0-9][0-9]", "MM/yyyy");
			register(Locale.GERMANY, "[0-9][0-9]/[0-9][0-9][0-9][0-9]", "MM/yyyy");
			register(Locale.ENGLISH, "[0-9][0-9][0-9][0-9]\\/[0-9][0-9]", "yyyy/MM");
			register(Locale.US, "[0-9][0-9][0-9][0-9]\\/[0-9][0-9]", "yyyy/MM");
		}
		catch (Exception ex)
		{
			throw new UndeclaredThrowableException(ex);
		}
	}

	private static void register(Locale locale, String stringPatternStr, String dateFormatPattern)
	{
		Pattern stringPattern = Pattern.compile(stringPatternStr);
		SimpleMonthFormatData formatData = new SimpleMonthFormatData(locale, stringPattern, dateFormatPattern);
		instanceMap.put(locale, formatData);
	}

	static SimpleMonthFormatData instanceFor(Locale locale)
	{
		return instanceMap.get(locale);
	}

	private Locale locale;
	private Pattern stringPattern;
	private String dateFormatPattern;

	SimpleMonthFormatData(Locale locale, Pattern stringPattern, String dateFormatPattern)
	{
		this.locale = locale;
		this.stringPattern = stringPattern;
		this.dateFormatPattern = dateFormatPattern;
	}

	public Locale getLocale()
	{
		return this.locale;
	}

	public Pattern getStringPattern()
	{
		return this.stringPattern;
	}

	public String getDateFormatPattern()
	{
		return this.dateFormatPattern;
	}
}
