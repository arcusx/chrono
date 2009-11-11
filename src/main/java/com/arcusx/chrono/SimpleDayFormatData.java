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
final class SimpleDayFormatData implements Serializable
{
	private static final long serialVersionUID = 1L;

	private static Map<Locale, SimpleDayFormatData> instanceMap = new HashMap<Locale, SimpleDayFormatData>();
	static
	{
		try
		{
			// germany
			register(Locale.GERMAN, "[0-9][0-9]\\.[0-9][0-9]\\.[0-9][0-9][0-9][0-9]", "dd.MM.yyyy");
			register(Locale.GERMANY, "[0-9][0-9]\\.[0-9][0-9]\\.[0-9][0-9][0-9][0-9]", "dd.MM.yyyy");

			// english uk
			register(Locale.ENGLISH, "[0-9][0-9]\\/[0-9][0-9]\\/[0-9][0-9][0-9][0-9]", "MM/dd/yyyy");
			register(Locale.UK, "[0-9][0-9]\\/[0-9][0-9]\\/[0-9][0-9][0-9][0-9]", "MM/dd/yyyy");

			// englisch us
			register(Locale.US, "[0-9][0-9]\\/[0-9][0-9]\\/[0-9][0-9][0-9][0-9]", "dd/MM/yyyy");
		}
		catch (Exception ex)
		{
			throw new UndeclaredThrowableException(ex);
		}
	}

	private static void register(Locale locale, String stringPatternStr, String dateFormatPattern)
	{
		Pattern stringPattern = Pattern.compile(stringPatternStr);
		SimpleDayFormatData formatData = new SimpleDayFormatData(locale, stringPattern, dateFormatPattern);
		instanceMap.put(locale, formatData);
	}

	static SimpleDayFormatData instanceFor(Locale locale)
	{
		return instanceMap.get(locale);
	}

	private Locale locale;
	private Pattern stringPattern;
	private String dateFormatPattern;

	SimpleDayFormatData(Locale locale, Pattern stringPattern, String dateFormatPattern)
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
