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

import java.io.*;
import java.util.*;

/**
 * A month.
 * 
 * Created 30.05.2003, 12:36:44.
 * 
 * @author conni
 * @version $Id$
 */
public class MonthOfYear implements Serializable
{

	private static final long serialVersionUID = 1L;

	/**
	 * All month of year instances.
	 */
	private static final MonthOfYear[] MONTHS_OF_YEAR = new MonthOfYear[]
	{ new MonthOfYear(Calendar.JANUARY, "JANUARY", "JAN"), //
			new MonthOfYear(Calendar.FEBRUARY, "FEBRUARY", "FEB"), //
			new MonthOfYear(Calendar.MARCH, "MARCH", "MAR"), //
			new MonthOfYear(Calendar.APRIL, "APRIL", "APR"), //
			new MonthOfYear(Calendar.MAY, "MAY", "MAY"), //
			new MonthOfYear(Calendar.JUNE, "JUNE", "JUN"), //
			new MonthOfYear(Calendar.JULY, "JULY", "JUL"), //
			new MonthOfYear(Calendar.AUGUST, "AUGUST", "AUG"), //
			new MonthOfYear(Calendar.SEPTEMBER, "SEPTEMBER", "SEP"), //
			new MonthOfYear(Calendar.OCTOBER, "OCTOBER", "OCT"), //
			new MonthOfYear(Calendar.NOVEMBER, "NOVEMBER", "NOV"), //
			new MonthOfYear(Calendar.DECEMBER, "DECEMBER", "DEC"), };

	public static final List ALL = Collections.unmodifiableList(Arrays.asList(MONTHS_OF_YEAR));

	public static final MonthOfYear JANUARY = MONTHS_OF_YEAR[Calendar.JANUARY];

	public static final MonthOfYear FEBRUARY = MONTHS_OF_YEAR[Calendar.FEBRUARY];

	public static final MonthOfYear MARCH = MONTHS_OF_YEAR[Calendar.MARCH];

	public static final MonthOfYear APRIL = MONTHS_OF_YEAR[Calendar.APRIL];

	public static final MonthOfYear MAY = MONTHS_OF_YEAR[Calendar.MAY];

	public static final MonthOfYear JUNE = MONTHS_OF_YEAR[Calendar.JUNE];

	public static final MonthOfYear JULY = MONTHS_OF_YEAR[Calendar.JULY];

	public static final MonthOfYear AUGUST = MONTHS_OF_YEAR[Calendar.AUGUST];

	public static final MonthOfYear SEPTEMBER = MONTHS_OF_YEAR[Calendar.SEPTEMBER];

	public static final MonthOfYear OCTOBER = MONTHS_OF_YEAR[Calendar.OCTOBER];

	public static final MonthOfYear NOVEMBER = MONTHS_OF_YEAR[Calendar.NOVEMBER];

	public static final MonthOfYear DECEMBER = MONTHS_OF_YEAR[Calendar.DECEMBER];

	private int month;

	private String shortName;

	private String longName;

	public static MonthOfYear[] all()
	{
		return (MonthOfYear[]) ALL.toArray(new MonthOfYear[ALL.size()]);
	}

	/**
	 * @throws IllegalArgumentException if the string could not be parsed.
	 */
	public static MonthOfYear valueOf(String s)
	{
		for (int i = 0; i < MONTHS_OF_YEAR.length; ++i)
		{
			if (MONTHS_OF_YEAR[i].shortName.equalsIgnoreCase(s) || MONTHS_OF_YEAR[i].longName.equalsIgnoreCase(s))
				return MONTHS_OF_YEAR[i];
		}

		throw new IllegalArgumentException("Unknown month: " + s);
	}

	public static MonthOfYear valueOf(int month)
	{
		if (month < 0 || month >= MONTHS_OF_YEAR.length)
			throw new IllegalArgumentException("Must be between 1 and 11 (is " + month + ")");

		return MONTHS_OF_YEAR[month];
	}

	private MonthOfYear(int month, String longName, String shortName)
	{
		this.month = month;
		this.longName = longName;
		this.shortName = shortName;
	}

	public int getMonthValue()
	{
		return this.month;
	}

	public String getShortName()
	{
		return this.shortName;
	}

	public String getLongName()
	{
		return this.longName;
	}

	public MonthOfYear next()
	{
		return add(1);
	}

	public MonthOfYear previous()
	{
		return add(-1);
	}

	//
	// comparison
	//

	public int hashCode()
	{
		return this.month;
	}

	public boolean equals(Object other)
	{
		if (this == other)
			return true;

		if (!(other instanceof MonthOfYear))
			return false;

		MonthOfYear otherMonth = (MonthOfYear) other;
		return otherMonth.month == this.month;
	}

	public String toString()
	{
		return "MonthOfYear{" + this.shortName + "}";
	}

	//
	// aritmetics
	//

	public MonthOfYear subtract(int months)
	{
		return add(-months);
	}

	public MonthOfYear add(int months)
	{
		return MonthOfYear.valueOf((months + this.month) % 12);
	}
}