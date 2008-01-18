/**
 * This software is written by arcus(x) GmbH and subject 
 * to a contract between arcus(x) and its customer.
 *
 * This software stays property of arcus(x) unless differing
 * arrangements between arcus(x) and its customer apply.
 *
 * arcus(x) GmbH
 * Hein-Hoyer-Strasse 75
 * D-20359 Hamburg, Germany
 * 
 * Tel.: +49 (0)40.333 102 92  
 * http://www.arcusx.com
 * mailto:info@arcusx.com
 */

package com.arcusx.chrono;

import java.io.Serializable;
import java.util.*;

/**
 * Created on 25.07.2007.
 * 
 * @author chwa
 * @version $Id$
 */
public class Quarter implements Serializable
{
	private static final long serialVersionUID = 1L;

	public static final int FIRST = 0;
	public static final int SECOND = 1;
	public static final int THIRD = 2;
	public static final int FORTH = 3;

	private int year;
	private int quarter;

	public Quarter(int year, int quarter)
	{
		if (quarter < FIRST || quarter > FORTH)
			throw new IllegalArgumentException("Quarter value must be between "
					+ FIRST
					+ " and "
					+ FORTH
					+ ".");
		this.year = year;
		this.quarter = quarter;
	}

	/**
	 * Get the quarter of the current day.
	 * 
	 * @return Instance of current quarter.
	 */
	public static Quarter current()
	{
		Calendar cal = new GregorianCalendar();

		return Quarter.valueOf(cal);
	}

	/**
	 * Get quarter from Calendar.
	 * 
	 * @param cal Calendar to get quarter from.
	 * @return The quarter.
	 */
	public static Quarter valueOf(Calendar cal)
	{
		if (cal == null)
			throw new IllegalArgumentException("Calendar may not be null.");

		return new Quarter(
				cal.get(Calendar.YEAR),
				Quarter.getQuarterValueFor(cal.get(Calendar.MONTH)));
	}

	/**
	 * Get quarter from a date or default value if date is null.
	 * 
	 * @param date Date to get quarter from.
	 * @param defaultValue The default value to use if date is null.
	 * @return Quarter or default value.
	 */
	public static Quarter valueOf(Date date, Quarter defaultValue)
	{
		if (date == null)
			return defaultValue;

		return Quarter.valueOf(date);
	}

	public static Quarter valueOf(Date date)
	{
		if (date == null)
			throw new IllegalArgumentException("Date may not be null.");

		Calendar cal = new GregorianCalendar();
		cal.setTime(date);

		return Quarter.valueOf(cal);
	}

	public static Quarter valueOf(Month month, Quarter defaultValue)
	{
		if (month == null)
			return defaultValue;

		return Quarter.valueOf(month);
	}

	public static Quarter valueOf(Month month)
	{
		if (month == null)
			throw new IllegalArgumentException("Month may not be null.");

		int year = month.getYearValue();
		int quarter = Quarter.getQuarterValueFor(month.getMonthOfYear());
		return new Quarter(year, quarter);
	}

	public static int getQuarterValueFor(MonthOfYear month)
	{
		return getQuarterValueFor(month.getMonthValue());
	}

	public static int getQuarterValueFor(int monthValue)
	{
		return monthValue / 3;
	}

	public Year getYear()
	{
		return new Year(this.year);
	}

	public int getYearValue()
	{
		return this.year;
	}

	//
	// aritmetics
	//

	public Quarter subtract(int quarters)
	{
		return add(-quarters);
	}

	public Quarter add(int quarters)
	{
		Month month = getFirstMonth();
		month = month.add(quarters * 3);

		return Quarter.valueOf(month);
	}

	public MonthSequence toMonths()
	{
		return new MonthSequence(getFirstMonth(), getLastMonth());
	}

	public Month getFirstMonth()
	{
		MonthOfYear monthOfYear = MonthOfYear.valueOf(this.quarter * 3);
		return new Month(this.year, monthOfYear);
	}

	public Month getLastMonth()
	{
		MonthOfYear monthOfYear = MonthOfYear.valueOf(this.quarter * 3 + 2);
		return new Month(this.year, monthOfYear);
	}

	public int getQuarterValue()
	{
		return this.quarter;
	}
}
